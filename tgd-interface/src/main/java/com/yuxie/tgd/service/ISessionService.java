package com.yuxie.tgd.service;



/**
 * session 服务
 * <p>
 * Created by liuzhenhua
 * on 2017-3-17
 */
public interface ISessionService {

    /**
     * 根据 userId 获取 sessionInfo 信息
     * @param userId 用户ID
     * @return sessionID
     */
    String getSessionId(String userId);

    /**
     * 保存会话ID到redis中
     * @param userId 用户ID
     * @param sessionId 会话ID
     */
    void saveOrUpdateSessionInfo(String userId , String sessionId);

    /**
     * 根据用户ID生成sessionID
     * @param userId 用户ID
     * @return sessionID 会话ID
     */
    String setAndGetSessionId(Long userId) throws Exception;

    /**
     * 删除用户的登录状态
     * @param userId 用户ID
     * @throws Exception 可能发生的redis等异常
     */
    void delSessionId(Long userId) throws Exception;
}
