package com.cao.youth.validators;

import com.cao.youth.validators.related.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author 曹学习
 * @description PasswordEqual
 * @date 2020/8/18 18:35
 */
@Documented//使得注释能够被加入到文档里面
@Retention(RetentionPolicy.RUNTIME)//注解被保留到什么阶段
@Target({ElementType.TYPE})//指定这个注解可以用在哪些上面
@Constraint(validatedBy = PasswordValidator.class)//这个是可以传入数组，指定多个校验类的
public @interface PasswordEqual {
    int min() default 6;
    int max() default 16;
    String message() default "password are not equal";

    //规范里要求要有这两个东西
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    //关联类
}
