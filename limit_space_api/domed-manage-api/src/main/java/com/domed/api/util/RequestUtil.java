package com.domed.api.util;


import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName RequestUtil
 * @Description TODO
 * @Author 段剑锋
 * @Date 2018/10/17 17:52
 * @Version 1.0
 **/
public class RequestUtil {

    public static String getRequestBody(HttpServletRequest request)
    {
        StringBuilder sb = new StringBuilder();
        InputStream is = null;
        try
        {
            is = request.getInputStream();

            byte[] b = new byte[4096];
            for (int n; (n = is.read(b)) != -1;)
            {
                sb.append(new String(b, 0, n));
            }
        }
        catch (IOException e)
        {
            //e.printStackTrace();
        }
        finally
        {
            if (null != is)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }

    }
    /**
     * 获取 request 中 json 字符串的内容
     *
     * @param request
     * @return : <code>byte[]</code>
     * @throws IOException
     */
    public static String getRequestJsonString(HttpServletRequest request)
            throws IOException {
        String submitMehtod = request.getMethod();
        // GET
        if (submitMehtod.equals("GET")) {
            String str = new String(request.getQueryString().getBytes("iso-8859-1"),"utf-8").replaceAll("%22", "\"");
            String deStr = URLDecoder.decode(str);
            return getRequestGetStr(deStr);
            // POST
        } else {
            return getRequestPostStr(request);
        }
    }

    /**
     * 获取Get请求的 内容
     * @param str
     * @return
     */
    public static String getRequestGetStr(String str){
        Map map = new HashMap<>();
        String strs = str.replaceAll("&", ",");
        String st = strs.replaceAll("=", ":");
        String[] param = st.split(",");
        for (int i = 0; i < param.length; i++) {
            int index = param[i].indexOf(':');
            map.put(param[i].substring(0,index), param[i].substring((index + 1)));
        }
        String sts = JSONObject.toJSONString(map);
        return sts;
    }

    /**
     * 获取POST请求的 内容
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }

    /**
     * 获取POST请求的 byte 数组
     * @param request
     * @return
     * @throws IOException
     */
    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {
            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    /**
     * JSONArray 根据name去重复
     * @param array
     */
    public static JSONArray delRepeatIndexid(JSONArray array) {
        JSONArray arrayTemp = new JSONArray();
        int num = 0;
        for(int i = 0;i < array.size();i++){
            if(num==0){
                arrayTemp.add(array.get(i));
            }else{
                int numJ = 0;
                for(int j = 0;j < arrayTemp.size(); j++){
                    JSONObject newJsonObjectI = (JSONObject)array.get(i);
                    JSONObject newJsonObjectJ = (JSONObject)arrayTemp.get(j);
                    String index_idI = newJsonObjectI.get("code").toString();
                    String valueI = newJsonObjectI.get("name").toString();
                    String index_idJ = newJsonObjectJ.get("code").toString();
                    if(index_idI.equals(index_idJ)){
                        arrayTemp.remove(j);
                        JSONObject newObject = new JSONObject();
                        newObject.put("code", index_idI);
                        newObject.put("name", valueI);
                        arrayTemp.add(newObject);
                        break;
                    }
                    numJ++;
                }
                if(numJ-1 == arrayTemp.size()-1){
                    arrayTemp.add(array.get(i));
                }
            }
            num++;
        }
        return arrayTemp;
    }

}
