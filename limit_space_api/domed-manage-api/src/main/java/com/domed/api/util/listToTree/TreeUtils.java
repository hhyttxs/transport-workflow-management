package com.domed.api.util.listToTree;

import com.alibaba.nacos.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeUtils {
    public static List<Map> buildTree1(List<Map> zoneList) {
        List<Map> result = new ArrayList<>();
        for (Map zone : zoneList) {
            if (zone.get("pid") == null || StringUtils.isEmpty(zone.get("pid").toString())) {
                result.add(zone);
                setChildren(zoneList, zone);
            }
        }
        return result;
    }

    public static void setChildren(List<Map> list, Map parent) {
        for (Map zone : list) {
            if (parent.get("code").equals(zone.get("pid"))) {
                ArrayList<Object> children = null;
                if (parent.get("children") == null) {
                    children = new ArrayList<>();
                } else {
                    children = (ArrayList<Object>) parent.get("children");
                }
                children.add(zone);
                parent.put("children", children);
            }
        }
        if (parent.get("children") == null) {
            return;
        }
        for (Map zone : (List<Map>) parent.get("children")) {
            setChildren(list, zone);
        }
    }


}
