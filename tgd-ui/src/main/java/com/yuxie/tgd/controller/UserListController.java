package com.yuxie.tgd.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yuxie.tgd.common.util.JsonResponse;
import com.yuxie.tgd.common.util.MyConstans;
import com.yuxie.tgd.common.util.MySQLException;
import com.yuxie.tgd.common.util.RedisException;
import com.yuxie.tgd.pojo.dto.ListParamDTO;
import com.yuxie.tgd.pojo.vo.UserBaseVO;
import com.yuxie.tgd.service.IUserListService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 获取用户列表合集
 *
 * 参数:排序规则
 * 对象ID
 * 用户状态
 * 返回该用户关注的用户的列表
 * Created by 147356 on 2017/4/8.
 */
@Controller
@RequestMapping(value = "/userList")
@Api(value = "获取用户列表合集", description = "获取用户列表合集")
public class UserListController {

    private static final Logger log = Logger.getLogger(ObjectListController.class);

    @Autowired
    private IUserListService userListService;

    /**
     * 获取用户列表
     * @param listParamDTO 包含有获取用户列表信息
     * @return 由UserBaseVO组成的LIST转换而成的JSON串
     */
    @ResponseBody
    @RequestMapping(value = "/getUserList",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表" , httpMethod = "POST" , notes = "获取用户列表")
    public JSONObject getUserList(@ApiParam(required = true, name = "listParamDTO", value = "包含有获取用户列表信息")
                                      @RequestBody ListParamDTO listParamDTO){
        if (listParamDTO == null || listParamDTO.getUserId() == null
                || StringUtils.isBlank(listParamDTO.getSessionId())) {
            log.info("=======用户未传入任何参数======");
            return JsonResponse.fail(MyConstans.TOKEN_EXPIRE);
        }
        List<UserBaseVO> userBaseVOList;
        String json;
        try {
            userBaseVOList = userListService.getUserList(listParamDTO);
            if (userBaseVOList == null) {
                log.debug("=======未获取到相关用户列表信息======");
                return JsonResponse.fail(MyConstans.DO_NOT_FIND_USER_LIST);
            } else if (userBaseVOList.isEmpty()) {
                log.debug("=======未获取到相关用户列表信息======");
                return JsonResponse.fail(MyConstans.DO_NOT_FIND_USER_LIST);
            }
            else {
                json = JSON.toJSONString(userBaseVOList);
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
