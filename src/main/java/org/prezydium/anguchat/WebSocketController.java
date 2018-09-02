package org.prezydium.anguchat;

import org.prezydium.anguchat.database.MessageEntity;
import org.prezydium.anguchat.database.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
public class WebSocketController {

    private Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    private final MessageRepository messageRepository;

    public WebSocketController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @MessageMapping("/chatsoc")
    @SendTo("/topic/messages")
    public OutputMessage send(Message message) throws Exception {
        String nick = message.getNick();
        String text = message.getMsgText();
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        try {
            if (nick == null || nick.isEmpty()) {
                throw new IllegalArgumentException("Nick cannot be empty");
            }
            if (text == null || text.isEmpty()) {
                throw new IllegalArgumentException("Message cannot be empty");
            }
        } catch (IllegalArgumentException e){
            logger.error(e.getMessage());
        }
        messageRepository.save(new MessageEntity(nick, text, time));
        return new OutputMessage(nick, text, time);
    }
}
