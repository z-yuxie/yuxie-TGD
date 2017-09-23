package com.yuxie.tgd.pojo.bean.dataMerge;

import java.io.Serializable;
import java.util.Date;

public class DataUnit implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //数据来源优先级排序号
    private int priority = Integer.MAX_VALUE;
    //数据来源 0来自用户自行上传 1来自官方 2第三方认证机构 3实名认证 4数据平台
    private int dataSource = Integer.MIN_VALUE;
    //是否经过认证 0未认证 1已完全认证 2部分认证
    private int hasVerified = Integer.MIN_VALUE;
    //数据本身
    private Object data;
    //创建时间
    private Date creataTime;
    //更新时间
    private Date updateTime;

    @Override
    public String toString() {
        return "DataUnit{" +
                "priority=" + priority +
                ", dataSource=" + dataSource +
                ", hasVerified=" + hasVerified +
                ", data=" + data +
                ", creataTime=" + creataTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getDataSource() {
        return dataSource;
    }

    public void setDataSource(int dataSource) {
        this.dataSource = dataSource;
    }

    public int getHasVerified() {
        return hasVerified;
    }

    public void setHasVerified(int hasVerified) {
        this.hasVerified = hasVerified;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Date getCreataTime() {
        return creataTime;
    }

    public void setCreataTime(Date creataTime) {
        this.creataTime = creataTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
