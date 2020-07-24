package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServiceResopnse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.CategoryExample;
import com.mmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Service
public class CategoryImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    private Logger logger = LoggerFactory.getLogger(CategoryImpl.class);

    @Override
    public ServiceResopnse<String> addcategory(String categoryName, Integer parentId) {
        if (StringUtils.isBlank(categoryName) || parentId ==null) {
            return ServiceResopnse.createByErrorMessage("参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);
        int count = categoryMapper.insertSelective(category);
        if (count > 0) {
            return ServiceResopnse.createBySuccessMessage("增加种类成功");
        }
        return ServiceResopnse.createByErrorMessage("增加种类失败");
    }

    public ServiceResopnse<String> updateCategoryName(Integer id,String categoryName) {
        if (id == null || StringUtils.isBlank(categoryName)) {
            return ServiceResopnse.createByErrorMessage("参数错误");
        }
        Category newcategory = new Category();
        newcategory.setName(categoryName);
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andIdEqualTo(id);
        int count = categoryMapper.updateByExampleSelective(newcategory,categoryExample);
        if (count > 0) {
            return ServiceResopnse.createBySuccessMessage("更改种类名称成功");
        }
        return ServiceResopnse.createByErrorMessage("更改种类名称失败");
    }

    public ServiceResopnse<List<Category>> getSameParentBrother(Integer id){
        List<Category> children = categoryMapper.getSameParentBrother(id);
        if (CollectionUtils.isEmpty(children)){
            logger.info("未找到！！！");
        }
        return ServiceResopnse.createBySuccess(children);
    }

    public ServiceResopnse<List<Integer>> getAllDescendants(Integer id){
        Set<Category> newset = Sets.newHashSet();
        finalCategory(newset,id);
        List<Integer> respondid = Lists.newArrayList();
        if (id != null) {
            for(Category cate1 : newset){
                respondid.add(cate1.getId());
            }
        }
        return ServiceResopnse.createBySuccess(respondid);
    }

    private Set<Category> finalCategory(Set<Category> children ,Integer id){
        Category category = categoryMapper.selectById(id);
        if (category != null) {
            children.add(category);
        }
        List<Category> mylist = categoryMapper.getSameParentBrother(id);
        for(Category cat1 : mylist){
            finalCategory(children,cat1.getId());
        }
        return children;
    }
}
