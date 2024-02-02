INSERT INTO `performance` (name, price, round, type, start_date, is_reserve)
VALUES ('Reveca', 100000, 1, 0, '2024-01-20 19:30:00', 'disable');

INSERT INTO performance_seat_info VALUES
 (DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 1, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 2, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 3, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 4, 'enable', DEFAULT, DEFAULT);

INSERT INTO 'user' (name, phone_number, amount, membership) VALUES ('Ethan', '010-1234-1234', 200000, 'GOLD');
INSERT INTO 'user' (name, phone_number, amount, membership) VALUES ('Ann', '010-1234-1234', 200000, 'SILVER');
INSERT INTO 'user' (name, phone_number, amount, membership) VALUES ('Key', '010-1234-1234', 200000, 'BRONZE');
INSERT INTO 'user' (name, phone_number, amount, membership) VALUES ('Justin', '010-1234-1234', 200000, 'GOLD');