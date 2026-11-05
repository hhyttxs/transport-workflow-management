package com.domed.api.controller.commonController;

import com.alibaba.fastjson.JSONObject;
import com.domed.api.service.commonService.ICommonDepartmentService;
import com.domed.api.service.commonService.IManageCommonService;
import com.domed.common.json.JsonUtil;
import com.domed.common.result.Result;
import com.domed.core.exception.ApplicationException;
import com.domed.core.exception.LogUtil;
import com.domed.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RefreshScope
@RestController
@CrossOrigin
@RequestMapping( value = "/manageCommon" )
public class ManageCommonController {
    @Autowired
    private IManageCommonService manageCommonService;

    @Autowired
    private ICommonDepartmentService commonDepartmentService;

    /**
     *
     * @Title: queryBaseData
     * @Description: 查询基础数据
     * @param
     * @return:
     * @author: shanyuwei
     * Create Date:  2019/12/25
     * @throws
     */
    @RequestMapping( value = "/queryBaseData.json", method = {RequestMethod.POST}, produces = "application/json" )
    public Result<Map> afterRentContList(HttpServletRequest request, HttpServletResponse response, @RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> manageCommonService.queryBaseData(infoMap));
    }

    /**
     *
     * @Title: queryEditHistory
     * @Description: 查询修改历史
     * @param
     * @return:
     * @author: shanyuwei
     * Create Date:  2019/12/25
     * @throws
     */
    @RequestMapping( value = "/queryEditHistory.json", method = {RequestMethod.POST}, produces = "application/json" )
    public Result<Map> queryEditHistory(HttpServletRequest request, HttpServletResponse response, @RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> manageCommonService.queryEditHistory(infoMap));
    }

    /**
     * @Description: 查询员工列表
    　* @param
    　* @return
    　* @throws
    　* @author syl
    　* @date 2019/12/25 19:20
    　*/
    @RequestMapping( value = "queryStaffList.json", method = {RequestMethod.POST}, produces = "application/json" )
    public Result<Map> queryStaffList(HttpServletRequest request, HttpServletResponse response, @RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> manageCommonService.queryStaffList(infoMap));
    }

    /**
     * @Description: 部门管理树状图初始化
    　* @param
    　* @return
    　* @throws
    　* @author syl
    　* @date 2019/12/25 13:37
    　*/
    @RequestMapping( value = "/queryDepartmentTreeList.json", method = {RequestMethod.POST}, produces = "application/json" )
    public Result<Map> queryDepartmentTreeList(HttpServletRequest request, HttpServletResponse response, @RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> commonDepartmentService.departmentLevelList());
    }


    /**
     * @title 部门检索(带权限)
     * @description 部门检索接口,带权限过滤
     * @author wangqianhe
     * @date 2021/06/23 15:23
     * @method post
     * @param infoMap 必选 {function_id:"391",staff_id:"31"}
     * @return {"code":0,"data":{"list":[{"departmentName":"大连海米","id":52,"level":"1","levelTop":"ROOT"},...]},"msg":"操作成功"}
     */
    @RequestMapping( value = "queryAuthLevelDepartment.json", method = {RequestMethod.POST}, produces = "application/json" )
    public Result<Map> queryAuthLevelDepartment(HttpServletRequest request, HttpServletResponse response, @RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> commonDepartmentService.queryAuthLevelDepartment(infoMap));
    }
    /***
     * 查询公司
     * @param request
     * @param response
     */
    @RequestMapping( value = "/queryCompanyDepartmet.json", method = {RequestMethod.POST}, produces = "application/json" )
    public Result<Map> queryCompanyDepartmet(HttpServletRequest request, HttpServletResponse response) {
        return CommonUtil.controllerResult(() -> commonDepartmentService.companyDepartmetList());
    }


    /**
     * @Description: 保存图片  file
    　* @param
    　* @return
    　* @throws
    　* @author syl
    　* @date 2019/12/25 13:37
    　*/
    @RequestMapping( value = "/saveFileToOss.json", method = {RequestMethod.POST}, produces = "application/json" )
    public Result<Map> saveFileToOss(HttpServletRequest request, HttpServletResponse response, @RequestBody MultipartFile multipartFile, @RequestBody String saveFilePathName) {
        return CommonUtil.controllerResult(() -> manageCommonService.saveFileToOss(multipartFile,saveFilePathName));
    }

    /**
     * @Description: 保存图片  base64
    　* @param
    　* @return
    　* @throws
    　* @author syl
    　* @date 2019/12/25 13:37
    　*/
    @RequestMapping( value = "/saveStringToOss.json", method = {RequestMethod.POST}, produces = "application/json" )
    public Result<Map> saveStringToOss(HttpServletRequest request, HttpServletResponse response, @RequestBody String img, @RequestBody String saveFilePathName) {
        return CommonUtil.controllerResult(() -> manageCommonService.saveStringToOss(img,saveFilePathName));
    }

    /**
     * @Title: 保存文件到本地
     * @Description:
     * @author 高杨
     * @date 2022/4/25 18:25
     * @return:
     */
    @RequestMapping(value = "saveFileToLocal.json")
    public Result<Map>  saveFileToLocal(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return CommonUtil.controllerResult(() -> manageCommonService.saveFileToLocal(request));
    }
    /**
     * @Title: FileDownload
     * @Description: 下载文件
     * @author bianpengrui
     * @date 2022/4/29 11:33
     * @return:
     */
    @GetMapping(value = "fileDownload.json")
    public void  FileDownload(HttpServletRequest request, HttpServletResponse response)  {
        manageCommonService.fileDownload(request,response);
    }
    /**
     * @Title: fileCheck
     * @Description: 文件预览
     * @author 姜雪鹰
     * @date 2022/4/29 11:33
     * @return:
     */
    @GetMapping(value = "fileCheck.json")
    public void  fileCheck(HttpServletRequest request, HttpServletResponse response)  {
        manageCommonService.fileCheck(request,response);
    }

    @GetMapping(value = "fileCheckKq.json")
    public void  fileCheckKq(HttpServletRequest request, HttpServletResponse response)  {
        manageCommonService.fileCheckKq(request,response);
    }

    /**
     * @Title: 查询区/所/站数据
     * @Description:
     * @author 高杨
     * @date 2022/4/25 18:25
     * @return:
     */
    @RequestMapping(value = "getXzjgTree.json")
    public Result<Map>  getXzjgTree(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return CommonUtil.controllerResult(() -> manageCommonService.getXzjgTree());
    }
    /**
     * @Title: 查询区/所/站数据
     * @Description:
     * @author 高杨
     * @date 2022/4/25 18:25
     * @return:
     */
    @RequestMapping(value = "getXzjgTreePart2.json")
    public Result<Map>  getXzjgTreePart2(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return CommonUtil.controllerResult(() -> manageCommonService.getXzjgTreePart2());
    }

    /**
     * @Title: 查询区/所/站数据
     * @Description:
     * @author 高杨
     * @date 2022/4/25 18:25
     * @return:
     */
    @RequestMapping(value = "getSignLog.json")
    public Result<Map>  getSignLog(HttpServletRequest request, HttpServletResponse response, @RequestBody Map infoMap) throws IOException, ServletException {
        return CommonUtil.controllerResult(() -> manageCommonService.getSignLog(infoMap));
    }

}
