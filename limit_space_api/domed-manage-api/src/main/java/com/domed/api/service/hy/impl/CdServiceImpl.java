package com.domed.api.service.hy.impl;


import com.domed.api.service.hy.ICdService;
import com.domed.api.service.hy.IClbService;
import com.domed.common.result.Result;
import com.domed.core.dao.IGenericDAO;
import com.domed.core.query.support.IPageList;
import com.domed.core.service.ICommonService;
import com.domed.domain.hy.Clb;
import com.domed.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CdServiceImpl implements ICdService {

    @Resource
    private ICommonService commonService;

    @Override
    public Result getTreeMenuList(Map infoMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT URL,\"LEVEL\",ORDERID,CODE,NAME AS \"title\",PARENT_CODE FROM TAB_MENU WHERE STATUS = '0' ORDER BY TO_NUMBER(ORDERID)");
        List<Map<String, Object>> list = commonService.nativeQuery(sb.toString());
        List<Map<String, Object>> topList = getTopTitle(list);
        List<Map<String, Object>> treeList = forTreeList(topList, list);

        return Result.success(treeList);
    }

    private List<Map<String, Object>> getTopTitle(List<Map<String, Object>> list) {
        return list.stream()
                .filter(map -> map.get("PARENT_CODE") == null || map.get("PARENT_CODE").toString().isEmpty())
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> forTreeList(List<Map<String, Object>> topList, List<Map<String, Object>> list) {
        for (Map<String, Object> map : topList) {
            String parentCode = String.valueOf(map.get("CODE"));
            List<Map<String, Object>> children = list.stream()
                    .filter(map1 -> map1.get("PARENT_CODE") != null)
                    .filter(map1 -> parentCode.equals(String.valueOf(map1.get("PARENT_CODE"))))
                    .collect(Collectors.toList());
            map.put("children", children);
        }
        return topList;
    }
}