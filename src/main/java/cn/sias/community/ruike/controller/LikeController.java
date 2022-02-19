package cn.sias.community.ruike.controller;


import cn.sias.community.ruike.entity.Event;
import cn.sias.community.ruike.entity.User;
import cn.sias.community.ruike.event.EventProducer;
import cn.sias.community.ruike.service.LikeService;
import cn.sias.community.ruike.util.CommunityConstant;
import cn.sias.community.ruike.util.CommunityUtil;
import cn.sias.community.ruike.util.HostHolder;
import org.apache.kafka.common.internals.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController implements CommunityConstant {

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    /***
     *
     * @param entityType 点赞目标的类型  1---帖子  2---评论
     * @param entityId  点赞目标的id  不是msql表里存的entity_id
     * @param entityUserId 作者的id 用于增加被点赞数
     * @return
     */
    @RequestMapping(path = "/like",method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType,int entityId,int entityUserId,int postId){
        User user = hostHolder.getUser();

        //点赞
        likeService.like(user.getId(), entityType,entityId,entityUserId);
        //点赞数量和状态
        long likeCount = likeService.findEntityLikeCount(entityType,entityId);
        int likeStatus = likeService.findEntityLikeStatus(user.getId(), entityType,entityId);
        //返回的结果用map封装
        Map<String,Object> map = new HashMap<>();
        map.put("likeCount",likeCount);
        map.put("likeStatus",likeStatus);

        //触发点赞事件
        if(likeStatus==1){
            Event event = new Event()
                    .setTopic(TOPIC_LIKE)
                    .setUserId(hostHolder.getUser().getId())
                    .setEntityType(entityType)
                    .setEntityId(entityId)
                    .setEntityUserId(entityUserId)
                    .setData("postId",postId);
            eventProducer.fileEvent(event);
        }





        return CommunityUtil.getJSONString(0,null,map);

    }

}
