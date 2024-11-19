-- Создание базы данных, если она не существует
CREATE
    DATABASE IF NOT EXISTS `YourStock`;
USE
    `YourStock`;

SET foreign_key_checks = 0;

-- Создание таблицы `user`
CREATE TABLE IF NOT EXISTS `user`
(
    `id`
                  INTEGER
        NOT
            NULL
        AUTO_INCREMENT
        UNIQUE,
    `username`
                  VARCHAR(255),
    `password`    VARCHAR(255),
    `role`        VARCHAR(50),
    `employee_id` INTEGER,
    PRIMARY KEY
        (
         `id`
            ),
    FOREIGN KEY
        (
         `employee_id`
            ) REFERENCES `employee`
        (
         `id`
            )
);

-- Создание таблицы `employee`
CREATE TABLE IF NOT EXISTS `employee`
(
    `id`
                          INTEGER
        NOT
            NULL
        AUTO_INCREMENT
        UNIQUE,
    `position`
                          VARCHAR(255),
    `personal_details_id` INTEGER,
    PRIMARY KEY
        (
         `id`
            ),
    FOREIGN KEY
        (
         `personal_details_id`
            ) REFERENCES `personal_details`
        (
         `id`
            )
);

-- Создание таблицы `card_id`
CREATE TABLE IF NOT EXISTS `card_id`
(
    `id`
        INTEGER
        NOT
            NULL
        AUTO_INCREMENT
        UNIQUE,
    `employee_id`
        INTEGER,
    `created_date`
        DATETIME,
    `ended_date`
        DATETIME,
    PRIMARY
        KEY
        (
         `id`
            ),
    FOREIGN KEY
        (
         `employee_id`
            ) REFERENCES `employee`
        (
         `id`
            )
);

-- Создание таблицы `personal_details`
CREATE TABLE IF NOT EXISTS `personal_details`
(
    `id`
                 INTEGER
        NOT
            NULL
        AUTO_INCREMENT
        UNIQUE,
    `phone_number`
                 VARCHAR(13),
    `first_name` VARCHAR(255),
    `last_name`  VARCHAR(255),
    `email`      VARCHAR(255),
    `address_id` INTEGER,
    PRIMARY KEY
        (
         `id`
            ),
    FOREIGN KEY
        (
         `address_id`
            ) REFERENCES `address`
        (
         `id`
            )
);

-- Создание таблицы `address`
CREATE TABLE IF NOT EXISTS `address`
(
    `id`
              INTEGER
        NOT
            NULL
        AUTO_INCREMENT
        UNIQUE,
    `city`
              VARCHAR(255),
    `address` VARCHAR(255),
    PRIMARY KEY
        (
         `id`
            )
);

-- Создание таблицы `report`
CREATE TABLE IF NOT EXISTS `report`
(
    `id`
                  INTEGER
        NOT
            NULL
        AUTO_INCREMENT
        UNIQUE,
    `action`
                  VARCHAR(255),
    `details`     VARCHAR(255),
    `report_date` DATETIME,
    `employee_id` INTEGER,
    PRIMARY KEY
        (
         `id`
            ),
    FOREIGN KEY
        (
         `employee_id`
            ) REFERENCES `employee`
        (
         `id`
            )
);

-- Создание таблицы `customer`
CREATE TABLE IF NOT EXISTS `customer`
(
    `id`
                          INTEGER
        NOT
            NULL
        AUTO_INCREMENT
        UNIQUE,
    `name`
                          VARCHAR(255),
    `personal_details_id` INTEGER,
    PRIMARY KEY
        (
         `id`
            ),
    FOREIGN KEY
        (
         `personal_details_id`
            ) REFERENCES `personal_details`
        (
         `id`
            )
);

-- Создание таблицы `supplier`
CREATE TABLE IF NOT EXISTS `supplier`
(
    `id`
                          INTEGER
        NOT
            NULL
        AUTO_INCREMENT
        UNIQUE,
    `name`
                          VARCHAR(255),
    `personal_details_id` INTEGER,
    PRIMARY KEY
        (
         `id`
            ),
    FOREIGN KEY
        (
         `personal_details_id`
            ) REFERENCES `personal_details`
        (
         `id`
            )
);

-- Создание таблицы `material`
CREATE TABLE IF NOT EXISTS `material`
(
    `id`
        INTEGER
        NOT
            NULL
        AUTO_INCREMENT
        UNIQUE,
    `supplier_id`
        INTEGER,
    `description`
        VARCHAR(255),
    PRIMARY KEY
        (
         `id`
            ),
    FOREIGN KEY
        (
         `supplier_id`
            ) REFERENCES `supplier`
        (
         `id`
            )
);

