package com.ruoyi.common.core.domain.event;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 拉黑事件
 *
 * @author Lion Li
 */

@Data
public class UserBlackEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 对象名称
     */
    private String blackName;

    /**
     * 对象类型
     */
    private String blackType;

    /**
     * 锁定原因
     */
    private String remark;

    private String isLock;



}
