package com.function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.Map;

public class HttpDeleteQuery {
    @Expose(serialize = false, deserialize = false)
    public String requestProtocol;
    @Expose(serialize = false, deserialize = false)
    public String hostURL;
    @Expose(serialize = false, deserialize = false)
    public String resource;
    @Expose(serialize = false, deserialize = false)
    public String queryParameter;
    @Expose(serialize = true, deserialize = true)
    public String fhirQuery;
    @Expose(serialize = true, deserialize = true)
    private RequestType requestType;


    public HttpDeleteQuery(String requestProtocol, String hostURL, String resource, Map<String,String> parameterMap){
        this.requestProtocol =  requestProtocol;
        this.hostURL = hostURL;
        this.resource = resource;
        this.queryParameter = FHIRConstructor.generateQueryParameter(parameterMap);
        this.fhirQuery = requestProtocol + "://" + hostURL + "/" + resource + "?" + queryParameter;
        this.requestType = RequestType.DELETE;
    }


    @Override
    public String toString(){
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.disableHtmlEscaping();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(this);
    }
}
