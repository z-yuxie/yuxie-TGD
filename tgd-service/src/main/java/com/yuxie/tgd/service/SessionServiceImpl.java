package com.yuxie.tgd.service;

import com.yuxie.tgd.common.util.MyConstans;
import com.yuxie.tgd.common.util.RedisException;
import com.yuxie.tgd.common.util.RedisService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 生成用户会话信息,及其更新和删除
 * Created by 147356 on 2017/4/10.
 */
@Service
public class SessionServiceImpl implements ISessionService {
    private static final Logger log = Logger.getLogger(SessionServiceImpl.class);

    @Autowired
    private RedisService redisService;

    /**
     * 根据 userId 获取 sessionID 信息
     * @param userId 用户ID
     * @return sessionId 会话ID
     */
    @Override
    public String getSessionId(String userId) {
        String sessionId = redisService.get(userId);
        if (StringUtils.isNotBlank(sessionId)) {
            redisService.expire(userId , MyConstans.SESSION_EXPIRED_MINUTES * MyConstans.SECONDS_OF_MINUTES);
            return sessionId;
        } else {
            return null;
        }
    }

    /**
     * 保存或更新用户的会话信息
     * @param userId 用户ID
     * @param sessionId 会话ID
     */
    @Override
    public void saveOrUpdateSessionInfo(String userId , String sessionId) {
        //判断该sessionInfo是否存在
        String sessionIdOld = redisService.get(userId);
        if (StringUtils.isNotBlank(sessionIdOld)) {
            redisService.del(userId);
        }
        redisService.set(userId,sessionId
                , MyConstans.SESSION_EXPIRED_MINUTES * MyConstans.SECONDS_OF_MINUTES);
    }

    /**
     * 生成新的sessionId
     * @param userId
     * @return sessionId
     */
    @Override
    public String setAndGetSessionId(Long userId) throws Exception {

        if (userId ==null) {
            throw new RuntimeException("userId或userLevel为空！");
        }
        // 生成sessionId
        String sessionId = UUID.randomUUID().toString();
        //保存到redis
        try {
            saveOrUpdateSessionInfo(userId.toString() , sessionId);
        }catch (Exception e) {
            log.debug(MyConstans.REDIS_SAVE_IS_FAIL+e.getMessage());
            throw new RedisException("保存用户会话信息失败");
        }
        return sessionId;
    }

    /**
     * 删除用户的登录状态
     * @param userId 用户ID
     * @return 删除是否成功
     * @throws Exception 可能发生的redis等异常
     */
    @Override
    public void delSessionId(Long userId) throws Exception {
        if (userId ==null) {
            throw new RuntimeException("userId或userLevel为空！");
        }
        String sessionIdOld = redisService.get(userId.toString());
        if (sessionIdOld != null) {
            redisService.del(userId.toString());
        }
    }
}
