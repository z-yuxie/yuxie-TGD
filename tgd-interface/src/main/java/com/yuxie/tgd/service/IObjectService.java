package com.yuxie.tgd.service;

import com.yuxie.tgd.pojo.dto.ObjectDetailDTO;
import com.yuxie.tgd.pojo.dto.UserSessionDTO;
import com.yuxie.tgd.pojo.vo.ObjectDetailVO;

import java.util.List;

/**
 * 其他小型对对象操作
 * Created by 147356 on 2017/4/10.
 */
public interface IObjectService {
    /**
     * 根据对象ID获取对象详情
     * @param userSessionDTO 包含对象ID等信息
     * @return
     */
    ObjectDetailVO getObjectDetail(UserSessionDTO userSessionDTO) throws Exception;

    /**
     * 更新某个对象的详情
     * @param objectDetailDTO
     * @return
     */
    Integer updateObject(ObjectDetailDTO objectDetailDTO) throws Exception;

    /**
     * 创建某个新对象或对象的新版本
     * @param objectDetailDTO
     * @return
     */
    Long createObject(ObjectDetailDTO objectDetailDTO) throws Exception;

    /**
     * 根据对象ID获取对象当前的状态
     * @param objectId 对象ID
     * @return 对象当前的状态值
     * @throws Exception 可能发生的redis、sql等异常
     */
    Integer getObjectStatus(Long objectId) throws Exception;

    /**
     * 通过对象ID变更对象当前状态
     * @param userSessionDTO 包含对象ID 用户登录及权限验证等信息
     * @return 对象当前的状态值
     * @throws Exception 可能发生的redis、sql等异常
     */
    Integer changeObjectStatus(UserSessionDTO userSessionDTO) throws Exception;

    /**
     * 通过对象和用户ID获取该用户在该对象中的状态值列表
     * @param userId 用户ID
     * @param objectId 对象ID
     * @return 已激活状态值列表
     * @throws Exception 可能发生的redis、sql等异常
     */
    List<Integer> getStatusNumList(Long userId , Long objectId) throws Exception;

    /**
     * 变更用户在对象中的状态值,以及参加任务等
     * @param userSessionDTO 包含用户对象ID,会话ID,答案等信息
     * @return 变更成功则返回状态值,失败-1,赞踩需减1则返回-2
     * @throws Exception 可能发生的redis、sql等异常
     */
    Integer changeStatusNum(UserSessionDTO userSessionDTO) throws Exception;

    /**
     * 根据对象ID获取其任务ID
     * @param objectId 对象ID
     * @return 任务ID
     * @throws Exception 可能发生的redis、sql等异常
     */
    Long getTaskId(Long objectId) throws Exception;

    /**
     * 根据对象ID获取其父对象ID
     * @param objectId 对象ID
     * @return 父对象ID
     * @throws Exception 可能发生的redis、sql等异常
     */
    Long getParentObjectId(Long objectId) throws Exception;
}
