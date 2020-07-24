package com.mmall.service;

import com.mmall.common.ServiceResopnse;
import com.mmall.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICategoryService {

    ServiceResopnse<String> addcategory(String categoryName,Integer parentId);

    ServiceResopnse<String> updateCategoryName(Integer id,String categoryName);

    ServiceResopnse<List<Category>> getSameParentBrother(Integer id);

    ServiceResopnse<List<Integer>> getAllDescendants(Integer id);
}
