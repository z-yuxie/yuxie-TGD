package com.yuxie.tgd.common.util;

/**
 * Created by 147356 on 2017/4/8.
 */
public class MyConstans {

    /**
     * 数据整合相关
     */
    //一开始就是完整的
    public static final int IS_COMPLETED = 0;
    //一开始就是不完整的
    public static final int IS_NOT_COMPLETED = 1;
    //一开始不完整但被补充完整的
    public static final int BECOME_COMPLETED = 2;
    //一开始不完整,补充后仍旧不完整的
    public static final int BECOME_NOT_COMPLETED = 3;
    //未认证的
    public static final int NOT_HAS_VERIFIED = 0;
    //完全经过认证的
    public static final int ALL_HAS_VERIFIED = 1;
    //部分认证的
    public static final int PARTIAL_HAS_VERIFIED = 2;
    //没有过期时间
    public static final int NOT_HAS_EXPIRATION_DATE = 0;
    //有过期时间
    public static final int HAS_EXPIRATION_DATE = 1;
    //时间格式
    public static final String DATA_FORMAT = "yyyyMMdd";

    /**
     * 返回状态码
     */
    //返回成功状态码
    public static final String RESULT_STATUS_SUCCESS="200";
    //返回失败状态码
    public static final String RESULT_STATUS_FAIL="201";
    //返回错误状态码
    public static final String RESULT_STATUS_ERROR="202";

    /**
     * session 相关
     */
    public static final String SESSION_HEADER = "sessionId";

    public static final String USERID_HEADER = "userId";
    // session 过期时间(分钟)
    public final static Integer SESSION_EXPIRED_MINUTES = 30;

    public final static Integer SECONDS_OF_MINUTES = 60;
    // 消费信息过期时间(60分钟)
    public final static Integer SECONDS_OF_EXPIRED = 60 * 60;

    /**
     * 程序运行中的错误状态值
     */
    public static final Integer FAIL_STATUS_NUM = -5;

    public static final Integer REOPRT_NUM = -2;

    public static final Integer SUCCESS_STATUS_NUM = 1;

    public static final Integer MANNAGER_LEVEL_NUM = 1;

    public static final Integer USER_LEVEL_NUM = 0;

    public static final Integer GOD_LEVEL_NUM = -2;

    public static final Integer USER_LOCKED_NUM = -1;

    public static final Integer DATE_CHANGE_IS_FAIL_OR_NOT_FIND_NUM = 0;

    public static final Integer USER_IS_EXISTING_NUM = -1;

    public static final Integer ANOTHER_FAIL_NUM = -1;

    public static final Integer ILLEGAL_OPERATION_NUM= -1;

    public static final Integer ADD_NUM= 1;

    /**
     * 前后端交互错误码
     */
    public static final String ILLEGAL_OPERATION= "请勿非法操作";

    public static final String INSUFFICIENT_PERMISSIONS= "您的权限不足";

    public static final String REPEAT_LOGIN= "请勿重复登录";

    public static final String DONOT_LOGIN= "请登录";

    public static final String DONOT_REGIST= "请注册";

    public static final String PARAM_IS_NULL = "入参为空";

    public static final String SESSION_IS_FAIL = "用户会话信息已过期";

    public static final String TOKEN_EXPIRE = "服务器君:你这点钱根本不够我塞牙缝的,滚！";

    public static final String METHOD_IS_NULL = "服务器君:丑拒！就是不给你东西,你能咋地？";

    public static final String METHOD_IS_FAIL = "网络出现了一点问题,建议您顺着网线爬过来检查下！";

    public static final String SQL_DATE_FAIL = "操作数据库数据失败！";

    public static final String REDIS_SAVE_IS_FAIL ="保存用户会话信息失败";

    public static final String REDIS_GET_IS_FAIL ="获取用户会话信息失败";

    public static final String VERIFICATION_USER_STATUS_IS_ERROR = "验证用户登录状态发生错误";

    public static final String USER_IS_EXISTING = "该用户已存在";

    public static final String DO_NOT_FIND_OBJECT_LIST = "暂时还没有人有发言呢";

    public static final String DO_NOT_FIND_USER_LIST = "暂时没有找到符合条件的用户呢";
}
