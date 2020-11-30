package com.cao.youth.repository;

import com.cao.youth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author 曹学习
 * @description UserRepository
 * @date 2020/8/26 17:59
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Optional<User> findByOpenid(String openid);
    User findFirstById(Long id);
    User findByUnifyUid(Long uuid);
}
