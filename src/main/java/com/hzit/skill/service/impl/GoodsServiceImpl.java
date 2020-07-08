package com.hzit.skill.service.impl;
import com.hzit.skill.mapper.GoodsMapper;
import com.hzit.skill.req.GoodsData;
import com.hzit.skill.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public List<GoodsData> queryAllGoods()
    {
        return goodsMapper.queryAllGoods();
    }
}
