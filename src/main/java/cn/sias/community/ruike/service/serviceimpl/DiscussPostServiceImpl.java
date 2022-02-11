package cn.sias.community.ruike.service.serviceimpl;

import cn.sias.community.ruike.dao.DisscussPostMapper;
import cn.sias.community.ruike.entity.DiscussPost;
import cn.sias.community.ruike.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostServiceImpl implements DiscussPostService {

    @Autowired
    private DisscussPostMapper disscussPostMapper;
    @Override
    public List<DiscussPost> selectDisscussPosts(int userId, int offset, int limit) {
        return disscussPostMapper.selectDisscussPosts(userId, offset, limit);
    }

    @Override
    public int selectDisscussPostRows(int userId) {
        return disscussPostMapper.selectDisscussPostRows(userId);
    }
}
