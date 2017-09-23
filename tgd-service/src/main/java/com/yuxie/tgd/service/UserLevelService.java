package com.yuxie.tgd.service;

import com.yuxie.tgd.common.util.LoginValidation;
import com.yuxie.tgd.common.util.MyConstans;
import com.yuxie.tgd.common.util.MySQLException;
import com.yuxie.tgd.mymapper.UserMapper;
import com.yuxie.tgd.pojo.dto.UserSessionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 查询用户当前等级是否为管理员
 * Created by 147356 on 2017/4/17.
 */
@Service
public class UserLevelService {

    private static final Logger log= LoggerFactory.getLogger(UserLevelService.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginValidation loginValidation;

    /**
     * 查询用户是否为管理员
     * @param userSessionDTO 用户ID、会话ID等信息
     * @return 用户的等级 0普通用户 1管理员
     * @throws Exception redis、sql等异常
     */
    public int mannagerValidation(UserSessionDTO userSessionDTO) throws Exception{
        Integer ismannager;
        Long userId = userSessionDTO.getUserId();
        String sessionId = userSessionDTO.getSessionId();
        ismannager = loginValidation.validation(userId , sessionId);//判断用户是否已登录
        try {
            if (MyConstans.SUCCESS_STATUS_NUM.equals(ismannager)) {
                ismannager = userMapper.isManager(userId);//查询用户是否是管理员
            }
            if (!MyConstans.MANNAGER_LEVEL_NUM.equals(ismannager)) {
                ismannager = 0;//除去查询出该用户为管理员之外的值均设置为非管理员
            }
            userMapper.updateUserLevel(ismannager , userId);//将新的用户等级保存到数据库中
            return ismannager;
        }catch (Exception e){
            log.error("=======获取用户是否为管理员失败========"+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
    }
}
