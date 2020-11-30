package com.cao.youth.logic;

import com.cao.youth.bo.SkuOrderBO;
import com.cao.youth.core.enumeration.CouponType;
import com.cao.youth.core.money.IMoneyDiscount;
import com.cao.youth.exception.http.ForbiddenException;
import com.cao.youth.exception.http.ParameterException;
import com.cao.youth.model.Category;
import com.cao.youth.model.Coupon;
import com.cao.youth.util.CommonUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 曹学习
 * @description CouponChecker
 * @date 2020/8/31 12:33
 */
public class CouponChecker {
    private Coupon coupon;
    private IMoneyDiscount iMoneyDiscount;

    public CouponChecker(Coupon coupon,IMoneyDiscount iMoneyDiscount){
        this.iMoneyDiscount=iMoneyDiscount;
        this.coupon=coupon;
    }

    /**
     * 校验是否过期
     */
    public void isOK(){
        Date now=new Date();
        Boolean isInTimeline= CommonUtil.isInTimeLine(now,this.coupon.getStartTime(),this.coupon.getEndTime());
        if(!isInTimeline){
            throw new ForbiddenException(40007);
        }
    }

    /**
     * 检测最终价格是不是正确的
     */
    public void finalTotalPriceIsOk(BigDecimal orderFinalTotalPrice,
                                    BigDecimal serverTotalPrice){
        BigDecimal serverFinalTotalPrice;
        switch(CouponType.toType(this.coupon.getType())){
            case FULL_MINUS: //逻辑和下面的一样，可以合并分支
            case NO_THRESHOLD_MINUS:
                serverFinalTotalPrice=serverTotalPrice.subtract(this.coupon.getMinus());
                //价格不能小于0
                if(serverFinalTotalPrice.compareTo(new BigDecimal("0"))<0){
                    throw new ForbiddenException(50000);
                }
                break;
            case FULL_OFF:
                //浮点数运算
                serverFinalTotalPrice=this.iMoneyDiscount.discount(serverTotalPrice,this.coupon.getRate());
                break;
            default:
                throw new ParameterException(40009);
        }
        int compare=serverFinalTotalPrice.compareTo(orderFinalTotalPrice);
        if(compare!=0){
            throw new ForbiddenException(50008);
        }
    }

    /**
     * 检测优惠券是否满足使用条件
     */
    public void canBeUsed(List<SkuOrderBO> skuOrderBOList,BigDecimal serverTotalPrice){
        // sku price 从sku_model里面获取
        // sku count  从order里面获取
        // sku category  从sku_model里面获取
        // coupon category
        //当前订单下所有属于优惠券分类的商品的总价格
        BigDecimal orderCategoryPrice;
        //如果是全场券，那么就是总价
        if(this.coupon.getWholeStore()){
            orderCategoryPrice=serverTotalPrice;
        }else{
            List<Long> cidList=this.coupon.getCategoryList()
                    .stream()
                    .map(Category::getId)
                    .collect(Collectors.toList());
            orderCategoryPrice=this.getSumByCategoryList(skuOrderBOList,cidList);

        }
        this.couponCanBeUsed(orderCategoryPrice);
    }

    private void couponCanBeUsed(BigDecimal orderCategoryPrice){
        switch (CouponType.toType(this.coupon.getType())){
            case FULL_OFF:
            case FULL_MINUS:
                int compare=this.coupon.getFullMoney().compareTo(orderCategoryPrice);
                if(compare>0){
                    throw new ParameterException(40008);
                }
                break;
            case NO_THRESHOLD_MINUS:
                break;
            default:
                throw new ParameterException(40009);
        }

    }

    private BigDecimal getSumByCategoryList(List<SkuOrderBO> skuOrderBOList,
                                            List<Long> cids){
        BigDecimal sum=cids.stream()
                .map(cid->this.getSumByCategory(skuOrderBOList,cid))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
        return sum;
    }

    private BigDecimal getSumByCategory(List<SkuOrderBO> skuOrderBOList,Long cid){
        BigDecimal sum=skuOrderBOList.stream()
                .filter(skuOrderBO -> skuOrderBO.getCategoryId().equals(cid))
                .map(SkuOrderBO::getTotalPrice) //获得一个sku总价的列表
                .reduce(((bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2)))
                .orElse(new BigDecimal("0"));
        return sum;
    }

}
