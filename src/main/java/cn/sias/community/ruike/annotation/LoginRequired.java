package cn.sias.community.ruike.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//声明次注解定义在方法上
@Target(ElementType.METHOD)
//程序运行时注解有效
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {

}
