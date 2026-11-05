package com.domed.api.util.entity;

import java.util.HashMap;
import java.util.Map;

public class DataAuthDTO {
    private Boolean seeAll = false;
    private Map dataMap = new HashMap();

    public Boolean getSeeAll() {
        return seeAll;
    }

    public void setSeeAll(Boolean seeAll) {
        this.seeAll = seeAll;
    }

    public Map getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map dataMap) {
        this.dataMap = dataMap;
    }
}
