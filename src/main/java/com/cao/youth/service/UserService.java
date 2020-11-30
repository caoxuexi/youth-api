package com.cao.youth.service;

import com.cao.youth.model.User;

import java.util.Map;

/**
 * @author 曹学习
 * @description UserService
 * @date 2020/8/29 23:38
 */
public interface UserService {
    User getUserById(Long id);
    User createDevUser(Long uid);
    User getUserByUnifyUid(Long uuid);
    void updateUserWxInfo(Map<String, Object> wxUser);
}
