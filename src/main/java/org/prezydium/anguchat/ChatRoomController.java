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

import javax.servlet.http.HttpServletRequest;

@RestController
public class ChatRoomController {

    private final MainChatService mainChatService;

    private final ObjectMapper objectMapper;

    @Autowired
    public ChatRoomController(MainChatService mainChatService, ObjectMapper objectMapper) {
        this.mainChatService = mainChatService;
        this.objectMapper = objectMapper;
    }


    @RequestMapping(path = "chat", method = RequestMethod.POST)
    public ResponseEntity<String> returnChat(@RequestBody JsonNode request) throws JsonProcessingException {
        Long lastID = request.get("lastId").asLong(-1);
        Long test1 = mainChatService.actualNewestID();
        return  (mainChatService.actualNewestID() > lastID)
                ? new ResponseEntity<String>(objectMapper.writeValueAsString(mainChatService.getChatRoom()) , HttpStatus.OK)
                        : new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
