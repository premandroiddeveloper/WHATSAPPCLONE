package com.example.whatsappclone.models;

public class MessgeModel {
    String uid,message,message_id;
    Long timestamp;

    public MessgeModel(){

    }
    public MessgeModel(String uid, String message, Long timestamp) {
        this.uid = uid;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public MessgeModel(String uid, String message) {
        this.uid = uid;
        this.message = message;
    }


    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid(){
        return uid;
    }

}
