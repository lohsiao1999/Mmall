package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResopnse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RequestMapping("/manage/product/")
@Controller
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    @RequestMapping("save_product.do")
    @ResponseBody
    public ServiceResopnse saveProduct(HttpSession session, Product product){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iProductService.productSaveOrUpdate(product);
        }else {
            return ServiceResopnse.createByErrorMessage("无权限");
        }
    }

    @RequestMapping("set_product_status.do")
    @ResponseBody
    public ServiceResopnse<String> setProductStatus(HttpSession session, Integer productId , Integer status){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iProductService.setStatus(productId, status);
        }else {
            return ServiceResopnse.createByErrorMessage("无权限");
        }
    }

    @RequestMapping("get_detail.do")
    @ResponseBody
    public ServiceResopnse<ProductDetailVo> getDetail(HttpSession session, Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iProductService.manageProductDetail(productId);
        }else {
            return ServiceResopnse.createByErrorMessage("无权限");
        }
    }

    @RequestMapping("get_all_product.do")
    @ResponseBody
    public ServiceResopnse<PageInfo> getAllProduct(HttpSession session ,
                                                   @RequestParam(value = "pagenum",defaultValue = "1") Integer pagenum ,
                                                   @RequestParam(value = "pagesize",defaultValue = "10")Integer pagesize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iProductService.getProductList(pagenum, pagesize);
        }else {
            return ServiceResopnse.createByErrorMessage("无权限");
        }
    }

    @RequestMapping("search_product.do")
    @ResponseBody
    public ServiceResopnse<PageInfo> searchProduct(HttpSession session , String productName, Integer productId,
                                                   @RequestParam(value = "pagenum",defaultValue = "1") Integer pagenum ,
                                                   @RequestParam(value = "pagesize",defaultValue = "10")Integer pagesize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iProductService.searchByNameOrId(productName,productId,pagenum,pagesize);
        }else {
            return ServiceResopnse.createByErrorMessage("无权限");
        }
    }

    @RequestMapping("upload.do")
    @ResponseBody
    public ServiceResopnse upLoad(HttpSession session,
                                  @RequestParam(value = "upload_file",required = false) MultipartFile file,
                                  HttpServletRequest request){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResopnse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upLoad(file,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;

            Map filemap = Maps.newHashMap();
            filemap.put("uri",targetFileName);
            filemap.put("url",url);
            return ServiceResopnse.createBySuccess(filemap);
        }else {
            return ServiceResopnse.createByErrorMessage("无权限");
        }

    }
}
