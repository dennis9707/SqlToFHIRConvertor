package com.function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ErrorMessage {
    public String errorMessage;
    public String exceptionMessage;

    public ErrorMessage(String errorM, String exceptionM){
        this.errorMessage = errorM;
        this.exceptionMessage = exceptionM;
    }

    public String toString(){
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }
}