package com.cao.youth.service;

import com.cao.youth.dto.TokenGetDTO;

/**
 * @author 曹学习
 * @description AutenticationService
 * @date 2020/8/26 12:10
 */
public interface AuthenticationService {
    //一下两个接口给我们以后拓展邮箱登录，手机号登录用，因为我们用的小程序，暂时用不到
    void getTokenByEmail(TokenGetDTO userData);
    void register();
    void validateByWx();

    String code2Session(String account,String encryptedData,String iv);
}
