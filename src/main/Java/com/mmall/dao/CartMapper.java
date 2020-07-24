package com.mmall.dao;

import com.mmall.pojo.Cart;
import com.mmall.pojo.CartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CartMapper {
    long countByExample(CartExample example);

    int deleteByExample(CartExample example);

    int insert(Cart record);

    int insertSelective(Cart record);

    List<Cart> selectByExample(CartExample example);

    int updateByExampleSelective(@Param("record") Cart record, @Param("example") CartExample example);

    int updateByExample(@Param("record") Cart record, @Param("example") CartExample example);

    Cart getCartById(@Param("userId")Integer userId,@Param("productId")Integer productId);

    List<Cart> selectCartById(Integer userId);

    int selectCartProductCheckedStatus(Integer userId);

    int deleteProduct(@Param("userId")Integer userId,@Param("productids")List<String> productids);

    int checkedOrUncheckedAll(@Param("userId")Integer userId,@Param("checked")Integer checked,
                              @Param("productId")Integer productId);

    int getProductCount(Integer userId);

    List<Cart> selectCheckedCarByUserId(Integer userId);

}