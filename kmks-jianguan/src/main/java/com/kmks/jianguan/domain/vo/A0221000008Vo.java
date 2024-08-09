package com.kmks.jianguan.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
@AllArgsConstructor
public class A0221000008Vo {
    // 标记：1-成功；其他代码见业务接口错误码
    private String code;
    // 成功则返回当前分配的考生身份证明号码，失败则返回失败信息描述
    private String message;
    // 对于科目三，返回备案的考试路线编号
    private String retval;
}
