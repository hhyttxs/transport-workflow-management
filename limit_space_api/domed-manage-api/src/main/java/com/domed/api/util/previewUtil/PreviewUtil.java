package com.domed.api.util.previewUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.common.utils.StringUtils;
import com.domed.core.exception.ApplicationException;
import com.domed.util.OSSUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PreviewUtil {

    @Autowired
    private OSSUtil oSSUtil;

    public static String getToken(String serviceName) throws Exception {

        HttpClient httpClient = new HttpClient();

        PostMethod postMethod = new PostMethod("http://127.0.0.1:8080/dlyz_web_service/data_synchronization/getToken.do");

        postMethod.addRequestHeader("accept", "*/*");
        postMethod.addRequestHeader("connection", "Keep-Alive");
        //设置json格式传送
        postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        //必须设置下面这个Header
        postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
        //添加请求参数
        postMethod.addParameter("serialnum", "a9ba8d82cdd74791b7ae8a064f1e97bb");
        postMethod.addParameter("timestamp", String.valueOf(System.currentTimeMillis()));
        postMethod.addParameter("appkey", System.getenv("INTEGRATION_APPKEY"));
        postMethod.addParameter("appSecret", System.getenv("INTEGRATION_APPSECRET"));
        postMethod.addParameter("service", serviceName);
        postMethod.addParameter("version", "1.0");

        String res = "";
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            int code = httpClient.executeMethod(postMethod);
            if (code == 200) {
                res = postMethod.getResponseBodyAsString();
                resultMap = (Map<String, Object>) JSON.parse(res);
                if (!"0".equals(resultMap.get("code").toString())) {
                    throw new ApplicationException(resultMap.get("msg").toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap.get("data").toString();
    }


    public static String dataExChange(Map param) throws Exception {

        HttpClient httpClient = new HttpClient();

        PostMethod postMethod = new PostMethod("http://127.0.0.1:8080/dlyz_web_service/data_synchronization/dataExchange.do");

        postMethod.addRequestHeader("accept", "*/*");
        postMethod.addRequestHeader("connection", "Keep-Alive");
        //设置json格式传送
        postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        //必须设置下面这个Header
        postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
        //添加请求参数
        postMethod.addParameter("appSecret", System.getenv("INTEGRATION_APPSECRET"));

        String typeChild = param.get("typeChild").toString();
        if ("clbg".equals(typeChild)) { //车辆变更
            postMethod.addParameter("service_key", System.getenv("INTEGRATION_SERVICE_KEY"));
            postMethod.addParameter("token", getToken("t.web.service.clbg"));
            postMethod.addParameter("u_clbm", param.get("u_clbm").toString());  //车辆编码
            postMethod.addParameter("bg_gid", param.get("bg_gid").toString());  //变更表主键，如果有未完成变更需要传入
            postMethod.addParameter("cph", param.get("cph").toString());    //车牌号
            postMethod.addParameter("xh", param.get("xh").toString());  //型号
            postMethod.addParameter("cp", param.get("cp").toString());  //厂牌
            postMethod.addParameter("fdjh", param.get("fdjh").toString());  //发动机号
            postMethod.addParameter("cjh", param.get("cjh").toString());    //车架号
            postMethod.addParameter("jyxz", param.get("jyxz").toString());  //经营性质
            postMethod.addParameter("zcdjrq", param.get("zcdjrq").toString());  //注册发动日期  yyyy-MM-dd
            postMethod.addParameter("jsdj", param.get("jsdj").toString()); //技术登记 1一级 2二级 3不合格
            postMethod.addParameter("clcd", param.get("clcd").toString()); //车辆长度
            postMethod.addParameter("clkd", param.get("clkd").toString());  //车辆宽度
            postMethod.addParameter("clgd", param.get("clgd").toString());  //车辆高度
            postMethod.addParameter("bfrq", param.get("bfrq").toString());  //报废日期 yyyy-MM-dd
            postMethod.addParameter("lxfs", param.get("lxfs").toString());  //联系方式
            postMethod.addParameter("cz", param.get("cz").toString());  //车种
            postMethod.addParameter("rylx", param.get("rylx").toString());  //燃料类型
            postMethod.addParameter("c_jyfw_id", param.get("c_jyfw_id").toString());    //经营范围ID
            postMethod.addParameter("dzw", param.get("dzw").toString());    //吨座位
            postMethod.addParameter("zzl", param.get("zzl").toString());    //总质量
            postMethod.addParameter("wxpm", param.get("wxpm").toString());  //危险品名
            postMethod.addParameter("gtrj", param.get("gtrj").toString());  //罐体容积
            postMethod.addParameter("jzmd", param.get("jzmd").toString());  //介质密度

            if ((MultipartFile) param.get("clzp") != null) {
                MultipartFile multipartFile = (MultipartFile) param.get("clzp");
                Map map = new HashMap<>();
                map.put("pic", multipartFile);
                Map<String, String> picIdMap = getPicId(map);
                String picId = picIdMap.get("data");
                postMethod.addParameter("clzp_gid", picId); //车辆照片GID
            }
            postMethod.addParameter("bgyy", param.get("bgyy").toString());  //变更原因
        } else if ("clzx".equals(typeChild)) {   //车辆注销
            postMethod.addParameter("token", getToken("t.web.service.clzx"));
            postMethod.addParameter("service_key", System.getenv("INTEGRATION_SERVICE_KEY"));
            postMethod.addParameter("u_clbm", param.get("u_clbm").toString());  //车辆编码
            postMethod.addParameter("zx_gid", param.get("zx_gid").toString());  //注销表主键  处理注销未完成数据
            postMethod.addParameter("xcyy", param.get("xcyy").toString());    //销车原因
            postMethod.addParameter("shzj", param.get("shzj").toString());  //收回证件 1道路运输证正本 2道路运输证副本 4二级维护卡 5综合性能检测卡
            postMethod.addParameter("sfhbc", param.get("sfhbc").toString());  //是否黄标车 2是 1否
            postMethod.addParameter("bz", param.get("bz").toString());  //备注
        } else if ("clbz".equals(typeChild)) {    //车辆补证
            postMethod.addParameter("service_key", System.getenv("INTEGRATION_SERVICE_KEY"));
            postMethod.addParameter("token", getToken("t.web.service.clbz"));
            postMethod.addParameter("u_clbm", param.get("u_clbm").toString());  //车辆编码
            postMethod.addParameter("bz_gid", param.get("bz_gid").toString());  //补证表主键 处理未完成数据
            postMethod.addParameter("bhtype", param.get("bhtype").toString());  //补换原因 1损坏换发 2丢失补发
            postMethod.addParameter("new_yyzh", param.get("new_yyzh").toString());  //丢失补发时 新营运证号
            postMethod.addParameter("bz", param.get("bz").toString());  //备注
        } else if ("cldz".equals(typeChild)) {  //车辆打证
            postMethod.addParameter("service_key", System.getenv("INTEGRATION_SERVICE_KEY"));
            postMethod.addParameter("token", getToken("t.web.service.cldz"));
            postMethod.addParameter("clbm", param.get("clbm").toString());  //车辆编码
            postMethod.addParameter("step", param.get("step").toString());  //打印类别 1车辆登记打印 2车辆变更打印 3丢失补证打印 4损坏换证打印 5车辆换证打印 6辖区转换换证打印 7补打代理证 8国际道路运输打印
            postMethod.addParameter("type", param.get("type").toString());  //1正本 2副本
            postMethod.addParameter("clbg_gid", param.get("clbg_gid").toString());  //车辆变更表主键 车辆变更打印时必填
            postMethod.addParameter("bz_bid", param.get("bz_bid").toString());  //补证表BID 车辆补证打印时必填
            postMethod.addParameter("bz", param.get("bz").toString());  //副本中备注
            postMethod.addParameter("startDate", param.get("startDate").toString());  //有效期开始 到期换证必填 yyyy-mm-dd
            postMethod.addParameter("endDate", param.get("endDate").toString());  //有效期截至 到期换证必填 yyyy-mm-dd
            postMethod.addParameter("wshz_ywxh", param.get("wshz_ywxh").toString());  //网上换证业务序号 网上换证业务必填
            postMethod.addParameter("czr", param.get("czr").toString());  //操作人
            postMethod.addParameter("czsj", param.get("czr").toString());  //操作时间  yyyy-MM-dd HH:mm:ss
        } else if ("yhbg".equals(typeChild)) {  //业户变更
            postMethod.addParameter("service_key", System.getenv("INTEGRATION_SERVICE_KEY"));
            postMethod.addParameter("token", getToken("t.web.service.yhbg"));
            postMethod.addParameter("u_yhbm", param.get("u_yhbm").toString());    //业户编码
            postMethod.addParameter("bg_gid", param.get("bg_gid").toString());    //变更表主键，如果有未完成变更需要传入
            postMethod.addParameter("jyfw", param.get("jyfw").toString());    //变更后经营范围ID，父子级都传。如：1,3
            postMethod.addParameter("scope", param.get("scope").toString());    //经营性质
            postMethod.addParameter("sqrmc", param.get("sqrmc").toString());    //业户名称
            postMethod.addParameter("tyshxydm", param.get("tyshxydm").toString());    //统一社会信用代码
            postMethod.addParameter("bllx", param.get("bllx").toString());    //办理类型 1:即办件 2:承诺件 3:联办件 4:报批件
            postMethod.addParameter("address", param.get("address").toString());    //业户地址
            postMethod.addParameter("zjlx", param.get("zjlx").toString());    //证件类型 0身份证 1护照
            postMethod.addParameter("sfzh", param.get("sfzh").toString());    //法人身份证号
            postMethod.addParameter("jysx", param.get("jysx").toString());    //经营时限
            postMethod.addParameter("jjlx", param.get("jjlx").toString());    //经济性质
            postMethod.addParameter("jbrxm", param.get("jbrxm").toString());    //经办人姓名
            postMethod.addParameter("tel", param.get("tel").toString());    //电话
            postMethod.addParameter("mobile", param.get("mobile").toString());    //手机
            postMethod.addParameter("wxppm", param.get("wxppm").toString());    //危险品品名
            postMethod.addParameter("remark", param.get("remark").toString());    //备注
            postMethod.addParameter("qyygs", param.get("qyygs").toString());    //企业员工总数 网络货运传
            postMethod.addParameter("zczj", param.get("zczj").toString());    //注册资金 网络货运传
        } else if ("yhzx".equals(typeChild)) {  //业户注销
            postMethod.addParameter("service_key", System.getenv("INTEGRATION_SERVICE_KEY"));
            postMethod.addParameter("token", getToken("t.web.service.yhzx"));
            postMethod.addParameter("u_yhbm", param.get("u_yhbm").toString());    //业户编码
            postMethod.addParameter("zx_gid", param.get("zx_gid").toString());    //注销表主键  处理注销未完成数据
            postMethod.addParameter("shzj", param.get("shzj").toString());    //回收证件 1经营许可证正本 2经营许可证副本 逗号分割
            postMethod.addParameter("remark", param.get("remark").toString());    //备注
            postMethod.addParameter("xhlx", param.get("xhlx").toString());    //销户类型 0主动 1被动
            postMethod.addParameter("xhyy", param.get("xhyy").toString());    //销户原因
        } else if ("yhbz".equals(typeChild)) {    //业户补证
            postMethod.addParameter("service_key", System.getenv("INTEGRATION_SERVICE_KEY"));
            postMethod.addParameter("token", getToken("t.web.service.yhbz"));
            postMethod.addParameter("u_yhbm", param.get("u_yhbm").toString());    //业户编码
            postMethod.addParameter("bz_gid", param.get("bz_gid").toString());  //补证表主键 处理未完成数据
            postMethod.addParameter("bztype", param.get("bztype").toString());  //补证类型 1经营许可证正本 2经营许可证副本
            postMethod.addParameter("bhtype", param.get("bhtype").toString());  //补换原因 1损坏换发 2丢失补发
            postMethod.addParameter("new_yyzh", param.get("new_yyzh").toString());  //丢失补发时 新营运证号
            postMethod.addParameter("bz", param.get("bz").toString());  //备注
        } else if ("yhdz".equals(typeChild)) {
            postMethod.addParameter("service_key", System.getenv("INTEGRATION_SERVICE_KEY"));
            postMethod.addParameter("token", getToken("t.web.service.yhdz"));
            postMethod.addParameter("u_yhbm", param.get("u_yhbm").toString());    //业户编码
            postMethod.addParameter("step", param.get("step").toString());    //打印类别 1业户开业打印 2业户变更打印 3丢失补证打印 4损坏换证打印 5到期换证打印
            postMethod.addParameter("type", param.get("type").toString());    //补证件类型 1经营许可证正本 2经营许可证副本
            postMethod.addParameter("bzbid", param.get("bzbid").toString());    //HY_BZ 表 BID，如果为补证打印必填（打印类别为3或4）
            postMethod.addParameter("newyxqks", param.get("newyxqks").toString());    //有效期开始 许可换证打印必填 yyyy-MM-dd
            postMethod.addParameter("newyxqjz", param.get("newyxqjz").toString());    //有效期截至 许可换证打印必填 yyyy-MM-dd
            postMethod.addParameter("userId", param.get("userId").toString());    //操作人
            postMethod.addParameter("czsj", param.get("czsj").toString());    //操作时间 yyyy-MM-dd HH:mm:ss
        }else if ("cllr".equals(typeChild)){
            postMethod.addParameter("service_key",System.getenv("INTEGRATION_SERVICE_KEY"));
            postMethod.addParameter("token", getToken("t.web.service.cllr"));
            postMethod.addParameter("u_yhbm","05C1872C-BECB-4489-8AD3-EFB8BBE4B4B0"); //业户编码
            postMethod.addParameter("c_yyzh",param.get("c_yyzh").toString()); //运输证号
            postMethod.addParameter("djfs",param.get("djfs").toString());   //登记方式 1新车 2报废更新 3转籍过户 4外地车辆转籍
            postMethod.addParameter("xcjcsj",param.get("xcjcsj").toString());   //下次检测日期 yyyy-MM-dd
            postMethod.addParameter("jysx",param.get("jysx").toString());   //经营时限
            postMethod.addParameter("bfnx",param.get("bfnx").toString());   //报废年限
            postMethod.addParameter("bz",param.get("bz").toString());   //备注
            postMethod.addParameter("rljc","1");   //燃料检查  1 合格 2不合格
            postMethod.addParameter("cph",param.get("cph").toString()); //车牌号
            postMethod.addParameter("xh",param.get("xh").toString());   //型号（厂牌）
            postMethod.addParameter("cp",param.get("cp").toString());   //厂牌
            postMethod.addParameter("cpys",param.get("cpys").toString());   //车牌颜色
            postMethod.addParameter("cx",param.get("cx").toString());   //车型
            postMethod.addParameter("fdjh",param.get("fdjh").toString());   //发动机号
            postMethod.addParameter("cjh",param.get("cjh").toString());   //车架号
            postMethod.addParameter("jyxz","0");   //经营性质
            postMethod.addParameter("zcdjrq",param.get("zcdjrq").toString());   //注册登记日期 yyyy-MM-dd
            postMethod.addParameter("jsdj",param.get("jsdj").toString());   //技术等级 1 一级 2 二级 3 不合格
            postMethod.addParameter("clcd",param.get("clcd").toString());   //车辆长度
            postMethod.addParameter("clkd",param.get("clkd").toString());   //车辆宽度
            postMethod.addParameter("clgd",param.get("clgd").toString());   //车辆高度
            postMethod.addParameter("bfrq",param.get("bfrq").toString());   //报废日期 yyyy-MM-dd
            postMethod.addParameter("lxfs",param.get("lxfs").toString());   //联系方式
            postMethod.addParameter("cz",param.get("cz").toString());   //车种 1 单车 2 牵引车 3 挂车
            postMethod.addParameter("rylx",param.get("rylx").toString());   //燃料类型
            postMethod.addParameter("c_jyfw_id",param.get("c_jyfw_id").toString());   //经营范围ID
            if (StringUtils.isNotEmpty(param.get("dzw").toString()))
                postMethod.addParameter("dzw",param.get("dzw").toString());   //吨座位
            if (StringUtils.isNotEmpty(param.get("zzl").toString()))
                postMethod.addParameter("zzl",param.get("zzl").toString());   //总质量
            if (StringUtils.isNotEmpty(param.get("wxpm").toString()))
                postMethod.addParameter("wxpm",param.get("wxpm").toString());   //危险品名
            if (StringUtils.isNotEmpty(param.get("gtrj").toString()))
                postMethod.addParameter("gtrj",param.get("gtrj").toString());   //罐体容积
            if (StringUtils.isNotEmpty(param.get("jzmd").toString()))
                postMethod.addParameter("jzmd",param.get("jzmd").toString());   //介质密度
            if ((MultipartFile) param.get("clzp") != null) {
                MultipartFile multipartFile = (MultipartFile) param.get("clzp");
                Map map = new HashMap<>();
                map.put("pic", multipartFile);
                Map<String, String> picIdMap = getPicId(map);
                String picId = picIdMap.get("data");
                JSONObject jsonObject = JSON.parseObject(picId);
                Object data = jsonObject.get("data");
                postMethod.addParameter("clzp_gid", data.toString()); //车辆照片GID
            }
        }else if("yhlr".equals(typeChild)){
            postMethod.addParameter("service_key", System.getenv("INTEGRATION_SERVICE_KEY"));
            postMethod.addParameter("token", getToken("t.web.service.yhlr"));
            postMethod.addParameter("sqrmc", param.get("sqrmc").toString()); //业户名称
            postMethod.addParameter("xkzh", param.get("xkzh").toString()); //许可证号
            postMethod.addParameter("address", param.get("address").toString()); //业户地址
            postMethod.addParameter("tyshxydm", param.get("tyshxydm").toString()); //统一社会信用代码
            postMethod.addParameter("gszzfzrq", param.get("gszzfzrq").toString()); //工商执照发证日期
            postMethod.addParameter("bllx", param.get("bllx").toString()); //办理类型
            postMethod.addParameter("jysx", param.get("jysx").toString()); //经营时限
            postMethod.addParameter("fzrxm", param.get("fzrxm").toString()); //法人姓名
            postMethod.addParameter("zjlx", param.get("zjlx").toString()); //证件类型
            postMethod.addParameter("sfzh", param.get("sfzh").toString()); //法人身份证号
            postMethod.addParameter("mobile", param.get("mobile").toString()); //手机号
            postMethod.addParameter("jbrxm", param.get("jbrxm").toString()); //经办人姓名
            postMethod.addParameter("lxfs", param.get("lxfs").toString()); //联系方式
            postMethod.addParameter("xckcrxm", param.get("xckcrxm").toString()); //现场勘察人签名
            postMethod.addParameter("xckcshrxm", param.get("xckcshrxm").toString()); //现场勘察审核人签名
            postMethod.addParameter("xckcsj", param.get("xckcsj").toString()); //现场勘察时间
            postMethod.addParameter("xckcyj", param.get("xckcyj").toString()); //运管机构现场勘察情况及审核意见
            postMethod.addParameter("scope", param.get("scope").toString()); //经营性质 0经营性 1非经营性
            postMethod.addParameter("xzjg_id", param.get("xzjg_id").toString()); //行政机构表主键 ZGZJ
            postMethod.addParameter("jjlx", param.get("jjlx").toString()); //经济类型
            postMethod.addParameter("applicationFormType", param.get("applicationFormType").toString()); //业户类型 0普货 1危货 2场站 5汽车租赁 10网络货运
            postMethod.addParameter("jyfw_id", param.get("jyfw_id").toString()); //经营范围ID
            postMethod.addParameter("sqr", param.get("sqr").toString()); //申请人
            postMethod.addParameter("sqsj", param.get("sqsj").toString()); //申请时间
            postMethod.addParameter("slsx", param.get("slsx").toString()); //受理时限
            postMethod.addParameter("slr", param.get("slr").toString()); //受理人
            postMethod.addParameter("slsj", param.get("slsj").toString()); //受理时间
            postMethod.addParameter("spr1", param.get("spr1").toString()); //审批人
            postMethod.addParameter("spsj1", param.get("spsj1").toString()); //审批时间
            postMethod.addParameter("spr2", param.get("spr2").toString()); //审批人
            postMethod.addParameter("spsj2", param.get("spsj2").toString()); //审批时间
        }

        if (param.get("slr") != null && StringUtils.isNotEmpty(param.get("slr").toString()))    //受理人
            postMethod.addParameter("slr", param.get("slr").toString());
        if (param.get("slsj") != null && StringUtils.isNotEmpty(param.get("slsj").toString()))  //受理时间 yyyy-MM-dd HH:mm:ss
            postMethod.addParameter("slsj", param.get("slsj").toString());
        if (param.get("slyj") != null && StringUtils.isNotEmpty(param.get("slyj").toString()))  //受理意见
            postMethod.addParameter("slyj", param.get("slyj").toString());
        if (param.get("spr1") != null && StringUtils.isNotEmpty(param.get("spr1").toString()))  //审批人1
            postMethod.addParameter("spr1", param.get("spr1").toString());
        if (param.get("spsj1") != null && StringUtils.isNotEmpty(param.get("spsj1").toString()))  //审批时间1 yyyy-MM-dd HH:mm:ss
            postMethod.addParameter("spsj1", param.get("spsj1").toString());
        if (param.get("spyj1") != null && StringUtils.isNotEmpty(param.get("spyj1").toString()))  //审批意见1
            postMethod.addParameter("spyj1", param.get("spyj1").toString());
        if (param.get("spr2") != null && StringUtils.isNotEmpty(param.get("spr2").toString()))   //审批人2
            postMethod.addParameter("spr2", param.get("spr2").toString());
        if (param.get("spsj2") != null && StringUtils.isNotEmpty(param.get("spsj2").toString()))  //审批时间2 yyyy-MM-dd HH:mm:ss
            postMethod.addParameter("spsj2", param.get("spsj2").toString());
        if (param.get("spyj2") != null && StringUtils.isNotEmpty(param.get("spyj2").toString()))  //审批意见2
            postMethod.addParameter("spyj2", param.get("spyj2").toString());

        String res = "";
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            int code = httpClient.executeMethod(postMethod);
            if (code == 200) {
                res = postMethod.getResponseBodyAsString();
                resultMap = (Map<String, Object>) JSON.parse(res);
                if (!"0".equals(resultMap.get("code").toString())) {
                    throw new ApplicationException(resultMap.get("msg").toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap.get("data").toString();
    }

    public static Map<String, String> getPicId(Map param) throws Exception {
        Map<String, String> resultMap = new HashMap();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
//        PostMethod postMethod = new PostMethod("http://127.0.0.1:8080/dlyz_web_service/data_synchronization/dataExchange.do");
//
//        postMethod.addRequestHeader("accept", "*/*");
//        postMethod.addRequestHeader("connection", "Keep-Alive");
//        //设置json格式传送
//        postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//        //必须设置下面这个Header
//        postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
//        //添加请求参数
//        postMethod.addParameter("token", getToken());
//        postMethod.addParameter("appSecret", System.getenv("INTEGRATION_APPSECRET"));
//        postMethod.addParameter("service_key", System.getenv("INTEGRATION_SERVICE_KEY"));
//        postMethod.addParameter("upload_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        postMethod.addParameter("pic_name", "车辆变更补传照片");
            ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
            HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/dlyz_web_service/data_synchronization/dataExchange.do");
            MultipartFile multipartFile = (MultipartFile) param.get("pic");
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            String file_ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            String fileName = multipartFile.getOriginalFilename();
            builder.addBinaryBody("file", multipartFile.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
//            postMethod.addParameter("token", getToken());
//            postMethod.addParameter("appSecret", System.getenv("INTEGRATION_APPSECRET"));
//            postMethod.addParameter("service_key", System.getenv("INTEGRATION_SERVICE_KEY"));
//            postMethod.addParameter("upload_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//            postMethod.addParameter("pic_name", "车辆变更补传照片");
            builder.addTextBody("token", getToken("t.web.service.uploadFile"), contentType);
            builder.addTextBody("appSecret", System.getenv("INTEGRATION_APPSECRET"), contentType);
            builder.addTextBody("service_key", System.getenv("INTEGRATION_SERVICE_KEY"), contentType);
            builder.addTextBody("upload_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), contentType);
            builder.addTextBody("pic_name", "车辆变更补传照片", contentType);
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(-1)
                    .setConnectionRequestTimeout(-1).setSocketTimeout(-1).build();
            httpPost.setConfig(requestConfig);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();

            String res = "";
            resultMap.put("scode", String.valueOf(response.getStatusLine().getStatusCode()));
            resultMap.put("data", "");
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, java.nio.charset.Charset.forName("UTF-8"));
                resultMap.put("data", result);
                // "[{\"cname\":\"/dfss/fss1/data/2019/10/25/2rJFUUmMajNIYJyEuRifq2447245342.png\",\"downpath\":\"http://127.0.0.1:8080/dfss/fss1/data/2019/10/25/2rJFUUmMajNIYJyEuRifq2447245342.png\"}]"
            }
            //关闭请求
            httpClient.close();
        } catch (Exception e) {
            resultMap.put("scode", "error");
            resultMap.put("data", "HTTP请求出现异常: " + e.getMessage());
        } finally {
            return resultMap;
        }
    }


    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }

}
