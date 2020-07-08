package com.hzit.skill.service.impl;


import com.hzit.skill.mapper.GoodsMapper;
import com.hzit.skill.model.Seckill_goods;
import com.hzit.skill.req.GoodsDeatil;
import com.hzit.skill.service.GoodsDeatilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsDeatilImpl implements GoodsDeatilService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public GoodsDeatil queryGoodsDetail(long goodsId) {
       GoodsDeatil goodsDeatil=goodsMapper.queryAllGoodsDeatil(goodsId);
        return goodsDeatil;
    }
}
