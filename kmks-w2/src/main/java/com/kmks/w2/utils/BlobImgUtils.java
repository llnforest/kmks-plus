package com.kmks.w2.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

/**
 * 处理blob 格式图片
 */
@Slf4j
public class BlobImgUtils {

    public void getImgInfo(Blob img, HttpServletResponse response){
        InputStream in = null;
        ServletOutputStream out = null;
        try {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            if (img != null) {
                in = img.getBinaryStream();
            }
            if (in !=null) {
                log.info("不存在图片，设置默认值");
                String defaultUrl = "images/icon.jpg";
                in = Thread.currentThread().getContextClassLoader().getResourceAsStream(defaultUrl);
            }
            out = response.getOutputStream();
            IOUtils.copy(in,out);
        } catch (Exception e) {
            log.info("e",e);
        } finally {
            if (in!=null) {
                log.info("关闭输入流");
                try {
                    in.close();
                } catch (IOException e) {
                    log.info("关闭输入流异常");
                }
            }
            if (out!=null) {
                log.info("关闭输出流");
                try {
                    out.close();
                } catch (IOException e) {
                    log.info("关闭输出流异常");
                }
            }
        }
    }

}
