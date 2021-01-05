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

    /**
     * 公众号token 过期时间(2小时，微信规定的)
     */
    public static final String GZH_TOKEN = "gzh-token_";
    public static final String GZH_MY_ID = "gzh-ovzQ2640QXMz1hEKJbKim2pcsDzg";

    /**
     * 公众号模板信息颜色
     */
    public static final String GZH_TEMP_COLOR = "#173177";
    public static final String GZH_TEMP_OTHER_COLOR = "#FA3342";

    /**
     * 公众号是否已加载菜单
     */
    public static boolean INIT_MENU_FLAG = false;

    /**
     * 公众号设置菜单url
     */
    public static final String SET_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

    /**
     * 公众号发送模板的id
     */
    public static final String SEND_TEMP_ID = "3s3ewkB7oD4xSh5IwqJIjm7mzlihu0rxk4IGI6_7v_k";

    /**
     * 公众号发送模板url
     */
    public static final String SEND_TEMP_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    /**
     * 公众号设置所属行业
     */
    public static final String SET_INDUSTRY_URL = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=";

}
