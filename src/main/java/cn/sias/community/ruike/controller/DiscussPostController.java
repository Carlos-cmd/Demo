package cn.sias.community.ruike.controller;


import cn.sias.community.ruike.entity.Comment;
import cn.sias.community.ruike.entity.DiscussPost;
import cn.sias.community.ruike.entity.Page;
import cn.sias.community.ruike.entity.User;
import cn.sias.community.ruike.service.CommentService;
import cn.sias.community.ruike.service.DiscussPostService;
import cn.sias.community.ruike.service.LikeService;
import cn.sias.community.ruike.service.UserService;
import cn.sias.community.ruike.util.CommunityConstant;
import cn.sias.community.ruike.util.CommunityUtil;
import cn.sias.community.ruike.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.event.ObjectChangeListener;
import java.util.*;

import static cn.sias.community.ruike.util.Constans.AUDIT_STATUS_PASS;

@Controller
@RequestMapping("/discuss")
public class DiscussPostController implements CommunityConstant {
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;


    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title, String content) {
        User user = hostHolder.getUser();
        if (user == null) {
            return CommunityUtil.getJSONString(403, "你还没有登录");
        }

        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());
        discussPostService.addDiscussPost(post);


        //报错的情况，将来统一处理
        return CommunityUtil.getJSONString(0, "发布成功");

    }


    @RequestMapping(path = "/detail/{discussPostId}", method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable("discussPostId") int discussPostId, Model model, Page page) {
        //查询帖子
        DiscussPost post = discussPostService.findDiscussPostById(discussPostId);
        model.addAttribute("post", post);
        //作者
        User user = userService.findUserById(post.getUserId());
        model.addAttribute("user", user);
        //查询帖子赞数
        long postLikeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId());
        model.addAttribute("postLikeCount", postLikeCount);
        //点赞状态  已赞 未赞
        int postLikeCountStatus = hostHolder.getUser() == null ? 0 : likeService.findEntityLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_POST, post.getId());
        model.addAttribute("postLikeCountStatus", postLikeCountStatus);
        //评论的分页信息
        page.setLimit(5);
        page.setPath("/discuss/detail/" + discussPostId);
        page.setRows(post.getCommentCount());


        //评论：给帖子的评论
        //回复：给评论的评论
        //评论列表
        List<Comment> commentList = commentService.findCommentByEntity(ENTITY_TYPE_POST, post.getId(), page.getOffset(), page.getLimit());
        //页面显示的对象集合 VO列表
        List<Map<String, Object>> commentVoList = new ArrayList<>();
        if (commentList != null) {
            for (Comment comment : commentList) {
                // 一个评论的VO
                Map<String, Object> commentVo = new HashMap<>();
                //评论
                commentVo.put("comment", comment);
                //作者
                commentVo.put("user", userService.findUserById(comment.getUserId()));
                //赞数
                long commentLikeCount = likeService.findEntityLikeCount(ENTITY_TYPE_COMMENT, comment.getId());
                commentVo.put("commentLikeCount", commentLikeCount);
                //点赞状态  已赞 未赞
                int commentLikeCountStatus = hostHolder.getUser() == null ? 0 : likeService.findEntityLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_COMMENT, comment.getId());
                commentVo.put("commentLikeCountStatus", commentLikeCountStatus);
                //回复列表
                List<Comment> replyList = commentService.findCommentByEntity(
                        ENTITY_TYPE_COMMENT, comment.getId(), 0, Integer.MAX_VALUE
                );
                //回复的VO列表
                List<Map<String, Object>> replyVoList = new ArrayList<>();
                if (replyList != null) {
                    for (Comment reply : replyList) {
                        Map<String, Object> replyVo = new HashMap<>();
                        //添加回复实体
                        replyVo.put("reply", reply);
                        //添加回复用户实体
                        replyVo.put("user", userService.findUserById(reply.getUserId()));
                        //添加赞数
                        long replyLikeCount = likeService.findEntityLikeCount(ENTITY_TYPE_COMMENT, reply.getId());
                        replyVo.put("replyLikeCount", replyLikeCount);
                        //点赞状态  已赞 未赞
                        int replyLikeCountStatus = hostHolder.getUser() == null ? 0 : likeService.findEntityLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_COMMENT, reply.getId());
                        replyVo.put("replyLikeCountStatus", replyLikeCountStatus);
                        //回复的目标
                        User target = reply.getTargetId() == 0 ? null : userService.findUserById(reply.getTargetId());
                        replyVo.put("target", target);
                        replyVoList.add(replyVo);

                    }
                }
                commentVo.put("replys", replyVoList);
                //回复数量
                int replyCounts = commentService.findCommentCount(ENTITY_TYPE_COMMENT, comment.getId());
                commentVo.put("replyCount", replyCounts);
                commentVoList.add(commentVo);
            }
        }

        model.addAttribute("comments", commentVoList);
        return "site/discuss-detail";
    }
}
