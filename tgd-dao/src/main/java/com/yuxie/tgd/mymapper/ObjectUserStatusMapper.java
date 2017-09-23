package com.yuxie.tgd.mymapper;

import com.yuxie.tgd.pojo.bean.ObjectUserStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户与对象关系表操作mapper
 * Created by 147356 on 2017/4/21.
 */
@Repository
public interface ObjectUserStatusMapper {

    /**
     * 根据对象ID获取参与该对象给的所有用户的ID
     * @param objectId 对象ID
     * @return 参与该对象的用户的ID列表
     */
    List<Long> getInvolvedUserIdListByObejctId(Long objectId);

    /**
     * 根据对象ID获取参与该对象的用户的数量
     * @param objectId 对象ID
     * @return 参与该对象的用户数
     */
    Integer getInvolvedUserCountByObjectId(Long objectId);

    /**
     * 根据用户ID和状态值返回被该用户进行过该状态值操作的对象的列表
     * @param userId 用户ID
     * @param statusNum 状态值
     * @param showNum 是否激活
     * @return 满足条件的对象ID列表
     */
    List<Long> getObjectIdListByUserIdAndStatus(@Param("userId") Long userId , @Param("statusNum") Integer statusNum , @Param("showNum") Integer showNum);

    /**
     * 根据对象ID和状态值获取当前与该对象保持该状态值的用户数
     * @param objectId 对象ID
     * @param statusNum 状态值
     * @return 用户数
     */
    Integer getStatusTimes(@Param("objectId") Long objectId , @Param("statusNum") Integer statusNum);

    /**
     * 根据用户ID和对象ID判断该用户是否是该对象的拥有者或可参阅者
     * @param userId 用户ID
     * @param objectId 对象ID
     * @return 是否可跳过任务
     */
    Integer getPassStatusNum(@Param("userId") Long userId , @Param("objectId") Long objectId);

    /**
     * 根据用户和对象ID获取该用户在该对象中的状态列表
     * @param userId 用户ID
     * @param objectId 对象ID
     * @return 已激活的状态值列表
     */
    List<Integer> getStatusNum(@Param("userId") Long userId , @Param("objectId") Long objectId);

    /**
     * 根据对象ID、用户ID和状态值更新状态值
     * @param objectUserStatus 包含新旧使用状态值和对象用户ID
     * @return 是否成功
     */
    Integer updateStatus(ObjectUserStatus objectUserStatus);

    /**
     * 根据对象ID获取其被赞数
     * @param objectId 对象ID
     * @return 被点赞数
     */
    Integer getUpTimes(Long objectId);

    /**
     * 根据对象ID获取其被踩数
     * @param objectId 对象ID
     * @return 被点赞数
     */
    Integer getDownTimes(Long objectId);

    /**
     * 新插入一条用户对象状态记录
     * @param objectUserStatus 包含用户对象ID,状态值信息
     * @return 是否成功
     */
    Integer insertStatus(ObjectUserStatus objectUserStatus);

    /**
     * 查询用户在某对象中是否已拥有某种状态
     * @param objectUserStatus 包含用户对象ID状态值等信息
     * @return showNum
     */
    Integer getShowNum(ObjectUserStatus objectUserStatus);

    /**
     * 根据对象ID取消现有对象所有者的持有权限
     * @param objectId 对象ID
     * @return 是否成功
     */
    Integer updateOwnerStatusToNo(Long objectId);
}
