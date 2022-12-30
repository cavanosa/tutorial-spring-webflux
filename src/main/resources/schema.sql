CREATE TABLE IF NOT EXISTS `product`
( `id` INT UNSIGNED NOT NULL AUTO_INCREMENT , `name` VARCHAR(10) NOT NULL , `price` FLOAT NOT NULL , PRIMARY KEY (`id`), UNIQUE `name_unique` (`name`)) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `user`
( `id` INT UNSIGNED NOT NULL AUTO_INCREMENT , `username` VARCHAR(10) NOT NULL , `email` VARCHAR(10) NOT NULL , `password` VARCHAR(255) NOT NULL, `roles` VARCHAR(25) NOT NULL, PRIMARY KEY (`id`), UNIQUE `username_unique` (`username`), UNIQUE `email_unique` (`email`)) ENGINE = InnoDB;