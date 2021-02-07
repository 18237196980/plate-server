package com.fz.zf.model.el;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

import com.ex.framework.util.Lang;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品分类表
 * </p>
 *
 * @author
 * @since 2021-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("goods_cate")
public class GoodsCate implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类层级 1.一级分类 2.二级分类 3.三级分类
     */
    private Integer type;

    /**
     * 父级id
     */
    private String pid;

    /**
     * 是否启用
     */
    private Integer enable_flag;

    @TableField(value = "false")
    List<GoodsCate> children = Lang.list();


}
