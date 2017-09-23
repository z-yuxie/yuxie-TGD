package com.yuxie.tgd.service;

import com.yuxie.tgd.common.util.MySQLException;
import com.yuxie.tgd.mymapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 验证用户名密码是否重复
 * Created by 147356 on 2017/4/18.
 */
@Service
public class UserNamePwVerification {

    private static final Logger log= LoggerFactory.getLogger(UserNamePwVerification.class);

    @Autowired
    private  UserMapper userMapper;

    /**
     * 查询符合该用户名和密码的用户的数量
     * @param userName 用户名
     * @param userPw 用户密码
     * @return 返回符合条件的目标用户数
     * @throws MySQLException 查询数据库是发生异常
     */
    public Integer getUserCountByPw(String userName,String userPw) throws MySQLException {
        Integer userCount=null;
        try {
            userCount=userMapper.getUserCountByPw(userName,userPw);
            return userCount;
        }catch (Exception e){
            log.error("================查询符合该用户名密码的用户数失败=================");
            throw new MySQLException("查询符合该用户名密码的用户数失败");
        }
    }

    /**
     * 根据用户名和密码查询用户的ID
     * @param userName 用户名
     * @param userPw 用户密码
     * @return 用户的ID
     * @throws MySQLException 查询数据库是发生异常
     */
    public Long getUserIdByPw(String userName,String userPw) throws MySQLException {
        Long userId=null;
        try {
            userId=userMapper.getUserIdByPw(userName,userPw);
            return userId;
        }catch (Exception e){
            log.error("================查询符合该用户名密码的用户失败=================");
            throw new MySQLException("查询符合该用户名密码的用户失败");
        }
    }

}
