package com.domed.api.service.hy.impl;


import com.domed.api.service.hy.IClbService;
import com.domed.api.service.hy.IDlhwjyxkService;
import com.domed.common.result.Result;
import com.domed.core.dao.IGenericDAO;
import com.domed.core.query.support.IPageList;
import com.domed.core.service.ICommonService;
import com.domed.dao.hy.YhxxDAO;
import com.domed.domain.hy.Clb;
import com.domed.domain.hy.Dlhwjyxk;
import com.domed.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;
import java.util.ArrayList;

@Service
@Transactional
public class DlhwjyxkServiceImpl implements IDlhwjyxkService {

    @Autowired
    private ICommonService commonService;

    @Resource(name = "yhxxDAO")
    private IGenericDAO<Dlhwjyxk> yhxxDAO;

    @Override
    public Result saveOrUpdateDlhwjyxk(Map infoMap) {
        String projectName = commonService.getParam(infoMap, "projectName");
        String businessType = commonService.getParam(infoMap, "businessType");
        String operator = commonService.getParam(infoMap, "operator");
        String enterpriseAddress = commonService.getParam(infoMap, "enterpriseAddress");
        String licenseIssueDate = commonService.getParam(infoMap, "licenseIssueDate");
        String socialCreditCode = commonService.getParam(infoMap, "socialCreditCode");
        String legalPersonName = commonService.getParam(infoMap, "legalPersonName");
        String legalPersonIdNo = commonService.getParam(infoMap, "legalPersonIdNo");
        String legalPersonPhone = commonService.getParam(infoMap, "legalPersonPhone");
        String economicType = commonService.getParam(infoMap, "economicType");
        String transportTypes = commonService.getParam(infoMap, "transportTypes");
        String status = commonService.getParam(infoMap, "status");



        Dlhwjyxk dlhwjyxk = new Dlhwjyxk();
        dlhwjyxk.setGid(UUID.randomUUID().toString());
        dlhwjyxk.setSxmc(projectName);
        dlhwjyxk.setYwlx(businessType);
        dlhwjyxk.setJbr(operator);
        dlhwjyxk.setQydz(enterpriseAddress);
        dlhwjyxk.setTyshxydm(socialCreditCode);
        dlhwjyxk.setFrxm(legalPersonName);
        dlhwjyxk.setFrsfzh(legalPersonIdNo);
        dlhwjyxk.setFrlxdh(legalPersonPhone);
        dlhwjyxk.setJJLX(economicType);
        dlhwjyxk.setYsfs(transportTypes);
        dlhwjyxk.setStatus(status);



        if(StringUtils.isNotEmpty(licenseIssueDate)) {
            try {
                dlhwjyxk.setYyzzfzrq(CommonUtil.getDateFormatFromDatePicker(licenseIssueDate).getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }



        yhxxDAO.save(dlhwjyxk);
        return null;

    }

    @Override
    public Result queryDraftList(Map infoMap) {

        String sfzh = commonService.getParam(infoMap, "sfzh");
        String name = commonService.getParam(infoMap, "name");
        String status = commonService.getParam(infoMap, "status");
        String currentPage = commonService.notNull(infoMap, "currentPage","currentPage");
        String numberPage = commonService.notNull(infoMap, "numberPage","numberPage");

        System.out.println("身份证号：" + sfzh);
        System.out.println("姓名：" + name);
        System.out.println("状态：" + status);
        System.out.println("当前页：" + currentPage);
        System.out.println("每页数量：" + numberPage);

        Map<String, Object> resultMap = new HashMap<>();

        StringBuffer sb = new StringBuffer();
        sb.append(
                " SELECT " +
                        " GID AS \"gid\", " +
                        " SXMC AS \"sxmc\", " +
                        " YWLX AS \"ywlx\", " +
                        " JBR AS \"jbr\", " +
                        " TYSHXYDM AS \"tyshxydm\" " +
                        " FROM DLHWJYXK WHERE 1 = 1 "
        );
        if (StringUtils.isNotEmpty(status)){
            sb.append(" AND STATUS = '" + status + "'");
        }

        IPageList iPageList = commonService.pageQuery(sb.toString(), currentPage, numberPage);

        resultMap.put("data", iPageList);
        resultMap.put("pageCount", iPageList.getRowCount());
        resultMap.put("totalPage", iPageList.getRowCount());

        return Result.success(resultMap);
    }

    @Override
    public Result getDlhwjyxkInfoByGid(Map infoMap) {
        String gid = commonService.notNull(infoMap, "gid", "gid");
        Dlhwjyxk dlhwjyxk = yhxxDAO.get(gid);
        return Result.success(dlhwjyxk);
    }


}
