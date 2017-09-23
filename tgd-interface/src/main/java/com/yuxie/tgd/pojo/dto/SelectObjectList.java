package com.yuxie.tgd.pojo.dto;

/**
 * Created by 147356 on 2017/4/25.
 */
public class SelectObjectList {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //用户ID
    private Long userId;
    //父对象ID
    private Long parentObjectId;
    //父对象版本
    private Integer parentObjectVersion;
    //对象类型
    private Integer objectType;
    //获取状态种类
    private Integer listType;
    //排序规则
    private Integer sortType;

    @Override
    public String toString() {
        return "SelectObjectList{" +
                "userId=" + userId +
                ", parentObjectId=" + parentObjectId +
                ", parentObjectVersion=" + parentObjectVersion +
                ", objectType=" + objectType +
                ", listType=" + listType +
                ", sortType=" + sortType +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getParentObjectId() {
        return parentObjectId;
    }

    public void setParentObjectId(Long parentObjectId) {
        this.parentObjectId = parentObjectId;
    }

    public Integer getParentObjectVersion() {
        return parentObjectVersion;
    }

    public void setParentObjectVersion(Integer parentObjectVersion) {
        this.parentObjectVersion = parentObjectVersion;
    }

    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    public Integer getListType() {
        return listType;
    }

    public void setListType(Integer listType) {
        this.listType = listType;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }
}
