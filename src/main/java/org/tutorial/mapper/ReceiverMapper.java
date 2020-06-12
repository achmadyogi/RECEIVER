package org.tutorial.mapper;

import org.apache.ibatis.annotations.Insert;
import org.tutorial.model.Message;

public interface ReceiverMapper {
    final String addMessage = "INSERT INTO message (message) VALUES ( #{message} )";

    @Insert(addMessage)
    void addMessage(Message message);
}
