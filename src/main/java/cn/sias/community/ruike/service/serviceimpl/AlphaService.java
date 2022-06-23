package cn.sias.community.ruike.service.serviceimpl;


import cn.sias.community.ruike.dao.DiscussPostMapper;
import cn.sias.community.ruike.dao.UserMapper;
import cn.sias.community.ruike.entity.DiscussPost;
import cn.sias.community.ruike.entity.User;
import cn.sias.community.ruike.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;

@Service
public class AlphaService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

//    public String find() {
//        alphaMapper.select();
//    }

    /***
     *  // REQUIRED: 支持当前事务(外部事务,调用者),如果不存在则创建新事务.
     *     // REQUIRES_NEW: 创建一个新事务,并且暂停当前事务(外部事务).
     *     // NESTED: 如果当前存在事务(外部事务，调用者),则嵌套在该事务中执行(独立的提交和回滚),否则就会REQUIRED一样.
     * @return
     */
    //注解参数是           隔离级别                          事务传播机制
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public Object savel(){
        //新增用户
        User user = new User();
        user.setUsername("alpha");
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5("123")+user.getSalt());
        user.setEmail("alpha@qq.com");
        user.setHeaderUrl("http://image.nowcoder.com/head/99t.png");
        user.setCreateTime(new Date());
        userMapper.insertUser(user);
        //新增帖子
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle("title");
        post.setContent("新人报道");
        post.setCreateTime(new Date());
        discussPostMapper.insertDiscussPost(post);


        //人为编写错误，让事务进行回滚
        Integer.valueOf("abc");
        return "ok";
    }


    public Object save2(){
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //需要传一个接口
        return transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {

                /***
                 * 事务代码
                 */
                //新增用户
                User user = new User();
                user.setUsername("bbbbb");
                user.setSalt(CommunityUtil.generateUUID().substring(0,5));
                user.setPassword(CommunityUtil.md5("123")+user.getSalt());
                user.setEmail("beta@qq.com");
                user.setHeaderUrl("http://image.nowcoder.com/head/99t.png");
                user.setCreateTime(new Date());
                userMapper.insertUser(user);
                //新增帖子
                DiscussPost post = new DiscussPost();
                post.setUserId(user.getId());
                post.setTitle("title");
                post.setContent("新人报道");
                post.setCreateTime(new Date());
                discussPostMapper.insertDiscussPost(post);


                Integer.valueOf("abc");
                return "ok";
            }
        });
    }

}
