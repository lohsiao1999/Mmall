package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ServiceResopnse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/user/")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResopnse<User> login(String username, String password, HttpSession session){
        ServiceResopnse<User> resopnse = iUserService.login(username,password);
        if (resopnse.isSuccess()){
            User curentuser = resopnse.getData();
            if (curentuser.getRole() == Const.role.ROLE_ADMIN){
                session.setAttribute(Const.CURRENT_USER,curentuser);
                return resopnse;
            }else {
                return ServiceResopnse.createByErrorMessage("非管理员无法登录");
            }
        }
        return  resopnse;
    }

}
