package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServiceResopnse;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductService iproductService;

    @RequestMapping("detail_for_user.do")
    @ResponseBody
    public ServiceResopnse<ProductDetailVo> detailForUser(Integer productid){
        return iproductService.getDetail(productid);
    }

    @RequestMapping("product_list.do")
    @ResponseBody
    public ServiceResopnse<PageInfo> searchByIdOrKeyword(@RequestParam(value = "keyword",required = false) String keyword,
                                                         @RequestParam(value = "categoryid",required = false)Integer categoryid,
                                                         @RequestParam(value = "pagenum",defaultValue = "1") int pagenum,
                                                         @RequestParam(value = "pagesize",defaultValue = "10")int pagesize,
                                                         @RequestParam(value = "orderby",defaultValue = "")String orderby){
        return iproductService.userSearchProduct(keyword, categoryid, pagenum, pagesize, orderby);
    }
}
