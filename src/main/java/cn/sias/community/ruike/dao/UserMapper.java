package cn.sias.community.ruike.dao;

import cn.sias.community.ruike.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findUserById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    void insertUser(User user);

    void updateStatus(int id, int status);

    int updateHeader(int id, String headerUrl);

    User findUserByName(String username);
}
