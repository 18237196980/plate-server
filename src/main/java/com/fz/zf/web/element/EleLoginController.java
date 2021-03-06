package com.fz.zf.web.element;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ex.framework.data.Record;
import com.ex.framework.data.RecordBody;
import com.ex.framework.web.ApiResult;
import com.ex.framework.web.ExPage;
import com.ex.framework.web.ExPageResult;
import com.ex.framework.web.TableResult;
import com.fz.zf.app.BaseController;
import com.fz.zf.model.app.SysAdmin;
import com.fz.zf.model.app.User;
import com.fz.zf.model.common.Constast;
import com.fz.zf.model.el.GoodsCate;
import com.fz.zf.model.el.OaRole;
import com.fz.zf.service.app.SysAdminService;
import com.fz.zf.service.el.*;
import com.fz.zf.util.MD5;
import com.fz.zf.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * elementUI
 */
@RestController
@RequestMapping("/ele")
@Slf4j
@SuppressWarnings("all")
public class EleLoginController extends BaseController {

    @Autowired
    SysAdminService sysAdminService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    OaMenuService oaMenuService;
    @Autowired
    SysAdminService adminService;
    @Autowired
    OaRoleService oaRoleService;
    @Autowired
    OaRoleMenuService oaRoleMenuService;
    @Autowired
    OaUserRoleService oaUserRoleService;
    @Autowired
    GoodsCateService goodsCateService;

