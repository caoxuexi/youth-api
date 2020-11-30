package com.cao.youth.optional;

import net.minidev.json.JSONUtil;
import org.junit.Test;
import org.w3c.dom.ls.LSOutput;

import java.util.Optional;

/**
 * @author 曹学习
 * @description OptionalTest
 * @date 2020/8/25 12:15
 */
public class OptionalTest {
    @Test
    public void testOptional(){
        Optional<String> t1=Optional.ofNullable("a");
        //.map出来的对象仍然是一个Optional对象
        t1.ifPresentOrElse(t -> System.out.println("有值"+t), new Runnable() {
            @Override
            public void run() {
                System.out.println("没有值");
            }
        });
        t1.filter(t-> t.equals("a"));
        System.out.println(t1.get());
//        String s=t1.map(t->t+"b").orElse("c");
//        System.out.println("Optional内现在的值"+s);
    }
}
