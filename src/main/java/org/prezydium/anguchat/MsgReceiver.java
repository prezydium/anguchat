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

    @RequestMapping(path = "test", method = RequestMethod.POST)
    public ResponseEntity<String> test(HttpServletRequest httpRequest) {
        System.out.println(httpRequest);
        return new ResponseEntity<>("{ \"records\":[ {\"Name\":\"Alfreds Futterkiste\",\"City\":\"Berlin\",\"Country\":\"Germany\"}, {\"Name\":\"Ana Trujillo Emparedados y helados\",\"City\":\"México D.F.\",\"Country\":\"Mexico\"}, {\"Name\":\"Antonio Moreno Taquería\",\"City\":\"México D.F.\",\"Country\":\"Mexico\"}, {\"Name\":\"Around the Horn\",\"City\":\"London\",\"Country\":\"UK\"}, {\"Name\":\"B's Beverages\",\"City\":\"London\",\"Country\":\"UK\"}, {\"Name\":\"Berglunds snabbköp\",\"City\":\"Luleå\",\"Country\":\"Sweden\"}, {\"Name\":\"Blauer See Delikatessen\",\"City\":\"Mannheim\",\"Country\":\"Germany\"}, {\"Name\":\"Blondel père et fils\",\"City\":\"Strasbourg\",\"Country\":\"France\"}, {\"Name\":\"Bólido Comidas preparadas\",\"City\":\"Madrid\",\"Country\":\"Spain\"}, {\"Name\":\"Bon app'\",\"City\":\"Marseille\",\"Country\":\"France\"}, {\"Name\":\"Bottom-Dollar Marketse\",\"City\":\"Tsawassen\",\"Country\":\"Canada\"}, {\"Name\":\"Cactus Comidas para llevar\",\"City\":\"Buenos Aires\",\"Country\":\"Argentina\"}, {\"Name\":\"Centro comercial Moctezuma\",\"City\":\"México D.F.\",\"Country\":\"Mexico\"}, {\"Name\":\"Chop-suey Chinese\",\"City\":\"Bern\",\"Country\":\"Switzerland\"}, {\"Name\":\"Comércio Mineiro\",\"City\":\"São Paulo\",\"Country\":\"Brazil\"} ] }", HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "message", method = RequestMethod.POST)
    public ResponseEntity<String> addMessage(@RequestBody JsonNode incomingMsg) throws IOException, ServletException {
        if (incomingMsg.get("msg") == null){
            return new ResponseEntity<>("Error - empty msg", HttpStatus.BAD_REQUEST);
        }
        String message = incomingMsg.get("msg").asText("");
        System.out.println(message);
        mainChatService.addMessage(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
