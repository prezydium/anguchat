package org.prezydium.anguchat;

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

    @MessageMapping("/chatsoc")
    @SendTo("/topic/messages")
    public OutputMessage send(Message message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        try {
            if (message.getNick() == null) {
                throw new IllegalArgumentException("Nick cannot be empty");
            }
            if (message.getMsgText() == null) {
                throw new IllegalArgumentException("Message cannot be empty");
            }
        } catch (IllegalArgumentException e){
            logger.error(e.getMessage());
        }
        return new OutputMessage(message.getNick(), message.getMsgText(), time);
    }
}
