package cn.sias.community.ruike.service.serviceimpl;

import cn.sias.community.ruike.dao.DisscussPostMapper;
import cn.sias.community.ruike.entity.DiscussPost;
import cn.sias.community.ruike.service.DiscussPostService;
import cn.sias.community.ruike.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class DiscussPostServiceImpl implements DiscussPostService {

    @Autowired
    private DisscussPostMapper disscussPostMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Override
    public List<DiscussPost> selectDisscussPosts(int userId, int offset, int limit) {
        return disscussPostMapper.selectDisscussPosts(userId, offset, limit);
    }

    @Override
    public int selectDisscussPostRows(int userId) {
        return disscussPostMapper.selectDisscussPostRows(userId);
    }

    @Override
    public int addDiscussPost(DiscussPost post) {
        if(post == null){
            throw new IllegalArgumentException("参数不能为空");
        }
        //转移HTML标记
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));

        //过滤敏感词

        post.setTitle(sensitiveFilter.filter(post.getTitle()));
        post.setContent(sensitiveFilter.filter(post.getContent()));



        return disscussPostMapper.insertDiscussPost(post);
    }

    @Override
    public DiscussPost findDiscussPostById(int id) {
        return disscussPostMapper.selectDiscussPostById(id);
    }

    @Override
    public int updateCommentCount(int id, int commentCount) {
        return disscussPostMapper.updateCommentCount(id,commentCount);
    }
}
