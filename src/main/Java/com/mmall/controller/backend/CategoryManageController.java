package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResopnse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping(value = "add_category.do")
    @ResponseBody
    public ServiceResopnse<String> addCategory(HttpSession session,String categoryName,@RequestParam(value = "parentId",defaultValue = "0") Integer parentId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iCategoryService.addcategory(categoryName,parentId);
        }else {
            return ServiceResopnse.createByErrorMessage("无权限");
        }
    }

    @RequestMapping(value = "set_category_name.do")
    @ResponseBody
    public ServiceResopnse<String> setCategoryName(HttpSession session,Integer id,String categoryName){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iCategoryService.updateCategoryName(id,categoryName);
        }else {
            return ServiceResopnse.createByErrorMessage("无权限");
        }
    }

    @RequestMapping(value = "get_category_brother.do")
    @ResponseBody
    public ServiceResopnse getChildrenParallelCategory(HttpSession session,
                                                       @RequestParam(value = "parentId",defaultValue = "0") Integer parentId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iCategoryService.getSameParentBrother(parentId);
        }else {
            return ServiceResopnse.createByErrorMessage("无权限");
        }
    }

    @RequestMapping(value = "get_children_grandchildren.do")
    @ResponseBody
    public ServiceResopnse getChildrenAndGrandchildren(HttpSession session,
                                                       @RequestParam(value = "id",defaultValue = "0") Integer id){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iCategoryService.getAllDescendants(id);
        }else {
            return ServiceResopnse.createByErrorMessage("无权限");
        }
    }
}
