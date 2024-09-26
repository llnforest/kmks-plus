package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/11 15:34
 */
@Data
public class CommonOldBo<T> {
    @Valid
    @NotNull(message = "data请求参数必传")
    private T data;

    private CdxxOldBo cdxx;
}
