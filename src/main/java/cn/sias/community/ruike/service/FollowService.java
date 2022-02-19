package cn.sias.community.ruike.service;

import java.util.List;
import java.util.Map;

public interface FollowService {
    void follow(int userId, int entityType, int entityId);

    void unFollow(int userId, int entityType, int entityId);
    long findFollowerCount(int entityType,int entityId);
    boolean hasFollowed(int userId,int entityType,int entityId);
    long findFolloweeCount(int userId,int entityType);
    List<Map<String,Object>> findFollowers(int userId, int offset, int limit);
    List<Map<String,Object>> findFollowees(int userId,int offset ,int limit);
}
