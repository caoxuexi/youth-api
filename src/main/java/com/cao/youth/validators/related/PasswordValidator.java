package com.cao.youth.validators.related;

import com.cao.youth.dto.PersonDTO;
import com.cao.youth.validators.PasswordEqual;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 曹学习
 * @description PasswordValidator
 * @date 2020/8/18 18:41
 */
public class PasswordValidator implements ConstraintValidator<PasswordEqual, PersonDTO> {
    //第一个参数:自定义的注解 第二个参数:自定义注解修饰的目标类型(我们注解里表示的打在Type上)
    //如果我们在@Target里面加了{ElementType.FIELD}表明可以加在字段上面，这样第二个就要传入field的类型
    private int min;
    private int max;

    @Override
    public void initialize(PasswordEqual constraintAnnotation) {
        this.min=constraintAnnotation.min();
        this.max=constraintAnnotation.max();
    }

    @Override
    public boolean isValid(PersonDTO personDTO, ConstraintValidatorContext constraintValidatorContext) {
        String password1=personDTO.getPasswordF();
        String password2=personDTO.getPasswordS();
        if(password1==null&&password2==null){
            return false;
        }
        assert password1 != null;
        boolean match=password1.equals(password2);
        if(password1.length()<min||password1.length()>max){
            match=false;
        }
        return password1.equals(password2);
    }
}
