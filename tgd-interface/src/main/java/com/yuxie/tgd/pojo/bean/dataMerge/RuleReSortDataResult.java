package com.yuxie.tgd.pojo.bean.dataMerge;

import java.io.Serializable;
import java.util.List;

/**
 * 根据规则对数据进行重排序后的返回对象
 */
public class RuleReSortDataResult implements Serializable {
    //序列化ID
    private static final long serialVersionUID = 1L;
    //重排序后的规则分界点,该数值为重排序后的第一个不满足规则的元素的下标
    private int rulePoint = Integer.MAX_VALUE;
    //重排序后的数据集合
    private List<DataJsonAndOrder> dataJsonAndOrderList;

    @Override
    public String toString() {
        return "RuleReSortDataResult{" +
                "rulePoint=" + rulePoint +
                ", dataJsonAndOrderList=" + dataJsonAndOrderList +
                '}';
    }

    public int getRulePoint() {
        return rulePoint;
    }

    public void setRulePoint(int rulePoint) {
        this.rulePoint = rulePoint;
    }

    public List<DataJsonAndOrder> getDataJsonAndOrderList() {
        return dataJsonAndOrderList;
    }

    public void setDataJsonAndOrderList(List<DataJsonAndOrder> dataJsonAndOrderList) {
        this.dataJsonAndOrderList = dataJsonAndOrderList;
    }
}
