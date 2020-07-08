package com.hzit.skill.mapper;

import com.hzit.skill.model.Goods;
import com.hzit.skill.req.GoodsData;
import com.hzit.skill.req.GoodsDeatil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    List<GoodsData> queryAllGoods();

    GoodsDeatil queryAllGoodsDeatil(@Param("goodsId") Long goodsId);//（这里是形参，值从数据库传过来的）@Param用在传入的多个值，类型不同的时候，相同时可以去掉

    void DcrStock(Long goodsId);
}