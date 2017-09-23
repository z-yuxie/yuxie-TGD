package com.yuxie.tgd.pojo.vo;

import java.io.Serializable;

/**
 * 标签返回VO
 * Created by 147356 on 2017/4/11.
 */
public class LabelVO implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //标签ID
    private Long labelId;
    //标签名
    private String labelName;
    //热度值
    private Long hotNum;
    //标签关注状态
    private Integer relationType;

    @Override
    public String toString() {
        return "LabelVO{" +
                "labelId=" + labelId +
                ", labelName='" + labelName + '\'' +
                ", hotNum=" + hotNum +
                ", relationType=" + relationType +
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

    public Integer getRelationType() {
        return relationType;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
    }
}
