package com.example.engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Quiz {

    private static int ID_GENERATOR = 1;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Integer id = ID_GENERATOR++;

    private String title;
    private String text;
    private String[] options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int answer;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }
}
