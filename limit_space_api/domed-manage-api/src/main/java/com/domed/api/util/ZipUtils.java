package com.domed.api.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class ZipUtils {

    private static Logger logger = LoggerFactory.getLogger(ZipUtils.class);
    // 目录标识判断符
    private static final String PATCH = "/";
    // 基目录
    private static final String BASE_DIR = "/b/";
    // 缓冲区大小
    private static final int BUFFER = 2048;
    // 字符集
    private static final String CHAR_SET = "UTF-8";

    public static void compress(File srcFile, ZipOutputStream zipOutputStream, String basePath, String fileName) throws Exception {
        if (srcFile.isDirectory()) {
            compressDir(srcFile, zipOutputStream, basePath, fileName);
        } else {
            compressFile(srcFile, zipOutputStream, basePath, fileName);
        }
    }


    private static void compressDir(File dir, ZipOutputStream zipOutputStream, String basePath, String fileName) throws Exception {
        try {
            // 获取文件列表
            File[] files = dir.listFiles();

            if (files.length < 1) {
                ZipEntry zipEntry = new ZipEntry(basePath + dir.getName() + PATCH);

                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.closeEntry();
            }

            for (int i = 0, size = files.length; i < size; i++) {
                compress(files[i], zipOutputStream, basePath + dir.getName() + PATCH, fileName);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    private static void compressFile(File file, ZipOutputStream zipOutputStream, String dir, String fileName) throws Exception {
        BufferedInputStream bis = null;
        try {
            // 压缩文件
            ZipEntry zipEntry = new ZipEntry(dir + fileName);
            zipOutputStream.putNextEntry(zipEntry);

            // 读取文件
            bis = new BufferedInputStream(new FileInputStream(file));

            int count = 0;
            byte data[] = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                zipOutputStream.write(data, 0, count);
            }
            bis.close();
            zipOutputStream.closeEntry();
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
