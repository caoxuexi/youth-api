package com.cao.youth.api.v1;

import com.cao.youth.exception.http.NotFoundException;
import com.cao.youth.model.Category;
import com.cao.youth.model.GridCategory;
import com.cao.youth.service.CategoryService;
import com.cao.youth.service.GridCategoryService;
import com.cao.youth.vo.CategoriesAllVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author 曹学习
 * @description CategoryController
 * @date 2020/8/24 16:46
 */
@RequestMapping("category")
@Controller
@ResponseBody
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GridCategoryService gridCategoryService;

    @GetMapping("/all")
    public CategoriesAllVO getAll(){
        Map<Integer, List<Category>> categories=categoryService.getAll();
        return new CategoriesAllVO(categories);
    }

    @GetMapping("/grid/all")
    public List<GridCategory> getGridCategoryList(){
        List<GridCategory> gridCategories=gridCategoryService.getGridCategoryList();
        if(gridCategories.isEmpty()){
            throw new NotFoundException(30009);
        }
        return gridCategories;
    }
}
