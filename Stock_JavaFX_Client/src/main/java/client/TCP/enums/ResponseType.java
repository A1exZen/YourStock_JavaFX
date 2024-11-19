package client.TCP.enums;

public enum ResponseType {
    OK, // Успешный ответ
    NOT_FOUND, // Не найдено
    BAD_REQUEST, // Неверный запрос
    SERVER_ERROR, // Ошибка сервера
    FORBIDDEN, // Доступ запрещен
    UNAUTHORIZED, // Неавторизован
}
