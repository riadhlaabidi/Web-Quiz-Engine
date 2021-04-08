package com.example.engine;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Quiz {

    private String title;
    private String text;
    private String[] options;

    @JsonIgnore
    private int correctAnswer;

    public Quiz(String title, String text, String[] options, int correctAnswer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

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

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
