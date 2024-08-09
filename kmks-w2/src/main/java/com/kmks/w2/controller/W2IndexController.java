package com.kmks.w2.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.XlsxTplType;
import com.ruoyi.common.webservice.WebServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 车辆合码器
 *
 * @author lynn
 * @date 2023-05-04
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2")
@Slf4j
public class W2IndexController extends BaseController {

    @PostMapping("/downloadTpl/{tpl}")
    public void download(@PathVariable("tpl") String tpl, HttpServletResponse response, HttpServletRequest request) {
        XlsxTplType tplEnum = XlsxTplType.findCode(tpl);
        try {
            // path是指欲下载的文件的路径。
            String path = this.getClass().getClassLoader().getResource("static/tpl/"+tplEnum.getFilename()).getPath();
            File file = new File(path);
            // 取得文件名。
//            String filename = file.getName();
            // 取得文件的后缀名。
//            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            String formFileName = "";
            String userAgent = request.getHeader("User-Agent");
            // 针对IE或者以IE为内核的浏览器：
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                formFileName = java.net.URLEncoder.encode(tplEnum.getOutFilename(), "UTF-8");
            } else {
                // 非IE浏览器的处理：
                formFileName = new String(tplEnum.getOutFilename().getBytes("UTF-8"), "ISO-8859-1");
            }
            // 设置response的Header
            response.setHeader("Content-disposition",String.format("attachment; filename=\"%s\"", formFileName));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
