package com.yuxie.tgd.pojo.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by 郁写 on 2017/4/4 0004.
 */
public class ObjectDetail implements Serializable{
    //序列化ID
    private static final long serialVersionUID = 1L;
    //详情ID
    private Long objectDetailId;
    //所属对象ID
    private Long objectId;
    //所有者ID
    private Long userId;
    //对象版本号
    private Integer objectVersion;
    //对象标题
    private String objectTitle;
    //对象简介
    private String objectIntroduction;
    //标签集
    private String objectLabelList;
    //详情内容ID
    private Long objectMessageId;
    //问题答案关键字
    private String objectKeyWord;
    //奖池积累资源数
    private Integer resourcePool;
    //报名所需资源数
    private Integer resourceEnter;
    //跳过支付资源数
    private Integer resourceSkip;
    //对象状态
    private Integer objectStatus;
    //参与该对象的人数
    private Integer involvedNum;
    //创建时间
    private Timestamp createTime;
    //更新时间
    private Timestamp updateTime;

    @Override
    public String toString() {
        return "ObjectDetail{" +
                "objectDetailId=" + objectDetailId +
                ", objectId=" + objectId +
                ", userId=" + userId +
                ", objectVersion=" + objectVersion +
                ", objectTitle='" + objectTitle + '\'' +
                ", objectIntroduction='" + objectIntroduction + '\'' +
                ", objectLabelList='" + objectLabelList + '\'' +
                ", objectMessageId=" + objectMessageId +
                ", objectKeyWord='" + objectKeyWord + '\'' +
                ", resourcePool=" + resourcePool +
                ", resourceEnter=" + resourceEnter +
                ", resourceSkip=" + resourceSkip +
                ", objectStatus=" + objectStatus +
                ", involvedNum=" + involvedNum +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Long getObjectDetailId() {
        return objectDetailId;
    }

    public void setObjectDetailId(Long objectDetailId) {
        this.objectDetailId = objectDetailId;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getObjectMessageId() {
        return objectMessageId;
    }

    public void setObjectMessageId(Long objectMessageId) {
        this.objectMessageId = objectMessageId;
    }

    public String getObjectKeyWord() {
        return objectKeyWord;
    }

    public void setObjectKeyWord(String objectKeyWord) {
        this.objectKeyWord = objectKeyWord;
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

    public Integer getInvolvedNum() {
        return involvedNum;
    }

    public void setInvolvedNum(Integer involvedNum) {
        this.involvedNum = involvedNum;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
