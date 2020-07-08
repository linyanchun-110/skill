package com.hzit.skill.req;

import lombok.ToString;

import java.math.BigDecimal;

@lombok.Data
@ToString
public class Data {
    private Long id;

    private String goodsName;

    private String goodsTitle;

    private String goodsImg;

    private BigDecimal goodsPrice;

    private BigDecimal seckilPrice;
}
