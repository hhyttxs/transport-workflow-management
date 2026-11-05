package com.domed.api.util.ManageAreaUtil;

import com.alibaba.nacos.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManageAreaUtil {

    public static String getJyfwChar(List<Map<String, String>> allList, String jyfw) {
//        String wxplx = "";
        List jjfwArr = Arrays.asList(jyfw.split(","));
        List<String> jyfwKeys = new ArrayList();
        jyfwKeys.add("jyfw_fsx");
        jyfwKeys.add("jyfw_fsx_p");
        jyfwKeys.add("jyfw_wx");
        jyfwKeys.add("jyfw_wx_p");
        jyfwKeys.add("jyfw_pt");
//        List<Map<String,String>> filterList =  allList.stream().filter(s -> !jyfwKeys.contains(s.get("key"))).collect(Collectors.toList());
//        for (Map m : filterList) {
//            List<Map> childList = allList.stream().filter(s -> m.get("id").equals(s.get("pid"))).collect(Collectors.toList());
//            String cStr = "";
//            Boolean isHave = false;
//            Boolean isAllHave = true;
//            if (childList != null && childList.size() > 1) {
//                for (Map ch : childList) {
//                    if (jjfwArr.contains(ch.get("key"))) {
//                        cStr = cStr + ch.get("value") + ",";
//                        isHave = true;
//                    }
//                }
//                if (cStr.length()>1)cStr = cStr.substring(0,cStr.length()-1);
//                 if (isHave) {
//                     if(StringUtils.isNotEmpty(wxplx)){
//                         wxplx = wxplx+","+m.get("value")+ "(" + cStr + ")";
//                     }else{
//                         wxplx = wxplx+m.get("value")+ "(" + cStr + ")";
//                     }
//                }
//            } else {
//                List<Map> pCheck = allList.stream().filter(s -> m.get("pid").equals(s.get("id"))).collect(Collectors.toList());
//                String key = pCheck.get(0).get("key").toString();
//                if(jyfwKeys.contains(key)){
//                    if (jjfwArr.contains(m.get("key"))) {
//                        if(StringUtils.isNotEmpty(wxplx)){
//                            wxplx = wxplx + "," + m.get("value");
//                        }else{
//                            wxplx = wxplx + m.get("value");
//                        }
//                    }
//                }
//            }
//        }
//        if (",".equals(wxplx.charAt(wxplx.length() - 1))) {
//            return wxplx.substring(0, wxplx.length() - 1);
//        }
        return "wxplx";
    }


}
