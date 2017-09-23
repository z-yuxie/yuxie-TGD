package com.yuxie.tgd.common.util;



import com.yuxie.tgd.pojo.bean.dataMerge.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortDataUtils {

    /**
     * 对传出的表单数据按照其优先级(数字越小优先级越高,排的越前)进行排序
     * @param columnDataAndBaseFormPropertyList 表单数据及其优先级
     * @return
     */
    public static void asceSortFormColumnDataByPriority(List<? extends ColumnDataAndBaseFormProperty> columnDataAndBaseFormPropertyList) {
        Collections.sort(columnDataAndBaseFormPropertyList , new Comparator<ColumnDataAndBaseFormProperty>() {
            @Override
            public int compare(ColumnDataAndBaseFormProperty o1, ColumnDataAndBaseFormProperty o2) {
                return o1.getColumnOrder() - o2.getColumnOrder();
            }
        });
    }

    /**
     * 对传出的表单数据按照其优先级(数字越大优先级越高,排的越前)进行排序
     * @param columnDataAndBaseFormPropertyList 表单数据及其优先级
     * @return
     */
    public static void descSortFormColumnDataByPriority(List<? extends ColumnDataAndBaseFormProperty> columnDataAndBaseFormPropertyList) {
        Collections.sort(columnDataAndBaseFormPropertyList , new Comparator<ColumnDataAndBaseFormProperty>() {
            @Override
            public int compare(ColumnDataAndBaseFormProperty o1, ColumnDataAndBaseFormProperty o2) {
                return o2.getColumnOrder() - o1.getColumnOrder();
            }
        });
    }

    /**
     * 对传入的元数据按照其优先级(数字越小优先级越高,排的越前)进行排序
     * @param dataJsonAndOrderList 元数据json及其优先级
     * @return
     */
    public static void asceSortDataJsonByPriority(List<? extends DataJsonAndOrder> dataJsonAndOrderList) {
        Collections.sort(dataJsonAndOrderList, new Comparator<DataJsonAndOrder>() {
            @Override
            public int compare(DataJsonAndOrder o1, DataJsonAndOrder o2) {
                return o1.getPriority() - o2.getPriority();
            }
        });
    }

    /**
     * 对传入的元数据按照其优先级(数字越大优先级越高,排的越前)进行排序
     * @param dataJsonAndOrderList 元数据json及其优先级
     * @return
     */
    public static void descSortDataJsonByPriority(List<? extends DataJsonAndOrder> dataJsonAndOrderList) {
        Collections.sort(dataJsonAndOrderList, new Comparator<DataJsonAndOrder>() {
            @Override
            public int compare(DataJsonAndOrder o1, DataJsonAndOrder o2) {
                return o2.getPriority() - o1.getPriority();
            }
        });
    }
}
