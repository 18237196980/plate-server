package com.fz.zf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fz.zf.model.app.SysAdmin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

public interface SysAdminMapper extends BaseMapper<SysAdmin> {

    IPage<Map<String, Object>> listUsers(Page mPage, @Param("cnname") String cnname);
}
