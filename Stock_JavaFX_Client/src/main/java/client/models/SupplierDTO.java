package client.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDTO {
    private Integer id;
    private String name;
    private PersonalDetailDTO personalDetails; // Подключаем DTO для personalDetails
}