package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ServiceResopnse;
import com.mmall.dao.ShoppingMapper;
import com.mmall.pojo.Shopping;
import com.mmall.pojo.ShoppingExample;
import com.mmall.service.IShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class ShoppingServiceImpl implements IShoppingService {

    @Autowired
    private ShoppingMapper shoppingMapper;

    public ServiceResopnse add(Integer userId,Shopping shopping){
        shopping.setUserId(userId);
        int count = shoppingMapper.insertSelective(shopping);
        if (count > 0) {
            Map<String,Integer> map = Maps.newHashMap();
            map.put("shoppingId",shopping.getId());
            return ServiceResopnse.createBySuccess("新建地址成功",map);
        }
        return ServiceResopnse.createByErrorMessage("新建地址失败");
    }

    public ServiceResopnse<String> delete(Integer userId,Integer shoppingId){
        int count = shoppingMapper.deleteByUseridAndShoppingId(userId,shoppingId);
        if (count > 0) {
            return ServiceResopnse.createBySuccessMessage("删除地址成功");
        }
        return ServiceResopnse.createByErrorMessage("删除地址失败");
    }

    public ServiceResopnse<String> update(Integer userId,Shopping shopping){
        ShoppingExample example = new ShoppingExample();
        ShoppingExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andIdEqualTo(shopping.getId());
        shopping.setUserId(userId);
        int count = shoppingMapper.updateByExampleSelective(shopping,example);
        if (count > 0) {
            return ServiceResopnse.createBySuccessMessage("更新地址成功");
        }
        return ServiceResopnse.createByErrorMessage("更新地址失败");
    }

    public ServiceResopnse<Shopping> select(Integer userId,Integer shoppingId){
       Shopping shopping = shoppingMapper.selectByUseridAndShoppingid(userId, shoppingId);
        if (shopping != null) {
            return ServiceResopnse.createBySuccess(shopping);
        }
        return ServiceResopnse.createByErrorMessage("地址不存在");
    }

    public ServiceResopnse<PageInfo> selectAll(Integer userId,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        ShoppingExample example = new ShoppingExample();
        ShoppingExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<Shopping> list = shoppingMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return ServiceResopnse.createBySuccess(pageInfo);
    }
}
