package cn.sias.community.ruike;

import cn.sias.community.ruike.entity.DiscussPost;
import cn.sias.community.ruike.service.DiscussPostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private DiscussPostService discussPostService;
    @Test
    void discussTest(){
//        List<DiscussPost> discussPosts = discussPostService.selectDisscussPosts(0, 0, 10);
//        int row = discussPostService.selectDisscussPostRows(149);
//        System.out.println(row);

    }

}
