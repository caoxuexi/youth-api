package com.cao.youth.repository;

import com.cao.youth.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 曹学习
 * @description CategoryRepository
 * @date 2020/8/24 18:15
 */
public interface CategoryRepository extends JpaRepository<Category,Long> {
    //查询是根节点的
    List<Category> findAllByIsRootOrderByIndexAsc(Boolean isRoot);
}
