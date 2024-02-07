-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema wanted
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema wanted
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `wanted` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `wanted` ;

-- -----------------------------------------------------
-- Table `wanted`.`performance`
-- -----------------------------------------------------
drop table `wanted`.`performance`;
drop table `wanted`.`performance_seat_info`;
drop table `wanted`.`reservation`;

CREATE TABLE IF NOT EXISTS `wanted`.`performance` (
                                                      `price` INT NOT NULL,
                                                      `reserved` BIT(1) NOT NULL,
                                                      `round` INT NOT NULL,
                                                      `start_date` DATE NOT NULL,
                                                      `type` INT NOT NULL,
                                                      `performance_id` VARBINARY(16) NOT NULL,
                                                      `performance_name` VARCHAR(255) NOT NULL,
                                                      PRIMARY KEY (`performance_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `wanted`.`performance_seat_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wanted`.`performance_seat_info` (
                                                                `gate` INT NOT NULL,
                                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                                `line` CHAR(1) NOT NULL,
                                                                `reserved` BIT(1) NOT NULL,
                                                                `round` INT NOT NULL,
                                                                `seat` INT NOT NULL,
                                                                `performance_id` VARBINARY(16) NOT NULL,
                                                                PRIMARY KEY (`id`),
                                                                UNIQUE INDEX `performance_seat_info_unique` (`performance_id` ASC, `round` ASC, `line` ASC, `seat` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `wanted`.`reservation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wanted`.`reservation` (
                                                      `gate` INT NOT NULL,
                                                      `id` INT NOT NULL AUTO_INCREMENT,
                                                      `line` CHAR(1) NOT NULL,
                                                      `rate` INT NOT NULL,
                                                      `round` INT NOT NULL,
                                                      `seat` INT NOT NULL,
                                                      `performance_id` VARBINARY(16) NOT NULL,
                                                      `name` VARCHAR(255) NOT NULL,
                                                      `phone_number` VARCHAR(255) NOT NULL,
                                                      `reservation_status` ENUM('RESERVE', 'CANCEL') NULL DEFAULT NULL,
                                                      PRIMARY KEY (`id`),
                                                      INDEX `performance_id_idx` (`performance_id` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;

alter table performance_seat_info add constraint fk_performance_seat_info_performance_id
foreign key (`performance_id`)
references performance (`performance_id`) on delete cascade on update cascade ;

alter table reservation add constraint fk_reservation_performance_id
    foreign key (`performance_id`)
        references performance (`performance_id`) on delete cascade on update cascade ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
