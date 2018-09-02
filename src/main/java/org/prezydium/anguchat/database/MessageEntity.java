package org.prezydium.anguchat.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nick;

    private String text;

    private String time;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMsgText() {
        return text;
    }

    public void setMsgText(String msgText) {
        this.text = msgText;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MessageEntity() {
    }

    public MessageEntity(String nick, String text, String time) {
        this.nick = nick;
        this.text = text;
        this.time = time;
    }

}
