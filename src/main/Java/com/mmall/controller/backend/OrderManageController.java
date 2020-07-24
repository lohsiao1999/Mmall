package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResopnse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import com.mmall.service.IUserService;
import com.mmall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/order/")
public class OrderManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("get_all.do")
    @ResponseBody
    public ServiceResopnse<PageInfo> getAll(HttpSession session,
                                            @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iOrderService.backendGetAll(pageNum,pageSize);
        }else {
            return ServiceResopnse.createByErrorMessage("无权限");
        }
    }

    @RequestMapping("get_detail.do")
    @ResponseBody
    public ServiceResopnse<OrderVo> detail(HttpSession session, Long orderNo){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iOrderService.backendGetDetail(orderNo);
        }else {
            return ServiceResopnse.createByErrorMessage("无权限");
        }
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServiceResopnse<PageInfo> search(HttpSession session, Long orderNo,
                                           @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                           @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iOrderService.backendSearch(orderNo,pageNum,pageSize);
        }else {
            return ServiceResopnse.createByErrorMessage("无权限");
        }
    }

    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServiceResopnse<String> sendGoods(HttpSession session, Long orderNo){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iOrderService.sendGoods(orderNo);
        }else {
            return ServiceResopnse.createByErrorMessage("无权限");
        }
    }

}
