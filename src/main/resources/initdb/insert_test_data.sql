-- Insert data into discount_policy table
INSERT INTO discount_policy (discount_type)
VALUES ('Fixed'),
       ('None'),
       ('Percentage');

-- Insert data into amount_discount_policy table
INSERT INTO amount_discount_policy (amount, id)
VALUES (50, 1),
       (0, 2),
       (10, 3);

-- Insert data into none_discount_policy table
INSERT INTO none_discount_policy (id)
VALUES (2),
       (3);

-- Insert data into percent_discount_policy table
INSERT INTO percent_discount_policy (percent, id)
VALUES (15.5, 1),
       (5.0, 3);

-- Insert data into performance table
INSERT INTO performance (price, discount_policy_id, id, name, type)
VALUES (100, 1, UNHEX(REPLACE(UUID(), '-', '')), 'Concert A', 'CONCERT'),
       (75, 2, UNHEX(REPLACE(UUID(), '-', '')), 'Exhibition B', 'EXHIBITION'),
       (50, 3, UNHEX(REPLACE(UUID(), '-', '')), 'None Discount Show', 'NONE');

-- Insert data into perform table
INSERT INTO perform (gate, reservation_available, round, start_date, id, performance_id)
VALUES (1, 1, 1, '2024-02-01', UNHEX(REPLACE(UUID(), '-', '')),
        (SELECT id FROM performance WHERE name = 'Concert A')),
       (2, 0, 2, '2024-02-15', UNHEX(REPLACE(UUID(), '-', '')),
        (SELECT id FROM performance WHERE name = 'Exhibition B'));

-- Insert data into reservation table
INSERT INTO reservation (id, name, phone_number)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), 'John Doe', '123-456-7890'),
       (UNHEX(REPLACE(UUID(), '-', '')), 'Jane Smith', '987-654-3210');

-- Insert data into reservation_reserve_item_list table
INSERT INTO reservation_reserve_item_list (fee, line, purchase_date, round, seat, id, no,
                                           reservation_id, name, reservation_status)
VALUES (100, 'A', '2024-02-05', 1, 10, UNHEX(REPLACE(UUID(), '-', '')),
        UNHEX(REPLACE(UUID(), '-', '')), (SELECT id FROM reservation WHERE name = 'John Doe'),
        'Concert A - A10', 'RESERVE'),
       (75, 'B', '2024-02-10', 2, 5, UNHEX(REPLACE(UUID(), '-', '')),
        UNHEX(REPLACE(UUID(), '-', '')), (SELECT id FROM reservation WHERE name = 'Jane Smith'),
        'Exhibition B - B5', 'RESERVE'),
       (50, 'C', '2024-03-01', 1, 8, UNHEX(REPLACE(UUID(), '-', '')),
        UNHEX(REPLACE(UUID(), '-', '')), (SELECT id FROM reservation WHERE name = 'John Doe'),
        'None Discount Show - C8', 'RESERVE');

-- Insert data into reservation_cancel_subscribe table
INSERT INTO reservation_cancel_subscribe (id, perfor_id, user_id)
VALUES (UNHEX(REPLACE(UUID(), '-', '')), (SELECT id FROM perform WHERE round = 1), 'user123'),
       (UNHEX(REPLACE(UUID(), '-', '')), (SELECT id FROM perform WHERE round = 2), 'user456');
