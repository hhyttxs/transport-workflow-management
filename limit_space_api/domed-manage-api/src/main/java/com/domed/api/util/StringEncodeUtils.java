package com.domed.api.util;

import java.io.UnsupportedEncodingException;

public class StringEncodeUtils {
    public static String strToUtf8(String str){
        if(str != null){
            String nowEncode = getEncoding(str);
            if(!"UTF-8".equals(nowEncode)){
                /*System.out.println("转变前:"+str);
                //用默认字符编码解码字符串。
                byte[] bs = str.getBytes();
                //用新的字符编码生成字符串
                str = convertEncodingFormat(str, nowEncode,"UTF-8");
                System.out.println("转变后:"+str);
                System.out.println(str);*/
                return "a";
            }
        }
        return str;
    }
    public static String getEncoding(String str){
        String encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是UTF-8
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GB2312";

        try {
            if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是GB2312
                String s = encode;
                return s; //是的话，返回“GB2312“，以下代码同理
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是ISO-8859-1
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }

        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是GBK
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return ""; //如果都不是，说明输入的内容不属于常见的编码格式。
    }
    public static String convertEncodingFormat(String str, String formatFrom, String FormatTo) {
        String result = null;
        if (!(str == null || str.length() == 0)) {
            try {
                result = new String(str.getBytes(formatFrom), FormatTo);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
