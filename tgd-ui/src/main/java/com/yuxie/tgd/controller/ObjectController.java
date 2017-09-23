package com.yuxie.tgd.controller;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yuxie.tgd.common.util.*;
import com.yuxie.tgd.pojo.vo.ObjectDetailVO;
import com.yuxie.tgd.service.IObjectService;
import com.yuxie.tgd.service.UserLevelService;
import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.yuxie.tgd.pojo.dto.ObjectDetailDTO;
import com.yuxie.tgd.pojo.dto.UserSessionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 对象基础操作
 * Created by 147356 on 2017/4/8.
 */
@Controller
@RequestMapping(value = "/objectBase")
@Api(value = "对象基础操作", description = "对象基础操作")
public class ObjectController {

    private static final Logger log= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IObjectService objectService;
    @Autowired
    private UserLevelService userLevelService;
    @Autowired
    private LoginValidation loginValidation;

    /**
     * 根据对象ID获取某个对象的详细信息
     * @param userSessionDTO 包含对象ID等信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getObjectDetail",method = RequestMethod.POST)
    @ApiOperation(value = "根据对象ID获取某个对象的详细信息" , httpMethod = "POST" , notes = "根据对象ID获取某个对象的详细信息")
    public JSONObject getObjectDetail(@ApiParam(required = true, name = "userSessionDTO", value = "包含对象ID等信息")
                                          @RequestBody UserSessionDTO userSessionDTO){
        if (userSessionDTO == null || userSessionDTO.getObjectId() == null) {
            log.debug("=======参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        ObjectDetailVO objectDetailVO;
        String json;
        try {
            objectDetailVO = objectService.getObjectDetail(userSessionDTO);
            if (objectDetailVO == null) {
                log.debug("=======获取对象详细信息失败======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
            }else {
                json = JSONObject.toJSONString(objectDetailVO);
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
     * 更新某个对象的详情
     * @param objectDetailDTO 对象的详细信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateObject",method = RequestMethod.POST)
    @ApiOperation(value = "更新某个对象的详情" , httpMethod = "POST" , notes = "更新某个对象的详情")
    public JSONObject updateObject(@ApiParam(required = true, name = "objectDetailDTO", value = "对象的详细信息")
                                       @RequestBody ObjectDetailDTO objectDetailDTO){
        if (objectDetailDTO == null || objectDetailDTO.getObjectId() == null
                || objectDetailDTO.getObjectType() == null
                || StringUtils.isBlank(objectDetailDTO.getObjectMessage())
                || objectDetailDTO.getUserId() ==null
                || StringUtils.isBlank(objectDetailDTO.getSessionId())) {
            log.debug("=======参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        Integer status;
        String json;
        try {
            status = objectService.updateObject(objectDetailDTO);
            if (status.equals(MyConstans.SUCCESS_STATUS_NUM)) {
                json = JSONObject.toJSONString(status);
                return JsonResponse.success(json);
            }else {
                log.debug("=======更新对象详细信息失败======");
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
     * 创建某个新对象
     * @param objectDetailDTO 对象的详细信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/createObject",method = RequestMethod.POST)
    @ApiOperation(value = "创建某个新对象" , httpMethod = "POST" , notes = "创建某个新对象")
    public JSONObject createObject(@ApiParam(required = true, name = "objectDetailDTO", value = "对象的详细信息")
                                       @RequestBody ObjectDetailDTO objectDetailDTO){
        if (objectDetailDTO == null || objectDetailDTO.getObjectType() ==null
                || objectDetailDTO.getUserId() ==null
                || StringUtils.isBlank(objectDetailDTO.getSessionId())) {
            log.debug("=======参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        Long objectId;
        String json;
        try {
            objectId = objectService.createObject(objectDetailDTO);
            if ( objectId == null) {
                log.debug("=======创建对象失败======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
            } else if (objectId <= 0) {
                log.debug("=======创建对象失败======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
            } else {
                json = JSONObject.toJSONString(objectId);
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
     * 根据对象ID获取对象当前的状态
     * @param userSessionDTO 包含用户登录及权限验证,对象ID等信息
     * @return 对象当前的状态
     */
    @ResponseBody
    @RequestMapping(value = "/getObjectStatus",method = RequestMethod.POST)
    @ApiOperation(value = "根据对象ID获取对象当前的状态" , httpMethod = "POST" , notes = "根据对象ID获取对象当前的状态")
    public JSONObject getObjectStatus(@ApiParam(required = true, name = "userSessionDTO", value = "包含用户登录及权限验证,对象ID等信息")
                                          @RequestBody UserSessionDTO userSessionDTO){
        if(userSessionDTO == null || userSessionDTO.getUserId() == null
                || StringUtils.isBlank(userSessionDTO.getSessionId())
                || userSessionDTO.getObjectId() == null){
            log.info("=======参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        int userStatus;
        int objectStatus;
        String json;
        try{
            userStatus = userLevelService.mannagerValidation(userSessionDTO);
            if (userStatus == MyConstans.MANNAGER_LEVEL_NUM) {//用户是管理员
                objectStatus = objectService.getObjectStatus(userSessionDTO.getObjectId());
            } else {//用户不是管理员
                log.error("=======用户不是管理员======");
                return JsonResponse.fail(MyConstans.INSUFFICIENT_PERMISSIONS);
            }
            if (objectStatus == MyConstans.FAIL_STATUS_NUM) {
                log.error("=======获取对象当前状态失败======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
            } else {
                json = JSONObject.toJSONString(objectStatus);
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
     * 根据对象ID变更对象当前的状态
     * 在service做验证
     * @param userSessionDTO 包含用户登录及权限验证,对象ID等信息
     * @return 对象当前的状态
     */
    @ResponseBody
    @RequestMapping(value = "/changeObjectStatus",method = RequestMethod.POST)
    @ApiOperation(value = "根据对象ID变更对象当前的状态" , httpMethod = "POST" , notes = "根据对象ID变更对象当前的状态")
    public JSONObject changeObjectStatus(@ApiParam(required = true, name = "userSessionDTO", value = "包含用户登录及权限验证,对象ID等信息")
                                             @RequestBody UserSessionDTO userSessionDTO){
        if(userSessionDTO == null || userSessionDTO.getUserId() == null
                || StringUtils.isBlank(userSessionDTO.getSessionId())
                || userSessionDTO.getObjectId() == null
                || userSessionDTO.getStatus() == null){
            log.info("=======参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        int objectStatus;
        String json;
        try{
            objectStatus = objectService.changeObjectStatus(userSessionDTO);
            if (objectStatus == MyConstans.FAIL_STATUS_NUM) {
                log.error("=======变更对象当前状态失败======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
            } else {
                json = JSONObject.toJSONString(objectStatus);
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
     * 通过对象和用户ID获取该用户在该对象中的状态值列表
     * @param userSessionDTO 包含用户登录及权限验证,对象ID等信息
     * @return 状态值列表
     */
    @ResponseBody
    @RequestMapping(value = "/getStatusNumList",method = RequestMethod.POST)
    @ApiOperation(value = "通过对象和用户ID获取该用户在该对象中的状态值列表" , httpMethod = "POST" , notes = "通过对象和用户ID获取该用户在该对象中的状态值列表")
    public JSONObject getStatusNumList(@ApiParam(required = true, name = "userSessionDTO", value = "包含用户登录及权限验证,对象ID等信息")
                                           @RequestBody UserSessionDTO userSessionDTO){
        if(userSessionDTO == null || userSessionDTO.getUserId() == null
                || StringUtils.isBlank(userSessionDTO.getSessionId())
                || userSessionDTO.getObjectId() == null){
            log.info("=======用户参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        int loginStatus;
        List<Integer> statusNumList = null;
        String json;
        Long userId = userSessionDTO.getUserId();
        Long objectId = userSessionDTO.getObjectId();
        try{
            loginStatus = loginValidation.validation(userId , userSessionDTO.getSessionId());
            if (loginStatus == MyConstans.SUCCESS_STATUS_NUM) {//用户已登录
                statusNumList = objectService.getStatusNumList(userId , objectId);
            } else {//用户未登录
                log.error("=======用户未登录======");
                return JsonResponse.fail(MyConstans.DONOT_LOGIN);
            }
            if (statusNumList == null) {
                log.error("=======获取对象当前状态失败======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
            } else {
                json = JSONObject.toJSONString(statusNumList);
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
     * 变更用户在对象中的状态值,以及参加任务等
     * @param userSessionDTO 包含用户对象ID,会话ID,答案等信息
     * @return 变更成功则返回状态值,失败-1,赞踩需减1则返回-2
     */
    @ResponseBody
    @RequestMapping(value = "/changeStatusNum",method = RequestMethod.POST)
    @ApiOperation(value = "变更用户在对象中的状态值,以及参加任务等" , httpMethod = "POST" , notes = "变更用户在对象中的状态值,以及参加任务等")
    public JSONObject changeStatusNum(@ApiParam(required = true, name = "userSessionDTO", value = "包含用户对象ID,会话ID,答案等信息")
                                          @RequestBody UserSessionDTO userSessionDTO){
        if(userSessionDTO == null || userSessionDTO.getUserId() == null
                || StringUtils.isBlank(userSessionDTO.getSessionId())
                || userSessionDTO.getObjectId() == null
                || userSessionDTO.getStatus() == null){
            log.info("=======用户参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        int loginStatus;
        Integer status;
        String json;
        try{
            loginStatus = loginValidation.validation(userSessionDTO.getUserId() , userSessionDTO.getSessionId());
            if (loginStatus == MyConstans.SUCCESS_STATUS_NUM) {//用户已登录
                status = objectService.changeStatusNum(userSessionDTO);
            } else {//用户未登录
                log.error("=======用户未登录======");
                return JsonResponse.fail(MyConstans.DONOT_LOGIN);
            }
            if (status == null) {
                log.error("=======变更用户在该对象状态失败======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
            } else if (status.equals(MyConstans.FAIL_STATUS_NUM)) {
                log.error("=======用户资源不足======");
                return JsonResponse.fail("您的资源不足,请努力赚取资源后再来。");
            } else {
                json = JSONObject.toJSONString(status);
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
     * 根据对象ID获取其任务ID
     * @param userSessionDTO 对象ID
     * @return 任务ID
     */
    @ResponseBody
    @RequestMapping(value = "/getTaskId",method = RequestMethod.POST)
    @ApiOperation(value = "根据对象ID获取其任务ID" , httpMethod = "POST" , notes = "根据对象ID获取其任务ID")
    public JSONObject getTaskId(@ApiParam(required = true, name = "userSessionDTO", value = "对象ID")
                                       @RequestBody UserSessionDTO userSessionDTO){
        if(userSessionDTO == null || userSessionDTO.getObjectId() == null){
            log.info("=======用户参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        Long objectId = userSessionDTO.getObjectId();
        Long taskId;
        String json;
        try{
            taskId = objectService.getTaskId(objectId);
            if (taskId == null) {
                log.error("=======获取任务ID失败======");
                return JsonResponse.fail("好像没有发现任务的存在");
            } else {
                json = JSONObject.toJSONString(taskId);
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
     * 根据对象ID获取其父对象ID
     * @param userSessionDTO 对象ID
     * @return 父对象ID
     */
    @ResponseBody
    @RequestMapping(value = "/getParentObjectId",method = RequestMethod.POST)
    @ApiOperation(value = "根据对象ID获取其父对象ID" , httpMethod = "POST" , notes = "根据对象ID获取其父对象ID")
    public JSONObject getParentObjectId(@ApiParam(required = true, name = "userSessionDTO", value = "对象ID")
                                   @RequestBody UserSessionDTO userSessionDTO){
        if(userSessionDTO == null || userSessionDTO.getObjectId() == null){
            log.info("=======用户参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        Long objectId = userSessionDTO.getObjectId();
        Long parentObjectId;
        String json;
        try{
            parentObjectId = objectService.getParentObjectId(objectId);
            if (parentObjectId == null) {
                log.error("=======获取父对象ID失败======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
            } else {
                json = JSONObject.toJSONString(parentObjectId);
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
}