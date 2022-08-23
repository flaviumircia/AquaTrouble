package com.flaviumircia.aquatrouble.restdata.model;

public class FeedbackModel {
    String sender;
    String phone_model;
    String content;
    String subject;

    public FeedbackModel(){}
    public FeedbackModel(String sender, String phone_model, String content, String subject) {
        this.sender = sender;
        this.phone_model = phone_model;
        this.content = content;
        this.subject = subject;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getPhone_model() {
        return phone_model;
    }

    public void setPhone_model(String phone_model) {
        this.phone_model = phone_model;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
