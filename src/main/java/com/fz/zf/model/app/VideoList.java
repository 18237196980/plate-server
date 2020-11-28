package com.fz.zf.model.app;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-10-14
 */
@Accessors(chain = true)
@TableName("video_list")
public class VideoList {

    private static final long serialVersionUID = 1L;

    /**
     * 视频id
     */
    @TableId(value = "vid", type = IdType.AUTO)
    private Integer vid;

    /**
     * 视频标题
     */
    private String vtitle;

    /**
     * 作者姓名
     */
    private String author;

    /**
     * 封面图
     */
    private String coverUrl;

    /**
     * 作者头像url
     */
    private String headurl;

    /**
     * 用户评论数
     */
    private Integer comment_num;

    /**
     * 点赞数
     */
    private Integer like_num;

    /**
     * 收藏数
     */
    private Integer collect_num;

    /**
     * 视频url
     */
    private String playUrl;

    /**
     * 创建时间
     */
    private LocalDateTime create_time;

    /**
     * 更新时间
     */
    private LocalDateTime update_time;

    /**
     * 视频分类ID
     */
    private Integer category_id;

    /**
     * 用户是否对该视频点赞
     */
    @TableField(exist = false)
    private Boolean dian;
    /**
     * 用户是否对该视频收藏
     */
    @TableField(exist = false)
    private Boolean shou;

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public String getVtitle() {
        return vtitle;
    }

    public void setVtitle(String vtitle) {
        this.vtitle = vtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    public Integer getComment_num() {
        return comment_num;
    }

    public void setComment_num(Integer comment_num) {
        this.comment_num = comment_num;
    }

    public Integer getLike_num() {
        return like_num;
    }

    public void setLike_num(Integer like_num) {
        this.like_num = like_num;
    }

    public Integer getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(Integer collect_num) {
        this.collect_num = collect_num;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
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

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Boolean getDian() {
        return dian;
    }

    public void setDian(Boolean dian) {
        this.dian = dian;
    }

    public Boolean getShou() {
        return shou;
    }

    public void setShou(Boolean shou) {
        this.shou = shou;
    }
}
