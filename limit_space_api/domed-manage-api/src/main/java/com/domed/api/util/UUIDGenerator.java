package com.domed.api.util;

import java.util.UUID;

public class UUIDGenerator {

	public static String getUUID(){
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }

    public static String getBigUUID(){
        String s = UUID.randomUUID().toString();
        s = s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
        s = s.toUpperCase();
        return s;
    }

    public static String getNewUUID(){
        String s = UUID.randomUUID().toString();
        return s.toUpperCase();
    }

    /** 
     * 获得指定数目的UUID 
     * @param number int 需要获得的UUID数量 
     * @return String[] UUID数组 
     */ 
    public static String[] getUUID(int number){ 
        if(number < 1){ 
            return null; 
        } 
        String[] ss = new String[number]; 
        for(int i=0;i<number;i++){ 
            ss[i] = getUUID(); 
        } 
        return ss; 
    } 
	
	
}
