package com.domed.api.service.commonService;

import com.domed.common.result.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;


public interface IManageCommonService {


    /**
     * @Description: 查询基础数据表
     * @date 2019/7/10 19:22
     */
    public Result<Map> queryBaseData(Map infoMap);

    /**
     * @Description: 查询基础数据表
     * @date 2019/7/10 19:22
     */
    public String queryBaseDataValue(String id);

    /**
     * @Description: 查询员工列表
     * @date 2019/7/10 19:22
     */
    public Result<List<Map>> queryStaffList(Map infoMap);

    /**
     * @Description: 获取请求ip
     * @date 2019/7/10 19:22
     */
    public String getRemoteHost(HttpServletRequest request);

    /**
     * @Description: oss文件上传
    　* @param
    　* @return
    　* @throws
    　* @author syl
    　* @date 2019/7/12 12:06
    　*/
    public Result<Map> saveFileToOss(MultipartFile multipartFile, String saveFilePathName) throws IOException;

    /**
     * @Description: oss图片上传
    　* @param
    　* @return
    　* @throws
    　* @author syl
    　* @date 2019/7/12 12:06
    　*/
    public Result<Map> saveStringToOss(String img, String saveFilePathName);

    /**
     *
     * @Title: queryEditHistory
     * @Description: 查询修改历史
     * @param
     * @return:
     * @author: shanyuwei
     * Create Date:  2019/12/28
     * @throws
     */
    public Result<Map> queryEditHistory(Map infoMap);

    /**
     * 获取员工职务类型
     * @param staff_id
     * @return
     */
    public String getStaffDutyType(String staff_id) throws Exception;

    /**
     * 查询
     * @param dutyTypesPolicies
     * @return
     * @throws Exception
     */
    public Result<Map> queryPolicyDecision(String... dutyTypesPolicies) throws Exception;

    /**
     * 时间转换
     * @param time,format
     * @return
     * @throws Exception
     */
    public String getFormatDate(String time,String format) throws Exception;

    public Date getDate(String time) throws ParseException;

    public Date stringToDate(String time) throws ParseException;

    /**
     * @Title: 保存文件到本地
     * @Description:
     * @author 高杨
     * @date 2022/4/25 18:32
     * @return:
     */
    public Result<Map> saveFileToLocal(HttpServletRequest request) throws Exception;
    /**
      * @Description: 文件下载
    　* @param FileDownload
    　* @return
    　* @throws
    　* @author 姜雪鹰
    　* @date 2022/4/30 1:49
    　*/
    void  fileDownload(HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 文件预览(pdf)
    　* @param FileDownload
    　* @return
    　* @throws
    　* @author 姜雪鹰
    　* @date 2022/4/30 1:49
    　*/
    void  fileCheck(HttpServletRequest request, HttpServletResponse response);

    void  fileCheckKq(HttpServletRequest request, HttpServletResponse response);

    public Result<Map> getXzjgTree();
    public Result<Map> getXzjgTreePart2();
    public Result<Map> getXzqhList();


    public Result<Map> getSignLog(Map infoMap);
}
