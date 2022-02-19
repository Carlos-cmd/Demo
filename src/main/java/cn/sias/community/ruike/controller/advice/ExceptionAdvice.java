package cn.sias.community.ruike.controller.advice;


import cn.sias.community.ruike.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/***
 * 只去扫描带有@Controller的Bean
 * @author Administrator
 */
@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);
    /***
     * 处理所有异常的方法
     */
    @ExceptionHandler({Exception.class})
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("服务器发生异常"+e.getMessage());
        //异常详细信息
        for(StackTraceElement element :e.getStackTrace()){
            logger.error(element.toString());
        }
        String xRequestedWith = request.getHeader("x-requested-with");
//                                 ↓相等说明是异步请求↓
        if("XMLHttpRequest".equals(xRequestedWith)){
//                                  响应数据为文本格式↓
            response.setContentType("application/plain:charset=utf-8");

            PrintWriter writer = response.getWriter();
            writer.write(CommunityUtil.getJSONString(1,"服务器异常"));
        }else{
            response.sendRedirect(request.getContextPath()+"/error");
        }
    }
}
