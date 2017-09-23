package com.yuxie.tgd.pojo.dto;

import java.io.Serializable;

/**
 * 获取对象或用户List参数整合
 * Created by 147356 on 2017/4/10.
 */
public class ListParamDTO implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //用户ID
    private Long userId;
    //会话ID
    private String sessionId;
    //用户名
    private String userName;
    //对象ID
    private Long objectId;
    //搜索内容 如:对象标题或简介或标签名
    private String seachWord;
    //标签名
    private String label;
    //排序规则 0按更新时间,1按创建时间
    private Integer sortType;
    //获取类型 -2所有异常用户或对象,-1被封禁,0所有正常用户或对象,1被举报,2获取全部,3关注的用户或收藏的对象
    private Integer listType;
    //页码
    private Integer pageNum;
    //对象类型
    private Integer objectType;
    //对象版本号
    private Integer objectVersion;

    @Override
    public String toString() {
        return "ListParamDTO{" +
                "userId=" + userId +
                ", sessionId='" + sessionId + '\'' +
                ", userName='" + userName + '\'' +
                ", objectId=" + objectId +
                ", seachWord='" + seachWord + '\'' +
                ", label='" + label + '\'' +
                ", sortType=" + sortType +
                ", listType=" + listType +
                ", pageNum=" + pageNum +
                ", objectType=" + objectType +
                ", objectVersion=" + objectVersion +
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getSeachWord() {
        return seachWord;
    }

    public void setSeachWord(String seachWord) {
        this.seachWord = seachWord;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public Integer getListType() {
        return listType;
    }

    public void setListType(Integer listType) {
        this.listType = listType;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    public Integer getObjectVersion() {
        return objectVersion;
    }

    public void setObjectVersion(Integer objectVersion) {
        this.objectVersion = objectVersion;
    }
}
