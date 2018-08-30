package org.prezydium.anguchat;

public class OutputMessage {

    private String nick;

    private String msgText;

    private String time;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public OutputMessage() {
    }

    public OutputMessage(String nick, String msgText, String time) {
        this.nick = nick;
        this.msgText = msgText;
        this.time = time;
    }

}
