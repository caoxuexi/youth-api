package com.cao.youth.logic;

import com.cao.youth.bo.SkuOrderBO;
import com.cao.youth.dto.OrderDTO;
import com.cao.youth.dto.SkuInfoDTO;
import com.cao.youth.exception.http.ParameterException;
import com.cao.youth.model.OrderSku;
import com.cao.youth.model.Sku;
import lombok.Getter;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 曹学习
 * @description OrderChecker
 * @date 2020/8/31 12:33
 */
public class OrderChecker {
    private OrderDTO orderDTO;
    private List<Sku> serverSkuList;
    private CouponChecker couponChecker;
    private Integer maxSkuLimit;
    @Getter
    private List<OrderSku> orderSkuList=new ArrayList<>();

    public OrderChecker(OrderDTO orderDTO, List<Sku> serverSkuList,
                        CouponChecker couponChecker,Integer maxSkuLimit){
        this.orderDTO=orderDTO;
        this.couponChecker=couponChecker;
        this.serverSkuList=serverSkuList;
        this.maxSkuLimit=maxSkuLimit;
    }

    public void isOK(){
        BigDecimal serverTotalPrice=new BigDecimal("0");
        List<SkuOrderBO> skuOrderBOList=new ArrayList<>();
        //校验的是orderTotalPrice和serverTotalPrice
        //下架和售罄 超出库存检测
        // 商品是不是有限量购买
        //优惠券 CouponChecker
        this.skuNotOnSale(orderDTO.getSkuInfoList().size(),this.serverSkuList.size());
        for(int i=0;i<this.serverSkuList.size();i++){
            Sku sku =this.serverSkuList.get(i);
            SkuInfoDTO skuInfoDTO=this.orderDTO.getSkuInfoList().get(i);
            this.containsSoldOutSku(sku);
            this.beyondSkuStock(sku,skuInfoDTO);
            this.beyondMaxSkuLimit(skuInfoDTO);

            serverTotalPrice=serverTotalPrice.add(this.calculateSkuOrderPrice(sku,skuInfoDTO));
            skuOrderBOList.add(new SkuOrderBO(sku,skuInfoDTO));
            this.orderSkuList.add(new OrderSku(sku,skuInfoDTO));
        }
        this.totalPriceIsOK(orderDTO.getTotalPrice(),serverTotalPrice);
        if(this.couponChecker!=null){
            this.couponChecker.isOK();
            this.couponChecker.canBeUsed(skuOrderBOList,serverTotalPrice);
            this.couponChecker.finalTotalPriceIsOk(orderDTO.getFinalTotalPrice(),serverTotalPrice);
        }
    }

    public String getLeaderImg(){
        return this.serverSkuList.get(0).getImg();
    }

    public String getLeaderTitle(){
        return this.serverSkuList.get(0).getTitle();
    }

    public Integer getTotalCount(){
        return this.orderDTO.getSkuInfoList()
                .stream()
                .map(SkuInfoDTO::getCount)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private void totalPriceIsOK(BigDecimal orderTotalPrice,BigDecimal serverTotalPrice){
        if(orderTotalPrice.compareTo(serverTotalPrice)!=0){
            throw new ParameterException(50005);
        }
    }


    private BigDecimal calculateSkuOrderPrice(Sku sku,SkuInfoDTO skuInfoDTO){
        //不可能买小于0件或者0件
        if(skuInfoDTO.getCount()<=0){
            throw new ParameterException(50007);
        }
        return sku.getActualPrice().multiply(new BigDecimal(skuInfoDTO.getCount()));
    }

    private void skuNotOnSale(int count1,int count2){
        if(count1!=count2){
            throw new ParameterException(50002);
        }
    }

    private void containsSoldOutSku(Sku sku){
        if(sku.getStock()==0){
            throw new ParameterException(50001);
        }
    }

    private void beyondSkuStock(Sku sku,SkuInfoDTO skuInfoDTO){
        if(sku.getStock()< skuInfoDTO.getCount()){
            throw new ParameterException(50003);
        }
    }

    private void beyondMaxSkuLimit(SkuInfoDTO skuInfoDTO){
        if(skuInfoDTO.getCount()>this.maxSkuLimit){
            throw new ParameterException(50004);
        }
    }
}
