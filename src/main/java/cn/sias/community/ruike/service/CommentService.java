package cn.sias.community.ruike.service;

import cn.sias.community.ruike.entity.Comment;

import java.util.List;

public interface CommentService {


    List<Comment> findCommentByEntity(int entityType,int entityId,int offset,int limit);

    int findCommentCount(int entityType,int entityId);

    //增加评论业务

     int addComment(Comment comment);

    Comment selectCommentByid(int id);
}
