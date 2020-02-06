package com.function;
import java.util.Map;

public class HttpPostQuery {
    private String requestProtocol;
    private String hostURL;
    private String resource;
    private String body;


    public HttpPostQuery(String requestProtocol, String hostURL, String resource){
        this.requestProtocol =  requestProtocol;
        this.hostURL = hostURL;
        this.resource = resource;
    }

    public void setBody(Map<String,String> valuesMap){
        StringBuilder bodyBuilder = new StringBuilder("{\n");
        bodyBuilder.append(ConvertUtil.quoteWrapper("resourceType")).append(": ").append(ConvertUtil.quoteWrapper(this.resource)).append(",\n");
        bodyBuilder.append(ConvertUtil.quoteWrapper("id")).append(": ").append(ConvertUtil.quoteWrapper(valuesMap.get("id"))).append(",\n");
        for(String key : valuesMap.keySet()){
            if(!key.equals("id")) {
                bodyBuilder.append(ConvertUtil.quoteWrapper(key)).append(": ").append(ConvertUtil.quoteWrapper(valuesMap.get(key))).append(",\n");
            }
        }
        bodyBuilder.append("}");
        this.body = bodyBuilder.toString();
    }

    @Override
    public String toString() {
        return requestProtocol + "://" + hostURL + "/" + resource + "\n" + body;
    }
}
