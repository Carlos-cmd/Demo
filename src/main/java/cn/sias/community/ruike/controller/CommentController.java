package cn.sias.community.ruike.controller;


import cn.sias.community.ruike.entity.Comment;
import cn.sias.community.ruike.entity.DiscussPost;
import cn.sias.community.ruike.entity.Event;
import cn.sias.community.ruike.event.EventProducer;
import cn.sias.community.ruike.service.CommentService;
import cn.sias.community.ruike.service.DiscussPostService;
import cn.sias.community.ruike.util.CommunityConstant;
import cn.sias.community.ruike.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping("/comment")
public class CommentController implements CommunityConstant {


    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private DiscussPostService discussPostService;

    @RequestMapping(path = "/add/{discussPostId}",method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId")int discussPostId, Comment comment){
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);

        //触发评论事件
        Event event = new Event()
                .setTopic(TOPIC_COMMENT)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(comment.getEntityType())
                .setEntityId(comment.getEntityId())
                .setData("postId",discussPostId);
        if(comment.getEntityType() == ENTITY_TYPE_POST){
            //获取帖子,然后根据帖子获取对应用户id
            DiscussPost target = discussPostService.findDiscussPostById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        } else if(comment.getEntityType()==ENTITY_TYPE_COMMENT){
            Comment target = commentService.selectCommentByid(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }

        eventProducer.fileEvent(event);


        return "redirect:/discuss/detail/"+ discussPostId;
    }
}
