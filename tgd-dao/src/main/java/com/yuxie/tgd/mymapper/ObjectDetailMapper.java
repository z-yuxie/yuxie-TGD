package com.yuxie.tgd.mymapper;

import com.yuxie.tgd.pojo.bean.ObjectDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * Created by 147356 on 2017/4/10.
 */
@Repository
public interface ObjectDetailMapper {

    /**
     * 根据对象详情ID删除对象详情
     * @param objectDetailId 对象详情ID
     */
    void delByDetailId(Long objectDetailId);

    /**
     * 根据对象向前ID更新对象详情
     * @param objectDetail 对象详情
     * @return 是否成功
     */
    Integer updateObjectDetailById(ObjectDetail objectDetail);

    /**
     * 根据最新详情号获取该对象详情状态
     * @param objectDetailId 最新对象详情号
     * @return object_status 当前版本对象详情状态
     */
    Integer getObjectStatus(Long objectDetailId);

    /**
     * 根据详情ID获取对象详情
     * @param objectDetailId
     * @return
     */
    ObjectDetail getObjectDetailById(Long objectDetailId);

    /**
     * 根据详情ID获取该详情对应的大文本对象内容
     * @param objectDetailId 详情ID
     * @return 大字段对象内容
     */
    Long getMessageId(Long objectDetailId);

    /**
     * 新插入一个对象的详情
     * @param objectDetail 对象详情
     * @return 主键
     */
    Long insertDetail(ObjectDetail objectDetail);

    /**
     * 根据详情ID更新该版本详情的内容ID
     * @param objectDetailId 详情ID
     * @param objectMessageId 内容ID
     * @return 是否成功
     */
    Integer updateMessageId(@Param("objectDetailId") Long objectDetailId , @Param("objectMessageId") Long objectMessageId);

    /**
     * 根据对象详情ID更新对象的当前状态
     * @param objectDetailId 对象详情ID
     * @param objectStatus 新的状态值
     * @return 是否成功
     */
    Integer updateObjectDetailStatus(@Param("objectDetailId") Long objectDetailId , @Param("objectStatus") Integer objectStatus);

    /**
     * 根据对象详情ID获取任务答案
     * @param objectDetailId 详情ID
     * @return 任务答案关键词
     */
    String getKeyWordByDetailId(Long objectDetailId);

    /**
     * 更新任务奖池资源数
     * @param resource 资源数,其中 -1表示清空奖池
     * @param objectDetailId 资源数,其中 -1表示清空奖池
     * @return 是否成功
     */
    Integer updateObjectResourcePool(@Param("resource") Integer resource , @Param("objectDetailId") Long objectDetailId);

    /**
     * 根据详情ID获取任务当前奖池积累的资源数
     * @param objectDetailId
     * @return
     */
    Integer getObjectResourcePool(Long objectDetailId);

    /**
     * 根据详情ID获取对象当前持有者ID
     * @param objectDetailId
     * @return
     */
    Long getUserIdByDetailId(Long objectDetailId);
}
