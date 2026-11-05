package com.domed.api.service.hy.impl;


import com.domed.api.service.hy.IClbService;
import com.domed.common.result.Result;
import com.domed.core.dao.IGenericDAO;
import com.domed.core.query.support.IPageList;
import com.domed.core.service.ICommonService;
import com.domed.domain.hy.Clb;
import com.domed.domain.hy.Dlhwjyxk;
import com.domed.util.CommonUtil;
import com.domed.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClbServiceImpl implements IClbService {

    @Autowired
    private ICommonService commonService;

    @Resource(name = "clbDAO")
    private IGenericDAO<Clb> clbDAO;

    @Override
    public Result saveOrUpdateClb(Map infoMap) {
        String plateNumber = commonService.getParam(infoMap, "plateNumber");
        String businessType = commonService.getParam(infoMap, "businessType");
        String handler = commonService.getParam(infoMap, "handler");
        String businessScope = commonService.getParam(infoMap, "businessScope");
        String vehicleSource = commonService.getParam(infoMap, "vehicleSource");
        String vin = commonService.getParam(infoMap, "vin");
        String vehicleType = commonService.getParam(infoMap, "vehicleType");
        String totalMass = commonService.getParam(infoMap, "totalMass");
        String approvedLoad = commonService.getParam(infoMap, "approvedLoad");
        String brand = commonService.getParam(infoMap, "brand");
        String vehicleModel = commonService.getParam(infoMap, "vehicleModel");
        String registerDate = commonService.getParam(infoMap, "registerDate");
        String scrapDate = commonService.getParam(infoMap, "scrapDate");
        String vehicleLength = commonService.getParam(infoMap, "vehicleLength");
        String vehicleWidth = commonService.getParam(infoMap, "vehicleWidth");
        String vehicleHeight = commonService.getParam(infoMap, "vehicleHeight");
        String fuelType = commonService.getParam(infoMap, "fuelType");
        String technicalLevel = commonService.getParam(infoMap, "technicalLevel");
        String engineNumber = commonService.getParam(infoMap, "engineNumber");
        String axleCount = commonService.getParam(infoMap, "axleCount");
        String productionDate = commonService.getParam(infoMap, "productionDate");
        String purchaseDate = commonService.getParam(infoMap, "purchaseDate");
        String enginePower = commonService.getParam(infoMap, "enginePower");
        String plateColor = commonService.getParam(infoMap, "plateColor");
        String emissionStandard = commonService.getParam(infoMap, "emissionStandard");
        String status = commonService.getParam(infoMap, "status");

        Clb clb = new Clb();

        clb.setGid(UUID.randomUUID().toString());
        clb.setAddtime(new Date());
        clb.setStatus("0");
        clb.setYwlx(businessType);
        clb.setJbr(handler);
        clb.setJyfw(businessScope);
        clb.setClly(vehicleSource);
        clb.setClsbdm(vin);
        clb.setCllx(vehicleType);
        clb.setXszh(plateNumber);
        clb.setZzl(totalMass);
        clb.setHdzzl(approvedLoad);
        clb.setClcp(brand);
        clb.setClxh(vehicleModel);
        if (StringUtils.isNotEmpty(registerDate)) {
            try {
                clb.setZcrq(CommonUtil.getDateFormatFromDatePicker(registerDate).getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isNotEmpty(scrapDate)) {
            try {
                clb.setBfrq(CommonUtil.getDateFormatFromDatePicker(scrapDate).getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        clb.setClcd(vehicleLength);
        clb.setClkd(vehicleWidth);
        clb.setClgd(vehicleHeight);
        clb.setRllx(fuelType);
        clb.setJsdj(engineNumber);
        clb.setFdjh(engineNumber);
        clb.setZs(axleCount);
        if (StringUtils.isNotEmpty(productionDate)) {
            try {
                clb.setCcrq(CommonUtil.getDateFormatFromDatePicker(productionDate).getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isNotEmpty(purchaseDate)) {
            try {
                clb.setClgmrq(CommonUtil.getDateFormatFromDatePicker(purchaseDate).getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        clb.setPower(enginePower);
        clb.setCpys(plateColor);
        clb.setPfbz(emissionStandard);


        clbDAO.save(clb);
        return null;
    }
    @Override
    public Result queryDraftList(Map infoMap) {
        return queryClbList(infoMap);
    }

    @Override
    public Result getClbInfoByGid(Map infoMap) {
        String gid = commonService.notNull(infoMap, "gid", "gid");
        Clb clb = clbDAO.get(gid);
        return Result.success(clb);
    }

    @Override
    public Result queryClbList(Map infoMap) {
        String currentPage = this.commonService.notNull(infoMap, "currentPage", "当前页");
        String numberPage = this.commonService.notNull(infoMap, "numberPage", "每页条数");
        String plateNumber = commonService.getParam(infoMap, "plateNumber");
        String vin = commonService.getParam(infoMap,"vin" );
        String status = commonService.getParam(infoMap, "status");

        Map<String, Object> resultMap = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append(" GID AS \"gid\", ");
        sb.append(" CLLX AS \"vehicleType\", ");
        sb.append(" XSZH AS \"plateNumber\", ");
        sb.append(" CLCP AS \"brand\", ");
        sb.append(" CLSBDM AS \"vin\", ");
        sb.append(" STATUS AS \"status\", ");
        sb.append(" ADDTIME AS \"addtime\" ");
        sb.append(" FROM CLB ");

        if (StringUtils.isNotEmpty(plateNumber)) {
            sb.append(" AND XSZH LIKE '%" + plateNumber +"%'");
        }
        if (StringUtils.isNotEmpty(vin)) {
            sb.append(" AND CLSBDM LIKE '%" + vin +"%'");
        }

        sb.append(" ORDER BY ADDTIME DESC ");

        IPageList iPageList = commonService.pageQuery(sb.toString(), currentPage, numberPage);
        resultMap.put("data", iPageList);
        resultMap.put("pageCount", iPageList.getRowCount());
        resultMap.put("totalPage", iPageList.getRowCount());

        return Result.success(resultMap);
    }

}
