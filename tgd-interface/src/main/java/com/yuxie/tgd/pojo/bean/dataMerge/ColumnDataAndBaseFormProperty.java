package com.yuxie.tgd.pojo.bean.dataMerge;

import java.io.Serializable;

public class ColumnDataAndBaseFormProperty implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //该列的排序
    private int columnOrder;
    //该列的key
    private String columnKey;
    //该列的值
    private Object columnData;
    //使用的组件类型
    private String moduleType;

    @Override
    public String toString() {
        return "ColumnDataAndBaseFormProperty{" +
                "columnKey='" + columnKey + '\'' +
                ", columnData=" + columnData +
                ", columnOrder=" + columnOrder +
                ", moduleType='" + moduleType + '\'' +
                '}';
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public Object getColumnData() {
        return columnData;
    }

    public void setColumnData(Object columnData) {
        this.columnData = columnData;
    }

    public int getColumnOrder() {
        return columnOrder;
    }

    public void setColumnOrder(int columnOrder) {
        this.columnOrder = columnOrder;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }
}
