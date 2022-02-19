package cn.sias.community.ruike.util;

public class RedisKeyUtil {

    private static final String SPLIT = ":";

    private static final String PREFIX_ENTITY_LIKE = "like:entity";

    private static final String PREFIX_USER_LIKE = "like:user";

    //被关注者
    private static final String PREFIX_FOLLOWEE = "followee";


    //粉丝
    private static final String PREFIX_FOLLOWER = "follower";

    //验证码前缀
    private static final String PREFIX_KAPTCHA = "paptcha";

    //登录凭证前缀

    private static final String PREFIX_TICKET = "ticket";



    private static final String PREFIX_USER = "user";

    //某个实体的赞
    //like:entity:entityType:entityId-->set
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    /**
     * 统计某个用户的赞数
     * like:user:userId -> int
     */

    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }


    //某个用户关注的实体   那要是相同用户关注了帖子和评论呢?如何去计数
    //followee:userId:entityType --> zset(entityId,now)
    public static String  getFolloweeKey(int userId, int entityType) {
        return PREFIX_FOLLOWEE + SPLIT + userId + SPLIT + entityType;

    }
    //某个实体拥有的粉丝
    //follower:entityType:entityId -->zset(userId,now)
    public static String getFollowerKey(int entityType, int entityId) {
        return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;

    }

    //登录验证码
    public static String getKaptchaKey(String owner){
        return PREFIX_KAPTCHA+SPLIT+owner;
    }

    //登录凭证
    public static String getTicketKey(String ticket){
        return PREFIX_TICKET+SPLIT+ticket;
    }


    //用户
    public static String getUserKey(int userId){
        return PREFIX_USER+SPLIT+userId;
    }

}
