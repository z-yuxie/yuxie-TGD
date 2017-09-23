package com.yuxie.tgd.pojo.bean;

import java.io.Serializable;

/**
 * Created by 147356 on 2017/4/17.
 */
public class UserRelation implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //关系ID
    private Long relationId;
    //用户ID
    private Long userId;
    //被关注用户ID
    private Long relationUserId;
    //关注状态值
    private Integer relationType;

    @Override
    public String toString() {
        return "UserRelation{" +
                "relationId=" + relationId +
                ", userId=" + userId +
                ", relationUserId=" + relationUserId +
                ", relationType=" + relationType +
                '}';
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRelationUserId() {
        return relationUserId;
    }

    public void setRelationUserId(Long relationUserId) {
        this.relationUserId = relationUserId;
    }

    public Integer getRelationType() {
        return relationType;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
    }
}
