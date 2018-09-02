package org.prezydium.anguchat;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.prezydium.anguchat.database.MessageEntity;
import org.prezydium.anguchat.database.MessageRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HistoryController {

    private final MessageRepository messageRepository;
    private final ObjectMapper objectMapper;

    public HistoryController(MessageRepository messageRepository, ObjectMapper objectMapper) {
        this.messageRepository = messageRepository;
        this.objectMapper = objectMapper;
    }

    @RequestMapping(path = "history", method = RequestMethod.GET)
    public String getHistory() throws JsonProcessingException {
        List<MessageEntity> listOfMessages = messageRepository.findAll();
        List<OutputMessage> listOfOutputMessages = new ArrayList<>();
        listOfMessages.forEach( x -> listOfOutputMessages.add(new OutputMessage(x.getNick(), x.getMsgText(), x.getTime())));
        return objectMapper.writeValueAsString(listOfOutputMessages);
    }
}
