package com.yuxie.tgd.pojo.bean.dataMerge;

import java.io.Serializable;

public class DateUnit implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //创建时间
    private String creataTime = "00000000";
    //更新时间
    private String updateTime = "00000000";

    @Override
    public String toString() {
        return "DateUnit{" +
                "creataTime='" + creataTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }

    public String getCreataTime() {
        return creataTime;
    }

    public void setCreataTime(String creataTime) {
        this.creataTime = creataTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
