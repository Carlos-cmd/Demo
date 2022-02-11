package cn.sias.community.ruike.service;

import cn.sias.community.ruike.entity.DiscussPost;


import java.util.List;


public interface DiscussPostService {
    List<DiscussPost> selectDisscussPosts(int userId, int offset, int limit);

    int selectDisscussPostRows(int userId);

}
