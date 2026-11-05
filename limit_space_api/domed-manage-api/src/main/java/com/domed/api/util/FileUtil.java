package com.domed.api.util;

import org.springframework.util.Base64Utils;
import sun.misc.*;

import java.io.*;

/**
 * @ClassName FileUtil
 * @Description TODO
 * @Author 段剑锋
 * @Date 2018/11/19 16:28
 * @Version 1.0
 **/
public class FileUtil {
    public  static void inputstreamtofile(InputStream ins, File file) {
        try(
                OutputStream os = new FileOutputStream(file);
        ) {

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String inputStream2Base64(InputStream in){
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try(ByteArrayOutputStream swapStream = new ByteArrayOutputStream()) {
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "data:image/png;base64,"+new String(Base64Utils.encode(data));

    }

    public static final InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }

    public static final byte[] input2byte(InputStream inStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
         swapStream.write(buff, 0, rc);
        }
        return swapStream.toByteArray();
    }

    public static InputStream base64ToInputStream(String base64){
        ByteArrayInputStream stream = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes1 = decoder.decodeBuffer(base64);
            stream = new ByteArrayInputStream(bytes1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stream;
    }
}
