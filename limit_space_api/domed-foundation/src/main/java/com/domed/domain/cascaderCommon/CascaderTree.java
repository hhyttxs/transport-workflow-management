package com.domed.domain.cascaderCommon;

import lombok.Data;

import java.util.List;

@Data
public class CascaderTree {
    private String value;
    private String label;
    private List<CascaderTree> childList;
}
