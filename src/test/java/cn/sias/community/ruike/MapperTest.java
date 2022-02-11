package cn.sias.community.ruike;

import cn.sias.community.ruike.dao.DisscussPostMapper;
import cn.sias.community.ruike.entity.DiscussPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MapperTest {

    @Autowired
    private DisscussPostMapper disscussPostMapper;
    @Test
    public void testSelectPosts(){
        List<DiscussPost> discussPosts = disscussPostMapper.selectDisscussPosts(0, 0, 10);
//        for (DiscussPost post : discussPosts) {
//            System.out.println(post);
//        }
        int rows = disscussPostMapper.selectDisscussPostRows(149);
        System.out.println(rows);
    }
}
