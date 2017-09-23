package com.yuxie.tgd.pojo.vo;

import java.io.Serializable;

/**
 * 用户基本信息包装类
 * Created by 147356 on 2017/4/8.
 */
public class UserBaseVO implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //用户ID
    private Long userId;
    //用户名
    private String userName;
    //用户个性签名
    private String contact;
    //用户当前状态
    private Integer userStatus;

    @Override
    public String toString() {
        return "UserBaseVO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", contact='" + contact + '\'' +
                ", userStatus=" + userStatus +
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
}
