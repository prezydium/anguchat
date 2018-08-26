package org.prezydium.anguchat;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class MainChatService {

    private Map<Long, String> chatRoom = new HashMap<>();

    public Map<Long, String> getChatRoom() {
        return chatRoom;
    }

    public void addMessage(String msg) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String msgWithTime = localDateTime.toLocalTime().toString().concat("   ".concat(msg));
        Long nextId = 0L;
        if (!chatRoom.isEmpty()) {
            nextId = Collections.max(chatRoom.keySet()) + 1L;
        }
        chatRoom.put(nextId, msgWithTime);
    }


}
