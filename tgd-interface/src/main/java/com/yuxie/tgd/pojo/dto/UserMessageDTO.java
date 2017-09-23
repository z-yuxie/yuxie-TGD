package com.yuxie.tgd.pojo.dto;

import java.io.Serializable;

/**
 * 用户详细信息入参数据接受类
 * Created by 147356 on 2017/4/8.
 */
public class UserMessageDTO implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //用户ID
    private Long userId;
    //会话ID
    private String sessionId;
    //用户经加密后的密码
    private String userPw;
    //用户名
    private String userName;
    //邮箱
    private String email;
    //手机号
    private Long phone;
    //个性签名
    private String contact;

    @Override
    public String toString() {
        return "UserMessageDTO{" +
                "userId=" + userId +
                ", sessionId='" + sessionId + '\'' +
                ", userPw='" + userPw + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", contact='" + contact + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
