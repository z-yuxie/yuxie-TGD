package com.yuxie.tgd.pojo.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by 郁写 on 2017/4/4 0004.
 */
public class ObjectLink implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //对象ID
    private Long objectId;
    //创建者ID
    private Long userId;
    //对象状态
    private Integer objectStatus;
    //对象类型
    private Integer objectType;
    //最新详情ID
    private Long maxDetailId;
    //父对象ID;
    private Long parentObjectId;
    //父对象版本号
    private Integer parentObjetVersion;
    //创建时间
    private Timestamp createTime;
    //更新时间
    private Timestamp updateTime;

    @Override
    public String toString() {
        return "ObjectLink{" +
                "objectId=" + objectId +
                ", userId=" + userId +
                ", objectStatus=" + objectStatus +
                ", objectType=" + objectType +
                ", maxDetailId=" + maxDetailId +
                ", parentObjectId=" + parentObjectId +
                ", parentObjetVersion=" + parentObjetVersion +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
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

    public Integer getObjectStatus() {
        return objectStatus;
    }

    public void setObjectStatus(Integer objectStatus) {
        this.objectStatus = objectStatus;
    }

    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    public Long getMaxDetailId() {
        return maxDetailId;
    }

    public void setMaxDetailId(Long maxDetailId) {
        this.maxDetailId = maxDetailId;
    }

    public Long getParentObjectId() {
        return parentObjectId;
    }

    public void setParentObjectId(Long parentObjectId) {
        this.parentObjectId = parentObjectId;
    }

    public Integer getParentObjetVersion() {
        return parentObjetVersion;
    }

    public void setParentObjetVersion(Integer parentObjetVersion) {
        this.parentObjetVersion = parentObjetVersion;
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
