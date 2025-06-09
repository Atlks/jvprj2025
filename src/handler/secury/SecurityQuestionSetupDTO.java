package handler.secury;

//package com.example.dto;

import lombok.Data;
import util.annos.CurrentUsername;
@Data
public class SecurityQuestionSetupDTO {
    @CurrentUsername
    public String userId;
    public String question;
    public String answer;

    // getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
