package com.yuxie.tgd.common.util;

import java.io.Serializable;

/**
 * session信息 〈功能详细描述〉
 *
 * @author 144637
 * @version [1.0.0, 2017年3月1日]
 */
public class SessionInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sessionId;// 前端会话id

    private String userId; // 用户ID

    private Integer userLevel;//用户权限值

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }
}
