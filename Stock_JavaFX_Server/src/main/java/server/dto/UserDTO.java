package server.dto;

import lombok.Builder;
import lombok.Data;
import server.enums.RoleType;
import server.models.Employee;

@Data
@Builder
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private Employee employee;
    private RoleType role;
}
