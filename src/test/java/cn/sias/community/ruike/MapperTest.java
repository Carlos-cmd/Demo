package cn.sias.community.ruike;

import cn.sias.community.ruike.dao.DiscussPostMapper;
import cn.sias.community.ruike.dao.LoginTicketMapper;
import cn.sias.community.ruike.entity.DiscussPost;
import cn.sias.community.ruike.entity.LoginTicket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class MapperTest {

    @Autowired
    private LoginTicketMapper loginTicketMapper;


    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Test
    public void testSelectPosts(){
        List<DiscussPost> discussPosts = discussPostMapper.selectDisscussPosts(0, 0, 10);
//        for (DiscussPost post : discussPosts) {
//            System.out.println(post);
//        }
        int rows = discussPostMapper.selectDisscussPostRows(149);
        System.out.println(rows);
    }

    @Test
    public void testInsertLoginTicket(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis()+1000*60*10));
        loginTicket.setUserId(101);
        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testSelectLoginTicket(){
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);
        loginTicketMapper.updateStatus("abc",1);
        LoginTicket loginTicket2 = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket2);
    }
}
