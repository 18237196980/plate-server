package ${controllerPackage};

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ${modelPackage}.${modelName};
import ${servicePackage}.${modelName}Service;
import ${basePackage}.web.BaseController;
import com.ex.framework.data.IDUtils;
import com.ex.framework.data.Record;
import com.ex.framework.data.RecordBody;
import com.ex.framework.web.ApiResult;
import com.ex.framework.web.ExPage;
import com.ex.framework.web.ExPageResult;
import com.ex.framework.web.TableResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/${modelName2}")
public class ${modelName}Controller extends BaseController {

    protected static Logger logger = LoggerFactory.getLogger(${modelName}Controller.class);

    @Autowired
    ${modelName}Service ${modelName2}Service;

    @GetMapping
    //@RequiresPermissions("${modelName2}:view")
    public String index(ModelMap map) {

        return "${modelName2}/index";
    }

    @PostMapping("list")
    //@RequiresPermissions("${modelName2}:view")
    @ResponseBody
    public Object list(@RecordBody Record record) {

        try {
            //此处代码仅供参考，需要手工修改
            String name = record.getString("name");
            String countryCode = record.getString("countryCode");

            QueryWrapper<${modelName}> wrapper = new QueryWrapper<>();
            wrapper.likeRight(StringUtils.isNotEmpty(name), "name", name)
                   .likeRight(StringUtils.isNotEmpty(countryCode), "countryCode", countryCode);

            ExPage page = parseExPage(record);
            ExPageResult<${modelName}> pageResult = ${modelName2}Service.list(page, wrapper);

            return TableResult.success(pageResult);
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage(), ex);

            return TableResult.error(ex.getLocalizedMessage());
        }
    }

    @GetMapping("add")
    //@RequiresPermissions("${modelName2}:create")
    public String createForm(ModelMap map) {

        return "${modelName2}/add";
    }

    @PostMapping("create")
    //@RequiresPermissions("${modelName2}:create")
    @ResponseBody
    public Object create(@RequestBody ${modelName} model) {

        try {
            if (model == null) {
                return ApiResult.error("添加失败");
            }

            model.setId(IDUtils.getSequenceStr());

            boolean isSuccess = ${modelName2}Service.add(model);
            if (!isSuccess) {
                return ApiResult.error("添加失败");
            }

            return ApiResult.success();
        } catch (Exception ex) {

            logger.error(ex.getLocalizedMessage(), ex);
            return ApiResult.error("添加失败");
        }
    }

    @GetMapping("edit")
    //@RequiresPermissions("${modelName2}:update")
    public String updateForm(@RequestParam("id") String id, ModelMap map) {
        try {
            if (StringUtils.isEmpty(id)) {
                return DEFAULT_ERROR_FORM;
            }

            ${modelName} model = ${modelName2}Service.get(id);
            if (model == null) {
                return DEFAULT_ERROR_FORM;
            }

            map.put("model", toJson(model));
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage(), ex);
            return DEFAULT_ERROR_FORM;
        }

        return "${modelName2}/edit";
    }

    @PostMapping("update")
    //@RequiresPermissions("${modelName2}:update")
    @ResponseBody
    public Object update(@RequestBody ${modelName} model) {

        try {
            if (model == null) {
                return ApiResult.error("修改失败");
            }

            if (StringUtils.isEmpty(model.getId())) {
                return ApiResult.error("修改失败");
            }

            boolean isSuccess = ${modelName2}Service.updateById(model);
            if (!isSuccess) {
                return ApiResult.error("修改失败");
            }

            return ApiResult.success();
        } catch (Exception ex) {

            logger.error(ex.getLocalizedMessage(), ex);
            return ApiResult.error("修改失败");
        }
    }

    @PostMapping("delete")
    //@RequiresPermissions("${modelName2}:delete")
    @ResponseBody
    public Object delete(@RequestParam("id") String id) {

        try {
            if (StringUtils.isEmpty(id)) {
                return ApiResult.error("删除失败");
            }

            boolean isSuccess = ${modelName2}Service.deleteById(id);
            if (!isSuccess) {
                return ApiResult.error("删除失败");
            }

            return ApiResult.success();
        } catch (Exception ex) {

            logger.error(ex.getLocalizedMessage(), ex);
            return ApiResult.error("删除失败");
        }
    }
}
