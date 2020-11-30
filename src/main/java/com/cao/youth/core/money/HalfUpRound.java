package com.cao.youth.core.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author 曹学习
 * @description HalfUpRound
 * @date 2020/8/31 18:42
 */
public class HalfUpRound implements IMoneyDiscount {
    @Override
    public BigDecimal discount(BigDecimal original, BigDecimal discount) {
        BigDecimal actualMoney = original.multiply(discount);
        BigDecimal finalMoney = actualMoney.setScale(2, RoundingMode.HALF_UP);
        return finalMoney;
    }
}
