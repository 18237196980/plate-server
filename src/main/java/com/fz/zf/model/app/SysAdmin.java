package com.fz.zf.model.app;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 系统管理员账号表
 * </p>
 *
 * @author
 * @since 2018-12-03
 */

@TableName("sys_admin")
public class SysAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String pid;

    private String openid;

    private String nickName;

    private String token;

    private Integer sex;

    private String city_code1;
    private String city_code2;
    private String city_code3;
    private String city_names;

    public String getCity_names() {
        return city_names;
    }

    public void setCity_names(String city_names) {
        this.city_names = city_names;
    }

    public String getCity_code1() {
        return city_code1;
    }

    public void setCity_code1(String city_code1) {
        this.city_code1 = city_code1;
    }

    public String getCity_code2() {
        return city_code2;
    }

    public void setCity_code2(String city_code2) {
        this.city_code2 = city_code2;
    }

    public String getCity_code3() {
        return city_code3;
    }

    public void setCity_code3(String city_code3) {
        this.city_code3 = city_code3;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public SysAdmin(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public SysAdmin() {
    }

    /**
     * 用户名
     */
    private String name;

    /**
     * 中文用户名
     */
    private String cnname;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 固话
     */
    private String tel;

    /**
     * 邮箱
     */
    private String email;

    private Integer admin_flag;

    /**
     * 是否启用
     */
    private Integer enable_flag;

    /**
     * 是否删除
     */
    private Integer delete_flag;

    /**
     * 备注
     */
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime create_time;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date birth;

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    private LocalDateTime update_time;

    private LocalDateTime delete_time;

    private String create_user;

    private String update_user;

    private String delete_user;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    private String header_path;

    public String getHeader_path() {
        return header_path;
    }

    public void setHeader_path(String header_path) {
        this.header_path = header_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnname() {
        return cnname;
    }

    public void setCnname(String cnname) {
        this.cnname = cnname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAdmin_flag() {
        return admin_flag;
    }

    public void setAdmin_flag(Integer admin_flag) {
        this.admin_flag = admin_flag;
    }

    public Integer getEnable_flag() {
        return enable_flag;
    }

    public void setEnable_flag(Integer enable_flag) {
        this.enable_flag = enable_flag;
    }

    public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }

    public LocalDateTime getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(LocalDateTime update_time) {
        this.update_time = update_time;
    }

    public LocalDateTime getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(LocalDateTime delete_time) {
        this.delete_time = delete_time;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    public String getDelete_user() {
        return delete_user;
    }

    public void setDelete_user(String delete_user) {
        this.delete_user = delete_user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "SysAdmin{" +
                "id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", openid='" + openid + '\'' +
                ", nickName='" + nickName + '\'' +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", cnname='" + cnname + '\'' +
                ", pwd='" + pwd + '\'' +
                ", mobile='" + mobile + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", admin_flag=" + admin_flag +
                ", enable_flag=" + enable_flag +
                ", delete_flag=" + delete_flag +
                ", remark='" + remark + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", delete_time=" + delete_time +
                ", create_user='" + create_user + '\'' +
                ", update_user='" + update_user + '\'' +
                ", delete_user='" + delete_user + '\'' +
                ", header_path='" + header_path + '\'' +
                '}';
    }
}
