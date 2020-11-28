package com.fz.zf.plate;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ex.framework.data.Record;
import com.ex.framework.data.RecordBody;
import com.ex.framework.web.ApiResult;
import com.ex.framework.web.ExPage;
import com.ex.framework.web.ExPageResult;
import com.ex.framework.web.TableResult;
import com.fz.zf.app.BaseController;
import com.fz.zf.model.app.Plate;
import com.fz.zf.model.app.SysAdmin;
import com.fz.zf.service.app.PlateService;
import com.fz.zf.service.app.SysAdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plate/main")
public class PlateMainController extends BaseController {

    @Autowired
    PlateService plateService;
    @Autowired
    SysAdminService sysAdminService;

    @RequestMapping("list")
    public Object list(@RecordBody Record record) {
        try {
            String code = record.getString("searchParam");

            QueryWrapper<Plate> wrapper = new QueryWrapper<>();
            wrapper.like(StringUtils.isNotEmpty(code), "code", code);

            ExPage page = parseExPage(record);
            ExPageResult<Plate> pageResult = plateService.list(page, wrapper);

            return TableResult.success(pageResult);
        } catch (Exception ex) {
            return TableResult.error(ex.getLocalizedMessage());
        }
    }

    @PostMapping("add")
    public ApiResult add(@RequestBody Plate model) {
        return plateService.addWithCheck(model);
    }

    @PostMapping("edit")
    public ApiResult edit(@RequestBody Plate model) {
        return plateService.updateWithCheck(model);
    }

    @PostMapping("del")
    public ApiResult del(String id) {
        boolean b = plateService.deleteById(id);
        if (b) {
            return ApiResult.success();
        } else {
            return ApiResult.error("删除失败");
        }
    }

    @GetMapping("initPlate")
    public ApiResult initPlate(String id) {
        Plate plate = plateService.get(id);
        String create_user = (String) sysAdminService.getSingleValue("cnname", new QueryWrapper<SysAdmin>().eq("id", plate.getCreate_user()));
        String update_user = (String) sysAdminService.getSingleValue("cnname", new QueryWrapper<SysAdmin>().eq("id", plate.getUpdate_user()));
        plate.setCreate_user(create_user);
        plate.setUpdate_user(update_user);
        return ApiResult.success(plate);
    }


}
