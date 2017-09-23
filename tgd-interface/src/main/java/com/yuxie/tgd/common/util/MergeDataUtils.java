package com.yuxie.tgd.common.util;

import com.alibaba.fastjson.JSONObject;
import com.yuxie.tgd.pojo.bean.dataMerge.DataJsonAndOrder;
import com.yuxie.tgd.pojo.bean.dataMerge.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class MergeDataUtils {

    /**
     * 按照不同数据源的优先级,合并包含在键集合不同数据源的数据
     * @param keySet 键集合
     * @param dataJsonAndOrderList 元数据及该数据的优先级等
     * @return 合并后的json
     */
    public static JSONObject mergeJsonStringDataByPriority(Set<String> keySet , List<? extends DataJsonAndOrder> dataJsonAndOrderList) {
        JSONObject jsonObject = new JSONObject();
        //首先对数据json按照优先级进行排序
        SortDataUtils.asceSortDataJsonByPriority(dataJsonAndOrderList);
        //合并数据
        for (String key : keySet) {
            for (DataJsonAndOrder dataJsonAndOrder : dataJsonAndOrderList) {
                String data = JSONDataUtils.getStringValueFromJson(key , dataJsonAndOrder.getData());
                if (StringUtils.isNotBlank(data)) {
                    jsonObject.put(key , data);
                    break;
                }
            }
        }
        return jsonObject;
    }

    /**
     * 按照不同数据源的优先级,合并包含在键集合不同数据源的数据
     * @param keySet 键集合
     * @param dataJsonAndOrderList 元数据及该数据的优先级等
     * @return 合并后的json
     */
    public static JSONObject mergeJsonObjectDataByPriority(Set<String> keySet , List<? extends DataJsonAndOrder> dataJsonAndOrderList) {
        JSONObject jsonObject = new JSONObject();
        //首先对数据json按照优先级进行排序
        SortDataUtils.asceSortDataJsonByPriority(dataJsonAndOrderList);
        //合并数据
        for (String key : keySet) {
            for (DataJsonAndOrder dataJsonAndOrder : dataJsonAndOrderList) {
                Object data = JSONDataUtils.getObjectValueFromJson(key , dataJsonAndOrder.getData());
                if (data != null) {
                    jsonObject.put(key , data);
                    break;
                }
            }
        }
        return jsonObject;
    }

    /**
     * 将String类型的数据合并,并以由DataUnit组成的Map的形式输出
     * @param keySet 有效键集合
     * @param dataJsonAndOrderList 元数据及该数据的优先级等
     * @return
     */
    public static Map<String , DataUnit> mergeJsonStringDataToDataUnitByPriority(Set<String> keySet , List<? extends DataJsonAndOrder> dataJsonAndOrderList) {
        Map<String , DataUnit> dataUnitMap = new HashMap<>();
        //首先对数据json按照优先级进行排序
        SortDataUtils.asceSortDataJsonByPriority(dataJsonAndOrderList);
        //合并数据
        for (String key : keySet) {
            for (DataJsonAndOrder dataJsonAndOrder : dataJsonAndOrderList) {
                String data = JSONDataUtils.getStringValueFromJson(key , dataJsonAndOrder.getData());
                if (data != null) {
                    DataUnit dataUnit = setDataIntoDataUnit(data , dataJsonAndOrder);
                    dataUnitMap.put(key , dataUnit);
                    break;
                }
            }
        }
        return dataUnitMap;
    }

    /**
     * 将Object类型的数据合并,并以由DataUnit组成的Map的形式输出
     * @param keySet 有效键集合
     * @param dataJsonAndOrderList 元数据及该数据的优先级等
     * @return
     */
    public static Map<String , DataUnit> mergeJsonObjectDataToDataUnitByPriority(Set<String> keySet , List<? extends DataJsonAndOrder> dataJsonAndOrderList) {
        Map<String , DataUnit> dataUnitMap = new HashMap<>();
        //首先对数据json按照优先级进行排序
        SortDataUtils.asceSortDataJsonByPriority(dataJsonAndOrderList);
        //合并数据
        for (String key : keySet) {
            for (DataJsonAndOrder dataJsonAndOrder : dataJsonAndOrderList) {
                Object data = JSONDataUtils.getObjectValueFromJson(key , dataJsonAndOrder.getData());
                if (data != null) {
                    DataUnit dataUnit = setDataIntoDataUnit(data , dataJsonAndOrder);
                    dataUnitMap.put(key , dataUnit);
                    break;
                }
            }
        }
        return dataUnitMap;
    }

    /**
     * 按照不同数据源的优先级,合并包含在键集合不同数据源的数据
     * @param keyAndOrderMap 键及该键在表单页的排序号
     * @param dataJsonAndFormProperties 元数据及该数据的优先级和是否允许被修改
     * @return 合并后的表单列数据
     */
    public static List<ColumnDataAndBaseFormProperty> mergeJsonStringDataAndPropertyByPriority(Map<String , Integer> keyAndOrderMap , List<? extends DataJsonAndFormProperty> dataJsonAndFormProperties) {
        List<ColumnDataAndBaseFormProperty> columnDataAndBaseFormPropertyList = new ArrayList<>();
        //首先对数据json按照优先级进行排序
        SortDataUtils.asceSortDataJsonByPriority(dataJsonAndFormProperties);
        //合并数据
        Set<String> keySet = keyAndOrderMap.keySet();
        for (String key : keySet) {
            for (DataJsonAndFormProperty dataJsonAndFormProperty : dataJsonAndFormProperties) {
                String data = JSONDataUtils.getStringValueFromJson(key , dataJsonAndFormProperty.getData());
                if (StringUtils.isNotBlank(data)) {
                    ColumnDataAndSourceSpecialProperty columnDataAndSourceSpecialProperty
                            = setDataIntoColumnDataAndSourceSpecialProperty(key , keyAndOrderMap.get(key) , dataJsonAndFormProperty.isCanBeEdit() , data);
                    columnDataAndBaseFormPropertyList.add(columnDataAndSourceSpecialProperty);
                    break;
                }
            }
        }
        SortDataUtils.asceSortFormColumnDataByPriority(columnDataAndBaseFormPropertyList);
        return columnDataAndBaseFormPropertyList;
    }

    /**
     * 按照不同数据源的优先级,合并包含在键集合不同数据源的数据
     * @param keyAndOrderMap 键及该键在表单页的排序号
     * @param dataJsonAndFormProperties 元数据及该数据的优先级和是否允许被修改
     * @return 合并后的表单列数据
     */
    public static List<ColumnDataAndBaseFormProperty> mergeJsonObjectDataAndPropertyByPriority(Map<String , Integer> keyAndOrderMap , List<? extends DataJsonAndFormProperty> dataJsonAndFormProperties) {
        List<ColumnDataAndBaseFormProperty> columnDataAndBaseFormPropertyList = new ArrayList<>();
        //首先对数据json按照优先级进行排序
        SortDataUtils.asceSortDataJsonByPriority(dataJsonAndFormProperties);
        //合并数据
        Set<String> keySet = keyAndOrderMap.keySet();
        for (String key : keySet) {
            for (DataJsonAndFormProperty dataJsonAndFormProperty : dataJsonAndFormProperties) {
                Object data = JSONDataUtils.getObjectValueFromJson(key , dataJsonAndFormProperty.getData());
                if (data != null) {
                    ColumnDataAndSourceSpecialProperty columnDataAndSourceSpecialProperty
                            = setDataIntoColumnDataAndSourceSpecialProperty(key , keyAndOrderMap.get(key) , dataJsonAndFormProperty.isCanBeEdit() , data);
                    columnDataAndBaseFormPropertyList.add(columnDataAndSourceSpecialProperty);
                    break;
                }
            }
        }
        SortDataUtils.asceSortFormColumnDataByPriority(columnDataAndBaseFormPropertyList);
        return columnDataAndBaseFormPropertyList;
    }

    /**
     * 将数据放入到DataUnit中
     * @param data 数据
     * @param dataJsonAndOrder 该数据的其他属性
     * @return DataUnit
     */
    private static DataUnit setDataIntoDataUnit(Object data , DataJsonAndOrder dataJsonAndOrder) {
        DataUnit dataUnit = new DataUnit();
        dataUnit.setDataSource(dataJsonAndOrder.getDataSource());
        dataUnit.setHasVerified(dataJsonAndOrder.getHasVerified());
        dataUnit.setPriority(dataJsonAndOrder.getPriority());
        dataUnit.setData(data);
        dataUnit.setCreataTime(dataJsonAndOrder.getCreataTime());
        dataUnit.setUpdateTime(dataJsonAndOrder.getUpdateTime());
        return dataUnit;
    }

    /**
     * 将数据放入到ColumnDataAndSourceSpecialProperty中
     * @param key 该列的键
     * @param columnOrder 该列的排序号/排位顺序
     * @param canBeEdit 是否允许编辑
     * @param data 该列的数据本身
     * @return ColumnDataAndSourceSpecialProperty
     */
    private static ColumnDataAndSourceSpecialProperty setDataIntoColumnDataAndSourceSpecialProperty(String key , int columnOrder , boolean canBeEdit , Object data) {
        ColumnDataAndSourceSpecialProperty columnDataAndSourceSpecialProperty = new ColumnDataAndSourceSpecialProperty();
        columnDataAndSourceSpecialProperty.setColumnOrder(columnOrder);
        columnDataAndSourceSpecialProperty.setColumnKey(key);
        columnDataAndSourceSpecialProperty.setColumnData(data);
        columnDataAndSourceSpecialProperty.setCanBeEdit(canBeEdit);
        return columnDataAndSourceSpecialProperty;
    }
}
