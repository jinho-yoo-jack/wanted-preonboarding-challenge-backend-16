-- 1_schema.sql
CREATE TABLE IF NOT EXISTS `performance`
(
    `id`         BINARY(16) default (uuid())              NOT NULL COMMENT '공연/전시 ID',
    `name`       varchar(255)                             NOT NULL COMMENT '공연/전시 이름',
    `price`      BIGINT                                   NOT NULL COMMENT '가격',
    `round`      INT                                      NOT NULL COMMENT '회차',
    `type`       INT                                      NOT NULL COMMENT 'NONE, CONCERT, EXHIBITION',
    `start_date` datetime                                 NOT NULL COMMENT '공연 일시',
    `is_reserve` varchar(255)                             NOT NULL DEFAULT 'disable',
    `created_at` DATETIME   DEFAULT NOW()                 NOT NULL,
    `updated_at` DATETIME   DEFAULT NOW()                 NOT NUll,
    PRIMARY KEY (id),
    UNIQUE KEY (`name`, `round`)
);

CREATE TABLE IF NOT EXISTS `performance_seat_info`
(
    `id`             INT(10)                NOT NULL AUTO_INCREMENT,
    `performance_id` BINARY(16)             NOT NULL COMMENT '공연전시ID',
    `gate`           INT                    NOT NULL COMMENT '입장 게이트',
    `line`           CHAR(2)                NOT NULL COMMENT '좌석 열',
    `seat`           INT                    NOT NULL COMMENT '좌석 행',
    `is_reserve`     varchar(255)           NOT NULL default 'enable',
    `created_at`     DATETIME DEFAULT NOW() NOT NULL,
    `updated_at`     DATETIME DEFAULT NOW() NOT NUll,
    PRIMARY KEY (id),
	FOREIGN KEY (performance_id) REFERENCES `performance`(id),
    UNIQUE KEY performance_seat_info_unique (performance_id, line, seat)
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
	`completed`		 BOOLEAN				NOT NULL default 'enable',
    `created_at`     DATETIME DEFAULT NOW() NOT NULL,
    `updated_at`     DATETIME DEFAULT NOW() NOT NUll,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user_info`(id),
    FOREIGN KEY (seat_id) REFERENCES `performance_seat_info`(id),
    UNIQUE KEY (seat_id)
);

CREATE TABLE IF NOT EXISTS `waitlist`
(
    `id`             INT(10)                NOT NULL AUTO_INCREMENT,
    `user_id`        INT(10)                NOT NULL COMMENT '유저(FK)',
    `performance_id` BINARY(16)             NOT NULL COMMENT '공연(FK)',
    `mail`           varchar(255)           NOT NULL COMMENT '알림 전송용 메일',
    `created_at`     DATETIME DEFAULT NOW() NOT NULL,
    `updated_at`     DATETIME DEFAULT NOW() NOT NULL,
    `deleted_at`     DATETIME DEFAULT NOW() NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user_info`(id),
    FOREIGN KEY (performance_id) REFERENCES `performance`(id)
);

CREATE TABLE IF NOT EXISTS `discount_policy`
(
    `id`             INT(10)                NOT NULL AUTO_INCREMENT,
    `performance_id` BINARY(16)             NOT NULL COMMENT '공연 id',
    `type`           varchar(255)           NOT NULL COMMENT '할인 종류(정책명, 할인률)',
    `name`           varchar(255)           NOT NULL COMMENT '할인 정책명',
    `rate`           DECIMAL(7, 2)              NULL COMMENT '할인률(%)',
    `discount_fee`   INT                        NULL COMMENT '할인 가격',
    `started_at`     DATETIME DEFAULT NOW() NOT NULL COMMENT '할인 시작일시',
    `ended_at`       DATETIME DEFAULT NOW() NOT NULL COMMENT '할인 종료일시',
    `created_at`     DATETIME DEFAULT NOW() NOT NULL,
    `updated_at`     DATETIME DEFAULT NOW() NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (performance_id) REFERENCES `performance`(id),
    UNIQUE KEY (performance_id, `type`, `name`)
);