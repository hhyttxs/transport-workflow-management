package com.domed.api.util.entity;

import java.util.HashSet;
import java.util.Set;

public class DataAuthLevelDepartmentDTO {
    Boolean seeAll = false;
    Set departMentSet = new HashSet();

    public Boolean getSeeAll() {
        return seeAll;
    }

    public void setSeeAll(Boolean seeAll) {
        this.seeAll = seeAll;
    }

    public Set getDepartMentSet() {
        return departMentSet;
    }

    public void setDepartMentSet(Set departMentSet) {
        this.departMentSet = departMentSet;
    }
}
