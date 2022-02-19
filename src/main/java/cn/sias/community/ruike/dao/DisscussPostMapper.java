package cn.sias.community.ruike.dao;


import cn.sias.community.ruike.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DisscussPostMapper {
    List<DiscussPost> selectDisscussPosts(int userId,int offset,int limit);

    //@Param注解用于给参数取别名，如果只有一个参数，并且在<if>里面使用，则必须加别名
    int selectDisscussPostRows(@Param("userId") int userId);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);

    //当用户评论时要在disscuss_post的comment_count的数+1

    int updateCommentCount(int id,int commentCount);
}
