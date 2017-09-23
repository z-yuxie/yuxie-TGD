package com.yuxie.tgd.service;

import com.yuxie.tgd.pojo.dto.UserSessionDTO;
import com.yuxie.tgd.pojo.vo.LabelVO;

import java.util.List;

/**
 * 标签列表服务
 * Created by 147356 on 2017/4/24.
 */
public interface ILabelListService {

    /**
     * 获取标签列表,返回数量根据配置文件决定
     * @param userSessionDTO 如果用户已登录,则可返回该用户关注的标签列表
     * @return 标签列表
     * @throws Exception 可能发生的redis、sql等异常
     */
    List<LabelVO> getLabelList(UserSessionDTO userSessionDTO) throws Exception;

    /**
     * 根据用户ID和标签ID变更或新建用户标签关注状态
     * @param userId 用户ID
     * @param labelId 标签ID
     * @return 是否成功
     * @throws Exception 可能发生的redis、sql等异常
     */
    Integer changeAttentionType(Long userId , Long labelId) throws Exception;
}
