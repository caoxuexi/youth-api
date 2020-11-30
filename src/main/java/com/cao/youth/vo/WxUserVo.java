package com.cao.youth.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 曹学习
 * @description WxUserVo
 * @date 2020/8/30 23:38
 */
@Setter
@Getter
public class WxUserVo {
    @JsonProperty("nickName")
    String nickName;
    Integer gender;
    String language;
    String city;
    String province;
    String country;
    @JsonProperty("avatarUrl")
    String avatarUrl;
}
