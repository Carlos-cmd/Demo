package cn.sias.community.ruike.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class AlphaAspect {
    /***
     *              第一个*是所有返回值,什么返回值都行
     * @Pointcut切面点位置
     */
    @Pointcut("execution(* cn.sias.community.ruike.service.*.*.*(..))")
    public void pointCut(){

    }
    /***
     * 连接点一开始就记日志
     *
     * @Before("...")说明是针对这些连接点有效
     */

    //前置通知
    @Before("pointCut()")
    public void before(){
        System.out.println("before");
    }
    //后置通知
    @After("pointCut()")
    public void after(){
        System.out.println("After");
    }
    //方法返回以后执行
    @AfterReturning("pointCut()")
    public void afterReturning(){
        System.out.println("AfterReturning");
    }

    //方法报错以后执行
    @AfterThrowing("pointCut()")
    public void afterThrowing(){
        System.out.println("AfterThrowing");
    }
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        //调用目标方法前的代码
        System.out.println("around before");
        //调用目标组件的方法
        Object object = joinPoint.proceed();
        //调用目标方法之后的代码
        System.out.println("around after");
        return object;
    }



}
