package cn.sias.community.ruike.controller;

import cn.sias.community.ruike.entity.DiscussPost;
import cn.sias.community.ruike.entity.Page;
import cn.sias.community.ruike.entity.User;
import cn.sias.community.ruike.service.DiscussPostService;
import cn.sias.community.ruike.service.LikeService;
import cn.sias.community.ruike.service.UserService;
import cn.sias.community.ruike.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController implements CommunityConstant {
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;


    @RequestMapping(path = "/index",method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page){
        //方法调用之前，SpringMVC会自动实例化Model和Page，并且将Page注入Model
        //所以在Thymeleaf中可以直接访问Page对象中的数据，不需要最后addAttribute
        page.setRows(discussPostService.selectDisscussPostRows(0));
        page.setPath("/index");


        List<DiscussPost> discussPosts = discussPostService.selectDisscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String,Object>> list = new ArrayList<>();
        for (DiscussPost post : discussPosts) {
            Map<String,Object> map = new HashMap<>();
            map.put("post",post);
            User user = userService.findUserById(post.getUserId());
            map.put("user",user);

            //查询帖子的赞数
            long likeConunt = likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId());
            map.put("likeCount",likeConunt);

            list.add(map);
        }
        model.addAttribute("discussPosts",list);
        return "index";
    }

    @RequestMapping(path = "/error",method = RequestMethod.GET)
    public String getErrorPage() {

        return "/error/500";
    }


}
