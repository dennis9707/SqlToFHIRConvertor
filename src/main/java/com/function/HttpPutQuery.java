package com.function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.Map;

public class HttpPutQuery {

    @Expose(serialize = false, deserialize = false)
    private String hostURL;
    @Expose(serialize = false, deserialize = false)
    private String resource;
    @Expose(serialize = false, deserialize = false)
    private String queryParameter;
    @Expose(serialize = true, deserialize = true)
    private String fhirQuery;
    @Expose(serialize = true, deserialize = true)
    private Map<String,String> body;
    @Expose(serialize = true, deserialize = true)
    private RequestType requestType;


    public HttpPutQuery(String hostURL,String resource,Map<String,String> parameterMap,Map<String,String> bodyValues){
        this.hostURL = hostURL;
        this.resource = resource;
        this.queryParameter = FHIRConstructor.generateQueryParameter(parameterMap);
        this.fhirQuery =  hostURL + "/" + resource + "?" + queryParameter;
        this.body = bodyValues;
        this.requestType = RequestType.PUT;
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
