package cn.sias.community.ruike.service;

import cn.sias.community.ruike.entity.DiscussPost;


import java.util.List;


public interface DiscussPostService {
    List<DiscussPost> selectDisscussPosts(int userId, int offset, int limit);

    int selectDisscussPostRows(int userId);

    int addDiscussPost(DiscussPost post);

    DiscussPost findDiscussPostById(int id);

    public int updateCommentCount(int id,int commentCount);

}
