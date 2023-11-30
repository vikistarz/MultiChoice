package com.company.mulitchoice.logInPage;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class LogInResponseModel {

        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("success")
        @Expose
        public Boolean success;
        @SerializedName("result")
        @Expose
        public Result result;
        @SerializedName("errors")
        @Expose
        public List<Error> errors;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
