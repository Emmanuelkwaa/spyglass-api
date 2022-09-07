CREATE TABLE `role` (
    `role_id` BIGINT unsigned NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(25) NOT NULL,
    PRIMARY KEY (`role_id`)
);

CREATE TABLE `category` (
    `category_id` BIGINT unsigned NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(25) NOT NULL,
    PRIMARY KEY (`category_id`)
);

CREATE TABLE `User` (
    `user_id` BIGINT unsigned AUTO_INCREMENT NOT NULL,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `email` VARCHAR(50) UNIQUE NOT NULL,
    `password` VARCHAR(500) NOT NULL,
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `user_role` (
     `user_id` BIGINT unsigned NOT NULL,
     `role_id` BIGINT unsigned NOT NULL,
     PRIMARY KEY (`user_id`,`role_id`),

     FOREIGN KEY (user_id)
         REFERENCES user (user_id)
         ON DELETE CASCADE ON UPDATE CASCADE,

     FOREIGN KEY (role_id)
         REFERENCES role (role_id)
         ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `goal` (
    `goal_id` BIGINT unsigned NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(250) NOT NULL,
    `discription` VARCHAR(500) NOT NULL,
    `picture` VARCHAR(500),
    `currently_saved` DECIMAL unsigned NOT NULL,
    `target_date` DATE NOT NULL,
    `target_amount` DECIMAL unsigned NOT NULL,
    `user_id` BIGINT unsigned NOT NULL,
    `category_id` BIGINT unsigned NOT NULL,
    PRIMARY KEY (`goal_id`),

    FOREIGN KEY (user_id)
        REFERENCES user (user_id)
        ON DELETE CASCADE ON UPDATE CASCADE,

    FOREIGN KEY (category_id)
        REFERENCES category (category_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);