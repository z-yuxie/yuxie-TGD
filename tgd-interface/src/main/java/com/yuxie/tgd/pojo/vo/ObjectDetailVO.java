package com.yuxie.tgd.pojo.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by 147356 on 2017/4/8.
 */
public class ObjectDetailVO implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //对象ID
    private Long objectId;
    //对象类型
    private Integer objectType;
    //与对象关系
    private List<Integer> objectUserStatus;
    //所有者ID
    private Long userId;
    //所有者名称
    private String userName;
    //对象版本号
    private Integer objectVersion;
    //对象标题
    private String objectTitle;
    //对象简介
    private String objectIntroduction;
    //标签集
    private String objectLabelList;
    //奖池积累资源数
    private Integer resourcePool;
    //报名所需资源数
    private Integer resourceEnter;
    //跳过支付资源数
    private Integer resourceSkip;
    //对象状态
    private Integer objectStatus;
    //被赞数
    private Integer upTimes;
    //被踩数
    private Integer downTimes;
    //创建时间
    private String createTime;
    //更新时间
    private String updateTime;
    //详情内容
    private String objectMessage;

    @Override
    public String toString() {
        return "ObjectDetailVO{" +
                "objectId=" + objectId +
                ", objectType=" + objectType +
                ", objectUserStatus=" + objectUserStatus +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", objectVersion=" + objectVersion +
                ", objectTitle='" + objectTitle + '\'' +
                ", objectIntroduction='" + objectIntroduction + '\'' +
                ", objectLabelList='" + objectLabelList + '\'' +
                ", resourcePool=" + resourcePool +
                ", resourceEnter=" + resourceEnter +
                ", resourceSkip=" + resourceSkip +
                ", objectStatus=" + objectStatus +
                ", upTimes=" + upTimes +
                ", downTimes=" + downTimes +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", objectMessage='" + objectMessage + '\'' +
                '}';
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    public List<Integer> getObjectUserStatus() {
        return objectUserStatus;
    }

    public void setObjectUserStatus(List<Integer> objectUserStatus) {
        this.objectUserStatus = objectUserStatus;
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

    public Integer getObjectVersion() {
        return objectVersion;
    }

    public void setObjectVersion(Integer objectVersion) {
        this.objectVersion = objectVersion;
    }

    public String getObjectTitle() {
        return objectTitle;
    }

    public void setObjectTitle(String objectTitle) {
        this.objectTitle = objectTitle;
    }

    public String getObjectIntroduction() {
        return objectIntroduction;
    }

    public void setObjectIntroduction(String objectIntroduction) {
        this.objectIntroduction = objectIntroduction;
    }

    public String getObjectLabelList() {
        return objectLabelList;
    }

    public void setObjectLabelList(String objectLabelList) {
        this.objectLabelList = objectLabelList;
    }

    public Integer getResourcePool() {
        return resourcePool;
    }

    public void setResourcePool(Integer resourcePool) {
        this.resourcePool = resourcePool;
    }

    public Integer getResourceEnter() {
        return resourceEnter;
    }

    public void setResourceEnter(Integer resourceEnter) {
        this.resourceEnter = resourceEnter;
    }

    public Integer getResourceSkip() {
        return resourceSkip;
    }

    public void setResourceSkip(Integer resourceSkip) {
        this.resourceSkip = resourceSkip;
    }

    public Integer getObjectStatus() {
        return objectStatus;
    }

    public void setObjectStatus(Integer objectStatus) {
        this.objectStatus = objectStatus;
    }

    public Integer getUpTimes() {
        return upTimes;
    }

    public void setUpTimes(Integer upTimes) {
        this.upTimes = upTimes;
    }

    public Integer getDownTimes() {
        return downTimes;
    }

    public void setDownTimes(Integer downTimes) {
        this.downTimes = downTimes;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getObjectMessage() {
        return objectMessage;
    }

    public void setObjectMessage(String objectMessage) {
        this.objectMessage = objectMessage;
    }
}
