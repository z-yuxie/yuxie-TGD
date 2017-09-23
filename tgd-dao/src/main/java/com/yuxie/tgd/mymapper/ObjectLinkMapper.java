package com.yuxie.tgd.mymapper;

import com.yuxie.tgd.pojo.bean.ObjectLink;
import com.yuxie.tgd.pojo.dto.ListParamDTO;
import com.yuxie.tgd.pojo.dto.ObjectIdDTO;
import com.yuxie.tgd.pojo.dto.SelectObjectList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户关联关系表mapper
 * Created by 147356 on 2017/4/10.
 */
@Repository
public interface ObjectLinkMapper {

    /**
     * 根据列表类型获取对应的任务ID列表
     * @param listType 列表类型
     * @return
     */
    List<Long> getTaskIdListByType(Integer listType);

    /**
     * 根据对象ID删除对象记录
     * @param objectId 对象ID
     */
    void delById(Long objectId);

    /**
     * 根据对象ID获取其最新详情号
     * @param objectId 对象ID
     * @return max_detail_id 最新对象详情ID
     */
    Long getMaxDetailId(Long objectId);

    /**
     * 根据排序规则和获取类型获取包含对象ID和对象类型的
     * @param selectObjectList 获取列表参数
     * @return 对象ID列表
     */
    List<ObjectIdDTO> getObjectIdDTOList(SelectObjectList selectObjectList);

    /**
     * 若对象ID对象对应的对象未被封禁则获取对象的创建者及对象类型等信息
     * @param objectId 对象ID
     * @return 对象ID 对象创建者ID 对象类型
     */
    ObjectIdDTO getObjectIdDTOByObjectId(Long objectId);

    /**
     * 根据对象ID获取该对象所包含的任务的ID
     * @param parentObjectId 对象ID
     * @return 任务ID
     */
    Long getTaskIdByObjectId(Long parentObjectId);

    /**
     * 根据对象ID获取该对象的类型
     * @param objectId 对象ID
     * @return 对象类型ID
     */
    Integer getObjectTypeByObjectId(Long objectId);

    /**
     * 插入新的ObjecLink对象
     * @param objectLink 包含新插入信息
     * @return 主键
     */
    Long insertObjectLink(ObjectLink objectLink);

    /**
     * 根据对象ID更新其最新详情ID
     * @param objectId 对象ID
     * @param maxDetailId 最新详情ID
     * @return 是否成功
     */
    Integer updateDetailId(@Param("objectId") Long objectId , @Param("maxDetailId") Long maxDetailId);

    /**
     * 根据对象的ID查询该对象当前的状态值
     * @param objectId 对象ID
     * @return 对象当前的状态值
     */
    Integer getObjectStatus(Long objectId);

    /**
     * 根据对象ID更新对象的当前状态
     * @param objectId 对象ID
     * @param objectStatus 新的状态值
     * @return 是否成功
     */
    Integer updateObjectStatus(@Param("objectId") Long objectId , @Param("objectStatus") Integer objectStatus);

    /**
     * 根据对象ID获取objectlink
     * @param objectId 对象ID
     * @return objectLink
     */
    ObjectLink getObjectLinkById(Long objectId);

    /**
     * 根据对象ID获取其父对象ID
     * @param objectId 对象ID
     * @return 父对象ID
     */
    Long getParentObjectId(Long objectId);
}
