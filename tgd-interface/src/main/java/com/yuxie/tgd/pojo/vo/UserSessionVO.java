package com.yuxie.tgd.pojo.vo;

import java.io.Serializable;

/**
 * Created by 147356 on 2017/4/8.
 */
public class UserSessionVO implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //用户ID
    private Long userId;
    //用户权限值
    private Integer userLevel;
    //会话ID
    private String sessionId;

    @Override
    public String toString() {
        return "UserSessionDTO{" +
                "userId=" + userId +
                ", userLevel=" + userLevel +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
