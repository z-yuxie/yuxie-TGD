package com.yuxie.tgd.common.util;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户返回JSON结果集
 */
public class JsonResponse {

    /**
     * 处理结果
     *
     * @param msg
     * @param data
     * @return
     */

    public static <T> JSONObject returnInfo(String msg, T data) {

        JSONObject jo = new JSONObject();
        jo.put("operateTime", System.currentTimeMillis());

        if (data == null) {
            jo.put("result", MyConstans.RESULT_STATUS_FAIL);
            jo.put("msg", "未查询到数据。");
        } else {
            jo.put("data", data);
            jo.put("result", MyConstans.RESULT_STATUS_SUCCESS);
            jo.put("msg", msg);
        }
        return jo;
    }

    /**
     * 处理结果
     *
     * @param total
     * @param data
     * @return
     */

    public static <T> JSONObject returnInfoPage(String total, T data) {

        JSONObject jo = new JSONObject();
        jo.put("operateTime", System.currentTimeMillis());

        if (data == null) {
            jo.put("result", MyConstans.RESULT_STATUS_FAIL);
            jo.put("msg", "未查询到数据。");
        } else {
            jo.put("data", data);
            jo.put("result", MyConstans.RESULT_STATUS_SUCCESS);
            jo.put("msg", "查询成功");
            jo.put("totalCount", total);
        }
        return jo;
    }

    /**
     * 处理结果
     *
     * @param msg
     * @param data
     * @return
     */
    public static <T> JSONObject success(String msg, T data) {

        JSONObject jo = new JSONObject();
        jo.put("operateTime", System.currentTimeMillis());
        jo.put("msg", msg);
        if (data == null) {
            jo.put("result", MyConstans.RESULT_STATUS_FAIL);
        } else {
            jo.put("data", data);
            jo.put("result", MyConstans.RESULT_STATUS_SUCCESS);
        }
        return jo;
    }

    public static <T> JSONObject success(T data) {

        JSONObject jo = new JSONObject();
        jo.put("result", MyConstans.RESULT_STATUS_SUCCESS);
        jo.put("operateTime", System.currentTimeMillis());
        jo.put("msg", "成功");
        jo.put("data", data);
        return jo;
    }

    public static <T> JSONObject successPage(T data, String total) {

        JSONObject jo = new JSONObject();
        jo.put("result", MyConstans.RESULT_STATUS_SUCCESS);
        jo.put("operateTime", System.currentTimeMillis());
        jo.put("msg", "成功");
        jo.put("data", data);
        jo.put("total", total);
        return jo;
    }

    public static <T> JSONObject success(String msg) {

        JSONObject jo = new JSONObject();
        jo.put("result", MyConstans.RESULT_STATUS_SUCCESS);
        jo.put("operateTime", System.currentTimeMillis());
        jo.put("msg", msg);
        return jo;
    }

    public static JSONObject fail(String msg) {
        LogTemplate.info(msg);
        JSONObject jo = new JSONObject();
        jo.put("result", MyConstans.RESULT_STATUS_FAIL);
        jo.put("operateTime", System.currentTimeMillis());
        jo.put("msg", msg);
        return jo;
    }

    public static JSONObject fail(String msg, String data) {
        LogTemplate.info(msg + "--" + data);
        JSONObject jo = new JSONObject();
        jo.put("result", MyConstans.RESULT_STATUS_FAIL);
        jo.put("operateTime", System.currentTimeMillis());
        jo.put("msg", msg);
        jo.put("data", data);
        return jo;
    }

    public static JSONObject error(String msg, Exception e) {
        LogTemplate.error(msg, e);
        JSONObject jo = new JSONObject();
        jo.put("result", MyConstans.RESULT_STATUS_ERROR);
        jo.put("operateTime", System.currentTimeMillis());
        jo.put("msg", msg);
        return jo;
    }

}