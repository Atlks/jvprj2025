package model.resetpwd;

//package com.example.dto;

public class SecurityQuestionResponseDTO {
    private String question;

    public SecurityQuestionResponseDTO() {}

    public SecurityQuestionResponseDTO(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
