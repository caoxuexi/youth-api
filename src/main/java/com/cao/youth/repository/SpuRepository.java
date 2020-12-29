package com.cao.youth.repository;

import com.cao.youth.model.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 曹学习
 * @description SpuRepository
 * @date 2020/8/21 23:16
 */
public interface SpuRepository extends JpaRepository<Spu,Long> {
    Spu findOneById(Long id); 
    /** "select * from spu where category_id="cid"  */
    Page<Spu> findByCategoryIdOrderByCreateTimeDesc(Long cid, Pageable pageable);
    Page<Spu> findByRootCategoryIdOrderByCreateTime(Long cid, Pageable pageable);
    Page<Spu> findByTitleLikeOrSubtitleLike(String keyword, String keyword1, Pageable pageable);
}
