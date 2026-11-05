package com.domed.api.util.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.domed.api.util.FileUtil;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HttpAPIService {

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;


    public String getResponseContent(CloseableHttpResponse response){
        String result = null;
        // 判断状态码是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            try {
                result =  EntityUtils.toString(response.getEntity(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * get请求，返回原生response
     * @param url
     * @param headers
     * @param body
     * @return
     * @throws Exception
     */
    public CloseableHttpResponse doGetNative(String url,Map<String,String> headers,Map<String, Object> body) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (body != null) {
                // 遍历map,拼接请求参数
                for (Map.Entry<String, Object> entry : body.entrySet()) {
                    uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
                }
            }

            HttpGet httpGet = new HttpGet(uriBuilder.build().toString());
            httpGet.setConfig(config);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpGet.setHeader(key, value);
                }
            }
            // 发起请求
            CloseableHttpResponse response = this.httpClient.execute(httpGet);

            httpGet.releaseConnection();
            return response;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public CloseableHttpResponse doGetNative(String url,Map<String, Object> body) {
        return this.doGetNative(url,null,body);
    }

    public CloseableHttpResponse doGetNative(String url) {
        return this.doGetNative(url,null,null);
    }

    public String doGet(String url,Map<String,String> headers,Map<String, Object> body) {
        return getResponseContent(doGetNative(url,headers,body));
    }
    public String doGet(String url,Map<String, Object> body) {
        return getResponseContent(doGetNative(url,body));
    }
    public String doGet(String url) {
        return getResponseContent(doGetNative(url));
    }


    public CloseableHttpResponse doPutNative(String url, Map<String,String> headers, InputStream in) {

        HttpPut httpPut = new HttpPut(url);
        httpPut.setConfig(config);

        if (headers != null){
            for(Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPut.setHeader(key, value);
            }
        }

        EntityBuilder builder = EntityBuilder.create();
        try {
            builder.setBinary(FileUtil.input2byte(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpPut.setEntity(builder.build());


        // 发起请求
        CloseableHttpResponse response = null;
        try {
            response = this.httpClient.execute(httpPut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpPut.releaseConnection();
        return response;
    }


    public CloseableHttpResponse doPostNative(String url,Map<String,String> headers,Map<String, Object> body) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (body != null) {
                // 遍历map,拼接请求参数
                for (Map.Entry<String, Object> entry : body.entrySet()) {
                    uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
                }
            }

            HttpPost httpPost = new HttpPost(uriBuilder.build().toString());
            httpPost.setConfig(config);

            if (headers != null) {
                for(Map.Entry<String, String> entry : headers.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpPost.setHeader(key, value);
                }
            }
            // 发起请求
            CloseableHttpResponse response = this.httpClient.execute(httpPost);

            httpPost.releaseConnection();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
