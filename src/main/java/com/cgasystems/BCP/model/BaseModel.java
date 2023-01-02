package com.cgasystems.BCP.model;

public class BaseModel {

    private String message = "Success";

    private boolean isStatusOk = true;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getStatusOk() {
        return isStatusOk;
    }

    public void setStatusOk(boolean statusOk) {
        this.isStatusOk = statusOk;
    }
}
