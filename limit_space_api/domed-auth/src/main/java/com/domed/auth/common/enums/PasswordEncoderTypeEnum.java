//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.domed.auth.common.enums;

public enum PasswordEncoderTypeEnum {
    BCRYPT("{bcrypt}", "BCRYPT加密"),
    NOOP("{noop}", "无加密明文"),
    MD5("{MD5}", "MD5加密");

    private String prefix;

    private PasswordEncoderTypeEnum(String prefix, String desc) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return this.prefix;
    }
}
