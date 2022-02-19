package cn.sias.community.ruike;


import cn.sias.community.ruike.util.MailClient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class MailTest {

    @Autowired
    private MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;
        @Value("${spring.mail.username}")
        private String from;
    @Test
    public  void testMail(){
        mailClient.sendMail("2541318891@qq.com","Test","ahahahahahhahahahaha");

    }

    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username","zhengjignyu");
        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);
        mailClient.sendMail("2541318891@qq.com","HTML",content);
    }
}
