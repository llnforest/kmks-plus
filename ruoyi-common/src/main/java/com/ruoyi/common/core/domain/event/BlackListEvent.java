package com.ruoyi.common.core.domain.event;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 登录事件
 *
 * @author Lion Li
 */

@Data
public class BlackListEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 操作类别
     */
    private String category;

    /**
     * 操作类型
     */
    private String operation;

    /**
     * 操作结果
     */
    private String state;

    /**
     * 业务类型
     */
    private String ywtype;

    /**
     * 操作内容
     */
    private String content;

    /**
     * 请求体
     */
    private HttpServletRequest request;

    /**
     * 其他参数
     */
    private Object[] args;


}
