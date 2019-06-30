package com.sofrecom.sofid.data.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by oibnchahdia on 14/05/2019
 */
public class SecurityQuestion {

    @JsonProperty("question")
    private String question;

    @JsonProperty("answer")
    private String answer;

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
