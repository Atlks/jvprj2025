package handler.secury;

//package com.example.dto;

public class RstRqResponseDTO {
    private String question;

    public RstRqResponseDTO() {}

    public RstRqResponseDTO(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
