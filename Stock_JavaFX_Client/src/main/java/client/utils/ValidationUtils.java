package client.utils;

import java.util.regex.Pattern;

public class ValidationUtils {

    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Регулярное выражение для проверки корректности номера телефона
    // Пример для международного формата: +375 (29) 123-45-67 или 80291234567
    public static final String PHONE_REGEX = "^(\\+375|80)(25|29|33|44|17)\\d{7}$";

    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    // Регулярное выражение для проверки имени (только буквы, пробелы)
    private static final String NAME_REGEX = "^[a-zA-Zа-яА-ЯёЁ\\s]{2,50}$";
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

    // Проверка корректности email
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    // Проверка корректности номера телефона
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    // Проверка корректности имени
    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }

    // Проверка, что поле не пустое
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // Проверка корректности города (только буквы)
    public static boolean isValidCity(String city) {
        return isValidName(city);
    }
}
