package com.yuxie.tgd.pojo.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by 郁写 on 2017/4/4 0004.
 */
public class YuxieUser implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //用户ID
    private Long userId;
    //用户名
    private String userName;
    //用户密码
    private String userPw;
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
    //注册时间
    private Timestamp resgitTime;
    //更新时间
    private Timestamp updateTime;

    @Override
    public String toString() {
        return "YuxieUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPw='" + userPw + '\'' +
                ", userLevel=" + userLevel +
                ", userStatus=" + userStatus +
                ", resource=" + resource +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", contact='" + contact + '\'' +
                ", resgitTime=" + resgitTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
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

    public Timestamp getResgitTime() {
        return resgitTime;
    }

    public void setResgitTime(Timestamp resgitTime) {
        this.resgitTime = resgitTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
