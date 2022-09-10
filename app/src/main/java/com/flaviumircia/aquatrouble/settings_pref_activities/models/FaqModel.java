package com.flaviumircia.aquatrouble.settings_pref_activities.models;

public class FaqModel {
    private String question_title;
    private String question_content;

    public FaqModel() {
    }

    public FaqModel(String question_title, String question_content) {
        this.question_title = question_title;
        this.question_content = question_content;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

}
