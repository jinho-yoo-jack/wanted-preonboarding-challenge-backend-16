-- schema.sql
DROP TABLE IF EXISTS performance;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS performance_seat_info;
DROP TABLE IF EXISTS accounts;

CREATE TABLE IF NOT EXISTS `performance`
(
    `id`         BINARY(16) DEFAULT RANDOM_UUID()         NOT NULL COMMENT '공연/전시 ID',
    `name`       VARCHAR(255)                             NOT NULL COMMENT '공연/전시 이름',
    `price`      INT                                      NOT NULL COMMENT '가격',
    `round`      INT                                      NOT NULL COMMENT '회차',
    `type`       INT                                      NOT NULL COMMENT 'NONE, CONCERT, EXHIBITION',
    `start_date` datetime                                 NOT NULL COMMENT '공연 일시',
    `is_reserve` VARCHAR(255)                             NOT NULL DEFAULT 'disable',
    `created_at` DATETIME    DEFAULT NOW()                 NOT NULL,
    `updated_at` DATETIME    DEFAULT NOW()                 NOT NUll,
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
    UNIQUE KEY performance_seat_info_unique (performance_id, round, `line`, seat)
);

CREATE TABLE IF NOT EXISTS `reservation`
(
    `id`             INT(10)                NOT NULL AUTO_INCREMENT,
    `performance_id` VARCHAR(36)            NOT NULL COMMENT '공연전시ID',
    `name`           varchar(255)           NOT NULL COMMENT '예약자명',
    `phone_number`   varchar(255)           NOT NULL COMMENT '예약자 휴대전화 번호',
    `round`          INT                    NOT NULL COMMENT '회차(FK)',
    `gate`           INT                    NOT NULL COMMENT '입장 게이트',
    `line`           CHAR                   NOT NULL COMMENT '좌석 열',
    `seat`           INT                    NOT NULL COMMENT '좌석 행',
    `created_at`     DATETIME DEFAULT NOW() NOT NULL,
    `updated_at`     DATETIME DEFAULT NOW() NOT NUll,
    PRIMARY KEY (id),
    UNIQUE KEY reservation_round_row_seat (performance_id, round, `line`, seat)
);

CREATE TABLE IF NOT EXISTS `users`
(
    `id`             BIGINT                   NOT NULL AUTO_INCREMENT,
    `name`           VARCHAR(255)             NOT NULL COMMENT '유저의 이름',
    `phone_number`   VARCHAR(255)             NOT NULL COMMENT '휴대폰 번호',
    `created_at`     DATETIME DEFAULT NOW() NOT NULL,
    `updated_at`     DATETIME DEFAULT NOW() NOT NUll,
    PRIMARY KEY (id),
    UNIQUE KEY (phone_number)
);

CREATE TABLE IF NOT EXISTS `accounts`
(
    `id`             BIGINT                   NOT NULL AUTO_INCREMENT,
    `user_id`        BIGINT                   NOT NULL COMMENT '계좌 주인 아이디',
    `amount`         DECIMAL(64, 0)           NOT NULL COMMENT '계좌 금액',
    `created_at`     DATETIME DEFAULT NOW() NOT NULL,
    `updated_at`     DATETIME DEFAULT NOW() NOT NUll,
    PRIMARY KEY (id),
    UNIQUE KEY (user_id)
)