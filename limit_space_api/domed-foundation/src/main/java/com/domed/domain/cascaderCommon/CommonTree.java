package com.domed.domain.cascaderCommon;

import lombok.Data;

import java.util.List;

@Data
public class CommonTree {
    private  String value;
    private String parentCode;
    private String label;
    private List<CommonTree> children;
}
