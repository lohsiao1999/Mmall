package com.mmall.service;

import com.mmall.common.ServiceResopnse;
import com.mmall.vo.CartVo;

public interface ICartService {

    ServiceResopnse<CartVo> addProduct(Integer userId, Integer productId, Integer count);

    ServiceResopnse<CartVo> update(Integer userId,Integer productId,Integer count);

    ServiceResopnse<CartVo> deletepRroduct(Integer userId , String productIds);

    ServiceResopnse<CartVo> getAll(Integer userId );

    ServiceResopnse<CartVo> checkedOrUncheckedAll(Integer userid,Integer productId,Integer checked);

    ServiceResopnse<Integer> getProductCount(Integer userId );
}
