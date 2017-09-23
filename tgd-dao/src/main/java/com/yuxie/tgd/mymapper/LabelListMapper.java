package com.yuxie.tgd.mymapper;

import com.yuxie.tgd.pojo.bean.LabelList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 标签列表操作类
 * Created by 147356 on 2017/4/24.
 */
@Repository
public interface LabelListMapper {

    /**
     * 根据标签ID获取该标签
     * @param labelId 标签ID
     * @return 标签对象
     */
    LabelList getLabelListBeanById(Long labelId);

    /**
     * 根据标签名变更标签热度
     * @param labelName 标签名
     * @param changeNum 标签热度变更值,一般为±1
     * @return 是否成功
     */
    Integer updateLabelHotNum(@Param("labelName") String labelName , @Param("changeNum") Integer changeNum);

    /**
     * 获取指定数量的标签
     * @param labelNum 获取数量
     * @return 标签列表
     */
    List<LabelList> getLabelList(Integer labelNum);

    /**
     * 通过标签名获取该标签的ID
     * @param labelName 标签名
     * @return 标签ID
     */
    Long getLableIdByName(String labelName);

    /**
     * 根据标签ID变更还标签的热度值
     * @param labelId 标签ID
     * @param changeNum 标签热度变更值,一般为±1
     * @return 是否成功
     */
    Integer updateLabelHotNumById(@Param("labelId") Long labelId , @Param("changeNum") Integer changeNum);

    /**
     * 新增一个标签
     * @param labelList 包含用户ID和标签名
     * @return 主键ID
     */
    Long insertLabel(LabelList labelList);
}
