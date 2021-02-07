package com.fz.zf.model.app;


public class Column {
    /**
    * 
    * 表名：dian_zan
    */
    public static class DianZan {

        public final String id = "id";

        /**
        * 视频id
        */
        public final String vid = "vid";

        /**
        * 用户id
        */
        public final String uid = "uid";

        /**
        * 点赞时间
        */
        public final String create_time = "create_time";

        /**
        * 1(点赞)，2(收藏),3(评论)
        */
        public final String type = "type";

    }

    /**
    * 
    * 表名：goods_cate
    */
    public static class GoodsCate {

        public final String id = "id";

        /**
        * 分类名称
        */
        public final String name = "name";

        /**
        * 分类层级 1.一级分类 2.二级分类 3.三级分类
        */
        public final String type = "type";

        /**
        * 父级id
        */
        public final String pid = "pid";

        /**
        * 是否启用
        */
        public final String enable_flag = "enable_flag";

    }

    /**
    * 菜单权限表
    * 表名：oa_menu
    */
    public static class OaMenu {

        /**
        * 主键id
        */
        public final String id = "id";

        /**
        * 父id
        */
        public final String parent_id = "parent_id";

        /**
        * 菜单标题
        */
        public final String name = "name";

        /**
        * 菜单图标
        */
        public final String icon = "icon";

        /**
        * 路径
        */
        public final String path = "path";

        /**
        * 组件
        */
        public final String component = "component";

        /**
        * 组件名字
        */
        public final String component_name = "component_name";

        /**
        * 一级菜单跳转地址
        */
        public final String redirect = "redirect";

        /**
        * 菜单排序
        */
        public final String sort_no = "sort_no";

        /**
        * 菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)
        */
        public final String menu_type = "menu_type";

        /**
        * 菜单权限编码
        */
        public final String perms = "perms";

        /**
        * 是否在菜单中显示: 0否,1是（默认值1）
        */
        public final String hidden = "hidden";

        /**
        * 是否为固定标签: 0:不是  1:是（默认值0）
        */
        public final String is_affix = "is_affix";

        /**
        * 是否缓存该页面:    1:是   0:不是
        */
        public final String keep_alive = "keep_alive";

        /**
        * 创建人
        */
        public final String create_user = "create_user";

        /**
        * 创建时间
        */
        public final String create_time = "create_time";

        /**
        * 更新人
        */
        public final String update_user = "update_user";

        /**
        * 更新时间
        */
        public final String update_time = "update_time";

        /**
        * 描述
        */
        public final String remark = "remark";

    }

    /**
    * OA系统角色菜单表
    * 表名：oa_role_menu
    */
    public static class OaRoleMenu {

        public final String id = "id";

        public final String role_id = "role_id";

        public final String menu_id = "menu_id";

    }

    /**
    * 
    * 表名：plate
    */
    public static class Plate {

        public final String id = "id";

        /**
        * 车牌号
        */
        public final String code = "code";

        /**
        * 车牌所属人
        */
        public final String apply_person = "apply_person";

        /**
        * 车辆联系人电话
        */
        public final String phone = "phone";

        /**
        * 房产地址
        */
        public final String apply_address = "apply_address";

        /**
        * 登记时间
        */
        public final String create_time = "create_time";

        /**
        * 登记人
        */
        public final String create_user = "create_user";

        /**
        * 删除时间
        */
        public final String del_time = "del_time";

        /**
        * 删除人
        */
        public final String del_user = "del_user";

        /**
        * 房产证照片
        */
        public final String home_img = "home_img";

    }

    /**
    * 系统管理员账号表
    * 表名：sys_admin
    */
    public static class SysAdmin {

        public final String id = "id";

        public final String pid = "pid";

        /**
        * 用户名
        */
        public final String name = "name";

        /**
        * 中文用户名
        */
        public final String cnname = "cnname";

        /**
        * 密码
        */
        public final String pwd = "pwd";

        /**
        * 手机
        */
        public final String mobile = "mobile";

        /**
        * 固话
        */
        public final String tel = "tel";

        /**
        * 邮箱
        */
        public final String email = "email";

        /**
        * 是否是管理员
        */
        public final String admin_flag = "admin_flag";

        /**
        * 是否启用
        */
        public final String enable_flag = "enable_flag";

        /**
        * 是否删除
        */
        public final String delete_flag = "delete_flag";

        /**
        * 备注
        */
        public final String remark = "remark";

        public final String create_time = "create_time";

        public final String update_time = "update_time";

        public final String delete_time = "delete_time";

        public final String create_user = "create_user";

        public final String update_user = "update_user";

        public final String delete_user = "delete_user";

        /**
        * 头像
        */
        public final String header_path = "header_path";

        /**
        * 微信昵称
        */
        public final String nickName = "nickName";

        /**
        * 微信openid
        */
        public final String openid = "openid";

        /**
        * 登录凭证
        */
        public final String token = "token";

        /**
        * 生日
        */
        public final String birth = "birth";

        /**
        * 性别(1男，2女)
        */
        public final String sex = "sex";

        /**
        * 省
        */
        public final String city_code1 = "city_code1";

        /**
        * 市
        */
        public final String city_code2 = "city_code2";

        /**
        * 区
        */
        public final String city_code3 = "city_code3";

        /**
        * 省市区名称
        */
        public final String city_names = "city_names";

    }

    /**
    * 
    * 表名：video_category
    */
    public static class VideoCategory {

        public final String category_id = "category_id";

        public final String category_name = "category_name";

    }

    /**
    * 
    * 表名：video_list
    */
    public static class VideoList {

        /**
        * 视频id

        */
        public final String vid = "vid";

        /**
        * 视频标题

        */
        public final String vtitle = "vtitle";

        /**
        * 作者姓名

        */
        public final String author = "author";

        /**
        * 封面图
        */
        public final String coverUrl = "coverUrl";

        /**
        * 作者头像url

        */
        public final String headurl = "headurl";

        /**
        * 用户评论数
        */
        public final String comment_num = "comment_num";

        /**
        * 点赞数
        */
        public final String like_num = "like_num";

        /**
        * 收藏数
        */
        public final String collect_num = "collect_num";

        /**
        * 视频url
        */
        public final String playUrl = "playUrl";

        /**
        * 创建时间
        */
        public final String create_time = "create_time";

        /**
        * 更新时间
        */
        public final String update_time = "update_time";

        /**
        * 视频分类ID
        */
        public final String category_id = "category_id";

    }

}
