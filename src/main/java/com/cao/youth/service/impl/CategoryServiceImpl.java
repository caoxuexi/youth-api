package com.cao.youth.service.impl;

import com.cao.youth.model.Category;
import com.cao.youth.repository.CategoryRepository;
import com.cao.youth.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 曹学习
 * @description CategoryImpl
 * @date 2020/8/24 22:49
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Map<Integer,List<Category>> getAll() {
        List<Category> roots=categoryRepository.findAllByIsRootOrderByIndexAsc(true);
        List<Category> subs=categoryRepository.findAllByIsRootOrderByIndexAsc(false);
        Map<Integer,List<Category>> categories=new HashMap<>();
        categories.put(1,roots);
        categories.put(2,subs);
        return categories;
    }
}
