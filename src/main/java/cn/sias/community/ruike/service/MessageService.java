package cn.sias.community.ruike.service;

import cn.sias.community.ruike.entity.Message;

import java.util.List;

public interface MessageService {

    List<Message> findConversations(int userId, int offset, int limit);

    int findConversationCount(int userId);

    public List<Message> findLetters(String conversationId, int offset, int limit);

    int findLetterCount(String conversationId);
    int findLetterUnreadCount(int userId, String conversationId);

    int addMessage(Message message);

    int readMessage(List<Integer> ids);
}
