package com.yuxie.tgd.mymapper;

import com.yuxie.tgd.pojo.bean.UserRelation;
import com.yuxie.tgd.pojo.dto.UserSessionDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户关系表操作类
 * Created by 147356 on 2017/4/19.
 */
@Repository
public interface UserRelationMapper {

    /**
     * 获取用户之间的关系
     * @param userId 当前用户ID
     * @param checkedUserId 被关注用户ID
     * @return 当前关注状态值
     */
    Integer getUserRelation(@Param("userId") Long userId , @Param("checkedUserId") Long checkedUserId);

    /**
     * 更新用户之间的关系
     * @param userRelation 包含用户间新关系信息的包装类
     * @return 是否成功
     */
    Integer updateUserRelation(UserRelation userRelation);

    /**
     * 插入新的用户关注记录
     * @param userRelation 包含用户间关系信息的转转呢
     * @return 是否插入成功
     */
    Integer insertUserRelation(UserRelation userRelation);

    /**
     * 根据用户ID获取该用户关注的用户的列表
     * @param userId 用户ID
     * @param relationType 是否关注
     * @return 该用户关注的所有用户的列表
     */
    List<Long> getUserIdList(@Param("userId") Long userId , @Param("relationType") Integer relationType);
}
