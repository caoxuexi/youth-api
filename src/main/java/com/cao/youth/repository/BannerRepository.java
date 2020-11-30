package com.cao.youth.repository;

import com.cao.youth.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 曹学习
 * @description BannerRepository
 * @date 2020/8/20 12:52
 */
@Repository
public interface BannerRepository extends JpaRepository<Banner,Long> {
    Banner findOneById(Long id);
    Banner findOneByName(String name);
}
