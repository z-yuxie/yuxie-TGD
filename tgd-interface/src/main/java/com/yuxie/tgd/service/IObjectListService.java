package com.yuxie.tgd.service;

import com.yuxie.tgd.pojo.dto.ListParamDTO;
import com.yuxie.tgd.pojo.vo.ObjectBaseVO;

import java.util.List;

/**
 * 获取对象列表的sevice接口
 * Created by 147356 on 2017/4/10.
 */
public interface IObjectListService {

    /**
     * 获取对象列表
     * @param listParamDTO 包含用于筛选对象列表类型的信息
     * @return 包含对象基本信息的对象列表
     * @throws Exception 可能发生的redis、sql等异常
     */
    List<ObjectBaseVO> getObjectList(ListParamDTO listParamDTO) throws Exception;
}
