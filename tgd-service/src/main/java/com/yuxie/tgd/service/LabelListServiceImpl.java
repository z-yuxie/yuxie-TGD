package com.yuxie.tgd.service;

import com.yuxie.tgd.common.util.LoginValidation;
import com.yuxie.tgd.common.util.MyConstans;
import com.yuxie.tgd.common.util.MySQLException;
import com.yuxie.tgd.mymapper.LabelListMapper;
import com.yuxie.tgd.mymapper.UserLabelMapper;
import com.yuxie.tgd.pojo.bean.LabelList;
import com.yuxie.tgd.pojo.bean.UserLabel;
import com.yuxie.tgd.pojo.dto.UserSessionDTO;
import com.yuxie.tgd.pojo.vo.LabelVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户标签操作服务
 * Created by 147356 on 2017/4/24.
 */
@Service
public class LabelListServiceImpl implements ILabelListService{

    private static final Logger log= LoggerFactory.getLogger(LabelListServiceImpl.class);

//    @Value(value = "${labelListNum}")
//    private Integer labelListNum;
    private Integer labelListNum = 20;

    @Autowired
    private LabelListMapper labelListMapper;
    @Autowired
    private UserLabelMapper userLabelMapper;
    @Autowired
    private LoginValidation loginValidation;

    /**
     * 获取标签列表,返回数量根据配置文件决定
     * @param userSessionDTO 如果用户已登录,则可返回该用户关注的标签列表
     * @return 标签列表
     * @throws Exception 可能发生的redis、sql等异常
     */
    @Override
    public List<LabelVO> getLabelList(UserSessionDTO userSessionDTO) throws Exception{
        Long userId = userSessionDTO.getUserId();
        String sessionId = (StringUtils.isBlank(userSessionDTO.getSessionId()) ? null : userSessionDTO.getSessionId());
        List<LabelVO> labelVOList = new ArrayList<>();
        List<Long> relationLabelIdList = new ArrayList<>();
        int i = 0;
        try {
            if (userId != null && sessionId != null) {
                Integer loginStatus = loginValidation.validation(userId , sessionId);
                if (loginStatus.equals(MyConstans.SUCCESS_STATUS_NUM)) {
                    List<Long> labelIdList =userLabelMapper.getLabelIdListByUserId(userId);
                    if (labelIdList == null ) {
                        return null;
                    }
                    if (!labelIdList.isEmpty()) {
                        for (Long labelId : labelIdList) {
                            if (i >= labelListNum) {
                                return labelVOList;
                            }
                            LabelList labelList = labelListMapper.getLabelListBeanById(labelId);
                            if (labelList != null) {
                                LabelVO labelVO = new LabelVO();
                                Long relationLabelId = new Long(labelList.getLabelId().toString());
                                labelVO.setLabelId(relationLabelId);
                                relationLabelIdList.add(relationLabelId);
                                labelVO.setLabelName(labelList.getLabelName());
                                labelVO.setHotNum(labelList.getHotNum());
                                labelVO.setRelationType(1);
                                labelVOList.add(labelVO);
                                i++;
                            }
                        }
                    }
                }
            }
            List<LabelList> labelVOS = labelListMapper.getLabelList(labelListNum);
            Long labelId;
            String labelName;
            if (labelVOS != null) {
                if (!labelVOS.isEmpty()) {
                    for (LabelList labelList :labelVOS) {
                        if (i >= labelListNum) {
                            return labelVOList;
                        }
                        if (!relationLabelIdList.isEmpty()) {//如果关注标签ID的List不为空
                            if (relationLabelIdList.contains(labelList.getLabelId())) {//判断该id是否已存在该list中
                                continue;//已存在则跳过
                            }
                        }
                        LabelVO labelVO = new LabelVO();
                        labelId = labelList.getLabelId();
                        labelName = labelList.getLabelName();
                        labelVO.setLabelId(labelId);
                        labelVO.setLabelName(labelName);
                        labelVO.setHotNum(labelList.getHotNum());
                        labelVO.setRelationType(0);
                        labelVOList.add(labelVO);
                        i++;
                    }
                    return labelVOList;
                }
            }
        } catch (Exception e) {
            log.error("============获取标签列表失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
        return null;
    }

    /**
     * 根据用户ID和标签ID变更或新建用户标签关注状态
     * @param userId 用户ID
     * @param labelId 标签ID
     * @return 更新后的状态值
     * @throws Exception 可能发生的redis、sql等异常
     */
    @Override
    public Integer changeAttentionType(Long userId, Long labelId) throws Exception{
        Integer attentionNum = 0;
        try {
            attentionNum = userLabelMapper.getAttentionNum(userId , labelId);
            UserLabel userLabel = new UserLabel();
            if (attentionNum != null) {
                userLabel.setUserId(userId);
                userLabel.setLabelId(labelId);
                attentionNum = (attentionNum + 1) % 2;
                userLabel.setAttentionType(attentionNum);
                userLabelMapper.updateAttentionType(userLabel);
            } else {
                userLabel.setUserId(userId);
                userLabel.setLabelId(labelId);
                userLabel.setAttentionType(1);
                userLabelMapper.insertAttention(userLabel);
                attentionNum = 1;
            }
        } catch (Exception e) {
            log.error("============变更标签关注状态失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
        return attentionNum;
    }
}
