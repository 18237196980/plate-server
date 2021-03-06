package com.fz.zf.model.app;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;

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
 * @since 2020-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("good_order")
public class GoodOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 订单id
     */
    private String order_num;

    /**
     * 金额
     */
    private BigDecimal order_amount;

    /**
     * 支付平台预支付id
     */
    private String pri_pay_id;

    /**
     * 支付时间
     */
    private LocalDateTime pay_time;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 用户id
     */
    private String uid;


}
