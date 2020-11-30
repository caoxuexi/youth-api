package com.cao.youth.repository;

import com.cao.youth.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author 曹学习
 * @description ThemeRepository
 * @date 2020/8/25 11:43
 */
@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    @Query("select t from Theme t where t.name in (:names1)")
    List<Theme> findByNames(@Param("names1") List<String> names);

    Optional<Theme> findByName(String name);
}

