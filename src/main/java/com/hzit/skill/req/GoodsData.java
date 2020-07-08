package com.hzit.skill.req;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 前端展示的商品
 */
@Data
public class GoodsData {

    private int id;

    private String goodsName;

    private String goodsTitle;

    private String goodsImg;

    private BigDecimal goodsPrice;

    private Integer goodsStock;

    private BigDecimal seckillPrice;

}
