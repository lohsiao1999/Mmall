package com.mmall.controller.portal;

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
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResopnse<User> login(String username, String password, HttpSession session){
        ServiceResopnse<User> resopnse = iUserService.login(username,password);
        if (resopnse.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,resopnse.getData());
        }
        return  resopnse;
    }

    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResopnse<User> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServiceResopnse.createBySuccess();
    }

    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResopnse<String> register(User user){
        return iUserService.register(user);
    }

    @RequestMapping(value = "vaild.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResopnse<String> vaild(String str,String type){
        return iUserService.checkVaild(str, type);
    }

    @RequestMapping(value = "get_user_info.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResopnse<User> getUserInfo(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByErrorMessage("用户未登录");
        }
        return ServiceResopnse.createBySuccess(user);
    }

    @RequestMapping(value = "question_for_forget.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResopnse<String> questionForForget(String username){
        return iUserService.getQuestion(username);
    }

    @RequestMapping(value = "check_question_answer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResopnse<String> checkQuestionAnswer(String username,String question,String answer){
       return iUserService.checkAnswer(username, question, answer);
    }

    @RequestMapping(value = "reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResopnse<String> resetPassword(String username,String newpassword,String token){
        return iUserService.resetPassword(username, newpassword, token);
    }

    @RequestMapping(value = "online_reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResopnse<String> onlineResetPassword(String oldpassword,String newpassword,HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByErrorMessage("用户未登录");
        }
        return iUserService.onlineResetPassword(oldpassword,newpassword,user);
    }

    @RequestMapping(value = "update_info.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResopnse<User> onlineUpdateInfo(HttpSession session,User user){
        User currentuser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentuser == null) {
            return ServiceResopnse.createByErrorMessage("用户未登录");
        }
        user.setId(currentuser.getId()); //从当前session中取出userid，防止篡改他人信息
        user.setUsername(currentuser.getUsername());
        ServiceResopnse<User> vaild = iUserService.updateInformation(user);
        if (vaild.isSuccess()) {
            vaild.getData().setUsername(user.getUsername());
            session.setAttribute(Const.CURRENT_USER,vaild.getData());
        }
        return vaild;
    }

    @RequestMapping(value = "get_info.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResopnse<User> getInfo(HttpSession session){
        User currentuser = (User)session.getAttribute(Const.CURRENT_USER);
        if (currentuser == null) {
            return ServiceResopnse.createByError(10,"未登录");
        }
        return iUserService.getUserInfo(currentuser.getId());
    }
}
