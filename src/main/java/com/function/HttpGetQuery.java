package com.function;

import java.util.Map;

public class HttpGetQuery {
    public String requestProtocol;
    public String hostURL;
    public String resource;
    public String queryParameter;

    public void setQueryParameter(Map<String,String> parameterMap){
        StringBuilder queryParameterBuilder = new StringBuilder();
        int count = 0;
        for(String key : parameterMap.keySet()){
            queryParameterBuilder.append(key + "=" + parameterMap.get(key));
            count++;
            if(count < parameterMap.keySet().size()){
                queryParameterBuilder.append("&");
            }
        }
        this.queryParameter = queryParameterBuilder.toString();
    }

    public HttpGetQuery(String requestProtocol,String hostURL,String resource,Map<String,String> parameterMap){
        this.requestProtocol =  requestProtocol;
        this.hostURL = hostURL;
        this.resource = resource;
        this.setQueryParameter(parameterMap);
    }


    @Override
    public String toString(){
        return requestProtocol + "://" + hostURL + "/" + resource + "?" + queryParameter;
    }
}
