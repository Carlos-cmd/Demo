package cn.sias.community.ruike.controller.interceptor;

import cn.sias.community.ruike.annotation.LoginRequired;
import cn.sias.community.ruike.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Override
    //                                                                                 这个参数是拦截的目标
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否拦截的是否为方法
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取method对象
            Method method = handlerMethod.getMethod();
            //在method对象里面，获取LoginRequired注解
            LoginRequired annotation = method.getAnnotation(LoginRequired.class);
            //annotation不为空说明是@LoginRequired注解的方法，需要拦截
            if(annotation!=null && hostHolder.getUser()==null){
                response.sendRedirect(request.getContextPath()+"/login");
                return false;
            }

        }

        return true;
    }
}
