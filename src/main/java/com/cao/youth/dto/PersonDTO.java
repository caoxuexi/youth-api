package com.cao.youth.dto;

import com.cao.youth.validators.PasswordEqual;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


/**
 * @author 曹学习
 * @description PersonDTO
 * @date 2020/8/17 23:41
 */
@PasswordEqual
@Getter
@Setter
public class PersonDTO {
    @Length(min = 2,max=10,message = "name长度不对")
    private String name;
    private Integer age;

    private String passwordF;
    private String passwordS;
}
