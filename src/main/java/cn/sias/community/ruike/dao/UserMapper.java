package cn.sias.community.ruike.dao;

import cn.sias.community.ruike.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findUserById(int id);
}
