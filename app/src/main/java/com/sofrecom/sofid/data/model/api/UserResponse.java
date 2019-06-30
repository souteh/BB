package com.sofrecom.sofid.data.model.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by oibnchahdia on 10/04/2019
 */

public class UserResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("is_default_password")
    private Boolean hasDefaultPassword;

    @JsonProperty("has_security_question")
    private Boolean hasSecurityQuestion;

    @JsonProperty("error")
    private ApiError error;

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setError(ApiError error) {
        this.error = error;
    }

    public ApiError getError() {
        return error;
    }

    public boolean isHasSecurityQuestion() {
        return hasSecurityQuestion;
    }

    public void setHasSecurityQuestion(boolean hasSecurityQuestion) {
        this.hasSecurityQuestion = hasSecurityQuestion;
    }

    public Boolean isHasDefaultPassword() {
        return hasDefaultPassword;
    }

    public void setHasDefaultPassword(Boolean hasDefaultPassword) {
        this.hasDefaultPassword = hasDefaultPassword;
    }
}