package com.fz.zf.service.el;

import com.ex.framework.base.BaseCRUDService;
import com.ex.framework.data.IDUtils;
import com.ex.framework.data.Record;
import com.ex.framework.util.Lang;
import com.ex.framework.web.ApiResult;
import com.fz.zf.mapper.GoodsCateMapper;
import com.fz.zf.model.app.Column;
import com.fz.zf.model.el.GoodsCate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2021-02-07
 */
@Service
@SuppressWarnings(value = "all")
public class GoodsCateService extends BaseCRUDService<GoodsCateMapper, GoodsCate> {

    protected Column.GoodsCate c = new Column.GoodsCate();

    public List<GoodsCate> goodsCateLevel(String type) {
        List<GoodsCate> list = list();
        List<GoodsCate> rootMenus = list(query().eq(c.pid, 0));
        for (GoodsCate menu : rootMenus) {
            if (StringUtils.isEmpty(type)) {
                // 查询商品分类所有层级
                addChildren(list, menu);
            } else if (StringUtils.equals(type, "2")) {
                // 查询商品分类前两级
                theSecondCate(list, menu);
            }
        }
        return rootMenus;
    }

    private void theSecondCate(List<GoodsCate> list, GoodsCate menu) {
        List<GoodsCate> child = Lang.list();
        for (GoodsCate goodsCate : list) {
            if (StringUtils.equals(goodsCate.getPid(), menu.getId())) {
                child.add(goodsCate);
            }
        }
        if (Lang.isEmpty(child)) {
            menu.setChildren(null);
        } else {
            menu.setChildren(child);
        }
    }

    private void addChildren(List<GoodsCate> list, GoodsCate menu) {
        List<GoodsCate> child = Lang.list();
        for (GoodsCate oaMenu : list) {
            if (StringUtils.equals(oaMenu.getPid(), menu.getId())) {
                child.add(oaMenu);
                addChildren(list, oaMenu);
                menu.setChildren(child);
            }
        }
    }

    @Transactional
    public ApiResult addGoodsCate(GoodsCate goodsCate) {
        if (goodsCate == null) {
            return ApiResult.error("添加失败");
        }
        if (StringUtils.isEmpty(goodsCate.getName())) {
            return ApiResult.error("请输入分类名称");
        }
        if (StringUtils.isEmpty(goodsCate.getPid())) {
            goodsCate.setPid("0");
            goodsCate.setType(1);
        } else {
            GoodsCate cate = get(goodsCate.getPid());
            if (cate == null) {
                return ApiResult.error("父级分类不存在");
            }
            goodsCate.setType(cate.getType() == 1 ? 2 : 3);
        }

        goodsCate.setId(IDUtils.getSequenceStr());
        goodsCate.setEnable_flag(1);

        boolean add = add(goodsCate);

        if (add) {
            return ApiResult.success("添加成功");
        } else {
            return ApiResult.error("添加失败");
        }
    }

    @Transactional
    public ApiResult getGoodsCate(Record record) {
        String id = record.getString("id");
        if (StringUtils.isEmpty(id)) {
            return ApiResult.error("查询商品分类失败");
        }
        GoodsCate cate = get(id);
        List<Object> ids = Lang.list();
        if (cate.getType() == 3) {
            GoodsCate level2 = get(cate.getPid());
            if (level2 == null) {
                return ApiResult.error("父级商品分类不存在");
            } else {
                GoodsCate level1 = get(level2.getPid());
                if (level1 == null) {
                    return ApiResult.error("父级商品分类不存在");
                } else {
                    ids.add(level1.getId());
                    ids.add(level2.getId());
                }
            }
        } else if (cate.getType() == 2) {
            GoodsCate level = get(cate.getPid());
            if (level == null) {
                return ApiResult.error("父级商品分类不存在");
            } else {
                ids.add(level.getId());
            }
        }

        if (cate == null) {
            return ApiResult.error("商品分类不存在");
        }
        Record r = Record.build()
                         .set("id", id)
                         .set("name", cate.getName())
                         .set("enable_flag", cate.getEnable_flag())
                         .set("ids", ids);
        System.out.println("r------------------------:" + r);
        return ApiResult.success(r);
    }

    @Transactional
    public ApiResult editGoodsCate(GoodsCate goodsCate) {
        if (StringUtils.isEmpty(goodsCate.getId())) {
            return ApiResult.error("id不能为空");
        }
        GoodsCate cate = get(goodsCate.getId());
        if (cate == null) {
            return ApiResult.error("该商品分类不存在");
        }
        boolean update = updateById(goodsCate);
        if (update) {
            return ApiResult.success("编辑成功");
        } else {
            return ApiResult.error("编辑失败");
        }
    }

    @Transactional
    public ApiResult delGoodsCate(Record record) {
        String id = record.getString("id");
        if (StringUtils.isEmpty(id)) {
            return ApiResult.error("id不能为空");
        }
        GoodsCate cate = get(id);
        if (cate == null) {
            return ApiResult.error("该商品分类不存在");
        }
        boolean exists = exists(query().eq(c.pid, id));
        if (exists) {
            return ApiResult.error("删除失败，请先删除子分类");
        }
        boolean b = deleteById(id);
        if (b) {
            return ApiResult.success("删除成功");
        } else {
            return ApiResult.error("删除失败");
        }

    }
}
