package com.yuxie.tgd.service;

import com.yuxie.tgd.common.util.MySQLException;
import com.yuxie.tgd.common.util.RedisException;
import com.yuxie.tgd.pojo.dto.UserMessageDTO;
import com.yuxie.tgd.pojo.dto.UserSessionDTO;
import com.yuxie.tgd.pojo.vo.UserMessageVO;

/**
 * 用户小操作Service
 * Created by 147356 on 2017/4/19.
 */
public interface IUserService {
    /**
     * 根据用户ID获取用户的详细信息
     * @param userSessionDTO
     * @return
     */
    UserMessageVO getUserMessageById(UserSessionDTO userSessionDTO) throws Exception;

    /**
     * 更新某个用户的信息
     * @param userMessageDTO
     * @return
     */
    UserMessageVO updateUserMessage(UserMessageDTO userMessageDTO) throws MySQLException, RedisException, Exception;

    /**
     * 用户注册
     * @param userMessageDTO
     * @return
     */
    UserMessageVO userRegist(UserMessageDTO userMessageDTO) throws Exception;

    /**
     * 用户登录
     * @param userId
     * @return
     */
    UserMessageVO userLogin(Long userId) throws Exception;

    /**
     * 获取用户与另一用户之间的关系或者关注状态
     * @param userSessionDTO 包含两者UserId和会话ID
     * @return 用户之间的关系状态码
     * @throws Exception 可能发生的redis、SQL以及未知的异常
     */
    int getUserRelation(UserSessionDTO userSessionDTO) throws Exception;

    /**
     * 变更用户与另一个用户之间的关系
     * @param userSessionDTO 包含两者UserID和会话ID
     * @return 变更是否成功
     * @throws Exception 可能发生的redis、sql以及未知的异常
     */
    int changeUserRelation(UserSessionDTO userSessionDTO) throws Exception;

    /**
     * 根据用户ID获取该用户当前的状态
     * @param userId 用户ID
     * @return 该用户当前的状态值
     * @throws Exception 可能发生的SQL等异常
     */
    int getUserStatus(Long userId) throws Exception;

    /**
     * 变更用户的状态
     * @param userSessionDTO 包含被操作用户ID及新的状态值
     * @return 变更后用户的状态
     * @throws Exception 可能发生的redis、sql等异常
     */
    int changeUserStatus(UserSessionDTO userSessionDTO) throws Exception;

    /**
     * 根据用户ID获取用户当前持有的资源数
     * @param userId 用户ID
     * @return 用户当前的资源数
     * @throws Exception SQL等可能出现的异常
     */
    int getUserResource(Long userId) throws Exception;

    /**
     * 变更用户当前持有的资源数
     * @param userSessionDTO 包含有用户ID、会话ID、需要变更的资源数等信息
     * @return 新的用户资源数
     * @throws Exception 可能发生的redis、sql等异常
     */
    int changeUserResource(UserSessionDTO userSessionDTO) throws Exception;
}
