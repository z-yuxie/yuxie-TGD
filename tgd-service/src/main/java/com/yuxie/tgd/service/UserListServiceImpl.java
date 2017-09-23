package com.yuxie.tgd.service;

import com.yuxie.tgd.common.util.LoginValidation;
import com.yuxie.tgd.common.util.MyConstans;
import com.yuxie.tgd.common.util.MySQLException;
import com.yuxie.tgd.mymapper.ObjectUserStatusMapper;
import com.yuxie.tgd.mymapper.UserMapper;
import com.yuxie.tgd.mymapper.UserRelationMapper;
import com.yuxie.tgd.pojo.dto.ListParamDTO;
import com.yuxie.tgd.pojo.dto.UserSessionDTO;
import com.yuxie.tgd.pojo.vo.UserBaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取用户列表Service
 * Created by 147356 on 2017/4/20.
 */
@Service
public class UserListServiceImpl implements IUserListService{

    private static final Logger log= LoggerFactory.getLogger(UserListServiceImpl.class);

    //每页用户数量
//    @Value("${pageObjectNum}")
    private Integer pageObjectNum = 30;

    @Autowired
    private LoginValidation loginValidation;
    @Autowired
    private UserLevelService userLevelService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRelationMapper userRelationMapper;
    @Autowired
    private ObjectUserStatusMapper objectUserStatusMapper;

    /**
     * 根据用户的ID和会话ID获取规则等信息,获取符合要求的包含用户基本信息的用户列表
     * @param listParamDTO 包含用户ID,会话ID等信息
     * @return List<UserBaseVO> 包含用户基本信息的用户信息列表
     * @throws Exception 可能发生的redis、sql等异常
     */
    @Override
    public List<UserBaseVO> getUserList(ListParamDTO listParamDTO) throws Exception{
        //页码
        int pageNum = ((listParamDTO.getPageNum() == null) ? 0 : (listParamDTO.getPageNum() - 1));
        List<Long> userIdList;
        List<UserBaseVO> userBaseVOList;
        UserBaseVO userBaseVO;
        Long userId = listParamDTO.getUserId();
        String sessionId = listParamDTO.getSessionId();
        Integer listType;
        Integer status = loginValidation.validation(userId , sessionId);
        if (!MyConstans.SUCCESS_STATUS_NUM.equals(status)) {//判断用户是否已登录
            return null;
        }
        UserSessionDTO userSessionDTO = new UserSessionDTO();
        userSessionDTO.setUserId(userId);
        userSessionDTO.setSessionId(sessionId);
        status = userLevelService.mannagerValidation(userSessionDTO);//验证用户是否为管理员
        listType = listParamDTO.getListType();
        try {
            if (listType == null || listType ==3 || listParamDTO.getObjectId() != null) {//此处用于筛选需要先获取用户ID列表后才能获取到用户基本信息列表的情况
                if (listParamDTO.getObjectId() != null) {
                    //此处通过对象ID获取用户ID列表时进行了去重
                    userIdList = objectUserStatusMapper.getInvolvedUserIdListByObejctId(listParamDTO.getObjectId());
                } else {
                    //获取用户关注的用户的列表
                    userIdList = userRelationMapper.getUserIdList(userId , 1);
                }
                if (userIdList.isEmpty()) {
                    return null;
                }
                userBaseVOList = new ArrayList<>();
                int i = 1;
                for (Long userIdTmp : userIdList) {
                    if (i <= (pageNum * pageObjectNum)) {//还未到达指定页数
                        i++;
                        continue;
                    }
                    if (i > (pageNum + 1) * pageObjectNum) {//用户列表中用户数量已到达一页的数量
                        log.info("========指定页中的用户基本信息列表获取完毕========");
                        break;
                    }
                    try {
                        userBaseVO = userMapper.getUserBaseMessage(userIdTmp);
                        userBaseVOList.add(userBaseVO);
                    } catch (Exception e) {
                        //如果循环过程中发生异常,直接捕获,日志输出错误信息后跳过,以免无法获取后续的用户信息
                        log.error("========获取用户"+userIdTmp+"的信息出错======="+e.toString());
                        continue;
                    }
                }
            } else {//管理员进行管理操作时,通过用户状态可直接获取到用户基本信息列表
                //如果用户不是管理员
                if (MyConstans.USER_LEVEL_NUM.equals(status)) {
                    return null;
                }
                int lineNum = pageNum*pageObjectNum+1;
                userBaseVOList = userMapper.getUserList(listType , lineNum);
            }
        } catch (Exception e) {
            log.error("======获取用户列表失败======="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
        if (userBaseVOList == null ||userBaseVOList.isEmpty()) {//由于某些原因导致没有获取到任一个用户的基本信息
            return null;
        } else {//成功获取到用户信息列表
            return userBaseVOList;
        }
    }
}
