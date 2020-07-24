package com.mmall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResopnse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.CartExample;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    public ServiceResopnse<CartVo> addProduct(Integer userId,Integer productId,Integer count){
        if (productId == null || count == null) {
            return ServiceResopnse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"参数错误");
        }
        Cart cart = cartMapper.getCartById(userId,productId);
        if (cart == null) { //当购物车不存在该商品时，创建一个新的购物车对象
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setProductId(productId);
            newCart.setQuantity(count);
            newCart.setChecked(Const.CartChecked.CHECKED);
            cartMapper.insert(newCart);
        }else { //当购物车存在该商品时，更新购物车中该商品的数量
            count = cart.getQuantity()+count;
            cart.setQuantity(count);
            CartExample example = new CartExample();
            CartExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(cart.getId());
            cartMapper.updateByExampleSelective(cart,example);
        }
        return cartObject(userId);
    }

    public ServiceResopnse<CartVo> update(Integer userId,Integer productId,Integer count){
        if (productId == null || count == null) {
            return ServiceResopnse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"参数错误");
        }
        Cart cart = cartMapper.getCartById(userId,productId);
        if (cart != null) {
            cart.setQuantity(count);
        }
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andIdEqualTo(cart.getId());
        cartMapper.updateByExampleSelective(cart,cartExample);
        return cartObject(userId);
    }

    public ServiceResopnse<CartVo> deletepRroduct(Integer userId , String productIds) {
        List<String> idlist = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(idlist)) {
            return ServiceResopnse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"参数错误");
        }
        int count = cartMapper.deleteProduct(userId,idlist);
        if (count > 0) {
            return cartObject(userId);
        }
        return ServiceResopnse.createByErrorMessage("删除商品失败");
    }

    public ServiceResopnse<CartVo> getAll(Integer userId ) {
        return cartObject(userId);
    }

    public ServiceResopnse<CartVo> checkedOrUncheckedAll(Integer userid,Integer productId,Integer checked) {
        cartMapper.checkedOrUncheckedAll(userid,checked,productId);
        return this.cartObject(userid);
    }

    public ServiceResopnse<Integer> getProductCount(Integer userId ) {
        if (userId == null) {
            return ServiceResopnse.createByErrorMessage("0");
        }
        return ServiceResopnse.createBySuccess(cartMapper.getProductCount(userId));
    }



    /**
     * //该方法返回用户对应的所有购物车对象
     * 且会判断购物车中各个商品的数量是否超过该商品的库存量
     * 若超过则将其数量设置为商品的最大库存量
     */
    private CartVo getCartLimit(Integer userId){
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartById(userId); //获取用户对应得购物车对象，一个购物车对象对应一个商品对象
        List<CartProductVo> cartVoList = Lists.newArrayList();
        BigDecimal cartTotalPrice = new BigDecimal("0");
        if (!CollectionUtils.isEmpty(cartList)) {
            for (Cart cartitem : cartList) {
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartitem.getId());
                cartProductVo.setUserId(cartitem.getUserId());
                cartProductVo.setProductId(cartitem.getProductId());
                Product product = productMapper.selectByID(cartitem.getProductId());
                if (product != null) {
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStock(product.getStock());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getPrice());
                    //判断库存
                    int buyCount = 0 ;
                    if (product.getStock() >= cartitem.getQuantity()) { //产品库存大于或等于购物车中的数量
                        cartProductVo.setLimitQuantity(Const.CartChecked.LIMIT_NUM_SUCCESS);
                        buyCount = cartitem.getQuantity();
                    }else {
                        buyCount = product.getStock(); //超出商品库存时，最大购买数量为商品库存
                        cartProductVo.setLimitQuantity(Const.CartChecked.LIMIT_NUM_FAIL);
                        Cart cartForCount = new Cart();
                        cartForCount.setQuantity(buyCount);
                        CartExample cartExample = new CartExample();
                        CartExample.Criteria criteria = cartExample.createCriteria();
                        criteria.andIdEqualTo(cartitem.getId());
                        cartMapper.updateByExampleSelective(cartForCount,cartExample);
                    }
                    cartProductVo.setQuantity(buyCount);
                    //一种商品的总价
                    cartProductVo.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),buyCount));
                }
                if (cartitem.getChecked() == Const.CartChecked.CHECKED) {
                    //购物车里所有商品的总价
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getTotalPrice().doubleValue());
                }
                cartVoList.add(cartProductVo);
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartVoList);
        cartVo.setAllChecked(getAllChecked(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVo;
    }

    private ServiceResopnse<CartVo> cartObject(Integer userId){
        CartVo cartVo = this.getCartLimit(userId);
        return ServiceResopnse.createBySuccess(cartVo);
    }

    private boolean getAllChecked(Integer userId) {
        if (userId == null) {
            return false;
        }
        return cartMapper.selectCartProductCheckedStatus(userId) == 0;
    }
}
