package com.yuxie.tgd.common.util;

import com.alibaba.fastjson.JSONObject;
import com.yuxie.tgd.pojo.bean.dataMerge.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataStatusUtils {

    /**
     * 获取某个要合并的数据的数据来源,该来源只取优先级最高的
     * @param keySet 有效的key的集合
     * @param dataJsonAndOrderList 合并前的各个来源的数据
     * @return 数据来源
     */
    public static int checkDataSourceByPriority(Set<String> keySet , List<DataJsonAndOrder> dataJsonAndOrderList) {
        //首先对数据json按照优先级进行排序
        SortDataUtils.asceSortDataJsonByPriority(dataJsonAndOrderList);
        int dataSource = Integer.MIN_VALUE;
        boolean hasFoundKey = Boolean.TRUE;
        int i = 0;
        do {
            DataJsonAndOrder dataJsonAndOrder = dataJsonAndOrderList.get(i);
            for (String key : keySet) {
                if (JSONDataUtils.checkJsonContainsStringData(key , dataJsonAndOrder.getData())) {
                    dataSource = dataJsonAndOrder.getDataSource();
                    hasFoundKey = Boolean.FALSE;
                    break;
                }
            }
            i++;
        } while (hasFoundKey || i <= dataJsonAndOrderList.size());
        return dataSource;
    }

    /**
     * 根据数据来源判断数据的完善状态
     * @param dataSource 作为分界点的数据来源,优先权高于或等于该来源的数据为判断数据初始完整性的数据,优先级低于该来源的数据为补充数据
     * @param keySet 判断数据完善状态依据的有效key的集合
     * @param dataJsonAndOrderList 元数据
     * @return 完善状态
     */
    public static int checkDataCompleteStatusByDataSource(int dataSource , Set<String> keySet , List<? extends DataJsonAndOrder> dataJsonAndOrderList) {
        RuleReSortDataResult ruleReSortDataResult = ClassifyDataUtils.classifyDataByDataSource(dataSource , dataJsonAndOrderList);
        ClassifyDataUtils.reSetDataPriority(ruleReSortDataResult.getDataJsonAndOrderList());
        int rulePoint = ruleReSortDataResult.getRulePoint();
        List<DataJsonAndOrder> qualifiedDataList = ruleReSortDataResult.getDataJsonAndOrderList().subList(0 , rulePoint);
        JSONObject qualifiedDataMergeJson = MergeDataUtils.mergeJsonStringDataByPriority(keySet , qualifiedDataList);
        JSONObject allDataMergeJson = MergeDataUtils.mergeJsonStringDataByPriority(keySet , dataJsonAndOrderList);
        Set<String> qualifiedNotCompleteKeysSet = JSONDataUtils.getNotCompleteKeysSet(keySet , qualifiedDataMergeJson , Boolean.FALSE);
        int completeStatus = MyConstans.IS_COMPLETED;
        if (CollectionUtils.isNotEmpty(qualifiedNotCompleteKeysSet)) {
            completeStatus = MyConstans.IS_NOT_COMPLETED;
            Set<String> qualifiedNotBeCompleteKeysSet = JSONDataUtils.getNotCompleteKeysSet(qualifiedNotCompleteKeysSet , allDataMergeJson , Boolean.FALSE);
            if (qualifiedNotBeCompleteKeysSet.size() != qualifiedNotCompleteKeysSet.size()) {
                if (CollectionUtils.isEmpty(qualifiedNotBeCompleteKeysSet)) {
                    completeStatus = MyConstans.BECOME_COMPLETED;
                } else {
                    completeStatus = MyConstans.BECOME_NOT_COMPLETED;
                }
            }
        }
        return completeStatus;
    }

    /**
     * 获取数据的认证状态
     * @param dataSource 作为区分标准的数据来源分界点,优先权高于或等于该来源的数据为判断数据初始完整性的数据,优先级低于该来源的数据为补充数据
     * @param keySet 判断数据完善状态依据的有效key的集合
     * @param dataJsonAndOrderList 元数据
     * @return 数据的认证状态
     */
    public static int checkDataHasVerifiedByDataSource(int dataSource , Set<String> keySet , List<? extends DataJsonAndOrder> dataJsonAndOrderList) {
        int hasVerified = MyConstans.NOT_HAS_VERIFIED;
        RuleReSortDataResult ruleReSortDataResult = ClassifyDataUtils.classifyDataByDataSource(dataSource , dataJsonAndOrderList);
        ClassifyDataUtils.reSetDataPriority(ruleReSortDataResult.getDataJsonAndOrderList());
        int rulePoint = ruleReSortDataResult.getRulePoint();
        if (rulePoint <= 0) {
            return hasVerified;
        }
        List<DataJsonAndOrder> qualifiedDataList = ruleReSortDataResult.getDataJsonAndOrderList().subList(0 , rulePoint);
        ruleReSortDataResult = ClassifyDataUtils.classifyDataByHasVerified(qualifiedDataList);
        rulePoint = ruleReSortDataResult.getRulePoint();
        if (rulePoint <= 0) {
            return hasVerified;
        }
        qualifiedDataList = ruleReSortDataResult.getDataJsonAndOrderList().subList(0 , rulePoint);
        JSONObject qualifiedDataMergeJson = MergeDataUtils.mergeJsonStringDataByPriority(keySet , qualifiedDataList);
        Set<String> qualifiedNotCompleteKeysSet = JSONDataUtils.getNotCompleteKeysSet(keySet , qualifiedDataMergeJson , Boolean.FALSE);
        if (CollectionUtils.isEmpty(qualifiedNotCompleteKeysSet)) {
            hasVerified = MyConstans.ALL_HAS_VERIFIED;
        } else {
            hasVerified = MyConstans.PARTIAL_HAS_VERIFIED;
        }
        return hasVerified;
    }

    /**
     * 根据过期时间字段是否作为初始数据且被认证过的值来判断该数据是否有过期时间
     * @param dataSource 作为区分标准的数据来源分界点,优先权高于或等于该来源的数据为判断数据初始完整性的数据,优先级低于该来源的数据为补充数据
     * @param expirationDateKey 该数据的过期时间字段的key
     * @param keySet 判断数据完善状态依据的有效key的集合
     * @param dataJsonAndOrderList 元数据
     * @return 该数据是否具有过期时间
     */
    public static int checkDataHasExpirationDateByDataSource(int dataSource , String expirationDateKey , Set<String> keySet
            , List<? extends DataJsonAndOrder> dataJsonAndOrderList) {
        int expirationDate = MyConstans.NOT_HAS_EXPIRATION_DATE;
        RuleReSortDataResult ruleReSortDataResult = ClassifyDataUtils.classifyDataByDataSource(dataSource , dataJsonAndOrderList);
        ClassifyDataUtils.reSetDataPriority(ruleReSortDataResult.getDataJsonAndOrderList());
        int rulePoint = ruleReSortDataResult.getRulePoint();
        if (rulePoint <= 0) {
            return expirationDate;
        }
        Map<String , DataUnit> dataUnitMap = MergeDataUtils.mergeJsonStringDataToDataUnitByPriority(keySet , dataJsonAndOrderList);
        if (!dataUnitMap.containsKey(expirationDateKey) || dataUnitMap.get(expirationDateKey) == null
                || StringUtils.isBlank(dataUnitMap.get(expirationDateKey).getData().toString())) {
            return expirationDate;
        }
        DataUnit dataUnit = dataUnitMap.get(expirationDateKey);
        if (MyConstans.ALL_HAS_VERIFIED == dataUnit.getHasVerified() && dataUnit.getPriority() < rulePoint) {
            expirationDate = MyConstans.HAS_EXPIRATION_DATE;
        }
        return expirationDate;
    }

    /**
     * 计算数据的创建和更新时间
     * @param keySet 判断数据完善状态依据的有效key的集合
     * @param dataJsonAndOrderList 元数据
     * @return 整合后的所有数据中优先级最高来源数据的创建及更新时间(String类型 yyyyMMdd格式)
     */
    public static DateUnit checkDataDateByPriority(Set<String> keySet , List<DataJsonAndOrder> dataJsonAndOrderList) {
        //首先对数据json按照优先级进行排序
        SortDataUtils.asceSortDataJsonByPriority(dataJsonAndOrderList);
        DateUnit dataUnit = new DateUnit();
        boolean hasFoundData = Boolean.TRUE;
        int i = 0;
        do {
            DataJsonAndOrder dataJsonAndOrder = dataJsonAndOrderList.get(i);
            for (String key : keySet) {
                if (JSONDataUtils.checkJsonContainsStringData(key , dataJsonAndOrder.getData())) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MyConstans.DATA_FORMAT);
                    dataUnit.setCreataTime(simpleDateFormat.format(dataJsonAndOrder.getCreataTime()));
                    dataUnit.setUpdateTime(simpleDateFormat.format(dataJsonAndOrder.getUpdateTime()));
                    hasFoundData = Boolean.FALSE;
                    break;
                }
            }
            i++;
        } while (hasFoundData || i <= dataJsonAndOrderList.size());
        return dataUnit;
    }
}
