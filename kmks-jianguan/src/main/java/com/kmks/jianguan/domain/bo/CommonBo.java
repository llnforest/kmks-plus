package com.kmks.jianguan.domain.bo;

import com.kmks.jianguan.params.FilesItemParam;
import com.kmks.jianguan.validate.CustomGroup;
import com.kmks.jianguan.validate.FileDataGroup;
import com.kmks.jianguan.validate.FileGroup;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/11 15:34
 */
@Data
public class CommonBo<T> {
    @Valid
    @NotNull(message = "data请求参数必传",groups = {CustomGroup.class})
    private T data;

    @Valid
    @NotEmpty(message = "files请求参数必传",groups = {FileGroup.class, FileDataGroup.class})
    private List<FilesItemParam> files;

    private CdxxBo cdxx;
}
