package com.cao.youth.api.v1;

import com.cao.youth.dto.TokenDTO;
import com.cao.youth.dto.TokenGetDTO;
import com.cao.youth.exception.http.NotFoundException;
import com.cao.youth.service.AuthenticationService;
import com.cao.youth.service.UserService;
import com.cao.youth.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 曹学习
 * @description TokenController
 * @date 2020/8/25 19:29
 */
@RequestMapping(value = "token")
@RestController
public class TokenController {
    @Autowired
    private AuthenticationService AuthenticationService;
    @Autowired
    private UserService userService;

    @PostMapping("")
    public Map<String, String> getToken(@RequestBody @Validated TokenGetDTO userData) {
        Map<String, String> map = new HashMap<>();
        String token = null;
        switch (userData.getType()) {
            case USER_WX:
                token = AuthenticationService.code2Session(userData.getAccount()
                        ,userData.getEncryptedData(),userData.getIv());
                break;
            case USER_Email:
                break;
            default:
                throw new NotFoundException(10003);
        }
        map.put("token", token);

        return map;
    }

    @PostMapping("/verify")
    public Map<String,Boolean> verify(@RequestBody TokenDTO tokenDTO){
        Map<String,Boolean> map=new HashMap<>();
        Boolean valid= JwtToken.verifyToken(tokenDTO.getToken());
        map.put("is_valid",valid);
        return map;
    }
}
