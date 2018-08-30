package org.prezydium.anguchat;

public class Message {

    private String nick;

    private String msgText;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public Message() {
    }

    public Message(String nick, String msgText) {
        this.nick = nick;
        this.msgText = msgText;
    }
}
