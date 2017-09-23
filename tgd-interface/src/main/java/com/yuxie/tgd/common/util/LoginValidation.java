package com.yuxie.tgd.common.util;

import com.yuxie.tgd.service.ISessionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 登录状态验证及会话ID的获取
 * Created by 147356 on 2017/4/17.
 */
@Service
public class LoginValidation {

    private static final Logger log= LoggerFactory.getLogger(LoginValidation.class);

    @Autowired
    private ISessionService sessionService;

    /**
     * 验证用户的登录状态，并根据
     * @param userId 用户ID
     * @param sessionId 会话ID
     * @return status 用户登录状态：0未登录，1已登录，2状态已过期，-1非法操作
     */
    public int validation(Long userId,String sessionId) throws RedisException{
        try {
            if(userId == null || sessionId == null || sessionId.isEmpty()){
                return MyConstans.DATE_CHANGE_IS_FAIL_OR_NOT_FIND_NUM;
            }
            String sessionIdOld = sessionService.getSessionId(userId.toString());
            if(StringUtils.isBlank(sessionIdOld)){
                return MyConstans.DATE_CHANGE_IS_FAIL_OR_NOT_FIND_NUM;
            }else if (sessionId.equals(sessionIdOld)){
                return MyConstans.SUCCESS_STATUS_NUM;
            }else {
                sessionService.delSessionId(userId);
                return MyConstans.ILLEGAL_OPERATION_NUM;
            }
        }catch (Exception e){
            log.error("=====================验证用户登录状态发生异常======================"+e.toString());
            throw new RedisException(MyConstans.VERIFICATION_USER_STATUS_IS_ERROR);
        }
    }

    /**
     * 根据用户ID获取改用户的会话ID
     * @param usrId 用户ID
     * @return 该用户的会话ID
     * @throws Exception 可能发生的redis等异常
     */
    public String getSessionId(Long usrId) throws Exception {
        return sessionService.setAndGetSessionId(usrId);
    }
}
