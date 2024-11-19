package client.models;

import client.enums.RoleType;
import lombok.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private EmployeeDTO employee;
    private RoleType role;
}

