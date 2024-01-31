CREATE TABLE amount_discount_policy
(
    amount INT    NOT NULL,
    id     BIGINT NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE discount_policy
(
    id            BIGINT      NOT NULL AUTO_INCREMENT,
    discount_type VARCHAR(31) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE none_discount_policy
(
    id BIGINT NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE percent_discount_policy
(
    percent FLOAT(53) NOT NULL,
    id      BIGINT    NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE perform
(
    gate                  INT        NOT NULL,
    reservation_available BIT        NOT NULL,
    round                 INT        NOT NULL,
    start_date            DATE       NOT NULL,
    id                    BINARY(16) NOT NULL,
    performance_id        BINARY(16) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE performance
(
    price              INT                                    NOT NULL,
    discount_policy_id BIGINT,
    id                 BINARY(16)                             NOT NULL,
    name               VARCHAR(255)                           NOT NULL,
    type               ENUM ('NONE', 'CONCERT', 'EXHIBITION') NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT UK_nuypy298tmuksi6s328r8231c UNIQUE (discount_policy_id)
) ENGINE = InnoDB;

CREATE TABLE reservation
(
    id           BINARY(16)   NOT NULL,
    name         VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE reservation_reserve_item_list
(
    fee                INT          NOT NULL,
    line               CHAR(1)      NOT NULL,
    purchase_date      DATE         NOT NULL,
    round              INT          NOT NULL,
    seat               INT          NOT NULL,
    id                 BINARY(16)   NOT NULL,
    no                 BINARY(16)   NOT NULL,
    reservation_id     BINARY(16)   NOT NULL,
    name               VARCHAR(255) NOT NULL,
    reservation_status ENUM ('RESERVE', 'CANCEL'),
    CONSTRAINT FKfgk3770kws30j6p3orvw25ks7 FOREIGN KEY (reservation_id) REFERENCES reservation (id)
) ENGINE = InnoDB;

CREATE TABLE reservation_cancel_subscribe
(
    id        BINARY(16)   NOT NULL,
    perfor_id BINARY(16),
    user_id   VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FKhe8dp5283hpdmadyvold8xo8i FOREIGN KEY (perfor_id) REFERENCES perform (id)
) ENGINE = InnoDB;

ALTER TABLE amount_discount_policy
    ADD CONSTRAINT FK2q0sacr6mjl9o5d6pmhr70xao FOREIGN KEY (id) REFERENCES discount_policy (id);

ALTER TABLE none_discount_policy
    ADD CONSTRAINT FK46ywd4acojrwuc30q5urngifl FOREIGN KEY (id) REFERENCES discount_policy (id);

ALTER TABLE percent_discount_policy
    ADD CONSTRAINT FK3w28nl5i2tpob3xx1xcro5g0w FOREIGN KEY (id) REFERENCES discount_policy (id);

ALTER TABLE perform
    ADD CONSTRAINT FKnkpoc1jbq41o850i7qvt584wx FOREIGN KEY (performance_id) REFERENCES performance (id);

ALTER TABLE performance
    ADD CONSTRAINT FK7ticpplal5ebcktyird7kw7x6 FOREIGN KEY (discount_policy_id) REFERENCES discount_policy (id);

ALTER TABLE reservation_cancel_subscribe
    ADD CONSTRAINT FKhe8dp5283hpdmadyvold8xo8i FOREIGN KEY (perfor_id) REFERENCES perform (id);