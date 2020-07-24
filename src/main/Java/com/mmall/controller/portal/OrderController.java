package com.mmall.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResopnse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping("create.do")
    @ResponseBody
    public ServiceResopnse create(HttpSession session, Integer shippingId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.createOrder(user.getId(),shippingId);
    }

    @RequestMapping("cancel.do")
    @ResponseBody
    public ServiceResopnse cancel(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.cancel(user.getId(),orderNo);
    }

    @RequestMapping("get_order_cart_product.do")
    @ResponseBody
    public ServiceResopnse getOrderCartProduct(HttpSession session) { //返回购物车当中用户选中的商品
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderProduct(user.getId());
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServiceResopnse detail(HttpSession session, Long orderno) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getDetail(user.getId(),orderno);
    }

    @RequestMapping("get_all.do")
    @ResponseBody
    public ServiceResopnse<PageInfo> getAll(HttpSession session,
                                            @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10")int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getAll(user.getId(),pageNum,pageSize);
    }

    @RequestMapping("pay.do")
    @ResponseBody
    public ServiceResopnse pay(HttpSession session, Long orderno, HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        String path =request.getSession().getServletContext().getRealPath("upload");
        return iOrderService.payOrder(orderno,user.getId(),path);
    }

    @RequestMapping("alipay_callback.do")
    @ResponseBody
    public Object alipayCallBack(HttpServletRequest request){//支付宝回调时会将参数保存在request当中
        Map<String,String> param = Maps.newHashMap();
        Map requestParam=request.getParameterMap();
        //将reques中的参数取出保存在map当中
        for(Iterator iterator=requestParam.keySet().iterator();iterator.hasNext();) {
            String name = (String) iterator.next();
            String[] values = (String[]) requestParam.get(name);
            String valueStr = "";
            for (int i = 0;i < values.length;i++){
                valueStr = (i == values.length - 1)?valueStr+values[i]:valueStr+values[i]+",";
            }
            param.put(name,valueStr);
        }
        logger.info("支付宝回调,sign:{},trade_status:{},参数:{}",param.get("sign"),param.get("trade_status"),param.toString());

        param.remove("sign_type"); //rsa验签方法中没有除去，官方文档中要求出去sign和sign_type
        try {
            boolean alipayRSAChecked = AlipaySignature.rsaCheckV2(param, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            if(!alipayRSAChecked) {
                return ServiceResopnse.createByErrorMessage("非法请求");
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝回调异常",e);
        }
        //todo 验证各种数据，可以在service中验证

        ServiceResopnse resopnse = iOrderService.alipayCallBack(param);
        if (resopnse.isSuccess()) {
            return Const.alipayCallBack.RESOPNSE_SUCCESS;
        }
        return Const.alipayCallBack.RESPONSE_FAILED;
    }

    @RequestMapping("query_order_status.do")
    @ResponseBody
    public ServiceResopnse<Boolean> queryOrderPayStatus(HttpSession session,Long orderNo){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        ServiceResopnse<Boolean> resopnse = iOrderService.queryPayStatus(user.getId(),orderNo);
        if (resopnse.isSuccess()) {
            return ServiceResopnse.createBySuccess(true);
        }
        return ServiceResopnse.createByError(false);
    }

}
