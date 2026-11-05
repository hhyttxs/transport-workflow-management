package com.domed.util;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

@Data
@Component
public class OSSUtil {

    //@Value("${END_POINT}")
    public String END_POINT;

    //@Value("${ACCESS_ID}")
    public String ACCESS_ID;

    //@Value("${ACCESS_KEY}")
    public String ACCESS_KEY;

//    @Value("${bucketName}")
    public String bucketName;

    @Value("${ACCESS_URL}")
    public String ACCESS_URL;

//    @Value("${sizeThreshol}")
    public int sizeThreshol;    //设置最多只允许在内存中存储的数据,单位:字节

//    @Value("${sizeMax}")
    private int sizeMax;    //设置允许用户上传文件大小,单位:字节

    @Value("${LOCAL_FILE_PATH}")
    public String LOCAL_FILE_PATH;

    @Value("${OFFICE_PATH}")
    public String OFFICE_PATH;

    private ClientConfiguration clientConfiguration = new ClientConfiguration();

    private OSSClient ossClient;

    /**
     *
     * @Title: 获取配置文件信息
     * @Description:
     * @param
     * @return:
     * @author: djf
     * Create Date: 2019/6/19  10:20
     * @throws
     */
    public ClientConfiguration getClientConfiguration() {
        return this.clientConfiguration;
    }

    /**
     *
     * @Title: 初始化OSSClient
     * @Description:
     * @param
     * @return:
     * @author: djf
     * Create Date: 2019/6/19  10:22
     * @throws
     */
    public OSSClient getClient() {
        return this.ossClient;
    }

    @PostConstruct
    private void init(){
//        this.ossClient  = new OSSClient(getEND_POINT(), getACCESS_ID(), getACCESS_KEY(), getClientConfiguration());
    }


    /**
     *
     * @Title: 上传图片到oss
     * @Description:
     * @param
     * @return:
     * @author: djf
     * Create Date: 2019/6/19  10:23
     * @throws
     */
    public String upload(InputStream content, String key, int fileSize) {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(fileSize);
        // 可以在metadata中标记文件类型
        String[] ext = key.split("\\.");
        String retExt = "";
        if(ext != null && ext.length > 0){
            retExt = ext[ext.length -1];
        }
        boolean flag = "gif,jpg,jpeg,bmp,png".contains(retExt);
        if(flag){
            meta.setContentType("image/jpg");
        }
        getClient().putObject(getBucketName(), key, content, meta);
        return getACCESS_URL() + key;
    }

    /*
     *@Description 上传图片到OSS
     *@Author 高杨
     *@Date 2019/12/10 17:57
     *@Param [content, key, fileSize]
     *@return java.lang.String
    **/
    public String uploadOSS(InputStream content, String key, int fileSize) {
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 必须设置ContentLength
        meta.setContentLength(fileSize);
        // 可以在metadata中标记文件类型
        meta.setContentType("image/jpg");
        // 上传Object.
        getClient().putObject(getBucketName(), key, content, meta);
        // 返回路径
        return getACCESS_URL() + key;
    }

    public String uploadMp3(String ur){
        HttpURLConnection connection = null;
        InputStream in = null;
        String fileName=null;
        int fileSize =0;
        try {
            // 1.获取连接对象
            URL url = new URL(ur);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Range", "bytes=0-");
            connection.connect();
            if (connection.getResponseCode() / 100 != 2) {
                return null;
            }
            // 2.获取连接对象的流
            in = connection.getInputStream();
            fileSize = connection.getContentLength();
            fileName = url.getFile();
            int extIndex = fileName.lastIndexOf(".");
            String ext = fileName.substring(extIndex,fileName.length());
            fileName = UUID.randomUUID().toString() + ext;
            fileName = "mp3" + "/" + fileName;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 必须设置ContentLength
        meta.setContentLength(fileSize);
        // 可以在metadata中标记文件类型
        meta.setContentType("audio/mp3");
        // 上传Object.
        getClient().putObject(getBucketName(), fileName, in, meta);
        return getACCESS_URL() + fileName;
    }


    /**
     *
     * @Title: 删除对象
     * @Description:
     * @param
     * @return:
     * @author: djf
     * Create Date: 2019/6/19  10:24
     * @throws
     */
    public void deleteObject(String key) {
        if(!(key == null || key.equals(""))){
            getClient().deleteObject(getBucketName(), key);
        }
    }

}
