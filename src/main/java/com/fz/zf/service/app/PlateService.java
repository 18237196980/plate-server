package com.fz.zf.service.app;

import com.ex.framework.base.BaseCRUDService;
import com.ex.framework.data.IDUtils;
import com.ex.framework.web.ApiResult;
import com.fz.zf.mapper.PlateMapper;
import com.fz.zf.model.app.Plate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PlateService extends BaseCRUDService<PlateMapper, Plate> {

    @Transactional
    public ApiResult addWithCheck(Plate model) {
        if (model == null) {
            return ApiResult.error();
        }
        if (StringUtils.isEmpty(model.getCode())) {
            return ApiResult.error("车牌号不能为空");
        }
        if (StringUtils.isEmpty(model.getApply_person())) {
            return ApiResult.error("车主姓名不能为空");
        }
        if (StringUtils.isEmpty(model.getPhone())) {
            return ApiResult.error("电话不能为空");
        }
        if (StringUtils.isEmpty(model.getApply_address())) {
            return ApiResult.error("户号不能为空");
        }

        model.setId(IDUtils.getSequenceStr());
        model.setCreate_user("1");
        model.setCreate_time(LocalDateTime.now());

        boolean add = add(model);

        if (add) {
            return ApiResult.success();
        } else {
            return ApiResult.error();
        }
    }

    @Transactional
    public ApiResult updateWithCheck(Plate model) {
        if (model == null) {
            return ApiResult.error();
        }
        if (StringUtils.isEmpty(model.getCode())) {
            return ApiResult.error("id不能为空");
        }
        model.setUpdate_time(LocalDateTime.now());
        model.setUpdate_user("1");
        boolean b = updateById(model);

        if (b) {
            return ApiResult.success();
        } else {
            return ApiResult.error("修改失败");
        }

    }
}
