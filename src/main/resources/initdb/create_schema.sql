-- schema.sql
CREATE TABLE IF NOT EXISTS `user`
(
    `id`	        BINARY(16)  default (uuid_to_bin(uuid()))   NOT NULL COMMENT '유저 ID',
    `name`          varchar(255)                                NOT NULL COMMENT '유저 이름',
    `phone_number`  varchar(255)                                NOT NULL COMMENT '유저 휴대전화 번호',
    `created_at`    TIMESTAMP    DEFAULT NOW()                   NOT NULL,
    `updated_at`    TIMESTAMP    DEFAULT NULL ON UPDATE NOW(),
    PRIMARY KEY(id),
    UNIQUE KEY user_unique (name, phone_number)
);

CREATE TABLE IF NOT EXISTS `performance`
(
    `id`         BINARY(16) default (uuid_to_bin(uuid())) NOT NULL COMMENT '공연/전시 ID',
    `name`       varchar(255)                             NOT NULL COMMENT '공연/전시 이름',
    `price`      INT                                      NOT NULL COMMENT '가격',
    `round`      INT                                      NOT NULL COMMENT '회차',
    `type`       INT                                      NOT NULL COMMENT 'NONE, CONCERT, EXHIBITION',
    `start_date` TIMESTAMP                                NOT NULL COMMENT '공연 일시',
    `is_reserve` varchar(255)                             NOT NULL DEFAULT 'disable',
    `created_at` TIMESTAMP   DEFAULT NOW()                 NOT NULL,
    `updated_at` TIMESTAMP   DEFAULT NULL ON UPDATE NOW(),
    PRIMARY KEY (id),
    UNIQUE KEY performance_unique (id, round)
);

CREATE TABLE IF NOT EXISTS `performance_seat_info`
(
    `id`             INT                    NOT NULL AUTO_INCREMENT,
    `performance_id` BINARY(16)             NOT NULL COMMENT '공연/전시 ID(FK)',
    `round`          INT                    NOT NULL COMMENT '회차',
    `gate`           INT                    NOT NULL COMMENT '입장 게이트',
    `line`           CHAR(2)                NOT NULL COMMENT '좌석 열',
    `seat`           INT                    NOT NULL COMMENT '좌석 행',
    `is_reserve`     varchar(255)           NOT NULL default 'enable',
    `created_at`     TIMESTAMP DEFAULT NOW() NOT NULL,
    `updated_at`     TIMESTAMP DEFAULT NULL ON UPDATE NOW(),
    PRIMARY KEY (id),
    UNIQUE KEY performance_seat_info_unique (performance_id, round, `line`, seat),
    CONSTRAINT seat_performance_fk FOREIGN KEY (performance_id) REFERENCES performance(id)
);

CREATE TABLE IF NOT EXISTS `reservation`
(
    `id`             INT                    NOT NULL AUTO_INCREMENT,
    `performance_id` BINARY(16)             NOT NULL COMMENT '공연/전시 ID(FK)',
    `user_id`        BINARY(16)             NOT NULL COMMENT '예약자 ID(FK)',
    `round`          INT                    NOT NULL COMMENT '회차',
    `gate`           INT                    NOT NULL COMMENT '입장 게이트',
    `line`           CHAR                   NOT NULL COMMENT '좌석 열',
    `seat`           INT                    NOT NULL COMMENT '좌석 행',
    `created_at`     TIMESTAMP DEFAULT NOW() NOT NULL,
    `updated_at`     TIMESTAMP DEFAULT NULL ON UPDATE NOW(),
    PRIMARY KEY (id),
    UNIQUE KEY reservation_round_row_seat (performance_id, round, `line`, seat),
    CONSTRAINT reservation_performance_fk FOREIGN KEY (performance_id) REFERENCES  performance(id),
    CONSTRAINT reservation_user_fk FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS `canceled_reservation`
(
    `id`             INT                    NOT NULL AUTO_INCREMENT,
    `performance_id` BINARY(16)             NOT NULL COMMENT '공연/전시 ID(FK)',
    `user_id`        BINARY(16)             NOT NULL COMMENT '예약자 ID(FK)',
    `round`          INT                    NOT NULL COMMENT '회차',
    `gate`           INT                    NOT NULL COMMENT '입장 게이트',
    `line`           CHAR                   NOT NULL COMMENT '좌석 열',
    `seat`           INT                    NOT NULL COMMENT '좌석 행',
    `created_at`     TIMESTAMP               NOT NULL COMMENT '예약일',
    `canceled_at`    TIMESTAMP DEFAULT NOW() NOT NULL COMMENT '취소일',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `alarm`
(
    `id`                INT                             NOT NULL AUTO_INCREMENT,
    `user_id`           BINARY(16)                      NOT NULL COMMENT '유저 ID(FK)',
    `performance_id`    BINARY(16)                      NOT NULL COMMENT '공연/전시 ID(FK)',
    `created_at`        TIMESTAMP    DEFAULT NOW()       NOT NULL,
    `updated_at`        TIMESTAMP    DEFAULT NULL ON UPDATE NOW(),
    PRIMARY KEY(id),
    UNIQUE KEY alarm_unique (user_id, performance_id),
    CONSTRAINT alarm_user_fk FOREIGN KEY (user_id) REFERENCES user(id)
);