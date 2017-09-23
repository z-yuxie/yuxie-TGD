package com.yuxie.tgd.service;

import com.yuxie.tgd.pojo.dto.ListParamDTO;
import com.yuxie.tgd.pojo.vo.UserBaseVO;

import java.util.List;

/**
 * 获取用户列表
 * Created by 147356 on 2017/4/10.
 */
public interface IUserListService {

    /**
     * 获取用户列表
     * @param listParamDTO 包含用户ID,会话ID等信息
     * @return 用户列表
     * @throws Exception 可能发生的redis、sql等异常
     */
    List<UserBaseVO> getUserList(ListParamDTO listParamDTO) throws Exception;
}
