package com.mmall.dao;

import com.mmall.pojo.Shopping;
import com.mmall.pojo.ShoppingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShoppingMapper {
    long countByExample(ShoppingExample example);

    int deleteByExample(ShoppingExample example);

    int insert(Shopping record);

    int insertSelective(Shopping record);

    List<Shopping> selectByExample(ShoppingExample example);

    int updateByExampleSelective(@Param("record") Shopping record, @Param("example") ShoppingExample example);

    int updateByExample(@Param("record") Shopping record, @Param("example") ShoppingExample example);

    int deleteByUseridAndShoppingId(@Param("userId")Integer userId,@Param("shoppingId")Integer shoppingId);

    Shopping selectByUseridAndShoppingid(@Param("userId")Integer userId,@Param("shoppingId")Integer shoppingId);
}