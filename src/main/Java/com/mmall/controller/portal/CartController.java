package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResopnse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import com.mmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@RequestMapping("/cart/")
@Controller
public class CartController {

    @Autowired
    private ICartService iCartService;

    @RequestMapping("add.do")
    @ResponseBody
    public ServiceResopnse<CartVo> add(HttpSession session, Integer productid, Integer count) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.addProduct(user.getId(),productid,count);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public ServiceResopnse<CartVo> update(HttpSession session, Integer productid, Integer count) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.update(user.getId(),productid,count);
    }

    @RequestMapping("delete_product.do")
    @ResponseBody
    public ServiceResopnse<CartVo> deleteProduct(HttpSession session, String productids) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.deletepRroduct(user.getId(),productids);
    }

    @RequestMapping("get_all.do")
    @ResponseBody
    public ServiceResopnse<CartVo> getAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.getAll(user.getId());
    }

    @RequestMapping("checked_all.do")
    @ResponseBody
    public ServiceResopnse<CartVo> checkedAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        return iCartService.checkedOrUncheckedAll(user.getId(),null,Const.CartChecked.CHECKED);
    }

    @RequestMapping("checked.do")
    @ResponseBody
    public ServiceResopnse<CartVo> checked(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.checkedOrUncheckedAll(user.getId(),productId,Const.CartChecked.CHECKED);
    }

    @RequestMapping("unchecked_all.do")
    @ResponseBody
    public ServiceResopnse<CartVo> uncheckedAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.checkedOrUncheckedAll(user.getId(),null,Const.CartChecked.UN_CHECKED);
    }

    @RequestMapping("unchecked.do")
    @ResponseBody
    public ServiceResopnse<CartVo> unchecked(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.checkedOrUncheckedAll(user.getId(),productId,Const.CartChecked.UN_CHECKED);
    }

    @RequestMapping("get_cart_product_count.do")
    @ResponseBody
    public ServiceResopnse<Integer> getCartProductCount(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.getProductCount(user.getId());
    }
}
