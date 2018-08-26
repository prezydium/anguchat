package org.prezydium.anguchat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
public class ChatRoomController {

    private final MainChatService mainChatService;

    private final ObjectMapper objectMapper;

    @Autowired
    public ChatRoomController(MainChatService mainChatService, ObjectMapper objectMapper) {
        this.mainChatService = mainChatService;
        this.objectMapper = objectMapper;
    }


    @RequestMapping(path = "chat", method = RequestMethod.GET)
    public ResponseEntity<String> test(HttpServletRequest httpRequest) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(mainChatService.getChatRoom());
        return new ResponseEntity<String>(json, HttpStatus.ACCEPTED);
    }
}
