-- schema.sql
CREATE TABLE IF NOT EXISTS `performance`
(
    `id`         BINARY(16) default (uuid_to_bin(uuid())) NOT NULL COMMENT '공연/전시 ID',
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
    `round`          INT                    NOT NULL COMMENT '회차(FK from performance.round)',
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
    `performance_id` BINARY(16)             NOT NULL COMMENT '공연전시ID(FK from performance.id)',
    `name`           varchar(255)           NOT NULL COMMENT '예약자명(FK from user_info.name)',
    `phone_number`   varchar(255)           NOT NULL COMMENT '예약자 휴대전화 번호(FK from user_info.phone_number)',
    `round`          INT                    NOT NULL COMMENT '회차(FK from performance.round)',
    `gate`           INT                    NOT NULL COMMENT '입장 게이트',
    `line`           CHAR                   NOT NULL COMMENT '좌석 열',
    `seat`           INT                    NOT NULL COMMENT '좌석 행',
    `created_at`     DATETIME DEFAULT NOW() NOT NULL,
    `updated_at`     DATETIME DEFAULT NOW() NOT NUll,
    PRIMARY KEY (id),
    UNIQUE KEY reservation_round_row_seat (performance_id, round, `line`, seat)
);

CREATE TABLE IF NOT EXISTS `user_info`
(
    `user_uuid`      BINARY(16) default (uuid_to_bin(uuid())) NOT NULL COMMENT '유저 UUID',
    `name`           varchar(255)                             NOT NULL COMMENT '유저 이름',
    `id`             varchar(255)                             NOT NULL COMMENT '유저 아이디',
    `email`          varchar(255)                             NOT NULL COMMENT '유저 이메일',
    `password`       varchar(255)                             NOT NULL COMMENT '유저 비밀번호',
    `phone_number`   varchar(255)                             NOT NULL COMMENT '유저 휴대전화 번호',
    `birthday`       DATETIME                                 NOT NULL COMMENT '생일',
    `created_at`     DATETIME   DEFAULT NOW()                 NOT NULL COMMENT '가입 일시',
    `default_payment_code` INT                                NOT NULL COMMENT '결제 수단 선택 코드',
    PRIMARY KEY (user_uuid),
    UNIQUE KEY user_info_unique (id, email, phone_number)
);

CREATE TABLE IF NOT EXISTS `payment_card`
(
    `id`              INT                                       NOT NULL AUTO_INCREMENT COMMENT '결제 수단 ID',
    `user_uuid`       BINARY(16)  DEFAULT (uuid_to_bin(uuid())) NOT NULL COMMENT '유저 UUID',
    `balance_amount`  INT                                       NOT NULL COMMENT '결제 수단 잔액',
    `card_name`       VARCHAR(255)                              NOT NULL COMMENT '카드 명',
    `card_num`        VARCHAR(255)                              NOT NULL COMMENT '카드 번호',
    `expired_date`    VARCHAR(255)                              NOT NULL COMMENT '카드 유효기간',
    `cvc`             VARCHAR(255)                              NOT NULL COMMENT '카드 cvc 번호',
    PRIMARY KEY (id),
    UNIQUE KEY (card_num)
);

CREATE TABLE IF NOT EXISTS `payment_point`
(
    `id`              INT                                       NOT NULL AUTO_INCREMENT COMMENT '결제 수단 ID',
    `user_uuid`       BINARY(16)  DEFAULT (uuid_to_bin(uuid())) NOT NULL COMMENT '유저 UUID',
    `balance_amount`  INT                                       NOT NULL COMMENT '결제 수단 잔액',
    PRIMARY KEY (id)
);