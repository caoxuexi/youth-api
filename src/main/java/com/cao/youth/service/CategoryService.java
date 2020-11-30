package com.cao.youth.service;

import com.cao.youth.model.Category;

import java.util.List;
import java.util.Map;

/**
 * @author 曹学习
 * @description CategoryService
 * @date 2020/8/24 22:49
 */
public interface CategoryService {
    public Map<Integer, List<Category>> getAll();
}
