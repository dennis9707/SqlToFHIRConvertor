package com.function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.Map;

public class HttpPostQuery {

    @Expose(serialize = false, deserialize = false)
    private String hostURL;
    @Expose(serialize = false, deserialize = false)
    private String resource;
    @Expose(serialize = true, deserialize = true)
    private String fhirQuery;
    @Expose(serialize = true, deserialize = true)
    private Map<String,String> body;
    @Expose(serialize = true, deserialize = true)
    private RequestType requestType;


    public HttpPostQuery( String hostURL, String resource,Map<String,String> valuesMap){
        this.hostURL = hostURL;
        this.resource = resource;
        this.fhirQuery = hostURL + "/" + resource;
        this.body = valuesMap;
        this.requestType = RequestType.POST;
    }

    @Override
    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.disableHtmlEscaping();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }
}
