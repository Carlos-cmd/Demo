package cn.sias.community.ruike.service.serviceimpl;

import cn.sias.community.ruike.service.LikeService;
import cn.sias.community.ruike.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;


@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private RedisTemplate redisTemplate;


    /***
     * 点赞
     * @param userId 发起点赞的人
     * @param entityType 点赞目标的类型  1---帖子  2---评论
     * @param entityId  点赞目标的id  不是msql表里存的entity_id
     * @param entityUserId 作者的id 用于增加被点赞数
     */
    @Override
    public void like(int userId, int entityType, int entityId, int entityUserId) {
//        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
//        //判断userId在不在集合里面,也就是说看这个用户是否点过赞了
//        Boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
//        if(isMember){
//            //取消赞
//            redisTemplate.opsForSet().remove(entityLikeKey,userId);
//        }else{
//            //添加赞
//            redisTemplate.opsForSet().add(entityLikeKey,userId);
//        }
//
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {

                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
                //点赞之后,是在被点赞的user里被赞数据+1,因此是entityid包含主题用户的id,也就是作者id
                String userLikeKey = RedisKeyUtil.getUserLikeKey(entityUserId);
                //看当前登录用户是否已经点过赞了,redis查询代码要写在事务外,因为在事务内的查询都不会立即返回值
                boolean isMember = operations.opsForSet().isMember(entityLikeKey, userId);
                //开启事务
                operations.multi();
                if (isMember) {
                    //减少当前评论或者帖子的赞
                    operations.opsForSet().remove(entityLikeKey, userId);
                    //减少作者或者评论者收到的赞
                    operations.opsForValue().decrement(userLikeKey);
                } else {
                    //增加当前评论或者帖子的赞
                    operations.opsForSet().add(entityLikeKey, userId);
                    //增加作者或者评论者收到的赞
                    operations.opsForValue().increment(userLikeKey);
                }
                return operations.exec();
            }
        });

    }

    /**
     * 查询实体点赞的数量
     */
    @Override
    public long findEntityLikeCount(int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    /**
     * 查询实体点赞的状态----某人对某实体的点赞状态(已赞,未赞)
     */
    @Override
    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        //判断userId在不在集合里面,也就是说看这个用户是否点过赞了
        /**
         *                  1--已赞    2--未赞
         */
        int i = redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
        return i;
    }

    /***
     * 查询某个用户获得的赞数
     * @param entityUserId ---作者id,不是当前登录用户id
     * @return
     */
    @Override
    public int findUserLikeCount(int entityUserId) {
        String userLikeKey = RedisKeyUtil.getUserLikeKey(entityUserId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
        return count==null?0:count.intValue();
    }


}
