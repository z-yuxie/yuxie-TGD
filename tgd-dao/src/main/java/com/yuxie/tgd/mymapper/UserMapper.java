package com.yuxie.tgd.mymapper;

import com.yuxie.tgd.pojo.bean.YuxieUser;
import com.yuxie.tgd.pojo.vo.UserBaseVO;
import com.yuxie.tgd.pojo.vo.UserMessageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户表操作mapper
 * Created by 147356 on 2017/4/6.
 */
@Repository
public interface UserMapper {

    /**
     * 更新用户的个人信息
     * @param yuxieUser 包含用户个人信息的包装类
     * @return 更新影响条数 正常为1 更新失败为0
     */
    Integer updateUserMessage(YuxieUser yuxieUser);

    /**
     * 用户注册
     * @param yuxieUser 包含用户详情信息的对象
     * @return user_id 用户的ID
     */
    Long userResgit(YuxieUser yuxieUser);

    /**
     * 查询符合该用户名和密码的用户数量
     * @param userName 用户名
     * @param userPw 用户密码
     * @return 符合该用户ID和密码的用户数量
     */
    Integer getUserCountByPw(@Param("userName") String userName, @Param("userPw") String userPw);

    /**
     * 根据用户名和密码查询用户ID
     * @param userName
     * @param userPw
     * @return
     */
    Long getUserIdByPw(@Param("userName") String userName, @Param("userPw") String userPw);

    /**
     * 查询用户是否是管理员
     * @param userId 用户ID
     * @return 0不是管理员 1是管理员 null出错了
     */
    Integer isManager(Long userId);

    /**
     * 根据用户ID变更用户的权限等级
     * @param userLevel 新的权限等级
     * @param userId 用户ID
     * @return 是否成功
     */
    Integer updateUserLevel(@Param("userLevel") Integer userLevel , @Param("userId") Long userId);

    /**
     * 根据用户ID获取用户详情
     * @param userId 用户ID
     * @return 用户详情
     */
    UserMessageVO getUserMessage(Long userId);

    /**
     * 根据用户信息获取该用户的基本信息
     * @param userId 用户ID
     * @return 用户基本信息
     */
    UserBaseVO getUserBaseMessage(Long userId);

    /**
     * 根据用户的ID获取用户的状态
     * @param userId 用户ID
     * @return 用户当前的状态
     */
    Integer getUserStatus(Long userId);

    /**
     * 变更用户的状态
     * @param userId 被变更的用户的ID
     * @param userStatus 新的状态值
     * @return 是否成功
     */
    Integer updateUserStatus(@Param("userId") Long userId , @Param("userStatus") Integer userStatus);

    /**
     * 根据用户ID获取用户当前持有的资源数
     * @param userId 用户ID
     * @return 用户当前持有的资源数量
     */
    Integer getUserResource(Long userId);

    /**
     * 根据用户ID变更用户当前持有的资源数
     * @param userId 用户ID
     * @param resource 新的资源数
     * @return 是否成功
     */
    Integer updateUserResource(@Param("userId") Long userId , @Param("resource") Integer resource);

    /**
     * 获取用户列表
     * @param listType 列表类型
     * @param lineNum 页码
     * @return 用户基本信息列表
     */
    List<UserBaseVO> getUserList(@Param("listType") Integer listType , @Param("lineNum") Integer lineNum);

    /**
     * 根据用户类型获取用户ID列表
     * @param listType 用户类型
     * @return 用户ID组成的list
     */
    List<Long> getUserIdListByType(Integer listType);

    /**
     * 根据用户ID获取用户名
     * @param userId 用户ID
     * @return 用户名
     */
    String getUserNameById(Long userId);

    /**
     * 根据用户ID获取用户密码
     * @param userId 用户ID
     * @return 用户密码
     */
    String getUserPw(Long userId);
}
