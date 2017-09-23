package com.yuxie.tgd.pojo.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * Created by 147356 on 2017/4/8.
 */
public class UserMessageVO implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //用户ID
    private Long userId;
    //会话ID
    private String sessionId;
    //用户名
    private String userName;
    //权限等级
    private Integer userLevel;
    //用户状态
    private Integer userStatus;
    //虚拟币数量
    private Integer resource;
    //邮箱
    private String email;
    //手机号
    private Long phone;
    //个性签名
    private String contact;
    //关注状态
    private Integer relationType;
    //注册时间
    private Timestamp registTime;

    @Override
    public String toString() {
        return "UserMessageVO{" +
                "userId=" + userId +
                ", sessionId='" + sessionId + '\'' +
                ", userName='" + userName + '\'' +
                ", userLevel=" + userLevel +
                ", userStatus=" + userStatus +
                ", resource=" + resource +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", contact='" + contact + '\'' +
                ", relationType=" + relationType +
                ", registTime=" + registTime +
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getResource() {
        return resource;
    }

    public void setResource(Integer resource) {
        this.resource = resource;
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

    public Integer getRelationType() {
        return relationType;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
    }

    public Timestamp getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Timestamp registTime) {
        this.registTime = registTime;
    }
}
