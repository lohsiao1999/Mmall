package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServiceResopnse;
import com.mmall.vo.OrderVo;

import java.util.Map;

public interface IOrderService {

    ServiceResopnse<Map> payOrder(Long orderno, Integer userId, String path);

    ServiceResopnse alipayCallBack(Map<String,String> param);

    ServiceResopnse queryPayStatus(Integer userId,Long orderNo);

    ServiceResopnse createOrder(Integer userId,Integer shippingId);

    ServiceResopnse cancel(Integer userId,Long orderNo);

    ServiceResopnse getOrderProduct(Integer userId);

    ServiceResopnse<OrderVo> getDetail(Integer userId, Long orderNo);

    ServiceResopnse<PageInfo> getAll(Integer userId, int pageNum, int pageSize);

    ServiceResopnse<PageInfo> backendGetAll(int pageNum,int pageSize);

    ServiceResopnse<OrderVo> backendGetDetail(Long orderNo);

    ServiceResopnse<PageInfo> backendSearch(Long orderNo,int pageNum,int pageSize);

    ServiceResopnse<String> sendGoods(Long orderNo);
}
