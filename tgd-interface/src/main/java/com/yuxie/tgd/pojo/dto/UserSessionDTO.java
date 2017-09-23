package com.yuxie.tgd.pojo.dto;

import java.io.Serializable;

/**
 * Created by 147356 on 2017/4/11.
 */
public class UserSessionDTO implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //用户ID
    private Long userId;
    //会话ID
    private String sessionId;
    //被查询用户ID
    private Long checkedUserId;
    //被操作对象ID
    private Long objectId;
    //对象详情ID
    private Long detailId;
    //标签ID
    private Long labelId;
    //新的状态
    private Integer status;
    //答案
    private String answer;

    @Override
    public String toString() {
        return "UserSessionDTO{" +
                "userId=" + userId +
                ", sessionId='" + sessionId + '\'' +
                ", checkedUserId=" + checkedUserId +
                ", objectId=" + objectId +
                ", detailId=" + detailId +
                ", labelId=" + labelId +
                ", status=" + status +
                ", answer='" + answer + '\'' +
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

    public Long getCheckedUserId() {
        return checkedUserId;
    }

    public void setCheckedUserId(Long checkedUserId) {
        this.checkedUserId = checkedUserId;
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

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
