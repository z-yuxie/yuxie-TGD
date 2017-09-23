package com.yuxie.tgd.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yuxie.tgd.common.util.*;
import com.yuxie.tgd.pojo.dto.UserSessionDTO;
import com.yuxie.tgd.pojo.vo.LabelVO;
import com.yuxie.tgd.service.ILabelListService;
import com.yuxie.tgd.service.ISessionService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 关注标签
 * 查询标签列表,想获取用户关注的标签,再获取最热标签(2倍关注数量用于筛选),共计15个
 * 标签相关操作controller
 * Created by 147356 on 2017/4/19.
 */
@Controller
@RequestMapping(value = "/label")
@Api(value = "标签服务", description = "标签服务")
public class LabelController {

    private static final Logger log= LoggerFactory.getLogger(LabelController.class);

    @Autowired
    private ILabelListService labelListService;
    @Autowired
    private LoginValidation loginValidation;
    @Autowired
    private ISessionService sessionService;

    /**
     * 获取标签列表
     * @param userSessionDTO 包含用户验证信息等数据
     * @return 标签列表
     */
    @ResponseBody
    @RequestMapping(value = "/getLabelList",method = RequestMethod.POST)
    @ApiOperation(value = "获取标签列表" , httpMethod = "POST" , notes = "获取标签列表")
    public JSONObject getLabelList(@ApiParam(required = true, name = "userSessionDTO", value = "用户会话参数")
                                       @RequestBody UserSessionDTO userSessionDTO){
        List<LabelVO> labelVOList = null;
        String json;
        try{
            labelVOList = labelListService.getLabelList(userSessionDTO);
            if (labelVOList == null) {
                log.error("=======获取标签列表失败======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
            } else {
                json = JSON.toJSONString(labelVOList);
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
     * 变更用户对某标签的关注状态值
     * @param userSessionDTO 包含用户对象ID,会话ID等信息
     * @return 变更成功则返回1
     */
    @ResponseBody
    @RequestMapping(value = "/changeStatusNum",method = RequestMethod.POST)
    @ApiOperation(value = "变更用户对某标签的关注状态值" , httpMethod = "POST" , notes = "变更用户对某标签的关注状态值")
    public JSONObject changeStatusNum(@ApiParam(required = true, name = "userSessionDTO", value = "用户会话参数")
                                          @RequestBody UserSessionDTO userSessionDTO){
        if(userSessionDTO == null || userSessionDTO.getUserId() == null
                || userSessionDTO.getLabelId() == null){
            log.info("=======用户参数传递失败======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        int loginStatus;
        Integer status;
        String json;
        try{
            loginStatus = loginValidation.validation(userSessionDTO.getUserId() , userSessionDTO.getSessionId());
            if (loginStatus == MyConstans.SUCCESS_STATUS_NUM) {//用户已登录
                status = labelListService.changeAttentionType(userSessionDTO.getUserId() , userSessionDTO.getLabelId());
            } else {//用户未登录
                log.error("=======用户未登录======");
                return JsonResponse.fail(MyConstans.DONOT_LOGIN);
            }
            if (status == null) {
                log.error("=======变更用户关注标签状态失败======");
                return JsonResponse.fail(MyConstans.METHOD_IS_FAIL);
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
     * 测试用接口,根据用户ID获取已存在的sessionID
     * @param userId 用户ID
     * @return sessionId
     */
    @ResponseBody
    @RequestMapping(value = "/getSessionId",method = RequestMethod.POST)
    @ApiOperation(value = "获取已存在的sessionID" , httpMethod = "POST" , notes = "获取已存在的sessionID")
    public JSONObject getSessionId(@ApiParam(required = true, name = "userId", value = "用户ID")
                                      @RequestParam Long userId) {
        String sessionId = sessionService.getSessionId(userId.toString());
        String json = JSONObject.toJSONString(sessionId);
        return JsonResponse.success(json);
    }

    /**
     * 测试用接口,根据用户ID生成会话ID
     * @param userId 用户ID
     * @return 会话ID
     */
    @ResponseBody
    @RequestMapping(value = "/setAndGetSessionId",method = RequestMethod.POST)
    @ApiOperation(value = "新建并返回sessionID" , httpMethod = "POST" , notes = "新建并返回sessionID")
    public JSONObject setAndGetSessionId(@ApiParam(required = true, name = "userId", value = "用户ID")
                                   @RequestParam Long userId) {
        String sessionId = null;
        try {
            sessionId = sessionService.setAndGetSessionId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = JSONObject.toJSONString(sessionId);
        return JsonResponse.success(json);
    }

    /**
     * 测试用接口,根据用户ID删除已存在的会话ID
     * @param userId 用户ID
     * @return null
     */
    @ResponseBody
    @RequestMapping(value = "/delSessionId",method = RequestMethod.POST)
    @ApiOperation(value = "删除已存在的会话ID" , httpMethod = "POST" , notes = "删除已存在的会话ID")
    public JSONObject delSessionId(@ApiParam(required = true, name = "userId", value = "用户ID")
                                         @RequestParam Long userId) {
//        String sessionId = null;
        try {
            sessionService.delSessionId(userId);
            String json = JSONObject.toJSONString(userId);
            return JsonResponse.success(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResponse.success(null);
    }
}
