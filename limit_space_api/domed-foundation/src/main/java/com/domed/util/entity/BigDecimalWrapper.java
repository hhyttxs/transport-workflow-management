package com.domed.util.entity;

import java.math.BigDecimal;

public class BigDecimalWrapper {
    BigDecimal value;

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimalWrapper(BigDecimal value) {
        this.value = value;
    }

    /**
     * 除法
     * @param another
     * @param scale
     * @return
     */
    public BigDecimalWrapper divide(BigDecimal another,Integer scale){
        return new BigDecimalWrapper(value.divide(another,scale,BigDecimal.ROUND_HALF_UP));
    }
    public BigDecimalWrapper divide(BigDecimal another){
        return this.divide(another,2);
    }

    /**
     * 乘法
     * @param another
     * @param scale
     * @return
     */
    public BigDecimalWrapper multiply(BigDecimal another,Integer scale){
        return new BigDecimalWrapper(value.multiply(another).setScale(scale,BigDecimal.ROUND_HALF_UP));
    }
    public BigDecimalWrapper multiply(BigDecimal another){
        return this.multiply(another,2);
    }

    /**
     * 加法
     */
    public BigDecimalWrapper add(BigDecimal another){
        return new BigDecimalWrapper(value.add(another));
    }

    /**
     * 减法法
     */
    public BigDecimalWrapper subtract(BigDecimal another){
        return new BigDecimalWrapper(value.subtract(another));
    }
}
