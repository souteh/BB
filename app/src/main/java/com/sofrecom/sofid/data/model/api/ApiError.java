package com.sofrecom.sofid.data.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by oibnchahdia on 03/05/2019
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiError {
    private int code;
    private String message;

    public ApiError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ApiError createApiError(String message){
        return new ApiError(-1, message);
    }
}
