package cn.sias.community.ruike.controller.interceptor;

import cn.sias.community.ruike.entity.LoginTicket;
import cn.sias.community.ruike.entity.User;
import cn.sias.community.ruike.service.UserService;
import cn.sias.community.ruike.util.CookieUtil;
import cn.sias.community.ruike.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    /***
     * 每次请求之前就检查凭证，有效凭证就把用户查询出来存储到线程map里面
     *
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从cookie中获取凭证
        String ticket = CookieUtil.getValue(request, "ticket");
        if (ticket != null) {
            //查询凭证
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            //检查凭证是否有效                                                  超时时间晚于当前时间就有效
            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                //根据凭证查询用户
                User user = userService.findUserById(loginTicket.getUserId());
                //在本次请求当中持有用户
                hostHolder.setUsers(user);
            }
        }
        return true;
    }

    /***
     *      把获取的user存储到model里面，让模板进行渲染
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if(user!=null&&modelAndView!=null){
            modelAndView.addObject("loginUser",user);
        }
    }


    /***
     *  模板渲染完之后，就把存储在线程map里的user给清掉
     */

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
