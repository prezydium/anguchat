package org.prezydium.anguchat;

import com.fasterxml.jackson.databind.JsonNode;
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

@RestController
public class MsgReceiver {

    private final MainChatService mainChatService;

    @Autowired
    public MsgReceiver(MainChatService mainChatService) {
        this.mainChatService = mainChatService;
    }

    @RequestMapping(path = "message", method = RequestMethod.POST)
    public ResponseEntity<String> addMessage(@RequestBody JsonNode incomingMsg) throws IOException, ServletException {
        if (incomingMsg.get("msg") == null){
            return new ResponseEntity<>("Error - empty msg", HttpStatus.BAD_REQUEST);
        }
        if (incomingMsg.get("nick") == null) {
            return new ResponseEntity<>("Error - empty nick", HttpStatus.BAD_REQUEST);
        }
        String message = incomingMsg.get("msg").asText("");
        String nick = incomingMsg.get("nick").asText("");
        mainChatService.addMessage(nick + ": " + message);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
