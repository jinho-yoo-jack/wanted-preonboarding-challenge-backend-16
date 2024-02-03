-- schema.sql
CREATE TABLE IF NOT EXISTS `performance`
(
    `id`               INT                                                   NOT NULL AUTO_INCREMENT,
    `performance_id`   VARBINARY(16) DEFAULT UNHEX(REPLACE(UUID(), '-', '')) NOT NULL COMMENT '공연/전시 ID',
    `performance_name` varchar(255)                                          NOT NULL COMMENT '공연/전시 이름',
    `price`            INT                                                   NOT NULL COMMENT '가격',
    `round`            INT                                                   NOT NULL COMMENT '회차',
    `type`             INT                                                   NOT NULL COMMENT 'NONE, CONCERT, EXHIBITION',
    `start_date`       datetime                                              NOT NULL COMMENT '공연 일시',
    `reserved`         tinyint(1)    DEFAULT 1                               NOT NULL,
    `created_at`       DATETIME      DEFAULT NOW()                           NOT NULL,
    `updated_at`       DATETIME      DEFAULT NOW()                           NOT NUll,
    PRIMARY KEY (id),
    UNIQUE KEY (id, round)
);

CREATE TABLE IF NOT EXISTS `performance_seat_info`
(
    `id`             INT(10)                  NOT NULL AUTO_INCREMENT,
    `performance_id` VARBINARY(16)            NOT NULL COMMENT '공연전시ID',
    `round`          INT                      NOT NULL COMMENT '회차(FK)',
    `gate`           INT                      NOT NULL COMMENT '입장 게이트',
    `line`           CHAR(2)                  NOT NULL COMMENT '좌석 열',
    `seat`           INT                      NOT NULL COMMENT '좌석 행',
    `reserved`       tinyint(1) default 0     NOT NULL,
    `created_at`     DATETIME   DEFAULT NOW() NOT NULL,
    `updated_at`     DATETIME   DEFAULT NOW() NOT NUll,
    PRIMARY KEY (id),
    UNIQUE KEY performance_seat_info_unique (performance_id, round, `line`, seat)
);

CREATE TABLE IF NOT EXISTS `reservation`
(
    `id`                 INT(10)                NOT NULL AUTO_INCREMENT,
    `performance_id`     VARBINARY(16)          NOT NULL COMMENT '공연전시ID',
    `name`               varchar(255)           NOT NULL COMMENT '예약자명',
    `phone_number`       varchar(255)           NOT NULL COMMENT '예약자 휴대전화 번호',
    `round`              INT                    NOT NULL COMMENT '회차(FK)',
    `gate`               INT                    NOT NULL COMMENT '입장 게이트',
    `line`               CHAR                   NOT NULL COMMENT '좌석 열',
    `seat`               INT                    NOT NULL COMMENT '좌석 행',
    `created_at`         DATETIME DEFAULT NOW() NOT NULL,
    `updated_at`         DATETIME DEFAULT NOW() NOT NUll,
    `reservation_status` ENUM ('RESERVE', 'CANCEL'),
    `rate`               INT                    NOT NULL COMMENT '요금',
    PRIMARY KEY (id),
    UNIQUE KEY reservation_round_row_seat (performance_id, round, `line`, seat)
);
