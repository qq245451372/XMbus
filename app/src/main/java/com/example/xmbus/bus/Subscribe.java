package com.example.xmbus.bus;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//注解
@Target(ElementType.METHOD)  //作用于方法
@Retention(RetentionPolicy.RUNTIME)  //运行期
public @interface Subscribe {
    ThreadMode value() default ThreadMode.POSITION;
}
