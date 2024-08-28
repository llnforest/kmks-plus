package com.kmks.w2.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.kmks.w2.config.FaceConfig;
import com.kmks.w2.service.IImageService;
import com.kmks.w2.utils.FileUtil;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.system.service.ISysConfigService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Star
 * @description: TODO
 * @date 2024/5/31 19:18
 */
@Service
public class ImageServiceImpl implements IImageService {

    @Autowired
    private ISysConfigService configService;
    /**
     * 保存图像
     *
     * @param img img
     * @return {@link String}
     */
    @Override
    public String saveImage(String img){
        String path =  configService.selectConfigByKey(CacheNames.FILE_UPLOAD_PATH) + RandomUtil.randomString(12)+".jpg";
        FileUtil.base64ConvertToFile(img,path);
        return path;
    }
}
