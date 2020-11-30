package com.cao.youth.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author 曹学习
 * @description School
 * @date 2020/8/18 18:21
 */
@Getter
@Setter
public class SchoolDTO {
    @Length(min=2)
    private String schoolName;
}