    /**
     * elementUI 登陆
     *
     * @return
     */
    @RequestMapping("login")
    public ApiResult login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isEmpty(username)) {
            return ApiResult.error("请输入账号");
        }

        if (StringUtils.isEmpty(password)) {
            return ApiResult.error("请输入密码");
        }

        SysAdmin sysAdmin = sysAdminService.get(new QueryWrapper<SysAdmin>()
                .eq("name", username));
        if (sysAdmin == null) {
            return ApiResult.error("用户不存在");
        }

        String md5 = MD5.getMD5(password);

        if (!md5.equals(sysAdmin.getPwd())) {
            return ApiResult.error("密码错误");
        }

        // 登陆成功
        String token = UUID.randomUUID()
                           .toString();
        // 保存token到redis
        saveUserInRedis(sysAdmin, token);
        log.info("--------------redis存token----------------：" + token);

        Record record = Record.build()
                              .set("id", sysAdmin.getId())
                              .set("name", username)
                              .set("token", token);

        return ApiResult.success(record);
    }


    /**
     * 获取主页菜单
     *
     * @return
     */
    @RequestMapping("menus")
    public ApiResult menus() {
        try {
            List list = oaMenuService.listMenuByUid();
            return ApiResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error("获取菜单失败");
        }
    }

    /**
     * 用户列表
     *
     * @param record
     * @return
     */
    @PostMapping("users")
    public Object users(@RecordBody Record record) {
        try {
            ExPage page = parseExPage(record);
            ExPageResult<Map<String, Object>> pageResult = adminService.listUsers(page, record);
            return TableResult.success(pageResult);
        } catch (Exception ex) {
            return TableResult.error(ex.getLocalizedMessage());
        }

    }

    /**
     * 添加用户
     *
     * @param record
     * @return
     */
    @PostMapping("user/add")
    public ApiResult addUser(@RequestBody SysAdmin sysAdmin) {
        try {
            return sysAdminService.addWithCheck(sysAdmin);
        } catch (Exception ex) {
            return ApiResult.error("添加失败");
        }
    }

    /**
     * 根据用户id查询用户
     *
     * @param record
     * @return
     */
    @GetMapping("user/getById")
    public ApiResult getUserById(@RequestParam("id") String id) {
        try {
            if (StringUtils.isEmpty(id)) {
                return ApiResult.error("用户id不能为空");
            }
            SysAdmin sysAdmin = sysAdminService.get(id);
            if (sysAdmin == null) {
                return ApiResult.error("用户不存在");
            }
            return ApiResult.success(sysAdmin);
        } catch (Exception ex) {
            return ApiResult.error("添加失败");
        }
    }

    /**
     * 编辑用户
     *
     * @param record
     * @return
     */
    @PostMapping("user/edit")
    public ApiResult editUser(@RequestBody SysAdmin sysAdmin) {
        try {
            return sysAdminService.editWithCheck(sysAdmin);
        } catch (Exception ex) {
            return ApiResult.error("添加失败");
        }
    }

    /**
     * 删除用户
     *
     * @param record
     * @return
     */
    @PostMapping("user/del")
    public ApiResult delUser(@RequestBody SysAdmin sysAdmin) {
        try {
            return sysAdminService.delWithCheck(sysAdmin.getId());
        } catch (Exception ex) {
            return ApiResult.error("添加失败");
        }
    }

    /**
     * 获取角色列表
     *
     * @return
     */
    @RequestMapping("roles")
    public Object roles(@RecordBody Record record) {
        try {
            String name = record.getString("name");
            ExPage page = parseExPage(record);
            ExPageResult<OaRole> pageResult = oaRoleService.listRoles(page, record);
            return TableResult.success(pageResult);
        } catch (Exception ex) {
            ex.printStackTrace();
            return TableResult.error(ex.getLocalizedMessage());
        }
    }

    /**
     * 获取角色列表
     *
     * @return
     */
    @GetMapping("all/roles")
    public ApiResult allRoles(@RecordBody Record record) {
        try {
            QueryWrapper<OaRole> query = new QueryWrapper<>();
            query.eq("enable_flag", 1);
            List<OaRole> oaRoles = oaRoleService.list(query);
            return ApiResult.success(oaRoles);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ApiResult.error("获取角色列表失败");
        }
    }

    /**
     * 删除角色下的权限
     *
     * @param record
     * @return
     */
    @PostMapping("roleMenu/del")
    public ApiResult delRoleMenu(@RecordBody Record record) {
        try {
            return oaRoleMenuService.delRoleMenu(record);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ApiResult.error("删除失败");
        }
    }

    /**
     * 获取树形结构菜单权限
     *
     * @return
     */
    @GetMapping("treePrems")
    public ApiResult treePrems() {
        try {
            List list = oaMenuService.listAllMenus();
            return ApiResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error("获取权限失败");
        }
    }

    /**
     * 分配权限
     *
     * @param record
     * @return
     */
    @PostMapping("givePerms")
    public ApiResult givePerms(@RecordBody Record record) {
        try {
            return oaRoleMenuService.givePerms(record);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ApiResult.error("分配权限失败");
        }
    }

    /**
     * 为用户分配角色
     *
     * @param record
     * @return
     */
    @PostMapping("assignRoleForUser")
    public ApiResult assignRoleForUser(@RecordBody Record record) {
        try {
            return oaUserRoleService.assignRoleForUser(record);
        } catch (Exception ex) {
            return ApiResult.error("分配角色失败");
        }
    }

    /**
     * 获取商品分类三级数据
     *
     * @return
     */
    @GetMapping("goodsCateLevel")
    public ApiResult goodsCateLevel(@RecordBody Record record) {
        try {
            String type = record.getString("type");
            List<GoodsCate> list = goodsCateService.goodsCateLevel(type);
            return ApiResult.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error("获取权限失败");
        }
    }

    /**
     * 添加商品分类
     *
     * @return
     */
    @PostMapping("addGoodsCate")
    public ApiResult addGoodsCate(@RequestBody GoodsCate goodsCate) {
        try {
            return goodsCateService.addGoodsCate(goodsCate);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error("添加商品分类失败");
        }
    }

    /**
     * 编辑商品分类
     *
     * @return
     */
    @PostMapping("editGoodsCate")
    public ApiResult editGoodsCate(@RequestBody GoodsCate goodsCate) {
        try {
            return goodsCateService.editGoodsCate(goodsCate);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error("添加商品分类失败");
        }
    }

    /**
     * 根据商品分类id，查询商品分类
     *
     * @return
     */
    @GetMapping("getGoodsCate")
    public ApiResult getGoodsCate(@RecordBody Record record) {
        try {
            return goodsCateService.getGoodsCate(record);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error("查询商品分类失败");
        }
    }

    /**
     * 根据商品分类id，删除商品分类
     *
     * @return
     */
    @GetMapping("delGoodsCate")
    public ApiResult delGoodsCate(@RecordBody Record record) {
        try {
            return goodsCateService.delGoodsCate(record);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error("查询商品分类失败");
        }
    }

    // 存储到redis 格式zf-token_uid,token,当前时间
    private void saveUserInRedis(SysAdmin user, String token) {
        redisUtil.set(Constast.ZF_TOKEN + user.getId(), token, System.currentTimeMillis());
    }

}
