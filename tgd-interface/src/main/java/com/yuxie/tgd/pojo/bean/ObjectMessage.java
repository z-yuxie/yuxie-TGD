package com.yuxie.tgd.pojo.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by 郁写 on 2017/4/4 0004.
 */
public class ObjectMessage implements Serializable{
    //序列化ID
    private static final long serialVersionUID = 1L;
    //对象内容ID
    private Long objectMessageId;
    //所属详情ID
    private Long objectDetailId;
    //对象内容
    private String objectMessageText;
    //创建时间
    private Timestamp createTime;
    //更新时间
    private Timestamp updateTime;

    @Override
    public String toString() {
        return "ObjectMessage{" +
                "objectMessageId=" + objectMessageId +
                ", objectDetailId=" + objectDetailId +
                ", objectMessageText='" + objectMessageText + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Long getObjectMessageId() {
        return objectMessageId;
    }

    public void setObjectMessageId(Long objectMessageId) {
        this.objectMessageId = objectMessageId;
    }

    public Long getObjectDetailId() {
        return objectDetailId;
    }

    public void setObjectDetailId(Long objectDetailId) {
        this.objectDetailId = objectDetailId;
    }

    public String getObjectMessageText() {
        return objectMessageText;
    }

    public void setObjectMessageText(String objectMessageText) {
        this.objectMessageText = objectMessageText;
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
