package com.kmks.w2.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.kmks.w2.config.FaceConfig;
import com.kmks.w2.service.IImageService;
import com.kmks.w2.utils.FileUtil;
import org.springframework.stereotype.Service;

/**
 * @author Star
 * @description: TODO
 * @date 2024/5/31 19:18
 */
@Service
public class ImageServiceImpl implements IImageService {

    /**
     * 保存图像
     *
     * @param img img
     * @return {@link String}
     */
    @Override
    public String saveImage(String img){
        String path = FaceConfig.storagePath+ RandomUtil.randomString(12)+".jpg";
        FileUtil.base64ConvertToFile(img,path);
        return path;
    }
}
