package cn.sias.community.ruike.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Aspect
public class SeiviceLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(SeiviceLogAspect.class);

    @Pointcut("execution(* cn.sias.community.ruike.service.*.*.*(..))")
    public void pointCut(){

    }


    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        //用户[1.2.3.4],在[xxxx],访问了[cn.sias.community.ruike.service.xxx()]
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes==null){
            return;
        }

        HttpServletRequest request = requestAttributes.getRequest();
        //用户
        String ip = request.getRemoteHost();
        //时间
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        //得到类名

        String target = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

            logger.info(String.format("用户[%s],在[%s],访问了[%s].", ip, now, target));

        }



}
