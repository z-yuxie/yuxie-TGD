package com.yuxie.tgd.controller;

import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yuxie.tgd.common.util.*;
import com.yuxie.tgd.pojo.dto.UserMessageDTO;
import com.yuxie.tgd.pojo.dto.UserSessionDTO;
import com.yuxie.tgd.pojo.vo.UserMessageVO;
import com.yuxie.tgd.service.ISessionService;
import com.yuxie.tgd.service.IUserService;
import com.yuxie.tgd.service.UserLevelService;
import com.yuxie.tgd.service.UserNamePwVerification;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 用户基础操作
 * Created by 147356 on 2017/4/8.
 */
@Controller
@RequestMapping(value = "/userBase")
@Api(value = "用户基础操作", description = "用户基础操作")
public class UserController {

    private static final Logger log= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private UserLevelService userLevelService;
    @Autowired
    private LoginValidation loginValidation;
    @Autowired
    private UserNamePwVerification userNamePwVerification;
    @Autowired
    private ISessionService sessionService;


    /**
     * 根据用户ID获取用户的可公开详细信息
     * @param userSessionDTO 包含用户信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserMessageById",method = RequestMethod.POST)
    @ApiOperation(value = "根据用户ID获取用户的可公开详细信息" , httpMethod = "POST" , notes = "根据用户ID获取用户的可公开详细信息")
    public JSONObject getUserMessageById(@ApiParam(required = true, name = "userSessionDTO", value = "包含用户信息")
                                             @RequestBody UserSessionDTO userSessionDTO){
        if(userSessionDTO == null || userSessionDTO.getUserId() == null
                || StringUtils.isBlank(userSessionDTO.getSessionId())
                || userSessionDTO.getCheckedUserId() == null){
            log.debug("=======参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        UserMessageVO userMessageVO;
        String json;
        try{
            int loginStatus=loginValidation.validation(userSessionDTO.getUserId(),userSessionDTO.getSessionId());
            if (loginStatus != 1) {
                log.debug("=======获取用户信息失败======");
                return JsonResponse.fail(MyConstans.DONOT_LOGIN);
            }
            userMessageVO = userService.getUserMessageById(userSessionDTO);
            if (userMessageVO != null){
                json = JSONObject.toJSONString(userMessageVO);
                return JsonResponse.success(json);
            }else {
                log.debug("=======获取用户信息失败======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
            }
        } catch (RedisException e) {
            log.error("=======验证用户登录状态异常======"+e.toString());
            return JsonResponse.fail(MyConstans.REDIS_GET_IS_FAIL);
        } catch (MySQLException e){
            log.error("=======查询数据库时发生异常异常===="+e.toString());
            return JsonResponse.fail(MyConstans.SQL_DATE_FAIL);
        } catch (Exception e) {
            log.error("=======发生未知异常======="+e.toString());
            return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
        }
    }

    /**
     * 更新某个用户的信息
     * @param userMessageDTO 用户会话参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateUserMessageById",method = RequestMethod.POST)
    @ApiOperation(value = "更新某个用户的信息" , httpMethod = "POST" , notes = "更新某个用户的信息")
    public JSONObject updateUserMessage(@ApiParam(required = true, name = "userMessageDTO", value = "用户会话参数")
                                            @RequestBody UserMessageDTO userMessageDTO){
        if(userMessageDTO == null || userMessageDTO.getUserId() == null
                || StringUtils.isBlank(userMessageDTO.getSessionId())){
            log.debug("=======参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        UserMessageVO userMessageVO;
        String json;
        try{
            userMessageVO = userService.updateUserMessage(userMessageDTO);
            if (userMessageVO == null){
                log.debug("=======更新用户信息失败======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
            } else if(userMessageVO.getUserStatus().equals(MyConstans.USER_IS_EXISTING_NUM) && userMessageVO.getResource().equals(MyConstans.FAIL_STATUS_NUM)){
                log.debug("=======用户信息与其他用户重复,无法完成更新======");
                return JsonResponse.fail(MyConstans.USER_IS_EXISTING);
            } else {
                json = JSONObject.toJSONString(userMessageVO);
                return JsonResponse.success(json);
            }
        } catch (RedisException e) {
            log.error("=======验证用户登录状态异常======"+e.toString());
            return JsonResponse.fail(MyConstans.REDIS_GET_IS_FAIL);
        } catch (MySQLException e){
            log.error("=======查询数据库时发生异常异常===="+e.toString());
            return JsonResponse.fail(MyConstans.SQL_DATE_FAIL);
        } catch (Exception e) {
            log.error("=======发生未知异常======="+e.toString());
            return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
        }
    }

    /**
     * 用户注册
     * @param userMessageDTO 用户会话参数
     * @return userId,userLevel,sessionId
     */
    @ResponseBody
    @RequestMapping(value = "/userRegist",method = RequestMethod.POST)
    @ApiOperation(value = "用户注册" , httpMethod = "POST" , notes = "用户注册")
    public JSONObject userRegist(@ApiParam(required = true, name = "userMessageDTO", value = "用户会话参数")
                                     @RequestBody UserMessageDTO userMessageDTO){
        if(userMessageDTO == null || StringUtils.isBlank(userMessageDTO.getUserName())
                || StringUtils.isBlank(userMessageDTO.getUserPw())){
            log.debug("=======参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        UserMessageVO userMessageVO;
        String json;
        try{
            userMessageVO = userService.userRegist(userMessageDTO);
            if (userMessageVO != null){
                int userStatus=userMessageVO.getUserStatus();
                int resource=userMessageVO.getResource();
                if(userStatus == MyConstans.USER_IS_EXISTING_NUM && resource == MyConstans.FAIL_STATUS_NUM){
                    log.info("=======目标用户已存在======");
                    return JsonResponse.fail(MyConstans.USER_IS_EXISTING);
                } else if(userStatus == MyConstans.ANOTHER_FAIL_NUM && resource == MyConstans.FAIL_STATUS_NUM){
                    log.debug("=======查询用户是否已存在失败======");
                    return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
                } else {
                    json = JSONObject.toJSONString(userMessageVO);
                    return JsonResponse.success(json);
                }
            }else {
                log.debug("=======用户注册获取返回信息失败======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
            }
        } catch (RedisException e) {
            log.error("=======验证用户登录状态异常======"+e.toString());
            return JsonResponse.fail(MyConstans.REDIS_GET_IS_FAIL);
        } catch (MySQLException e){
            log.error("=======查询数据库时发生异常异常===="+e.toString());
            return JsonResponse.fail(MyConstans.SQL_DATE_FAIL);
        } catch (Exception e) {
            log.error("=======发生未知异常======="+e.toString());
            return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
        }
    }

    /**
     * 用户登录
     * @param userMessageDTO 登录信息
     * @return userId,userLevel,sessionId
     */
    @ResponseBody
    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    @ApiOperation(value = "用户登录" , httpMethod = "POST" , notes = "用户登录")
    public JSONObject userLogin(@ApiParam(required = true, name = "userMessageDTO", value = "登录信息")
                                    @RequestBody UserMessageDTO userMessageDTO){
        if(userMessageDTO == null || StringUtils.isBlank(userMessageDTO.getUserName())
                || StringUtils.isBlank(userMessageDTO.getUserPw())){
            log.debug("=======参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        UserMessageVO userMessageVO;
        String json;
        try {
            String userName = userMessageDTO.getUserName();
            String userPw = userMessageDTO.getUserPw();
            Integer userCount = userNamePwVerification.getUserCountByPw(userName,userPw);//根据用户名和密码确定满足条件的用户数量
            Long userId = null;
            String sessionId = null;
            int loginStatus = -1;
            if (userCount != null && MyConstans.SUCCESS_STATUS_NUM.equals(userCount)){//如果满足条件的用户只有一个
                userId = userNamePwVerification.getUserIdByPw(userName,userPw);//根据用户名和密码获取用户ID
//                sessionId = sessionService.getSessionId(userId.toString());
            } else {
                return JsonResponse.fail(MyConstans.DONOT_REGIST);
            }
            userMessageVO = userService.userLogin(userId);
//            if (StringUtils.isNotBlank(sessionId)) {
//                return JsonResponse.fail(MyConstans.REPEAT_LOGIN);
//            } else {
//                userMessageVO = userService.userLogin(userId);
//            }
            if (userMessageVO != null) {
                json = JSONObject.toJSONString(userMessageVO);
                return JsonResponse.success(json);
            } else {
                log.debug("=======用户登录返回用户信息失败=======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
            }
        } catch (RedisException e) {
            log.error("=======验证用户登录状态异常======" + e.toString());
            return JsonResponse.fail(MyConstans.REDIS_GET_IS_FAIL);
        } catch (MySQLException e){
            log.error("=======查询数据库时发生异常异常====" + e.toString());
            return JsonResponse.fail(MyConstans.SQL_DATE_FAIL);
        } catch (Exception e) {
            log.error("=======发生未知异常=======" + e.toString());
            return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
        }
    }

    /**
     * 根据用户ID获取用户间的关注状态
     * @param userSessionDTO 包含被查看用户的ID等信息
     * @return 用户间关系值
     */
    @ResponseBody
    @RequestMapping(value = "/getUserRelation",method = RequestMethod.POST)
    @ApiOperation(value = "根据用户ID获取用户间的关注状态" , httpMethod = "POST" , notes = "根据用户ID获取用户间的关注状态")
    public JSONObject getUserRelation(@ApiParam(required = true, name = "userSessionDTO", value = "包含被查看用户的ID等信息")
                                          @RequestBody UserSessionDTO userSessionDTO){
        if(userSessionDTO == null || userSessionDTO.getUserId() == null
                || StringUtils.isBlank(userSessionDTO.getSessionId())
                || userSessionDTO.getCheckedUserId() == null){
            log.info("=======参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        int relationType;
        String json;
        try{
            relationType = userService.getUserRelation(userSessionDTO);
            json = JSONObject.toJSONString(relationType);
            return JsonResponse.success(json);
        } catch (RedisException e) {
            log.error("=======验证用户登录状态异常======"+e.toString());
            return JsonResponse.fail(MyConstans.REDIS_GET_IS_FAIL);
        } catch (MySQLException e){
            log.error("=======查询数据库时发生异常异常===="+e.toString());
            return JsonResponse.fail(MyConstans.SQL_DATE_FAIL);
        } catch (Exception e) {
            log.error("=======发生未知异常======="+e.toString());
            return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
        }
    }

    /**
     * 变更用户之间的关系值
     * @param userSessionDTO 包含用户之间新关系值等信息
     * @return 变更后的用户间关系
     */
    @ResponseBody
    @RequestMapping(value = "/changeUserRelation",method = RequestMethod.POST)
    @ApiOperation(value = "变更用户之间的关系值" , httpMethod = "POST" , notes = "变更用户之间的关系值")
    public JSONObject changeUserRelation(@ApiParam(required = true, name = "userSessionDTO", value = "包含用户之间新关系值等信息")
                                             @RequestBody UserSessionDTO userSessionDTO){
        if(userSessionDTO == null || userSessionDTO.getUserId() == null
                || StringUtils.isBlank(userSessionDTO.getSessionId())
                || userSessionDTO.getCheckedUserId() == null){
            log.info("=======参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        int relationType;
        String json;
        try{
            relationType = userService.changeUserRelation(userSessionDTO);
            if (relationType == MyConstans.FAIL_STATUS_NUM) {
                log.error("=======变更关注状态失败======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
            } else {
                json = JSONObject.toJSONString(relationType);
                return JsonResponse.success(json);
            }
        } catch (RedisException e) {
            log.error("=======验证用户登录状态异常======"+e.toString());
            return JsonResponse.fail(MyConstans.REDIS_GET_IS_FAIL);
        } catch (MySQLException e){
            log.error("=======查询数据库时发生异常异常===="+e.toString());
            return JsonResponse.fail(MyConstans.SQL_DATE_FAIL);
        } catch (Exception e) {
            log.error("=======发生未知异常======="+e.toString());
            return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
        }
    }

    /**
     * 查询某一用户当前的状态
     * @param userSessionDTO 包含用户ID、会话ID等信息
     * @return 用户当前的状态
     */
    @ResponseBody
    @RequestMapping(value = "/getUserStatus",method = RequestMethod.POST)
    @ApiOperation(value = "查询某一用户当前的状态" , httpMethod = "POST" , notes = "查询某一用户当前的状态")
    public JSONObject getUserStatus(@ApiParam(required = true, name = "userSessionDTO", value = "包含用户ID、会话ID等信息")
                                        @RequestBody UserSessionDTO userSessionDTO){
        if(userSessionDTO == null || userSessionDTO.getUserId() == null
                || StringUtils.isBlank(userSessionDTO.getSessionId())
                || userSessionDTO.getCheckedUserId() == null){
            log.info("=======参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        int userStatus;
        String json;
        try{
            userStatus = userLevelService.mannagerValidation(userSessionDTO);
            if (userStatus == MyConstans.MANNAGER_LEVEL_NUM) {//用户是管理员
                userStatus = userService.getUserStatus(userSessionDTO.getCheckedUserId());//查询该用户当前的状态
            } else {//用户不是管理员
                log.error("=======用户不是管理员======");
                return JsonResponse.fail(MyConstans.INSUFFICIENT_PERMISSIONS);
            }
            if (userStatus == MyConstans.FAIL_STATUS_NUM) {
                log.error("=======获取用户当前状态失败======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
            } else {
                json = JSONObject.toJSONString(userStatus);
                return JsonResponse.success(json);
            }
        } catch (RedisException e) {
            log.error("=======验证用户登录状态异常======"+e.toString());
            return JsonResponse.fail(MyConstans.REDIS_GET_IS_FAIL);
        } catch (MySQLException e){
            log.error("=======查询数据库时发生异常异常===="+e.toString());
            return JsonResponse.fail(MyConstans.SQL_DATE_FAIL);
        } catch (Exception e) {
            log.error("=======发生未知异常======="+e.toString());
            return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
        }
    }

    /**
     * 变更用户的当前状态
     * @param userSessionDTO 包含用户新状态等信息
     * @return 新状态
     */
    @ResponseBody
    @RequestMapping(value = "/changeUserStatus",method = RequestMethod.POST)
    @ApiOperation(value = "变更用户的当前状态" , httpMethod = "POST" , notes = "变更用户的当前状态")
    public JSONObject changeUserStatus(@ApiParam(required = true, name = "userSessionDTO", value = "包含用户新状态等信息")
                                           @RequestBody UserSessionDTO userSessionDTO){
        if(userSessionDTO == null || userSessionDTO.getUserId() == null
                || StringUtils.isBlank(userSessionDTO.getSessionId())
                || userSessionDTO.getCheckedUserId() == null
                || userSessionDTO.getStatus() == null){
            log.info("=======参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        int userStatus;
        String json;
        try{
            userStatus = userService.changeUserStatus(userSessionDTO);//变更该用户当前的状态
            if (userStatus == MyConstans.FAIL_STATUS_NUM) {
                log.error("=======修改用户当前状态失败======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
            } else {
                json = JSONObject.toJSONString(userStatus);
                return JsonResponse.success(json);
            }
        } catch (RedisException e) {
            log.error("=======验证用户登录状态异常======"+e.toString());
            return JsonResponse.fail(MyConstans.REDIS_GET_IS_FAIL);
        } catch (MySQLException e){
            log.error("=======查询数据库时发生异常异常===="+e.toString());
            return JsonResponse.fail(MyConstans.SQL_DATE_FAIL);
        } catch (Exception e) {
            log.error("=======发生未知异常======="+e.toString());
            return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
        }
    }

    /**
     * 根据用户ID获取用户当前的资源数
     * @param userId 用户ID
     * @return 用户当前持有的资源数
     */
    @ResponseBody
    @RequestMapping(value = "/getUserResource",method = RequestMethod.POST)
    @ApiOperation(value = "根据用户ID获取用户当前的资源数" , httpMethod = "POST" , notes = "根据用户ID获取用户当前的资源数")
    public JSONObject getUserResource(@ApiParam(required = true, name = "userId", value = "用户ID")
                                          @RequestParam Long userId) {
        if(userId == null){
            log.info("=======参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        int resource;
        String json;
        try {
            resource = userService.getUserResource(userId);
            if (resource == MyConstans.FAIL_STATUS_NUM) {
                log.error("=======查询用户当前资源数失败======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
            } else {
                json = JSONObject.toJSONString(resource);
                return JsonResponse.success(json);
            }
        } catch (RedisException e) {
            log.error("=======验证用户登录状态异常======"+e.toString());
            return JsonResponse.fail(MyConstans.REDIS_GET_IS_FAIL);
        } catch (MySQLException e){
            log.error("=======查询数据库时发生异常异常===="+e.toString());
            return JsonResponse.fail(MyConstans.SQL_DATE_FAIL);
        } catch (Exception e) {
            log.error("=======发生未知异常======="+e.toString());
            return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
        }
    }

    /**
     * 退出登录状态
     * @param userSessionDTO 包含用户ID和会话ID等信息
     * @return 该用户ID
     */
    @ResponseBody
    @RequestMapping(value = "/loginOut",method = RequestMethod.POST)
    @ApiOperation(value = "退出登录" , httpMethod = "POST" , notes = "退出登录")
    public JSONObject loginOut(@ApiParam(required = true, name = "userSessionDTO", value = "包含用户新状态等信息")
                                   @RequestBody UserSessionDTO userSessionDTO){
        if(userSessionDTO == null || userSessionDTO.getUserId() == null
                || StringUtils.isBlank(userSessionDTO.getSessionId())){
            log.debug("=======参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        try{
            int loginStatus=loginValidation.validation(userSessionDTO.getUserId(),userSessionDTO.getSessionId());
            if (loginStatus == 1) {
                sessionService.delSessionId(userSessionDTO.getUserId());
            }
            Long userId = userSessionDTO.getUserId();
            String json = JSONObject.toJSONString(userId);
            return JsonResponse.success(json);
        } catch (RedisException e) {
            log.error("=======验证用户登录状态异常======"+e.toString());
            return JsonResponse.fail(MyConstans.REDIS_GET_IS_FAIL);
        } catch (MySQLException e){
            log.error("=======查询数据库时发生异常异常===="+e.toString());
            return JsonResponse.fail(MyConstans.SQL_DATE_FAIL);
        } catch (Exception e) {
            log.error("=======发生未知异常======="+e.toString());
            return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
        }
    }
}
