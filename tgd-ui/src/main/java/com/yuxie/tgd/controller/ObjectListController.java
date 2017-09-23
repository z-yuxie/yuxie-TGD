package com.yuxie.tgd.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yuxie.tgd.common.util.JsonResponse;
import com.yuxie.tgd.common.util.MyConstans;
import com.yuxie.tgd.common.util.MySQLException;
import com.yuxie.tgd.common.util.RedisException;
import com.yuxie.tgd.pojo.dto.ListParamDTO;
import com.yuxie.tgd.pojo.vo.ObjectBaseVO;
import com.yuxie.tgd.service.IObjectListService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 获取对象列表Controller合集
 *
 * 参数:排序规则
 * 用户ID
 * 关键词
 * 标签
 * 返回该用户关注的标签所包含对象列表
 * Created by 147356 on 2017/4/8.
 */
@Controller
@RequestMapping(value = "/objectList")
@Api(value = "获取对象列表Controller合集", description = "获取对象列表Controller合集")
public class ObjectListController {
    private static final Logger log = Logger.getLogger(ObjectListController.class);

    @Autowired
    private IObjectListService objectListService;

    /**
     * 获取对象的列表
     * @param listParamDTO 包含需要获取的列表信息的相关参数
     * @return 对象的列表
     */
    @ResponseBody
    @RequestMapping(value = "/getObjectList",method = RequestMethod.POST)
    @ApiOperation(value = "获取对象列表" , httpMethod = "POST" , notes = "根据传入的获取列表类型排序规则等参数获取对象列表")
    public JSONObject getObjectList(@ApiParam(required = true, name = "listParamDTO", value = "获取列表配置")
                                        @RequestBody ListParamDTO listParamDTO) {
        List<ObjectBaseVO> objectBaseVOList;
        String json;
        try {
            objectBaseVOList = objectListService.getObjectList(listParamDTO);
            if (objectBaseVOList == null) {
                log.debug("=======未获取到相关对象列表信息======");
                return JsonResponse.fail(MyConstans.DO_NOT_FIND_OBJECT_LIST);
            } else if (objectBaseVOList.isEmpty()){
                log.debug("=======未获取到相关对象列表信息======");
                return JsonResponse.fail(MyConstans.DO_NOT_FIND_OBJECT_LIST);
            } else {
                json = JSON.toJSONString(objectBaseVOList);
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
