package com.yuxie.tgd.pojo.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户与对象关系
 * Created by 147356 on 2017/4/8.
 */
public class ObjectUserStatus implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    private Long statusId;
    //对象ID
    private Long objectId;
    //用户ID
    private Long userId;
    //状态值
    private Integer statusNum;
    //是否激活此条记录状态值 0不激活 1激活
    private Integer showNum;
    //创建时间
    private Timestamp createTime;
    //更新时间
    private Timestamp updateTime;

    @Override
    public String toString() {
        return "ObjectUserStatus{" +
                "statusId=" + statusId +
                ", objectId=" + objectId +
                ", userId=" + userId +
                ", statusNum=" + statusNum +
                ", showNum=" + showNum +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
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

    public Integer getStatusNum() {
        return statusNum;
    }

    public void setStatusNum(Integer statusNum) {
        this.statusNum = statusNum;
    }

    public Integer getShowNum() {
        return showNum;
    }

    public void setShowNum(Integer showNum) {
        this.showNum = showNum;
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
