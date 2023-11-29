package com.onogawean.sun.model;

public class Chat {
    public static String SENT_BY_ME = "me";
    public static  String SENT_BY_BOT = "bot";
    String chat;
    String sentBy;

    public String getMessage(){
        return chat;
    }
    public void setMessage(String message){
        this.chat = message;
    }
    public String getSentBy(){
        return sentBy;
    }
    public void setSentBy(String sentBy){
        this.sentBy = sentBy;
    }
    public Chat(String chat,String sentBy){
        this.chat = chat;
        this.sentBy = sentBy;
    }
}
