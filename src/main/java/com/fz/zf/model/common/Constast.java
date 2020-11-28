package com.fz.zf.model.common;

/**
 * 常量接口
 */
public class Constast {

    /**
     * 状态码
     */
    public static final Integer OK = 200;
    public static final Integer ERROR = -1;

    /**
     * 用户类型
     */
    public static final Integer USER_TYPE_SUPER = 0;
    public static final Integer USER_TYPE_NORMAL = 1;

    public static final String TOKEN = "token";
    public static final String UID = "uid";

    /**
     * 点赞 收藏 评论
     */
    public static final Integer DIAN_ZAN = 1;
    public static final Integer SHOU_CANG = 2;
    public static final Integer PING_LUN = 3;


    /**
     * token 过期时间(1小时后需重新登陆)
     */
    public static final Integer TOKEN_EXPIRE = 60 * 60;
    public static final String ZF_TOKEN = "zf-token_";

    /**
     * 手机号验证码
     */
    public static final String PHONE_CODE = "plate_phone_code_";
    public static final Integer PHONE_CODE_EXPIRE = 60 * 1; // 验证码1分钟过期


}
