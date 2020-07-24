package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResopnse;
import com.mmall.pojo.Shopping;
import com.mmall.pojo.User;
import com.mmall.service.IShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@RequestMapping("/shopping/")
@Controller
public class ShoppingController {

    @Autowired
    private IShoppingService iShoppingService;

    @RequestMapping("add.do")
    @ResponseBody
    public ServiceResopnse add(HttpSession session, Shopping shopping){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShoppingService.add(user.getId(),shopping);
    }

    @RequestMapping("delete.do")
    @ResponseBody
    public ServiceResopnse delete(HttpSession session , Integer shoppingId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShoppingService.delete(user.getId(),shoppingId);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public ServiceResopnse update(HttpSession session , Shopping shopping){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShoppingService.update(user.getId(),shopping);
    }

    @RequestMapping("select.do")
    @ResponseBody
    public ServiceResopnse select(HttpSession session , Integer shoppingId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShoppingService.select(user.getId(),shoppingId);
    }

    @RequestMapping("select _all.do")
    @ResponseBody
    public ServiceResopnse<PageInfo> selectAll(HttpSession session,
                                               @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                               @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShoppingService.selectAll(user.getId(),pageNum,pageSize);
    }
}
