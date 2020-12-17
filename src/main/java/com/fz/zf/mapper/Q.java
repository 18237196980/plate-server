package com.fz.zf.mapper;

import com.ex.framework.mybatisplus.LambdaQueryBuilder;
import com.ex.framework.mybatisplus.QueryBuilder;
import com.ex.framework.util.SpringUtils;
import com.fz.zf.model.app.*;

public class Q {
    public static QueryBuilder<DianZan> DianZan() {
        DianZanMapper mapper = SpringUtils.getBean(DianZanMapper.class);
        return QueryBuilder.build(mapper);
    }

    public static LambdaQueryBuilder<DianZan> DianZanLambda() {
        DianZanMapper mapper = SpringUtils.getBean(DianZanMapper.class);
        return LambdaQueryBuilder.build(mapper);
    }

    public static QueryBuilder<GoodOrder> GoodOrder() {
        GoodOrderMapper mapper = SpringUtils.getBean(GoodOrderMapper.class);
        return QueryBuilder.build(mapper);
    }

    public static LambdaQueryBuilder<GoodOrder> GoodOrderLambda() {
        GoodOrderMapper mapper = SpringUtils.getBean(GoodOrderMapper.class);
        return LambdaQueryBuilder.build(mapper);
    }

    public static QueryBuilder<Plate> Plate() {
        PlateMapper mapper = SpringUtils.getBean(PlateMapper.class);
        return QueryBuilder.build(mapper);
    }

    public static LambdaQueryBuilder<Plate> PlateLambda() {
        PlateMapper mapper = SpringUtils.getBean(PlateMapper.class);
        return LambdaQueryBuilder.build(mapper);
    }

    public static QueryBuilder<SysAdmin> SysAdmin() {
        SysAdminMapper mapper = SpringUtils.getBean(SysAdminMapper.class);
        return QueryBuilder.build(mapper);
    }

    public static LambdaQueryBuilder<SysAdmin> SysAdminLambda() {
        SysAdminMapper mapper = SpringUtils.getBean(SysAdminMapper.class);
        return LambdaQueryBuilder.build(mapper);
    }

    public static QueryBuilder<VideoCategory> VideoCategory() {
        VideoCategoryMapper mapper = SpringUtils.getBean(VideoCategoryMapper.class);
        return QueryBuilder.build(mapper);
    }

    public static LambdaQueryBuilder<VideoCategory> VideoCategoryLambda() {
        VideoCategoryMapper mapper = SpringUtils.getBean(VideoCategoryMapper.class);
        return LambdaQueryBuilder.build(mapper);
    }

    public static QueryBuilder<VideoList> VideoList() {
        VideoListMapper mapper = SpringUtils.getBean(VideoListMapper.class);
        return QueryBuilder.build(mapper);
    }

    public static LambdaQueryBuilder<VideoList> VideoListLambda() {
        VideoListMapper mapper = SpringUtils.getBean(VideoListMapper.class);
        return LambdaQueryBuilder.build(mapper);
    }

}
