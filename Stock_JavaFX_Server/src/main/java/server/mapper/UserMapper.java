package server.mapper;

import server.dto.UserDTO;
import server.enums.RoleType;
import server.models.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .employeeId(user.getEmployee() != null ? user.getEmployee().getId() : null)
                .role(user.getRole() != null ? RoleType.valueOf(user.getRole()) : RoleType.USER) // по умолчанию USER
                .build();
    }

    public static User fromDTO(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole() != null ? userDTO.getRole().name() : RoleType.USER.name()); // по умолчанию USER
        return user;
    }
}
