package server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDetailDTO {
    private Integer id;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String email;
    private AddressDTO address; // Подключаем DTO для Address
}