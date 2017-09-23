package com.yuxie.tgd.mymapper;

import com.yuxie.tgd.pojo.bean.ObjectMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 对象内容表操作Mapper
 * Created by 147356 on 2017/4/22.
 */
@Repository
public interface ObjectMessageMapper {

    /**
     * 根据独享内容ID删除对象大字段内容
     * @param objectMessageId 对象内容ID
     */
    void delByMessageId(Long objectMessageId);

    /**
     * 根据对象内容ID获取对象内容
     * @param objectMessageId 对象内容ID
     * @return 对象的大字段内容
     */
    String getObjectMessageById(Long objectMessageId);

    /**
     * 根据对象内容ID获取对象内容Bean
     * @param objectMessageId 对象内容ID
     * @return objectMessage对象
     */
    ObjectMessage getObjectMessageBeanById(Long objectMessageId);

    /**
     * 更新对象的内容
     * @param objectMessageId 对象ID
     * @param objectMessageText 对象的新内容
     * @return 是否成功
     */
    Integer updateObjectMessage(@Param("objectMessageId") Long objectMessageId , @Param("objectMessageText") String objectMessageText);

    /**
     * 新插入一段对象的长文本内容
     * @param objectMessage 大字段内容
     * @return 主键
     */
    Long insertMessage(ObjectMessage objectMessage);
}
