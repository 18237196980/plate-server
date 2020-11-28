package com.fz.zf.model.app;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Plate implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 车牌号
     */
    private String code;

    /**
     * 车牌所属人
     */
    private String apply_person;

    /**
     * 车辆联系人电话
     */
    private String phone;

    /**
     * 房产地址
     */
    private String apply_address;

    /**
     * 登记时间
     */
    private LocalDateTime create_time;

    /**
     * 登记人
     */
    private String create_user;

    /**
     * 删除时间
     */
    private LocalDateTime del_time;

    /**
     * 删除人
     */
    private String del_user;

    /**
     * 房产证照片
     */
    private String home_img;

    /**
     * 更新时间
     */
    private LocalDateTime update_time;

    /**
     * 更新人
     */
    private String update_user;

}
