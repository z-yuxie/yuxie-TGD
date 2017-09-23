package com.yuxie.tgd.pojo.vo;

import java.io.Serializable;

/**
 * Created by 147356 on 2017/4/8.
 */
public class ObjectBaseVO implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //对象ID
    private Long objectId;
    //详情ID
    private Long detailId;
    //父对象ID
    private Long parentObjectId;
    //创建者ID
    private Long userId;
    //创建者名称
    private String userName;
    //当前所有者
    private Long ownerId;
    //所有者名称
    private String ownerName;
    //被赞数
    private Integer upTimes;
    //被踩数
    private Integer downTimes;
    //对象标题
    private String objectTitle;
    //对象简介
    private String objectIntroduction;
    //对象标签集
    private String objectLabelList;
    //对象类型
    private Integer objectType;
    //参与人数
    private Integer involvedNum;
    //被举报次数
    private Integer status;
    //奖池累计值
    private Integer resourcePool;

    @Override
    public String toString() {
        return "ObjectBaseVO{" +
                "objectId=" + objectId +
                ", detailId=" + detailId +
                ", parentObjectId=" + parentObjectId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", ownerId=" + ownerId +
                ", ownerName='" + ownerName + '\'' +
                ", upTimes=" + upTimes +
                ", downTimes=" + downTimes +
                ", objectTitle='" + objectTitle + '\'' +
                ", objectIntroduction='" + objectIntroduction + '\'' +
                ", objectLabelList='" + objectLabelList + '\'' +
                ", objectType=" + objectType +
                ", involvedNum=" + involvedNum +
                ", status=" + status +
                ", resourcePool=" + resourcePool +
                '}';
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    public Integer getInvolvedNum() {
        return involvedNum;
    }

    public void setInvolvedNum(Integer involvedNum) {
        this.involvedNum = involvedNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getResourcePool() {
        return resourcePool;
    }

    public void setResourcePool(Integer resourcePool) {
        this.resourcePool = resourcePool;
    }
}
