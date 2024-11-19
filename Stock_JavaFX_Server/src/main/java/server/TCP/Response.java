package server.TCP;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.TCP.enums.ResponseType;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private String message;
    private ResponseType responseType;

}
