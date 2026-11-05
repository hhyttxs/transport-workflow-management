package com.domed.api.util;

import org.springframework.util.Base64Utils;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class IDCardUtil {

    /**
     * <pre>
     * 省、直辖市代码表：
     *     11 : 北京  12 : 天津  13 : 河北   14 : 山西  15 : 内蒙古
     *     21 : 辽宁  22 : 吉林  23 : 黑龙江 31 : 上海  32 : 江苏
     *     33 : 浙江  34 : 安徽  35 : 福建   36 : 江西  37 : 山东
     *     41 : 河南  42 : 湖北  43 : 湖南   44 : 广东  45 : 广西  46 : 海南
     *     50 : 重庆  51 : 四川  52 : 贵州   53 : 云南  54 : 西藏
     *     61 : 陕西  62 : 甘肃  63 : 青海   64 : 宁夏  65 : 新疆
     *     71 : 台湾
     *     81 : 香港  82 : 澳门
     *     91 : 国外
     * </pre>
     */
    final static String CITY_CODE[] = {"11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82", "91"};

    /**
     * 效验码
     */
    final static char[] PARITYBIT = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    /**
     * 加权因子
     * Math.pow(2,  i - 1) % 11
     */
    final static int[] POWER = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    /**
     * @description: 根据身份证号获取性别
     * @author: J
     * @date: 2023-3-20 17:55
     */
    public static String judgeGender(String idNumber) throws IllegalArgumentException {
        if (idNumber.length() != 18 && idNumber.length() != 15) {
            throw new IllegalArgumentException("身份证号长度错误");
        }
        int gender = 0;
        if (idNumber.length() == 18) {
            //如果身份证号18位，取身份证号倒数第二位
            char c = idNumber.charAt(idNumber.length() - 2);
            gender = Integer.parseInt(String.valueOf(c));
        } else {
            //如果身份证号15位，取身份证号最后一位
            char c = idNumber.charAt(idNumber.length() - 1);
            gender = Integer.parseInt(String.valueOf(c));
        }
        if (gender % 2 == 1) {
            return "男";
        } else {
            return "女";
        }
    }

    /**
     * @description: 根据身份证号获取年龄
     * @author: J
     * @date: 2023-3-20 17:55
     */
    public static int countAge(String idNumber) {
        if (idNumber.length() != 18 && idNumber.length() != 15) {
            throw new IllegalArgumentException("身份证号长度错误");
        }
        String year;
        String yue;
        String day;
        if (idNumber.length() == 18) {
            year = idNumber.substring(6).substring(0, 4);// 得到年份
            yue = idNumber.substring(10).substring(0, 2);// 得到月份
            day = idNumber.substring(12).substring(0, 2);//得到日
        } else {
            year = "19" + idNumber.substring(6, 8);// 年份
            yue = idNumber.substring(8, 10);// 月份
            day = idNumber.substring(10, 12);//日
        }
        Date date = new Date();// 得到当前的系统时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fyear = format.format(date).substring(0, 4);// 当前年份
        String fyue = format.format(date).substring(5, 7);// 月份
        String fday = format.format(date).substring(8, 10);//
        int age = 0;
        if (Integer.parseInt(yue) == Integer.parseInt(fyue)) {//如果月份相同
            if (Integer.parseInt(day) <= Integer.parseInt(fday)) {//说明已经过了生日或者今天是生日
                age = Integer.parseInt(fyear) - Integer.parseInt(year);
            } else {
                age = Integer.parseInt(fyear) - Integer.parseInt(year) - 1;
            }
        } else {

            if (Integer.parseInt(yue) < Integer.parseInt(fyue)) {
                //如果当前月份大于出生月份
                age = Integer.parseInt(fyear) - Integer.parseInt(year);
            } else {
                //如果当前月份小于出生月份,说明生日还没过
                age = Integer.parseInt(fyear) - Integer.parseInt(year) - 1;
            }
        }
        return age;
    }

    public static String birthByNum(String idNumber) {
        String year;
        String yue;
        String day;
        if (idNumber.length() == 18) {
            year = idNumber.substring(6).substring(0, 4);// 得到年份
            yue = idNumber.substring(10).substring(0, 2);// 得到月份
            day = idNumber.substring(12).substring(0, 2);//得到日
        } else {
            year = "19" + idNumber.substring(6, 8);// 年份
            yue = idNumber.substring(8, 10);// 月份
            day = idNumber.substring(10, 12);//日
        }
        return year + "-" + yue + "-" + day;
    }

    /**
     * 身份证验证
     *
     * @param id 号码内容
     * @return 是否有效
     */
    public final static boolean isValid(String id) {
        if (id == null)
            return false;
        int len = id.length();
        if (len != 15 && len != 18)
            return false;
        //校验区位码
        if (!validCityCode(id.substring(0, 2)))
            return false;
        //校验生日
        if (!validDate(id))
            return false;
        if (len == 15)
            return true;
        //校验位数
        return validParityBit(id);
    }

    private static boolean validCityCode(String cityCode) {
        for (String code : CITY_CODE) {
            if (code.equals(cityCode))
                return true;
        }
        return false;
    }

    private static boolean validDate(String id) {
        try {
            String birth = id.length() == 15 ? "19" + id.substring(6, 12) : id.substring(6, 14);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date birthDate = sdf.parse(birth);
            if (!birth.equals(sdf.format(birthDate)))
                return false;
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private static boolean validParityBit(String id) {
        char[] cs = id.toUpperCase().toCharArray();
        int power = 0;
        for (int i = 0; i < cs.length; i++) {
            //最后一位可以是X
            if (i == cs.length - 1 && cs[i] == 'X')
                break;

            // 非数字
            if (cs[i] < '0' || cs[i] > '9')
                return false;

            // 加权求和
            if (i < cs.length - 1) {
                power += (cs[i] - '0') * POWER[i];
            }
        }
        return PARITYBIT[power % 11] == cs[cs.length - 1];
    }
}
