package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTests {

    @Autowired
    private MailClient mailClient;

    //如果是在controller里面只需要返回模板的路径DispatcherServlet就会自动调用，而测试处需要主动去调用模板引擎
    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail(){
        mailClient.sendMail("lcc661089@163.com", "TEST", "Welcome");
    }

    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username", "LiCongcong");

        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);
        mailClient.sendMail("lcc661089@163.com", "TEST", content);


    }



}
