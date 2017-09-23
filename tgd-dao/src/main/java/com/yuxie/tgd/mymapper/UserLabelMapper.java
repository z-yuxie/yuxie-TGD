package com.yuxie.tgd.mymapper;



import com.yuxie.tgd.pojo.bean.UserLabel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户标签表操作mapper
 * Created by 147356 on 2017/4/24.
 */
@Repository
public interface UserLabelMapper {

    /**
     * 根据用户ID获取其关注的标签ID列表
     * @param userId 用户ID
     * @return 标签ID列表
     */
    List<Long> getLabelIdListByUserId(Long userId);

    /**
     * 更新用户对某标签的关注状态
     * @param userLabel 包含用户和标签ID,关注状态等信息
     * @return 是否成功
     */
    Integer updateAttentionType(UserLabel userLabel);

    /**
     * 新插入一个标签关注记录
     * @param userLabel 包含用户和标签ID信息,关注状态数据库默认插入1
     * @return 主键ID
     */
    Integer insertAttention(UserLabel userLabel);

    /**
     * 根据用户ID和标签ID获取当前关注状态
     * @param userId 用户ID
     * @param labelId 标签ID
     * @return 关注状态
     */
    Integer getAttentionNum(@Param("userId") Long userId , @Param("labelId") Long labelId);
}
