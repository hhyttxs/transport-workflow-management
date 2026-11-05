package com.domed.util;

import java.math.BigDecimal;

/**
 * @ClassName UtilCommon
 * @Description TODO
 * @Author 高杨
 * @Date 2019/11/29 15:07
 * @Version 1.0
 **/
public class UtilCommon {

    /**
     *
     *<p>Title: getMultiplyValue</p>
     *<p>Description:两数相乘(保留小数点后2位小数)</p>
     *@date 2012-3-2
     *@param a
     *@param b
     *@return
     */
    public static double getMultiplyValue(double a,double b){
        BigDecimal A = new BigDecimal(Double.toString(a));
        BigDecimal B = new BigDecimal(Double.toString(b));
        double total = (A.multiply(B)).doubleValue();
        BigDecimal value = new BigDecimal(total + 0.00000001);
        value = value.setScale(2, BigDecimal.ROUND_HALF_UP);
        total = value.doubleValue();
        return total;
    }

    /**
     *
     *<p>Title: getMultiplyValueNoScale</p>
     *<p>Description:不保留2位小数</p>
     *@date 2012-8-19
     *@param a
     *@param b
     *@return
     */
    public static double getMultiplyValueNoScale(double a,double b){
        BigDecimal A = new BigDecimal(Double.toString(a));
        BigDecimal B = new BigDecimal(Double.toString(b));
        return(A.multiply(B)).doubleValue();
    }

    /**
     *
     *<p>Title: getScaleValue</p>
     *<p>Description:保留2位小数的方法</p>
     *@date 2012-8-19
     *@param num
     *@return
     */
    public static double getScaleValue(double num){
        BigDecimal value = new BigDecimal(num);
        value = value.setScale(2, BigDecimal.ROUND_HALF_UP);
        return value.doubleValue();
    }

    /**
     *
     *<p>Title: getScaleDeviation</p>
     *<p>Description:浮点数问题</p>
     *@date 2012-8-31
     *@param num
     *@return
     */
    public static double getScaleDeviation(double num){
        BigDecimal value = new BigDecimal(num + 0.00000001);
        value = value.setScale(2, BigDecimal.ROUND_HALF_UP);
        return value.doubleValue();
    }

    /**
     *
     *<p>Title: getScaleTo3</p>
     *<p>Description:小数点保留3位小数</p>
     *@date 2012-10-13
     *@param num
     *@return
     */
    public static double getScaleTo3(double num){
        BigDecimal value = new BigDecimal(num + 0.00000001);
        value = value.setScale(3, BigDecimal.ROUND_HALF_UP);
        return value.doubleValue();
    }

    /**
     *
     *<p>Title: getScaleTo6</p>
     *<p>Description:小数点保留6位小数</p>
     *@date 2012-10-13
     *@param num
     *@return
     */
    public static double getScaleTo6(double num){
        BigDecimal value = new BigDecimal(num + 0.00000001);
        value = value.setScale(6, BigDecimal.ROUND_HALF_UP);
        return value.doubleValue();
    }

    /**
     *
     *<p>Title: getDivisionVaule</p>
     *<p>Description:两数相除</p>
     *@date 2012-6-26
     *@param a
     *@param b
     *@return
     */
    public static double getDivisionVaule(double a, double b){
        if(b == 0)
            return 0;

        BigDecimal bc = new BigDecimal(a / b + 0.00000001);
        return bc.divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * Description:两数相除保留4位
     * @param a
     * @param b
     * @return
     */
    public static double getDivision4Vaule(double a, double b){
        if(b == 0)
            return 0;

        BigDecimal bc = new BigDecimal(a / b + 0.00000001);
        return bc.divide(new BigDecimal(1), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     *
     *<p>Title: getDivisionNoRound</p>
     *<p>Description:两数相除不保留2位小数</p>
     *@date 2012-8-1
     *@param a
     *@param b
     *@return
     */
    public static double getDivisionNoRound(double a, double b){
        BigDecimal bc = new BigDecimal(a / b + 0.00000001);
        return bc.doubleValue();
    }

    /**
     * @Title: 返回空字符串
     * @Description: 
     * @author 高杨 
     * @date 2022/2/19 14:06
     * @return:  
     */
    public static String getString(String value){
        if(value == null){
            return "";
        }
        return value;
    }

    /**
     * @Title: 字符串处理
     * @Description: 
     * @author 高杨 
     * @date 2022/4/4 15:53
     * @return:  
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}
