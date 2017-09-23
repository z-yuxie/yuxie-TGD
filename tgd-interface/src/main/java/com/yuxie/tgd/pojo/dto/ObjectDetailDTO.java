package com.yuxie.tgd.pojo.dto;

import java.io.Serializable;

/**
 * 对象详细信息入参数据接受类
 * Created by 147356 on 2017/4/10.
 */
public class ObjectDetailDTO implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //所有者ID
    private Long userId;
    //会话ID
    private String sessionId;
    //对象类型
    private Integer objectType;
    //详情内容
    private String objectMessage;
    //对象ID
    private Long objectId;
    //详情ID
    private Long detailId;
    //父对象ID号
    private Long parentObjectId;
    //父对象版本号
    private Integer parentObjectVersion;
    //对象标题
    private String objectTitle;
    //对象简介
    private String objectIntroduction;
    //标签集
    private String objectLabelList;
    //报名所需资源数
    private Integer resourceEnter;
    //跳过支付资源数
    private Integer resourceSkip;
    //答案关键词
    private String keyWord;

    @Override
    public String toString() {
        return "ObjectDetailDTO{" +
                "userId=" + userId +
                ", sessionId='" + sessionId + '\'' +
                ", objectType=" + objectType +
                ", objectMessage='" + objectMessage + '\'' +
                ", objectId=" + objectId +
                ", detailId=" + detailId +
                ", parentObjectId=" + parentObjectId +
                ", parentObjectVersion=" + parentObjectVersion +
                ", objectTitle='" + objectTitle + '\'' +
                ", objectIntroduction='" + objectIntroduction + '\'' +
                ", objectLabelList='" + objectLabelList + '\'' +
                ", resourceEnter=" + resourceEnter +
                ", resourceSkip=" + resourceSkip +
                ", keyWord='" + keyWord + '\'' +
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

    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    public String getObjectMessage() {
        return objectMessage;
    }

    public void setObjectMessage(String objectMessage) {
        this.objectMessage = objectMessage;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Long getParentObjectId() {
        return parentObjectId;
    }

    public void setParentObjectId(Long parentObjectId) {
        this.parentObjectId = parentObjectId;
    }

    public Integer getParentObjectVersion() {
        return parentObjectVersion;
    }

    public void setParentObjectVersion(Integer parentObjectVersion) {
        this.parentObjectVersion = parentObjectVersion;
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

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
