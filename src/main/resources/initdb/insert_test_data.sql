INSERT INTO `performance` (name, price, round, type, start_date, is_reserve)
VALUES ('레베카', 100000, 1, 0, '2024-01-20 19:30:00', 'disable');

INSERT INTO `performance` (id, name, price, round, type, start_date, is_reserve)
VALUES (UNHEX(REPLACE('123e4567-e89b-12d3-a456-426614174000', '-', '')), '공연 이름', 10000, 1, 1, '2024-01-01 20:00:00', 'enable');

INSERT INTO `users` VALUES
 (1, '원티드', '010-1112-2223', DEFAULT, DEFAULT);

INSERT INTO `accounts` VALUES
 (1, (SELECT id FROM users limit 1), 100000, DEFAULT, DEFAULT);

INSERT INTO `performance_seat_info` VALUES
 (1, (SELECT id FROM performance WHERE name = '공연 이름' limit 1), 1, 1, 'A', 1, 'enable', DEFAULT, DEFAULT)
,(2, (SELECT id FROM performance limit 1), 1, 1, 'A', 2, 'enable', DEFAULT, DEFAULT)
,(3, (SELECT id FROM performance limit 1), 1, 1, 'A', 3, 'enable', DEFAULT, DEFAULT)
,(4, (SELECT id FROM performance limit 1), 1, 1, 'A', 4, 'enable', DEFAULT, DEFAULT)
,(5, (SELECT id FROM performance limit 1), 1, 1, 'A', 5, 'disable', DEFAULT, DEFAULT);
