package com.yuxie.tgd.pojo.dto;

import java.io.Serializable;

/**
 * 接收某对象的ID和该对象的类型
 * Created by 147356 on 2017/4/21.
 */
public class ObjectIdDTO implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //对象ID
    private Long objectId;
    //对象的创建者
    private Long userId;
    //对象类型
    private Integer objectType;

    @Override
    public String toString() {
        return "ObjectIdDTO{" +
                "objectId=" + objectId +
                ", userId=" + userId +
                ", objectType=" + objectType +
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

    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }
}
