package cn.sias.community.ruike.service.serviceimpl;

import cn.sias.community.ruike.dao.CommentMapper;
import cn.sias.community.ruike.entity.Comment;
import cn.sias.community.ruike.service.CommentService;
import cn.sias.community.ruike.service.DiscussPostService;
import cn.sias.community.ruike.util.CommunityConstant;
import cn.sias.community.ruike.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;


@Service
public class CommentServiceImpl implements CommentService , CommunityConstant {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private DiscussPostService discussPostService;

    @Override
    public List<Comment> findCommentByEntity(int entityType, int entityId, int offset, int limit) {
        return commentMapper.selectCommentByEntity(entityType,entityId,offset,limit);
    }

    @Override
    public int findCommentCount(int entityType, int entityId) {
        return commentMapper.selectCountByEntity(entityType,entityId);
    }



    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    @Override
    public int addComment(Comment comment) {
        if(comment==null){
            throw  new IllegalArgumentException("参数不能为空！");
        }

        /***
         * 添加评论
         */

        //先过滤html标签，内容再用comment接收
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        //然后过滤敏感词，再用comment接收
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        //插入成功的行数，作为参数传入到更新评论总数方法
        int rows = commentMapper.insertComment(comment);

        /***
         * 更新帖子评论数量
         */
        //判断是否是帖子的评论
        if(comment.getEntityType() == ENTITY_TYPE_POST){
            int count = commentMapper.selectCountByEntity(comment.getEntityType(),comment.getEntityId());
            discussPostService.updateCommentCount(comment.getEntityId(),count);

        }



        return rows;
    }

    @Override
    public Comment selectCommentByid(int id) {
        return commentMapper.selectCommentByid(id);
    }
}
