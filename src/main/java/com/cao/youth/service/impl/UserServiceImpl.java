
package com.cao.youth.service.impl;

import com.cao.youth.core.LocalUser;
import com.cao.youth.model.User;
import com.cao.youth.repository.UserRepository;
import com.cao.youth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 曹学习
 * @description SearchController
 * @date 2020/8/29 23:37
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findFirstById(id);
    }


    public void updateUserWxInfo(Map<String, Object> wxUser) {
        User user =this.getUserById(LocalUser.getUser().getId());
        user.setNickname(wxUser.get("nickName").toString());
        user.setWxProfile(wxUser);
        userRepository.save(user);
    }

    public User createDevUser(Long uid) {
        User newUser = User.builder().unifyUid(uid).build();
        userRepository.save(newUser);
        return newUser;
    }

    public User getUserByUnifyUid(Long uuid) {
        return userRepository.findByUnifyUid(uuid);
    }
}

