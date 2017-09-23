package com.yuxie.tgd.service;

import com.yuxie.tgd.common.util.LoginValidation;
import com.yuxie.tgd.common.util.MyConstans;
import com.yuxie.tgd.common.util.MySQLException;
import com.yuxie.tgd.mymapper.UserMapper;
import com.yuxie.tgd.mymapper.UserRelationMapper;
import com.yuxie.tgd.pojo.bean.UserRelation;
import com.yuxie.tgd.pojo.bean.YuxieUser;
import com.yuxie.tgd.pojo.dto.UserMessageDTO;
import com.yuxie.tgd.pojo.dto.UserSessionDTO;
import com.yuxie.tgd.pojo.vo.UserMessageVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户小操作Service 返回用户相关的一些包含少量字段的数据
 * Created by 147356 on 2017/4/19.
 */
@Service
public class UserServiceImpl implements IUserService {

    private static final Logger log= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginValidation loginValidation;
    @Autowired
    private UserRelationMapper userRelationMapper;
    @Autowired
    private UserLevelService userLevelService;
    @Autowired
    private UserNamePwVerification userNamePwVerification;

    /**
     * 根据用户ID获取用户的详细信息
     * @param userSessionDTO 包含被查看用户ID等信息
     * @return 包含用户详细信息
     * @throws Exception redis、sql等异常
     */
    @Override
    public UserMessageVO getUserMessageById(UserSessionDTO userSessionDTO) throws Exception {
        UserMessageVO userMessageVO;
        Long userId = userSessionDTO.getUserId();
        Long checkedUserId = userSessionDTO.getCheckedUserId();
        String sessionId = userSessionDTO.getSessionId();
        try {
            userMessageVO = userMapper.getUserMessage(checkedUserId);
        }catch (Exception e){
            log.error("============获取用户信息失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
        if (userId != null && sessionId != null){
            int loginStatus = loginValidation.validation(userId , sessionId);
            if (loginStatus == MyConstans.SUCCESS_STATUS_NUM){
                userMessageVO.setRelationType(getUserRelation(userSessionDTO));
            }
        }
        if (!checkedUserId.equals(userId)){
            if (userMessageVO.getRelationType() == null) {
                userMessageVO.setRelationType(0);
            }
            userMessageVO.setEmail(null);
            userMessageVO.setPhone(null);
        }
        return userMessageVO;
    }

    /**
     * 更新用户的信息
     * @param userMessageDTO 包含用户最新信息
     * @return 返回包含更新后用户信息的
     * @throws Exception redis、sql等异常
     */
    @Override
    public UserMessageVO updateUserMessage(UserMessageDTO userMessageDTO) throws Exception {
        UserMessageVO userMessageVO = new UserMessageVO();
        Long userId = userMessageDTO.getUserId();
        String sessionId = userMessageDTO.getSessionId();
        String userPw = (StringUtils.isBlank(userMessageDTO.getUserPw()) ? null : userMessageDTO.getUserPw());
        String userName = (StringUtils.isBlank(userMessageDTO.getUserName()) ? null : userMessageDTO.getUserName());
        Integer userCount = 0;
        if (StringUtils.isBlank(userPw) || StringUtils.isBlank(userName)) {
            if (StringUtils.isBlank(userPw) && StringUtils.isNotBlank(userName)) {
                userPw = userMapper.getUserPw(userId);
            } else if (StringUtils.isNotBlank(userPw)) {
                userName = userMapper.getUserNameById(userId);
            }
        }
        userCount = userNamePwVerification.getUserCountByPw(userName,userPw);
        if(!MyConstans.DATE_CHANGE_IS_FAIL_OR_NOT_FIND_NUM.equals(userCount)){
            userMessageVO.setUserStatus(MyConstans.USER_IS_EXISTING_NUM);//用户已存在
            userMessageVO.setResource(MyConstans.FAIL_STATUS_NUM);//由于暂时没有可能的用于返回错误信息的变量，所以用resource和userStatus这两个变量的值一起来作为错误标识供controller层进行识别
            return userMessageVO;
        }
        Integer updateCount;
        Integer loginStatus = loginValidation.validation(userId , sessionId);
        if(!MyConstans.SUCCESS_STATUS_NUM.equals(loginStatus)){
            return null;
        }
        YuxieUser yuxieUser = new YuxieUser();
        yuxieUser = setUserMessage(yuxieUser , userMessageDTO);
        try{
            updateCount = userMapper.updateUserMessage(yuxieUser);
            if(MyConstans.SUCCESS_STATUS_NUM.equals(updateCount)) {
                userMessageVO = userMapper.getUserMessage(userId);
                userMessageVO.setSessionId(loginValidation.getSessionId(userId));//设置会话ID
                return userMessageVO;
            } else {
                return null;
            }
        }catch (Exception e){
            log.error("============更新用户信息失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
    }

    /**
     * 用户注册
     * @param userMessageDTO 包含用户注册信息
     * @return 包含用户信息
     * @throws Exception 可能发生的redis、sql等异常
     */
    @Override
    public UserMessageVO userRegist(UserMessageDTO userMessageDTO) throws Exception{
        UserMessageVO userMessageVO = new UserMessageVO();
        Long userId;
        String userName = userMessageDTO.getUserName();
        String userPw = userMessageDTO.getUserPw();
        Integer userCount = userNamePwVerification.getUserCountByPw(userName,userPw);
        YuxieUser yuxieUser = new YuxieUser();
        yuxieUser = setUserMessage(yuxieUser , userMessageDTO);
        if(userCount == null ){
            userMessageVO.setUserStatus(MyConstans.FAIL_STATUS_NUM);//查询失败
            userMessageVO.setResource(MyConstans.FAIL_STATUS_NUM);//由于暂时没有可能的用于返回错误信息的变量，所以用resource和userStatus这两个变量的值一起来作为错误标识供controller层进行识别
            return userMessageVO;
        } else if(!MyConstans.DATE_CHANGE_IS_FAIL_OR_NOT_FIND_NUM.equals(userCount)){
            userMessageVO.setUserStatus(MyConstans.USER_IS_EXISTING_NUM);//用户已存在
            userMessageVO.setResource(MyConstans.FAIL_STATUS_NUM);//由于暂时没有可能的用于返回错误信息的变量，所以用resource和userStatus这两个变量的值一起来作为错误标识供controller层进行识别
            return userMessageVO;
        } else {
            userMapper.userResgit(yuxieUser);
            userId = yuxieUser.getUserId();
            userMapper.updateUserResource(userId , 100);//注册奖励100资源
        }
        try {
            userMessageVO = userMapper.getUserMessage(userId);
        }catch (Exception e){
            log.error("============获取用户信息失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
        userMessageVO.setSessionId(loginValidation.getSessionId(userId));//设置会话ID
        return userMessageVO;
    }

    /**
     * 用户登录
     * @param userId 用户ID
     * @return 包含用户详细信息
     * @throws Exception 可能发生的redis、sql等异常
     */
    @Override
    public UserMessageVO userLogin(Long userId) throws Exception{
        UserMessageVO userMessageVO;
        Integer resource = 10;//登录奖励资源数
        try {
            resource = userMapper.getUserResource(userId) + resource;
            userMapper.updateUserResource(userId , resource);//更新用户当前持有资源数
            userMessageVO = userMapper.getUserMessage(userId);
            String sessionId = loginValidation.getSessionId(userId);
            userMessageVO.setSessionId(sessionId);//设置会话ID
            UserSessionDTO userSessionDTO = new UserSessionDTO();
            userSessionDTO.setUserId(userId);
            userSessionDTO.setSessionId(sessionId);
            userMessageVO.setUserLevel(userLevelService.mannagerValidation(userSessionDTO));
            return userMessageVO;
        }catch (Exception e){
            log.error("============获取用户信息失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
    }

    /**
     * 获取一个用户与另一个用户之间的关系状态值或者关注专注状态
     * @param userSessionDTO 包含两者UserId和会话ID的包装类
     * @return 状态值 0未关注 1 已关注
     * @throws Exception 可能发生的redis、SQL以及未知的异常
     */
    @Override
    public int getUserRelation(UserSessionDTO userSessionDTO) throws Exception {
        Integer relationType = null;
        int status = 0;
        if (userSessionDTO.getUserId() !=null
                && userSessionDTO.getSessionId() != null) {
            status = loginValidation.validation(userSessionDTO.getUserId()
                    , userSessionDTO.getSessionId());
        }
        try {
            if (status == MyConstans.SUCCESS_STATUS_NUM){
                relationType = userRelationMapper.getUserRelation(userSessionDTO.getUserId() , userSessionDTO.getCheckedUserId());
            }
            if (relationType == null){
                relationType = MyConstans.DATE_CHANGE_IS_FAIL_OR_NOT_FIND_NUM;
            }
            return relationType;
        }catch (Exception e){
            log.error("============获取用户关注信息失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
    }

    /**
     * 变更用户与另一个用户之间的关系
     * @param userSessionDTO 包含两者UserID和会话ID
     * @return 变更是否成功及当前状态值 -5失败 0未关注 1已关注
     * @throws Exception 可能发生的redis、sql以及未知的异常
     */
    @Override
    public int changeUserRelation(UserSessionDTO userSessionDTO) throws Exception {
        int status;
        Integer relationType;
        UserRelation userRelation = new UserRelation();
        userRelation.setUserId(userSessionDTO.getUserId());
        userRelation.setRelationUserId(userSessionDTO.getCheckedUserId());
        Integer loginStatus = loginValidation.validation(userSessionDTO.getUserId() , userSessionDTO.getSessionId());
        if(!MyConstans.SUCCESS_STATUS_NUM.equals(loginStatus)){
            return -5;
        }
        try {
            relationType = userRelationMapper.getUserRelation(userSessionDTO.getUserId() , userSessionDTO.getCheckedUserId());
            if (relationType != null) {
                relationType = (relationType + 1) % 2;
                userRelation.setRelationType(relationType);
                status = userRelationMapper.updateUserRelation(userRelation);
            } else {
                relationType = 1;
                userRelation.setRelationType(relationType);
                status = userRelationMapper.insertUserRelation(userRelation);
            }
        }catch (Exception e) {
            log.error("============获取用户间信息失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
        if(status == MyConstans.DATE_CHANGE_IS_FAIL_OR_NOT_FIND_NUM){
            relationType = MyConstans.FAIL_STATUS_NUM;
        } else {
            relationType = userRelation.getRelationType();
        }
        return relationType;
    }

    /**
     * 根据用户ID获取该用户当前的状态
     * @param userId 用户ID
     * @return 该用户当前的状态值 -5查询失败 -1被封禁 -2神 0正常 >0被举报次数
     * @throws Exception 可能发生的SQL等异常
     */
    @Override
    public int getUserStatus(Long userId) throws Exception {
        Integer userStatus;
        try {
            userStatus = userMapper.getUserStatus(userId);
            if (userStatus == null) {
                userStatus = MyConstans.FAIL_STATUS_NUM;
            }
            return userStatus;
        } catch (Exception e) {
            log.error("============获取用户状态信息失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
    }

    /**
     * 变更用户的状态
     * @param userSessionDTO 包含被操作用户ID及新的状态值
     * @return 变更后用户的状态
     * @throws Exception 可能发生的redis、sql等异常
     */
    @Override
    public int changeUserStatus(UserSessionDTO userSessionDTO) throws Exception {
        Integer userLevel;
        Integer userStatus;
        userLevel = userLevelService.mannagerValidation(userSessionDTO);
        try {
            if (!userSessionDTO.getStatus().equals(MyConstans.ADD_NUM) && !userLevel.equals(MyConstans.MANNAGER_LEVEL_NUM)) {
                userStatus = MyConstans.FAIL_STATUS_NUM;
                return userStatus;
            } else {
                userStatus = userMapper.getUserStatus(userSessionDTO.getCheckedUserId());
                if (MyConstans.ADD_NUM.equals(userSessionDTO.getStatus())) {
                    if (userStatus != -1) {
                        userSessionDTO.setStatus(userStatus + 1);
                    }
                } else{
                    userStatus = (userStatus == null) ? 0 : userStatus;
                    userStatus = (userStatus - 1) % 2;
                    userSessionDTO.setStatus(userStatus);
                }
                userLevel = userMapper.updateUserStatus(userSessionDTO.getCheckedUserId() , userSessionDTO.getStatus());
            }
            if (!MyConstans.SUCCESS_STATUS_NUM.equals(userLevel)) {
                userStatus = MyConstans.FAIL_STATUS_NUM;
            } else {
                userStatus = userSessionDTO.getStatus();
            }
            return userStatus;
        } catch (Exception e) {
            log.error("============用户信息操作失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
    }

    /**
     * 根据用户ID获取用户当前持有的资源数
     * @param userId 用户ID
     * @return 用户当前的资源数
     * @throws Exception SQL等可能出现的异常
     */
    @Override
    public int getUserResource(Long userId) throws Exception {
        Integer resource;
        try {
            resource = userMapper.getUserResource(userId);
            if (resource == null) {
                resource = MyConstans.FAIL_STATUS_NUM;
            }
            return resource;
        }catch (Exception e) {
            log.error("============获取用户状态信息失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
    }

    /**
     * 变更用户当前持有的资源数
     * @param userSessionDTO 包含有用户ID、会话ID、需要变更的资源数等信息
     * @return 新的用户资源数
     * @throws Exception 可能发生的redis、sql等异常
     */
    @Override
    public int changeUserResource(UserSessionDTO userSessionDTO) throws Exception {
        Long userId = userSessionDTO.getUserId();
        Integer resource = userSessionDTO.getStatus();
        String sessionId = userSessionDTO.getSessionId();
        Integer status = loginValidation.validation(userId , sessionId);
        if (MyConstans.SUCCESS_STATUS_NUM.equals(status)) {
            try {
                resource = userMapper.getUserResource(userId) + resource;
                status = userMapper.updateUserResource(userId , resource);
            } catch (Exception e) {
                log.error("============获取用户状态信息失败============="+e.toString());
                throw new MySQLException(MyConstans.SQL_DATE_FAIL);
            }
            if (MyConstans.SUCCESS_STATUS_NUM.equals(status)) {
                return resource;
            } else {
                return MyConstans.FAIL_STATUS_NUM;
            }
        } else {
            return MyConstans.FAIL_STATUS_NUM;
        }
    }

    /**
     * 从UserMessageDTO中获取YuxieUser需要的值
     * @param yuxieUser YuxieUser类的实例
     * @param userMessageDTO UserMessageDTO类的实例
     * @return 已复制好数据的YuxieUser类实例
     */
    private YuxieUser setUserMessage(YuxieUser yuxieUser , UserMessageDTO userMessageDTO){
        if (userMessageDTO.getUserId() != null){
            yuxieUser.setUserId(userMessageDTO.getUserId());
        }
        if (userMessageDTO.getUserName() != null) {
            yuxieUser.setUserName(userMessageDTO.getUserName());
        }
        if (userMessageDTO.getUserPw() != null) {
            yuxieUser.setUserPw(userMessageDTO.getUserPw());
        }
        if (userMessageDTO.getEmail() != null) {
            yuxieUser.setEmail(userMessageDTO.getEmail());
        }
        if (userMessageDTO.getPhone() != null) {
            yuxieUser.setPhone(userMessageDTO.getPhone());
        }
        if (userMessageDTO.getContact() != null) {
            yuxieUser.setContact(userMessageDTO.getContact());
        }
        return yuxieUser;
    }
}
