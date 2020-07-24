package com.mmall.dao;

import com.mmall.pojo.Product;
import com.mmall.pojo.ProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductMapper {
    long countByExample(ProductExample example);

    int deleteByExample(ProductExample example);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectByExample(ProductExample example);

    int updateByExampleSelective(@Param("record") Product record, @Param("example") ProductExample example);

    int updateByExample(@Param("record") Product record, @Param("example") ProductExample example);

    Product selectByID(Integer id);

    List<Product> getAll();

    List<Product> searchByIdOrName(@Param("productname") String productname,@Param("productid")Integer productid);

    List<Product> searchByCategoryidOrName(@Param("productname") String productname,@Param("categoryids")List<Integer> categoryids);
}