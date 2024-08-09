package com.kmks.w2.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * @author Star
 * @description: TODO
 * @date 2024/2/23 17:18
 */
@Slf4j
public class FileUtil {
    /**
     * 多部分文件转换为文件
     *
     * @param path          路径
     * @param multipartFile 多部件文件
     * @return {@link File}
     */
    public static File MultipartFileConvertToFile(String path, MultipartFile multipartFile) {
        File file = new File(path);
        try {
            existsOrCreatePath(path);
            multipartFile.transferTo(file);
            return file;
        } catch (Exception e) {
            log.error("IO处理异常:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 保存base64为文件
     *
     * @param base64Image    base64图像
     * @param outputFilePath 输出文件路径
     * @return {@link File}
     */
    public static File base64ConvertToFile(String base64Image, String outputFilePath) {
        try {
            // 解码Base64字符串
            byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

            // 创建目标文件
//            existsOrCreatePath(outputFilePath);
            File outputFile = new File(outputFilePath);

            // 将解码后的数据写入文件
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(decodedBytes);
            }

            return outputFile;
        } catch (IOException e) {
            log.error("base64转File异常：{}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static String convertImageToBase64(String imagePath){
        try {
            Path path = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(path);
            return Base64Utils.encodeToString(imageBytes);
        }catch (IOException e) {
            log.error("图片地址转换base64异常：{}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换为base64
     *
     * @param file 文件
     * @return {@link String}
     * @throws IOException IOException
     */
    public static String convertToBase64(MultipartFile file){
        try {
            // 获取文件的字节数组
            byte[] fileBytes = file.getBytes();

            // 使用 Base64 编码器将字节数组转换为 Base64 编码的字符串
            String base64String = Base64.getEncoder().encodeToString(fileBytes);

            // 可选：添加 MIME 类型头部信息
//        String mimeType = file.getContentType();
//        if (mimeType != null) {
//            base64String = "data:" + mimeType + ";base64," + base64String;
//        }

            return base64String;
        } catch (IOException e) {
            log.error("图片转换异常：{}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除图像
     *
     * @param imagePaths 图像路径
     */
    public static void deleteImage(String... imagePaths) {
        for (String imagePath : imagePaths) {
            Path path = Paths.get(imagePath);
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                log.error("删除图片异常:{}", e.getMessage());
                e.printStackTrace();
            }
        }

    }
    /**
     * 创建路径
     *
     * @param filePath 文件路径
     * @throws IOException IOException
     */
    private static void existsOrCreatePath(String filePath) throws IOException {
        // 检查目录是否存在，不存在则创建
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
}
