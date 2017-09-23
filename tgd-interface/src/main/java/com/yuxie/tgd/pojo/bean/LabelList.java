package com.yuxie.tgd.pojo.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by 147356 on 2017/4/8.
 */
public class LabelList implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //标签ID
    private Long labelId;
    //标签名
    private String labelName;
    //标签热度
    private Long hotNum;
    //首次创建者ID
    private Long userId;
    //创建时间
    private Timestamp createTime;
    //最后更新热度时间
    private Timestamp updateTime;

    @Override
    public String toString() {
        return "LabelList{" +
                "labelId=" + labelId +
                ", labelName='" + labelName + '\'' +
                ", hotNum=" + hotNum +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Long getHotNum() {
        return hotNum;
    }

    public void setHotNum(Long hotNum) {
        this.hotNum = hotNum;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
