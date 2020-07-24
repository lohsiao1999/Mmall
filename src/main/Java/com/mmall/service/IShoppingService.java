package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServiceResopnse;
import com.mmall.pojo.Shopping;

public interface IShoppingService {

    ServiceResopnse add(Integer userId, Shopping shopping);

    ServiceResopnse<String> delete(Integer userId,Integer shoppingId);

    ServiceResopnse<String> update(Integer userId,Shopping shopping);

    ServiceResopnse<Shopping> select(Integer userId,Integer shoppingId);

    ServiceResopnse<PageInfo> selectAll(Integer userId, Integer pageNum, Integer pageSize);

}
