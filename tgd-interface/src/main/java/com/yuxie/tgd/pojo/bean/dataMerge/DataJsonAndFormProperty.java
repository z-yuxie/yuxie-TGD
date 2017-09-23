package com.yuxie.tgd.pojo.bean.dataMerge;

public class DataJsonAndFormProperty extends DataJsonAndOrder {
    //是否允许编辑
    private boolean canBeEdit;

    @Override
    public String toString() {
        return "DataJsonAndFormProperty{" +
                "canBeEdit=" + canBeEdit +
                "} " + super.toString();
    }

    public boolean isCanBeEdit() {
        return canBeEdit;
    }

    public void setCanBeEdit(boolean canBeEdit) {
        this.canBeEdit = canBeEdit;
    }
}
