package com.yuxie.tgd.common.util;



import com.yuxie.tgd.pojo.bean.dataMerge.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 按指定规则对数据进行重排序并返回重排序后的规则分界点;对已按照需要的规则排序好的数据重设优先级
 */
public class ClassifyDataUtils {

    //部分经过认证
    private static final int PARTIAL_VERIFIED = 2;
    //全部经过认证
    private static final int HAS_VERIFIED = 1;
    //未经过认证
    private static final int NOT_VERIFIED = 0;
    //允许编辑
    private static final boolean CAN_BE_EDIT = Boolean.TRUE;

    /**
     * 根据优先级高低排名,将优先级高于等于某个名次对应的优先级的数据与低于该名次对应优先级的数据进行区分
     * @param ranking 名次,重排序后,下标从 0 到 ranking-1 为满足条件的数据,下标从ranking都最后为不满足条件的数据
     * @param dataJsonAndOrderList 重排序前的数据
     * @return
     */
    public static RuleReSortDataResult classifyDataByPriorityTop(int ranking , List<DataJsonAndOrder> dataJsonAndOrderList) {
        SortDataUtils.asceSortDataJsonByPriority(dataJsonAndOrderList);
        RuleReSortDataResult ruleReSortDataResult = new RuleReSortDataResult();
        ruleReSortDataResult.setRulePoint(ranking);
        ruleReSortDataResult.setDataJsonAndOrderList(dataJsonAndOrderList);
        return ruleReSortDataResult;
    }

    /**
     * 指定某个优先级数值,优先级不低于该指定值的即为合乎要求的数据,否则为不符合要求的数据
     * @param priority 指定的优先级数值
     * @param dataJsonAndOrderList 元数据
     * @return
     */
    public static RuleReSortDataResult classifyDataByPriority(int priority , List<? extends DataJsonAndOrder> dataJsonAndOrderList) {
        SortDataUtils.asceSortDataJsonByPriority(dataJsonAndOrderList);
        List<DataJsonAndOrder> qualifiedDataList = new ArrayList<>();
        List<DataJsonAndOrder> notQualifiedDataList = new ArrayList<>();
        for (DataJsonAndOrder dataJsonAndOrder : dataJsonAndOrderList) {
            if (priority >= dataJsonAndOrder.getPriority()) {
                qualifiedDataList.add(dataJsonAndOrder);
            } else {
                notQualifiedDataList.add(dataJsonAndOrder);
            }
        }
        return setRuleReSortDataResult(qualifiedDataList , notQualifiedDataList);
    }

    /**
     * 以是否经过认证为判断条件进行分类重排序
     * @param dataJsonAndOrderList 元数据
     * @return RuleReSortDataResult
     */
    public static RuleReSortDataResult classifyDataByHasVerified(List<? extends DataJsonAndOrder> dataJsonAndOrderList) {
        SortDataUtils.asceSortDataJsonByPriority(dataJsonAndOrderList);
        List<DataJsonAndOrder> qualifiedDataList = new ArrayList<>();
        List<DataJsonAndOrder> notQualifiedDataList = new ArrayList<>();
        for (DataJsonAndOrder dataJsonAndOrder : dataJsonAndOrderList) {
            if (HAS_VERIFIED == dataJsonAndOrder.getPriority()) {
                qualifiedDataList.add(dataJsonAndOrder);
            } else {
                notQualifiedDataList.add(dataJsonAndOrder);
            }
        }
        return setRuleReSortDataResult(qualifiedDataList , notQualifiedDataList);
    }

    /**
     * 根据时候允许编辑进行数据分类重排序
     * @param dataJsonAndFormPropertieList 元数据
     * @return RuleReSortDataResult
     */
    public static RuleReSortDataResult classifyDataByCanBeEdit(List<? extends DataJsonAndFormProperty> dataJsonAndFormPropertieList) {
        SortDataUtils.asceSortDataJsonByPriority(dataJsonAndFormPropertieList);
        List<DataJsonAndOrder> qualifiedDataList = new ArrayList<>();
        List<DataJsonAndOrder> notQualifiedDataList = new ArrayList<>();
        for (DataJsonAndFormProperty dataJsonAndFormProperty : dataJsonAndFormPropertieList) {
            if (dataJsonAndFormProperty.isCanBeEdit()) {
                qualifiedDataList.add(dataJsonAndFormProperty);
            } else {
                notQualifiedDataList.add(dataJsonAndFormProperty);
            }
        }
        return setRuleReSortDataResult(qualifiedDataList , notQualifiedDataList);
    }

    /**
     * 指定某个来源,优先级不低于该指定来源对应的优先级的数据的即为合乎要求的数据,否则为不符合要求的数据
     * @param dataSource
     * @param dataJsonAndOrderList
     * @return
     */
    public static RuleReSortDataResult classifyDataByDataSource(int dataSource , List<? extends DataJsonAndOrder> dataJsonAndOrderList) {
        int priority = Integer.MIN_VALUE;
        for (DataJsonAndOrder dataJsonAndOrder : dataJsonAndOrderList) {
            if (dataSource == dataJsonAndOrder.getDataSource()) {
                priority = dataJsonAndOrder.getPriority();
                break;
            }
        }
        return classifyDataByPriority(priority , dataJsonAndOrderList);
    }

    /**
     * 将重排序好的数据重设优先权
     * @param dataJsonAndOrderList 待重新设置优先权的已排序好的数据list
     */
    public static void reSetDataPriority(List<? extends DataJsonAndOrder> dataJsonAndOrderList) {
        for (int i = 0; i < dataJsonAndOrderList.size(); i++) {
            dataJsonAndOrderList.get(i).setPriority(i);
        }
    }

    /**
     * 设置RuleReSortDataResult的值
     * @param qualifiedDataList 符合规则的数据的list
     * @param notQualifiedDataList 不符合规则的数据的list
     * @return RuleReSortDataResult
     */
    private static RuleReSortDataResult setRuleReSortDataResult(List<DataJsonAndOrder> qualifiedDataList
            , List<DataJsonAndOrder> notQualifiedDataList) {
        int rulePoint = qualifiedDataList.size();
        SortDataUtils.asceSortDataJsonByPriority(qualifiedDataList);
        SortDataUtils.asceSortDataJsonByPriority(notQualifiedDataList);
        qualifiedDataList.addAll(notQualifiedDataList);
        RuleReSortDataResult ruleReSortDataResult = new RuleReSortDataResult();
        ruleReSortDataResult.setRulePoint(rulePoint);
        ruleReSortDataResult.setDataJsonAndOrderList(qualifiedDataList);
        return ruleReSortDataResult;
    }
}
