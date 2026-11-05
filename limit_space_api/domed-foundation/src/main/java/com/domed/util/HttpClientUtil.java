package com.domed.util;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class HttpClientUtil {

    public static String postRequest(String url, Map<String, Object> param) throws ClientProtocolException, IOException {

        CloseableHttpClient client = HttpClients.createDefault();

        Gson gson = new Gson();
        String parameter = gson.toJson(param);

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setEntity(new StringEntity(parameter, Charset.forName("UTF-8")));

//        se.setContentType(CONTENT_TYPE_TEXT_JSON);
//        httpPost.setEntity(se);

        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");

        return result;
    }

}