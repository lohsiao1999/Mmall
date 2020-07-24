package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServiceResopnse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

public interface IProductService {

    ServiceResopnse productSaveOrUpdate(Product product);

    ServiceResopnse<String> setStatus(Integer productId,Integer status);

    ServiceResopnse<ProductDetailVo> manageProductDetail(Integer productid);

    ServiceResopnse<PageInfo> getProductList(Integer pagenum, Integer pagesize);

    ServiceResopnse<PageInfo> searchByNameOrId(String productname,Integer productid,
                                               Integer pagenum, Integer pagesize);

    ServiceResopnse<ProductDetailVo> getDetail(Integer productid);

    ServiceResopnse<PageInfo> userSearchProduct(String keyword,Integer categoryid,int pagenum,
                                                int pagesize,String orderby);
}
