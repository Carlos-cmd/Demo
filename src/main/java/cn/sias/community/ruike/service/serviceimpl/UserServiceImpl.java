package cn.sias.community.ruike.service.serviceimpl;


import cn.sias.community.ruike.dao.UserMapper;
import cn.sias.community.ruike.entity.User;
import cn.sias.community.ruike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }
}
