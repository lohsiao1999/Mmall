package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResopnse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.pojo.ProductExample;
import com.mmall.service.ICategoryService;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;

    public ServiceResopnse productSaveOrUpdate(Product product){
        if (product != null) {
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String[] subImagersArray = product.getSubImages().split(",");
                if (subImagersArray.length > 0) {
                    product.setMainImage(subImagersArray[0]);
                }
            }
            if (product.getId() == null) {
                int count = productMapper.insertSelective(product);
                if (count > 0) {
                    return ServiceResopnse.createBySuccessMessage("增加商品成功");
                }
                return ServiceResopnse.createByErrorMessage("增加商品失败");
            }else {
                ProductExample example = new ProductExample();
                ProductExample.Criteria criteria = example.createCriteria();
                criteria.andIdEqualTo(product.getId());
                int count = productMapper.updateByExampleSelective(product,example);
                if (count > 0) {
                    return ServiceResopnse.createBySuccessMessage("更新商品信息成功");
                }
                return ServiceResopnse.createByErrorMessage("更新商品信息失败");
            }
        }
        return ServiceResopnse.createByErrorMessage("参数错误");
    }

    public ServiceResopnse<String> setStatus(Integer productId,Integer status){
        if (productId == null || status == null) {
            return ServiceResopnse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product newproduct = new Product();
        newproduct.setStatus(status);
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andIdEqualTo(productId);
        int count = productMapper.updateByExampleSelective(newproduct,productExample);
        if (count > 0) {
            return ServiceResopnse.createBySuccessMessage("更新商品状态成功");
        }
        return ServiceResopnse.createByErrorMessage("更新商品状态失败");
    }

    public ServiceResopnse<ProductDetailVo> manageProductDetail(Integer productid){
        Product product = productMapper.selectByID(productid);
        if (product == null) {
            return ServiceResopnse.createByErrorMessage("商品不存在");
        }
        ProductDetailVo productDetailVo = assmbleProductDetailVO(product);
        return ServiceResopnse.createBySuccess(productDetailVo);
    }

    private ProductDetailVo assmbleProductDetailVO(Product product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setName(product.getName());
        productDetailVo.setCategory(product.getCategory());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://image.lohsiao.com/"));
        Category category = categoryMapper.selectById(product.getCategory());
        if (category == null) {
            productDetailVo.setParentCategoryId(0); //默认其为根节点
        }else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    public ServiceResopnse<PageInfo> getProductList(Integer pagenum,Integer pagesize){
        PageHelper.startPage(pagenum,pagesize);
        List<Product> productList = productMapper.getAll();
        List<ProductListVo> productListVos = Lists.newArrayList();
        for (Product product:productList){
            ProductListVo productListVo = assmbleProductListVO(product);
            productListVos.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList); //根据productList集合进行分页处理
        pageInfo.setList(productListVos);
        return ServiceResopnse.createBySuccess(pageInfo);
    }

    private ProductListVo assmbleProductListVO(Product product) {
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategory(product.getCategory());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setPrice(product.getPrice());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setStatus(product.getStatus());

        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","ftp://image.lohsiao.com/"));
        return productListVo;
    }

    public ServiceResopnse<PageInfo> searchByNameOrId(String productname,Integer productid,
                                                      Integer pagenum, Integer pagesize){
        PageHelper.startPage(pagenum,pagesize);
        if (StringUtils.isNotBlank(productname)) {
            productname = new StringBuilder().append("%").append(productname).append("%").toString();
        }
        List<Product> productList = productMapper.searchByIdOrName(productname, productid);
        List<ProductListVo> productListVos = Lists.newArrayList();
        for (Product product:productList){
            ProductListVo productListVo = assmbleProductListVO(product);
            productListVos.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVos);
        return ServiceResopnse.createBySuccess(pageInfo);
    }

    public ServiceResopnse<ProductDetailVo> getDetail(Integer productid) {
        Product product = productMapper.selectByID(productid);
        if (product == null) {
            return ServiceResopnse.createByErrorMessage("商品不存在");
        }
        if (product.getStatus() != Const.productStatusEnum.ON_SALE.getStatus()) {
            return ServiceResopnse.createByErrorMessage("商品已下架");
        }
        ProductDetailVo productDetailVo = assmbleProductDetailVO(product);
        return ServiceResopnse.createBySuccess(productDetailVo);
    }

    public ServiceResopnse<PageInfo> userSearchProduct(String keyword,Integer categoryid,int pagenum,int pagesize,String orderby){
        if (StringUtils.isBlank(keyword) && categoryid == null) {
            return ServiceResopnse.createByErrorMessage("参数错误");
        }
        List<Integer> categoryids = new ArrayList<>();
        if (categoryid != null) {
            Category category = categoryMapper.selectById(categoryid);
            if (category ==null && StringUtils.isBlank(keyword)) {
                PageHelper.startPage(pagenum,pagesize);
                List<ProductListVo> productListVos = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVos);
                return ServiceResopnse.createBySuccess(pageInfo);
            }
            categoryids =iCategoryService.getAllDescendants(categoryid).getData();
        }
        if (StringUtils.isNotBlank(keyword)) {
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        PageHelper.startPage(pagenum,pagesize);
        if (StringUtils.isNotBlank(orderby)) {
            if (Const.productOrderBy.PRICE_ASC_DESC.contains(orderby)) {
                String[] orderbyarray = orderby.split("_");
                PageHelper.orderBy(orderbyarray[0] + " " + orderbyarray[1]);
            }
        }
        List<Product> productList = productMapper.searchByCategoryidOrName(StringUtils.isBlank(keyword)?null:keyword,
                categoryids == null?null:categoryids);
        List<ProductListVo> voList = Lists.newArrayList();
        for (Product items:productList) {
            voList.add(assmbleProductListVO(items));
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(voList);
        return ServiceResopnse.createBySuccess(pageInfo);
    }

}
