package server.TCP.enums;

public enum RequestType {
    LOGIN, // Вход пользователя
    REGISTER, // Регистрация пользователя
    GET_USER, // Получение информации о пользователе
    UPDATE_USER, // Обновление информации о пользователе
    DELETE_USER, // Удаление пользователя
    GET_ALL_USERS, // Получение списка всех пользователей
    GET_ROLES,

    CREATE_ROLE, // Создание новой роли
    GET_ROLE, // Получение информации о роли
    UPDATE_ROLE, // Обновление информации о роли
    DELETE_ROLE, // Удаление роли
    GET_ALL_ROLES, // Получение списка всех ролей

    CREATE_EMPLOYEE, // Создание нового сотрудника
    GET_EMPLOYEE, // Получение информации о сотруднике
    UPDATE_EMPLOYEE, // Обновление информации о сотруднике
    DELETE_EMPLOYEE, // Удаление сотрудника
    GET_ALL_EMPLOYEES, // Получение списка всех сотрудников

    CREATE_CUSTOMER, // Создание нового клиента
    GET_CUSTOMER, // Получение информации о клиенте
    UPDATE_CUSTOMER, // Обновление информации о клиенте
    DELETE_CUSTOMER, // Удаление клиента
    GET_ALL_CUSTOMERS, // Получение списка всех клиентов

    CREATE_SUPPLIER, // Создание нового поставщика
    GET_SUPPLIER, // Получение информации о поставщике
    UPDATE_SUPPLIER, // Обновление информации о поставщике
    DELETE_SUPPLIER, // Удаление поставщика
    GET_ALL_SUPPLIERS, // Получение списка всех поставщиков

    CREATE_PRODUCT, // Создание нового продукта
    GET_PRODUCT, // Получение информации о продукте
    UPDATE_PRODUCT, // Обновление информации о продукте
    DELETE_PRODUCT, // Удаление продукта
    GET_ALL_PRODUCTS, // Получение списка всех продуктов

    CREATE_ORDER, // Создание нового заказа
    GET_ORDER, // Получение информации о заказе
    UPDATE_ORDER, // Обновление информации о заказе
    DELETE_ORDER, // Удаление заказа
    GET_ALL_ORDERS, // Получение списка всех заказов

    CREATE_REPORT, // Создание нового отчета
    GET_REPORT, // Получение информации об отчете
    UPDATE_REPORT, // Обновление информации об отчете
    DELETE_REPORT, // Удаление отчета
    GET_ALL_REPORTS, // Получение списка всех отчетов

    CREATE_WAREHOUSE, // Создание нового склада
    GET_WAREHOUSE, // Получение информации о складе
    UPDATE_WAREHOUSE, // Обновление информации о складе
    DELETE_WAREHOUSE, // Удаление склада
    GET_ALL_WAREHOUSES, // Получение списка всех складов

    CREATE_MATERIAL, // Создание нового материала
    GET_MATERIAL, // Получение информации о материале
    UPDATE_MATERIAL, // Обновление информации о материале
    DELETE_MATERIAL, // Удаление материала
    GET_ALL_MATERIALS, // Получение списка всех материалов

    CREATE_ADDRESS, // Создание нового адреса
    GET_ADDRESS, // Получение информации об адресе
    UPDATE_ADDRESS, // Обновление информации об адресе
    DELETE_ADDRESS, // Удаление адреса
    GET_ALL_ADDRESSES, // Получение списка всех адресов

    CREATE_CARD_ID, // Создание нового идентификатора карты
    GET_CARD_ID, // Получение информации о карте
    UPDATE_CARD_ID, // Обновление информации о карте
    DELETE_CARD_ID, // Удаление карты
    GET_ALL_CARD_IDS, // Получение списка всех идентификаторов карты

    CREATE_ORDER_PRODUCT, // Создание нового заказа продукта
    GET_ORDER_PRODUCT, // Получение информации о заказе продукта
    UPDATE_ORDER_PRODUCT, // Обновление информации о заказе продукта
    DELETE_ORDER_PRODUCT, // Удаление заказа продукта
    GET_ALL_ORDER_PRODUCTS, // Получение списка всех заказов продукта

    CREATE_MATERIAL_STOCK, // Создание нового запаса материала
    GET_MATERIAL_STOCK, // Получение информации о запасе материала
    UPDATE_MATERIAL_STOCK, // Обновление информации о запасе материала
    DELETE_MATERIAL_STOCK, // Удаление запаса материала
    GET_ALL_MATERIAL_STOCKS, // Получение списка всех запасов материала

    CREATE_PRODUCT_STOCK, // Создание нового запаса продукта
    GET_PRODUCT_STOCK, // Получение информации о запасе продукта
    UPDATE_PRODUCT_STOCK, // Обновление информации о запасе продукта
    DELETE_PRODUCT_STOCK, // Удаление запаса продукта
    GET_ALL_PRODUCT_STOCKS // Получение списка всех запасов продукта
}
