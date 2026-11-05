package com.domed.api.service.transportService.roadTransportFirstService;

import com.domed.common.result.Result;

import java.util.Map;

public interface IRoadTransportFirstIssueService {

    /**
     * 首次核发新增
     *
     * @param infoMap
     * @return
     */
    Result saveOrUpdateRoadFirstIssue(Map infoMap);

    /**
     * 首次核发新增(临时表)
     *
     * @param infoMap
     * @return
     */
    Result saveOrUpdateRoadFirstIssueTemp(Map infoMap);

    Result holdRoadFirstIssueTemp(Map infoMap);

    /**
     * 修改车辆首次核发申报数据
     *
     * @param infoMap
     * @return
     */
    Result updateRoadFirstIssueTemp(Map infoMap);

    //车辆首次核发补正签发暂存
    Result holdOffSetRoadFirstIssueTemp(Map infoMap);

    /**
     * 首次核发回显
     *
     * @param infoMap
     * @return
     */
    Result browseRoadFirstIssue(Map infoMap);

    /**
     * 预受理页面列表
     *
     * @param infoMap
     * @return
     */
    Result browseCarForecastAcceptList(Map infoMap);

    /**
     * 进度查看页面
     *
     * @param infoMap
     * @return
     */
    Result roadTransportCardFirstIssueTemp(Map infoMap);

    /**
     * 受理页面列表页数据查询
     *
     * @param infoMap
     * @return
     */
    Result browseCarAcceptList(Map infoMap);

    /**
     * 审核页面列表页数据查询
     *
     * @param infoMap
     * @return
     */
    Result browseCarAuditList(Map infoMap);

    /**
     * 预审批页面列表页数据查询
     *
     * @param infoMap
     * @return
     */
    Result browseCarForecastExamineList(Map infoMap);

    /**
     * 查询业户许可证号
     *
     * @param infoMap
     * @return
     */
    Result browseUserMessage(Map infoMap);

    /**
     * 车型信息
     *
     * @param infoMap
     * @return
     */
    Result browseCxMessage(Map infoMap);

    /**
     * 预受理操作
     *
     * @param infoMap
     * @return
     */
    Result updateCarForecastAccept(Map infoMap);

    /**
     * 受理操作
     *
     * @param infoMap
     * @return
     */
    Result updateCarAccept(Map infoMap);

    Result updateCarExamine(Map infoMap);

    /**
     * 审核更改
     *
     * @param infoMap
     * @return
     */
    Result updateCarAudit(Map infoMap);

    /**
     * 预审批更改
     *
     * @param infoMap
     * @return
     */
    Result updateCarForecastExamine(Map infoMap);

    /**
     * 通用回显
     *
     * @param infoMap
     * @return
     */
    Result browseRoadGeneral(Map infoMap);

    /**
     * 临时表通用回显
     *
     * @param infoMap
     * @return
     */
    Result browseRoadGeneralTemp(Map infoMap);

    Result browseRoadGeneralTempOld(Map infoMap);

    /**
     * @description: 获取车辆类型下拉数据
     * @author: J
     * @date: 2023-2-18 11:31
     */
    Result initCarType(Map infoMap);

    Result queryYhxxByTyshxydm(Map infoMap);

    Result queryAllVinCode(Map infoMap);

    /**
     * @description: 批量提交车辆
     * @author: J
     * @date: 2023-5-29 15:12
     */
    Result batchSubmitCar(Map infoMap);

    Result queryCphAndVinCode(Map infoMap);

    Map<String, String> initRoadTransportCard(String xzqh);

    Result getAcceptCarByLetterGid(Map infoMap);

    Result getAcceptCarByClbm(Map infoMap);

    Result holdCarOffectNoticeInfo(Map infoMap);

    Result updateCardAndSend(Map infoMap);

    /**
     * @description: 获取车辆原材料
     * @author: J
     * @date: 2023-12-25 10:38
     */
    Result getOldMaterial(Map infoMap);

    Map<String,Object> getYhxxAccept(Map infoMap);

    Result getXkzYxq(Map infoMap);

    Result queryBusinessUndone(Map infoMap);

    Result queryNsyxq(Map infoMap);

    Result queryWfcx(Map infoMap);

    Result queryWfcxClb(Map infoMap);

    Result queryCarLastZjrq(Map infoMap);

//    Result querySyrqError() throws Exception;
}
