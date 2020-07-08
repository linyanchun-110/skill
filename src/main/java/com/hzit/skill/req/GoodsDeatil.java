package com.hzit.skill.req;

import lombok.Data;
import lombok.ToString;

/**
 * 秒杀商品详情
 */
@Data
@ToString
public class GoodsDeatil {

    private String goodsImg;
    private String goodsName;
    private String goodsTitle;
    private String seckillPrice;
    private String goodsPrice;
    private String startDate;
    private String endDate;
    private int stockCount;
    private long goodsId;





}
