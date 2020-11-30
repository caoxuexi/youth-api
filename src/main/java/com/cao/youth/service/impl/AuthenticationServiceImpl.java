package com.cao.youth.service.impl;

import com.cao.youth.dto.TokenGetDTO;
import com.cao.youth.exception.http.ParameterException;
import com.cao.youth.exception.http.ServerErrorException;
import com.cao.youth.model.User;
import com.cao.youth.repository.UserRepository;
import com.cao.youth.service.AuthenticationService;
import com.cao.youth.service.UserService;
import com.cao.youth.util.AesCbcUtil;
import com.cao.youth.util.JwtToken;
import com.cao.youth.vo.WxUserVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 曹学习
 * @description AutenticationServiceImpl
 * @date 2020/8/26 12:13
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Value("${wx.code2session}")
    private String code2SessionUrl;
    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.appsecret}")
    private String appsecret;

    public String code2Session(String code,String encryptedData,String iv){
        //将配置中的url和真实数据拼接好
        String url = MessageFormat.format(this.code2SessionUrl, this.appid, this.appsecret, code);
        //RestTemplate用于在后端发送http请求
        RestTemplate rest = new RestTemplate();
        Map<String, Object> session = new HashMap<>();
        //第一个为请求的url,第二个参数表明返回的类型
        String sessionText = rest.getForObject(url, String.class);
        try {
            //两种一种是定义一个类，但是为了简便我们这里用Map
            session = mapper.readValue(sessionText, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return this.registerUser(session,encryptedData,iv);
    }

    private String registerUser(Map<String, Object> session,String encryptedData,String iv) {
        String openid = (String)session.get("openid");
        String sessionKey=(String) session.get("session_key");
        if (openid == null){
            throw new ParameterException(20004);
        }
        Optional<User> userOptional = this.userRepository.findByOpenid(openid);
        AtomicReference<String> token=new AtomicReference<>();
        userOptional.ifPresentOrElse(user->{
            // TODO:返回JWT令牌
            // 数字等级
            token.set(JwtToken.makeToken(user.getId()));
        }, ()->{
            Map<String,Object> wxProfileMap=null;
            try {
                // wxProfileAll是包含了openid等我们不需要的字段的，所以我们通过getWxProfileMap去掉不需要的字段
                String wxProfileAll=AesCbcUtil.decrypt(encryptedData,sessionKey,iv,"utf-8");
                wxProfileMap=getWxProfileMap(wxProfileAll);
            }catch (Exception e){
                throw new ServerErrorException(998);
            }
            User user = User.builder()
                    .openid(openid)
                    .scope(1)
                    .wxProfile(wxProfileMap)
                    .build();
            userRepository.save(user);
            // TODO:返回JWT令牌
            Long uid = user.getId();
            token.set(JwtToken.makeToken(uid));
        });
        return token.get();
    }

    private Map<String,Object> getWxProfileMap(String wxProfileAll) throws JsonProcessingException {
        WxUserVo wxUserVo = mapper.readValue(wxProfileAll, WxUserVo.class);
        String wxProfile=mapper.writeValueAsString(wxUserVo);
        return mapper.readValue(wxProfile,HashMap.class);
    }

    @Override
    public void getTokenByEmail(TokenGetDTO userData) { }

    @Override
    public void register() { }

    @Override
    public void validateByWx() {

    }
}
