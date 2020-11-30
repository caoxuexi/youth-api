package com.cao.youth.core.money;

import java.math.BigDecimal;

/**
 * @author 曹学习
 * @description IMoneyDiscount
 * @date 2020/8/31 18:41
 */
public interface IMoneyDiscount {
    BigDecimal discount(BigDecimal original, BigDecimal discount);
}
