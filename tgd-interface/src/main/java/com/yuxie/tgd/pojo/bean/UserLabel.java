package com.yuxie.tgd.pojo.bean;

import java.io.Serializable;

/**
 * Created by 147356 on 2017/4/17.
 */
public class UserLabel implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //关注ID
    private Long attentionId;
    //用户ID
    private Long userId;
    //标签ID
    private Long labelId;
    //关注类型
    private Integer attentionType;

    @Override
    public String toString() {
        return "UserLabel{" +
                "attentionId=" + attentionId +
                ", userId=" + userId +
                ", labelId=" + labelId +
                ", attentionType=" + attentionType +
                '}';
    }

    public Long getAttentionId() {
        return attentionId;
    }

    public void setAttentionId(Long attentionId) {
        this.attentionId = attentionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public Integer getAttentionType() {
        return attentionType;
    }

    public void setAttentionType(Integer attentionType) {
        this.attentionType = attentionType;
    }
}