-- Создание таблицы `product`
CREATE TABLE IF NOT EXISTS `product`
(
    `id`
                  INTEGER
        NOT
            NULL
        AUTO_INCREMENT
        UNIQUE,
    `name`
                  VARCHAR(255),
    `price`       DECIMAL,
    `description` VARCHAR(255),
    PRIMARY KEY
        (
         `id`
            )
);

-- Создание таблицы `order`
CREATE TABLE IF NOT EXISTS `order`
(
    `id`
        INTEGER
        NOT
            NULL
        AUTO_INCREMENT
        UNIQUE,
    `customer_id`
        INTEGER,
    `order_date`
        DATETIME,
    PRIMARY
        KEY
        (
         `id`
            ),
    FOREIGN KEY
        (
         `customer_id`
            ) REFERENCES `customer`
        (
         `id`
            )
);

-- Создание таблицы `order_product`
CREATE TABLE IF NOT EXISTS `order_product`
(
    `id`
        INTEGER
        NOT
            NULL
        AUTO_INCREMENT
        UNIQUE,
    `product_id`
        INTEGER,
    `order_id`
        INTEGER,
    `quantity`
        INTEGER,
    PRIMARY
        KEY
        (
         `id`
            ),
    FOREIGN KEY
        (
         `product_id`
            ) REFERENCES `product`
        (
         `id`
            ),
    FOREIGN KEY
        (
         `order_id`
            ) REFERENCES `order`
        (
         `id`
            )
);

-- Создание таблицы `report_product`
CREATE TABLE IF NOT EXISTS `report_product`
(
    `id`
        INTEGER
        NOT
            NULL
        AUTO_INCREMENT
        UNIQUE,
    `report_id`
        INTEGER,
    `product_id`
        INTEGER,
    PRIMARY
        KEY
        (
         `id`
            ),
    FOREIGN KEY
        (
         `report_id`
            ) REFERENCES `report`
        (
         `id`
            ),
    FOREIGN KEY
        (
         `product_id`
            ) REFERENCES `product`
        (
         `id`
            )
);

-- Создание таблицы `report_material`
CREATE TABLE IF NOT EXISTS `report_material`
(
    `id`
        INTEGER
        NOT
            NULL
        AUTO_INCREMENT
        UNIQUE,
    `report_id`
        INTEGER,
    `material_id`
        INTEGER,
    PRIMARY
        KEY
        (
         `id`
            ),
    FOREIGN KEY
        (
         `report_id`
            ) REFERENCES `report`
        (
         `id`
            ),
    FOREIGN KEY
        (
         `material_id`
            ) REFERENCES `material`
        (
         `id`
            )
);

-- Создание таблицы `warehouse`
CREATE TABLE IF NOT EXISTS `warehouse`
(
    `id`
               INTEGER
        NOT
            NULL
        AUTO_INCREMENT
        UNIQUE,
    `name`
               VARCHAR(255),
    `location` VARCHAR(255),
    PRIMARY KEY
        (
         `id`
            )
);

-- Создание таблицы `material_stock`
CREATE TABLE IF NOT EXISTS `material_stock`
(
    `id`
        INTEGER
        NOT
            NULL
        AUTO_INCREMENT
        UNIQUE,
    `warehouse_id`
        INTEGER,
    `material_id`
        INTEGER,
    `quantity`
        INTEGER,
    PRIMARY
        KEY
        (
         `id`
            ),
    FOREIGN KEY
        (
         `warehouse_id`
            ) REFERENCES `warehouse`
        (
         `id`
            ),
    FOREIGN KEY
        (
         `material_id`
            ) REFERENCES `material`
        (
         `id`
            )
);

-- Создание таблицы `product_stock`
CREATE TABLE IF NOT EXISTS `product_stock`
(
    `id`
        INTEGER
        NOT
            NULL
        AUTO_INCREMENT
        UNIQUE,
    `warehouse_id`
        INTEGER,
    `product_id`
        INTEGER,
    `quantity`
        INTEGER,
    PRIMARY
        KEY
        (
         `id`
            ),
    FOREIGN KEY
        (
         `warehouse_id`
            ) REFERENCES `warehouse`
        (
         `id`
            ),
    FOREIGN KEY
        (
         `product_id`
            ) REFERENCES `product`
        (
         `id`
            )
);

SET foreign_key_checks = 1;