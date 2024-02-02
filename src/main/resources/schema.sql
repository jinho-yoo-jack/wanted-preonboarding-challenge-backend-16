-- schema.sql
CREATE TABLE IF NOT EXISTS `performance`
(
    `id`         BINARY(16) default (uuid()) NOT NULL COMMENT '공연/전시 ID', -- default (uuid_to_bin(uuid())) NOT NULL COMMENT '공연/전시 ID',
    `name`       varchar(255)                             NOT NULL COMMENT '공연/전시 이름',
    `price`      INT                                      NOT NULL COMMENT '가격',
    `round`      INT                                      NOT NULL COMMENT '회차',
    `type`       INT                                      NOT NULL COMMENT 'NONE, CONCERT, EXHIBITION',
    `start_date` datetime                                 NOT NULL COMMENT '공연 일시',
    `is_reserve` varchar(255)                             NOT NULL DEFAULT 'disable',
    `created_at` DATETIME   DEFAULT NOW()                 NOT NULL,
    `updated_at` DATETIME   DEFAULT NOW()                 NOT NUll,
    PRIMARY KEY (id),
    UNIQUE KEY (id, round)
);

CREATE TABLE IF NOT EXISTS `performance_seat_info`
(
    `id`             INT(10)                NOT NULL AUTO_INCREMENT,
    `performance_id` BINARY(16)             NOT NULL COMMENT '공연전시ID',
    `round`          INT                    NOT NULL COMMENT '회차(FK)',
    `gate`           INT                    NOT NULL COMMENT '입장 게이트',
    `line`           CHAR(2)                NOT NULL COMMENT '좌석 열',
    `seat`           INT                    NOT NULL COMMENT '좌석 행',
    `is_reserve`     varchar(255)           NOT NULL default 'enable',
    `created_at`     DATETIME DEFAULT NOW() NOT NULL,
    `updated_at`     DATETIME DEFAULT NOW() NOT NUll,
    PRIMARY KEY (id),
	FOREIGN KEY (performance_id, round) REFERENCES `performance`(id, round),
    UNIQUE KEY performance_seat_info_unique (performance_id, round, `line`, seat)
);

CREATE TABLE IF NOT EXISTS `user_info`
(
    `id`             INT(10)                NOT NULL AUTO_INCREMENT,
    `user_name`      varchar(255)           NOT NULL COMMENT '유저 이름',
    `phone_number`   varchar(255)           NOT NULL COMMENT '유저 휴대폰 번호'
);

CREATE TABLE IF NOT EXISTS `reservation`
(
    `id`             INT(10)                NOT NULL AUTO_INCREMENT,
    `user_id`        INT(10)                NOT NULL COMMENT '유저(FK)',
    `seat_id`        INT(10)                NOT NULL COMMENT '좌석(FK)',
	`completed`		 BOOLEAN				NOT NULL,
    `created_at`     DATETIME DEFAULT NOW() NOT NULL,
    `updated_at`     DATETIME DEFAULT NOW() NOT NUll,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user_info`(id),
    FOREIGN KEY (seat_id) REFERENCES `performance_seat_info`(id),
    UNIQUE KEY (seat_id)
);