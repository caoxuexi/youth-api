package com.cao.youth.repository;

import com.cao.youth.model.Spu;
import com.cao.youth.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 曹学习
 * @description TagRepository
 * @date 2020/12/29 11:32
 */
public interface TagRepository extends JpaRepository<Tag,Long> {
    List<Tag> findAllByType(Integer type);
}
