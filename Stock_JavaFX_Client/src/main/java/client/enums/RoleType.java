package client.enums;

public enum RoleType {
    USER,
    ADMIN,
    MATERIAL_MANAGER,
    MANUFACTURE_MANAGER,
    STOCK_MANAGER,
    ;

    @Override
    public String toString() {
        // Возвращаем имя роли с первой заглавной буквой
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
