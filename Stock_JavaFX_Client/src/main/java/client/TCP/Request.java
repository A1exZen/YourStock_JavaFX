package client.TCP;

import client.TCP.enums.RequestType;

public class Request {
    private String message;
    private RequestType requestType;


    public Request(String message,RequestType requestType) {
        this.message = message;
        this.requestType = requestType;
    }

    public Request() {}

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

