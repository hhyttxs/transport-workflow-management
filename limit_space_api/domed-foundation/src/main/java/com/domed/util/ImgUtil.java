package com.domed.util;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

/**
 * @Title: ImgUtil
 * @Description: TODO
 * @param_code
 * @return:
 * @author: shanyuwei
 * Create Date: 2020/9/28  15:11
 * @throws
 */
public class ImgUtil {
    /**
     * 本地图片转换Base64的方法
     *
     */

    public static InputStream ImageToBase64(byte[] data) {
        InputStream retInputStream = new ByteArrayInputStream(data);
        return retInputStream;
    }

    /**
     * @Description: 压缩图片到指定大小
     * @Param:  desFileSize：大小，accuracy：每次缩小几倍
     * @return:  String
     * @Author: zzy
     * @Date: 2019/9/29
     */
    public static InputStream commpressPicCycle(byte[] bytes, long desFileSize, double accuracy) throws IOException {
        long srcFileSizeJPG = bytes.length;
        // 2、判断大小，如果小于，不压缩；如果大于等于，压缩
        if (srcFileSizeJPG <= desFileSize * 1024000) {
            return ImageToBase64(bytes);
        }
        // 计算宽高
        BufferedImage bim = ImageIO.read(new ByteArrayInputStream(bytes));
        int srcWdith = bim.getWidth();
        int srcHeigth = bim.getHeight();
        int desWidth = new BigDecimal(srcWdith).multiply(
                new BigDecimal(accuracy)).intValue();
        int desHeight = new BigDecimal(srcHeigth).multiply(
                new BigDecimal(accuracy)).intValue();

        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //字节输出流（写入到内存）
        Thumbnails.of(new ByteArrayInputStream(bytes)).size(desWidth, desHeight).outputQuality(accuracy).toOutputStream(baos);
        byte[] bytes1 = baos.toByteArray();
        baos.close();
        return commpressPicCycle(bytes1, desFileSize, accuracy);
    }


    public static InputStream compressPrice(InputStream input) throws IOException{
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        byte[] bytes = output.toByteArray();
        input.close();
        return commpressPicCycle(bytes,1L,0.8D);
    }
}
