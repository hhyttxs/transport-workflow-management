package com.domed.api.util.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import com.domed.api.bean.PortExcelBean;
import com.domed.util.OSSUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @ClassName EasyPOIExcelUtil
 * @Description
 * @Author lirong
 * @Date 2022/4/14 9:52
 **/
public class EasyPOIExcelUtil {

    public void exportExcel(HttpServletResponse response, String fileName, String sheetName, List<Map> listData) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ExportParams params = new ExportParams(sheetName, sheetName);
        params.setSecondTitle("导出时间：" + format.format(new Date()));
        params.setStyle(EasyPOIExcelStyleUtil.class);
        List<Map> finalCdklist = listData;
        int talpage = (finalCdklist.size() / 10000) + 1;
        Workbook workbook = ExcelExportUtil.exportBigExcel(params, PortExcelBean.class, new IExcelExportServer() {
            @Override
            public List<Object> selectListForExcelExport(Object obj, int page) {
                //page每次加一，当等于obj的值时返回空，代码结束；
                if (((int) obj) < page) {
                    return null;
                }
                int start = -1;
                if (page == 1) {
                    start = 0;
                } else {
                    start = (page - 1) * 10000;
                }
                //不是空时：一直循环运行selectListForExcelExport。每次返回1万条数据。
                List<Object> list = new ArrayList<Object>();
                for (int i = start; i < (start + 10000); i++) {
                    if (i < finalCdklist.size()) {
                        Map dataMap = finalCdklist.get(i);
                        PortExcelBean portExcelBean = new PortExcelBean();
                        portExcelBean.setXh((i + 1) + "");
                        portExcelBean.setXkzh(getParam(dataMap, "XKZH"));
                        portExcelBean.setQymc(getParam(dataMap, "SQRMC"));
                        portExcelBean.setQyfr(getParam(dataMap, "FDDBR"));
                        portExcelBean.setShxydm(getParam(dataMap, "TYSHXYDM"));
                        String address = getParam(dataMap, "ADDRESS");
                        String bgdz = getParam(dataMap, "BGDZ");
                        portExcelBean.setQydz(!"".equals(bgdz) ? bgdz : address);
                        portExcelBean.setJyfw(getParam(dataMap, "JYFW"));
                        portExcelBean.setJydd(getParam(dataMap, "JY_AREA"));
                        portExcelBean.setXzqh(getParam(dataMap, "XZQH_NAME"));
                        portExcelBean.setJyqx(getParam(dataMap, "YXQKS") + "-" + getParam(dataMap, "YXQJZ"));
                        String lxr = getParam(dataMap, "JBR");
                        String lxr_phone = getParam(dataMap, "JBR_PHONE");
                        String con_fax = getParam(dataMap, "CON_FAX");
                        String con_mail = getParam(dataMap, "CON_MAIL");
                        String lxfs = "";
                        if (!"".equals(lxr)) {
                            lxfs += "联系人：" + lxr;
                        }
                        if (!"".equals(lxr_phone)) {
                            lxfs += " " + lxr_phone;
                        }
                        if (!"".equals(con_fax)) {
                            lxfs += " 传真：" + con_fax;
                        }
                        if (!"".equals(con_mail)) {
                            lxfs += " E-mail：" + con_mail;
                        }
                        portExcelBean.setLxfs(lxfs);
                        list.add(portExcelBean);
                    } else {
                        break;
                    }
                }
                return list;
            }
        }, talpage);
        exportRespont(workbook, response, fileName);
    }

    public void exportExcelCommon(HttpServletResponse response, Class<?> objClass, String fileName, String sheetName, List<Map> listData) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ExportParams params = new ExportParams(sheetName, sheetName);
        params.setSecondTitle("导出时间：" + format.format(new Date()));
        params.setStyle(EasyPOIExcelStyleUtil.class);
        List<Map> finalCdklist = listData;
        int talpage = (finalCdklist.size() / 10000) + 1;
        Workbook workbook = ExcelExportUtil.exportBigExcel(params, objClass, new IExcelExportServer() {
            @Override
            public List<Object> selectListForExcelExport(Object obj, int page) {
                //page每次加一，当等于obj的值时返回空，代码结束；
                if (((int) obj) < page) {
                    return null;
                }
                int start = -1;
                if (page == 1) {
                    start = 0;
                } else {
                    start = (page - 1) * 10000;
                }
                //不是空时：一直循环运行selectListForExcelExport。每次返回1万条数据。
                List<Object> list = new ArrayList<Object>();
                for (int i = start; i < (start + 10000); i++) {
                    if (i < finalCdklist.size()) {
                        Map dataMap = finalCdklist.get(i);
                        Object o = null;
                        try {
                            o =  objClass.newInstance();
                            Field[] fields = objClass.getDeclaredFields();
                            for(Field f : fields){
                                f.setAccessible(true);
                                String name = f.getName();
                                Class<?> type = objClass.getDeclaredField(name).getType();
                                String invokeMethod = name.substring(0, 1).toUpperCase() + name.substring(1);
                                Method method = objClass.getMethod("set"+invokeMethod,type);
                                method.invoke(o, dataMap.get(name));
                            }
                            
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        if(o == null ) continue;
                        list.add(o);
                    } else {
                        break;
                    }
                }
                return list;
            }
        }, talpage);
        exportRespont(workbook, response, fileName);
    }

    private void exportRespont(Workbook workbook, HttpServletResponse response, String fileName) {
        try {
//            String localFilePath = "D:\\ideaplace\\transport";
            //清空response
            response.reset();
            //设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            //提示信息
            response.addHeader("Filename", URLEncoder.encode(fileName, "utf-8"));
            //设置返回响应头
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
            OutputStream out = new BufferedOutputStream(response.getOutputStream());
//            File file = new File(localFilePath + "/supportModel/"+ fileName + ".xlsx");
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            FileOutputStream fopts = null;
//            fopts = new FileOutputStream(localFilePath + "/supportModel/"+ fileName + ".xlsx");

            workbook.write(out);
            workbook.close();
            out.flush();
            out.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getParam(Map infoMap, String name) {
        if (infoMap == null) {
            return "";
        }
        return infoMap.get(name) == null ? "" : infoMap.get(name).toString();
    }
}
