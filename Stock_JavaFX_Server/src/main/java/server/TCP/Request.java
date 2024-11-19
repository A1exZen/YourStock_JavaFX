package server.TCP;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.TCP.enums.RequestType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private String message;
    private RequestType requestType;
}

