package cn.sias.community.ruike.service;

import cn.sias.community.ruike.entity.Message;

import java.util.List;

public interface MessageService {

    List<Message> findConversations(int userId, int offset, int limit);

    int findConversationCount(int userId);

     List<Message> findLetters(String conversationId, int offset, int limit);

    int findLetterCount(String conversationId);
    int findLetterUnreadCount(int userId, String conversationId);

    int addMessage(Message message);

    int readMessage(List<Integer> ids);

    //查询某个主题下最新的通知

    Message selectLatestNotice(int userId,String topic);

    //查询某个主题所包含通知的数量

    int selectNoticeCount(int userId,String topic);

    //查询未读的通知的数量
    int selectNoticeUnReadCount(int userId,String topic);

    List<Message> findNotices(int userId,String topic,int offset,int limit);

}
