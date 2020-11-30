package com.cao.youth.service.impl;

import com.cao.youth.model.GridCategory;
import com.cao.youth.repository.GridCategoryRepository;
import com.cao.youth.service.GridCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 曹学习
 * @description GridCategoryServiceImpl
 * @date 2020/8/25 11:22
 */
@Service
public class GridCategoryServiceImpl implements GridCategoryService {
    @Autowired
    private GridCategoryRepository gridCategoryRepository;

    @Override
    public List<GridCategory> getGridCategoryList() {
        return gridCategoryRepository.findAll();
    }
}
