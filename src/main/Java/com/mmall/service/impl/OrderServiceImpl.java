package com.mmall.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ServiceResopnse;
import com.mmall.dao.*;
import com.mmall.pojo.*;
import com.mmall.service.IOrderService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.FTPUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.OrderItemVo;
import com.mmall.vo.OrderProductVo;
import com.mmall.vo.OrderVo;
import com.mmall.vo.ShippingVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service("iOrderService")
public class OrderServiceImpl implements IOrderService {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private PayInfoMapper payInfoMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ShoppingMapper shoppingMapper;

    @Autowired
    private ProductMapper productMapper;
    public ServiceResopnse<Map> payOrder(Long orderno,Integer userId,String path){
        Map<String,String> map = Maps.newHashMap();
        Order order = orderMapper.selectByUseridAndOrderno(orderno,userId);
        if (order == null) {
            return ServiceResopnse.createByErrorMessage("订单不存在");
        }
        map.put("orderNo",String.valueOf(order.getOrderNo()));

        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo().toString();

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = new StringBuilder().append("lohsiaoMmall扫码支付,订单号：").append(outTradeNo).toString();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = new StringBuilder().append("订单").append(outTradeNo).append("共消费").append(totalAmount).toString();

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        List<OrderItem> orderItemList = orderItemMapper.getOrderItemList(order.getOrderNo(),order.getUserId());
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        for (OrderItem item: orderItemList) {
            GoodsDetail goods1 = GoodsDetail.newInstance(item.getProductId().toString(), item.getProductName(),
                    BigDecimalUtil.mul(item.getCurrentUnitPrice().doubleValue(),new Double("100")).longValue(),
                    item.getQuantity());
            // 创建好一个商品后添加至商品明细列表
            goodsDetailList.add(goods1);
        }

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                               .setNotifyUrl(PropertiesUtil.getProperty("alipay.callback.url"))//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                logger.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                File folder = new File(path);
                if (!folder.exists()) {
                    folder.setWritable(true);
                    folder.mkdirs();
                }
                // 需要修改为运行机器上的路径
                String filePath = String.format(path+"/qr-%s.png", response.getOutTradeNo());//生成路径
                String fileName = String.format("qr-%s.png", response.getOutTradeNo()); //生成二维码图片文件名
                ZxingUtils.getQRCodeImge(response.getQrCode(),256,filePath); //生成二维码
                File newFile = new File(path,fileName);
                try {
                    FTPUtil.upLoadFile(Lists.newArrayList(newFile)); //上传到ftp服务器
                } catch (IOException e) {
                    logger.error("上传二维码异常" , e);
                }
                String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+newFile.getName();
                map.put("url",url);
                return ServiceResopnse.createBySuccess(map);

            case FAILED:
                logger.error("支付宝预下单失败!!!");
                return ServiceResopnse.createByErrorMessage("支付宝预下单失败!!!");

            case UNKNOWN:
                logger.error("系统异常，预下单状态未知!!!");
                return ServiceResopnse.createByErrorMessage("系统异常，预下单状态未知!!!");

            default:
                logger.error("不支持的交易状态，交易返回异常!!!");
                return ServiceResopnse.createByErrorMessage("不支持的交易状态，交易返回异常!!!");
        }
    }

    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            logger.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                logger.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            logger.info("body:" + response.getBody());
        }
    }

    public ServiceResopnse alipayCallBack(Map<String,String> param){
        long orderNo = Long.parseLong(param.get("out_trade_no"));
        String tradeNo = param.get("trade_no");
        String tradeStatus = param.get("trade_status");

        Order order = orderMapper.selectByOrderno(orderNo);
        if (order == null) {
            return ServiceResopnse.createByErrorMessage("不存在该订单，忽略");
        }
        if (order.getStatus()>=Const.orderStatusEnum.PAID.getCode()) {
            return ServiceResopnse.createBySuccessMessage("支付宝重复调用");
        }
        if (Const.alipayCallBack.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
            order.setPaymentTime(DateTimeUtil.strToDate(param.get("gmt_payment")));
            order.setStatus(Const.orderStatusEnum.PAID.getCode());
            OrderExample example =new OrderExample();
            OrderExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(order.getId());
            orderMapper.updateByExampleSelective(order,example);
        }
        PayInfo payInfo =new PayInfo();
        payInfo.setUserId(order.getUserId());
        payInfo.setOrderNo(order.getOrderNo());
        payInfo.setPayPlatform(Const.payPlatFormEnum.ALIPAY.getCode());
        payInfo.setPlatformNumber(tradeNo);
        payInfo.setPlatformStatus(tradeStatus);
        payInfoMapper.insertSelective(payInfo);
        return ServiceResopnse.createBySuccess();
    }

    public ServiceResopnse queryPayStatus(Integer userId,Long orderNo) {
        Order order = orderMapper.selectByUseridAndOrderno(orderNo,userId);
        if (order == null) {
            return ServiceResopnse.createByErrorMessage("该用户不拥有该订单");
        }
        if (order.getStatus() >= Const.orderStatusEnum.PAID.getCode()) {
            return ServiceResopnse.createBySuccess();
        }
        return ServiceResopnse.createByError();
    }

    //生成订单
    public ServiceResopnse createOrder(Integer userId,Integer shippingId) {
        List<Cart> cartList = cartMapper.selectCartById(userId);
        ServiceResopnse<List<OrderItem>> resopnse = getOrderItemList(userId,cartList);
        if (!resopnse.isSuccess()) {
            return resopnse;
        }
        List<OrderItem> orderItemList = resopnse.getData();
        BigDecimal payment = getTotalPrice(orderItemList);
        Order order = assembleOrder(userId,shippingId,payment);
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
        }
        orderItemMapper.insertOrderItem(orderItemList);
        //减少product表当中商品的库存
        reduceProductStock(orderItemList);
        //清空购物车当中用户选中的商品
        cleanCart(cartList);
        return ServiceResopnse.createBySuccess(assembleOrderVo(order,orderItemList));
    }

    //ordervo当中包含order，List<OrderItemVo>，ShippingVo
    private OrderVo assembleOrderVo(Order order,List<OrderItem> orderItemList){
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPaymentTypeDesc(Const.payEnum.codeOf(order.getPaymentType()).getStatus());
        orderVo.setPostage(order.getPostage());
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.orderStatusEnum.codeOf(order.getStatus()).getStatus());
        orderVo.setShippingId(order.getShoppingId());
        Shopping shopping = shoppingMapper.selectByUseridAndShoppingid(order.getUserId(),order.getShoppingId());
        if (shopping != null) {
            orderVo.setReceiveName(shopping.getReceiverName());
            orderVo.setShippingVo(assembleShippingVo(shopping));
        }
        orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        orderVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        for (OrderItem item : orderItemList) {
            OrderItemVo orderItemVo = assembleOrderItemVo(item);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }

    private OrderItemVo assembleOrderItemVo(OrderItem orderItem){
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());
        orderItemVo.setCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));
        return orderItemVo;
    }

    private ShippingVo assembleShippingVo(Shopping shopping){
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setReceiverName(shopping.getReceiverName());
        shippingVo.setReceiverAddress(shopping.getReceiverAddress());
        shippingVo.setReceiverCity(shopping.getReceiverCity());
        shippingVo.setReceiverPhone(shopping.getReceiverPhone());
        shippingVo.setReceiverProvince(shopping.getReceiverProvince());
        shippingVo.setReceiverZip(shopping.getReceiverZip());
        shippingVo.setReceiverDistrict(shopping.getReceiverDistrict());
        shippingVo.setReceiverMobile(shopping.getReceiverMobile());
        return shippingVo;
    }

    //清空购物车
    private void cleanCart(List<Cart> cartList){
        for (Cart cart:cartList){
            CartExample example = new CartExample();
            CartExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(cart.getId());
            cartMapper.deleteByExample(example);
        }
    }

    //创建订单成功时减少商品库存
    private void reduceProductStock(List<OrderItem> orderItems){
        for (OrderItem item : orderItems) {
            Product product = productMapper.selectByID(item.getProductId());
            product.setStock(product.getStock() - item.getQuantity());
            ProductExample example = new ProductExample();
            ProductExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(item.getProductId());
            productMapper.updateByExampleSelective(product,example);
        }
    }

    //根据userId,shippingId,payment三个数据组装order对象并存入数据库（注：生成订单时使用）
    private Order assembleOrder(Integer userId,Integer shippingId,BigDecimal payment){
        Order order = new Order();
        long orderNo = generateOrderNo();
        order.setUserId(userId);
        order.setShoppingId(shippingId);
        order.setOrderNo(orderNo);
        order.setPayment(payment);
        order.setPaymentType(Const.payEnum.ONLINE_PAY.getCode());
        order.setStatus(Const.orderStatusEnum.NO_PAY.getCode());
        order.setPostage(0);
        int count = orderMapper.insertSelective(order);
        if (count > 0) {
            return order;
        }
        return null;
    }

    //返回orderNo
    private long generateOrderNo(){
        long currentTime = System.currentTimeMillis();
        return currentTime+new Random().nextInt(100);
    }

    //计算订单的总价格
    private BigDecimal getTotalPrice(List<OrderItem> orderItems){
        BigDecimal price = new BigDecimal("0");
        for (OrderItem item : orderItems){
            price = BigDecimalUtil.add(price.doubleValue(),item.getTotalPrice().doubleValue());
        }
        return price;
    }

    //生成订单时，根据用户id和用户购物车中的商品生成包含List<OrderItem>的返回对象
    private ServiceResopnse<List<OrderItem>> getOrderItemList(Integer userId,List<Cart> cartList){
        if(CollectionUtils.isEmpty(cartList)) {
            return ServiceResopnse.createByErrorMessage("购物车为空");
        }
        List<OrderItem> orderItemList = Lists.newArrayList();
        for (Cart citem : cartList) {
            Product product = productMapper.selectByID(citem.getProductId());
            if (product == null) {
                return ServiceResopnse.createByErrorMessage("商品id错误");
            }
            if (Const.productStatusEnum.ON_SALE.getStatus() != product.getStatus()) {
                return ServiceResopnse.createByErrorMessage("商品不可售"+product.getName());
            }
            if (citem.getQuantity() > product.getStock()){
                return ServiceResopnse.createByErrorMessage("库存不足");
            }
            OrderItem item = new OrderItem();
            item.setUserId(userId);
            item.setCurrentUnitPrice(product.getPrice());
            item.setProductId(product.getId());
            item.setProductImage(product.getMainImage());
            item.setProductName(product.getName());
            item.setQuantity(citem.getQuantity());
            item.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),citem.getQuantity()));
            orderItemList.add(item);
        }
        return ServiceResopnse.createBySuccess(orderItemList);
    }

    public ServiceResopnse cancel(Integer userId,Long orderNo){
        Order order = orderMapper.selectByUseridAndOrderno(orderNo,userId);
        if (order == null) {
            return ServiceResopnse.createByErrorMessage("订单不存在");
        }
        if (order.getStatus() != Const.orderStatusEnum.NO_PAY.getCode()) {
            return ServiceResopnse.createByErrorMessage("已支付，无法取消");
        }
        Order newOrder = new Order();
        newOrder.setId(order.getId());
        newOrder.setStatus(Const.orderStatusEnum.CANCELED.getCode());
        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(order.getId());
        int row = orderMapper.updateByExampleSelective(newOrder,example);
        if (row > 0) {
            return ServiceResopnse.createBySuccess();
        }
        return ServiceResopnse.createByError();
    }

    public ServiceResopnse getOrderProduct(Integer userId){
        OrderProductVo orderProductVo = new OrderProductVo();
        List<Cart> cartList = cartMapper.selectCheckedCarByUserId(userId);
        ServiceResopnse<List<OrderItem>> resopnse = getOrderItemList(userId,cartList);
        if (!resopnse.isSuccess()) {
            return resopnse;
        }
        List<OrderItem> orderItemList = resopnse.getData();
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        BigDecimal payment = new BigDecimal("0");
        for (OrderItem item:orderItemList){
            payment = BigDecimalUtil.add(payment.doubleValue(),item.getTotalPrice().doubleValue());
            orderItemVoList.add(assembleOrderItemVo(item));
        }
        orderProductVo.setOrderItemVoList(orderItemVoList);
        orderProductVo.setProductTotalPrice(payment);
        orderProductVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return ServiceResopnse.createBySuccess(orderProductVo);
    }

    public ServiceResopnse<OrderVo> getDetail(Integer userId,Long orderNo) {
        Order order = orderMapper.selectByUseridAndOrderno(orderNo,userId);
        if (order == null) {
            return ServiceResopnse.createByErrorMessage("找不到订单");
        }
        List<OrderItem> orderItemList = orderItemMapper.getOrderItemList(orderNo,userId);
        OrderVo orderVo = assembleOrderVo(order,orderItemList);
        return ServiceResopnse.createBySuccess(orderVo);
    }

    public ServiceResopnse<PageInfo> getAll(Integer userId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUserid(userId);
        if (CollectionUtils.isEmpty(orderList)) {
            return ServiceResopnse.createByErrorMessage("找不到订单");
        }
        List<OrderVo> orderVoList = assembleOrderVoList(orderList,userId);
        PageInfo result = new PageInfo(orderList);
        result.setList(orderVoList);
        return ServiceResopnse.createBySuccess(result);
    }

    private List<OrderVo> assembleOrderVoList(List<Order> orderList,Integer userId){
        List<OrderVo> orderVoList = Lists.newArrayList();
        for (Order orderItem : orderList) {
            List<OrderItem> orderItemList = orderItemMapper.getOrderItemList(orderItem.getOrderNo(),userId);
            OrderVo orderVo = assembleOrderVo(orderItem,orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }

    //----------------------------------------BACKEND-----------------------------------------------

    public ServiceResopnse<PageInfo> backendGetAll(int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.getAllOrder();
        List<OrderVo> orderVoList = assembleOrderVoList(orderList,null);
        PageInfo result = new PageInfo(orderList);
        result.setList(orderVoList);
        return ServiceResopnse.createBySuccess(result);
    }

    public ServiceResopnse<OrderVo> backendGetDetail(Long orderNo){
        Order order = orderMapper.selectByOrderno(orderNo);
        if (order == null) {
            return ServiceResopnse.createByErrorMessage("订单不存在");
        }
        OrderItemExample example = new OrderItemExample();
        OrderItemExample.Criteria criteria = example.createCriteria();
        criteria.andOrderNoEqualTo(orderNo);
        List<OrderItem> orderItemList = orderItemMapper.selectByExample(example);
        OrderVo orderVo = assembleOrderVo(order,orderItemList);
        return ServiceResopnse.createBySuccess(orderVo);
    }

    public ServiceResopnse<PageInfo> backendSearch(Long orderNo,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Order order = orderMapper.selectByOrderno(orderNo);
        if (order == null) {
            return ServiceResopnse.createByErrorMessage("订单不存在");
        }
        OrderItemExample example = new OrderItemExample();
        OrderItemExample.Criteria criteria = example.createCriteria();
        criteria.andOrderNoEqualTo(orderNo);
        List<OrderItem> orderItemList = orderItemMapper.selectByExample(example);
        OrderVo orderVo = assembleOrderVo(order,orderItemList);
        PageInfo pageInfo = new PageInfo(Lists.newArrayList(order));
        pageInfo.setList(Lists.newArrayList(orderVo));
        return ServiceResopnse.createBySuccess(pageInfo);
    }

    public ServiceResopnse<String> sendGoods(Long orderNo){
        Order order = orderMapper.selectByOrderno(orderNo);
        if (order == null) {
            return ServiceResopnse.createByErrorMessage("订单不存在");
        }
        if (order.getStatus() == Const.orderStatusEnum.PAID.getCode()) {
            order.setStatus(Const.orderStatusEnum.SHIPPING.getCode());
            order.setSendTime(new Date());
            OrderExample example = new OrderExample();
            OrderExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(order.getId());
            orderMapper.updateByExampleSelective(order,example);
            return ServiceResopnse.createBySuccess("发货成功");
        }
        return ServiceResopnse.createByError("订单状态错误,无法发货");
    }

}
