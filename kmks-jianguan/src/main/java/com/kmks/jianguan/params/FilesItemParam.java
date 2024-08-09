package com.kmks.jianguan.params;


import com.kmks.jianguan.validate.FileDataGroup;
import com.kmks.jianguan.validate.FileGroup;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/9 11:20
 */
@Data
@AllArgsConstructor
public class FilesItemParam {
    // 文件唯一代号
    @NotBlank(message="文件唯一代号不能为空",groups = {FileGroup.class})
    private String fileid;

    // Base64 字符串格式的文件内容
    @NotBlank(message = "Base64 字符串格式的文件内容不能为空",groups = {FileDataGroup.class,FileGroup.class})
    private String data;
}
