package client.TCP;

import client.TCP.enums.ResponseType;
import lombok.Getter;

@Getter
public class Response {
    private String message;
    private ResponseType responseType;


    public Response(String message,ResponseType requestType) {
        this.message = message;
        this.responseType = requestType;

    }

    public Response() {
    }

    public void setResponseType(ResponseType requestType) {
        this.responseType = requestType;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
