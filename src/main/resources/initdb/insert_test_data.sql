INSERT INTO `performance` (name, price, round, type, start_date, is_reserve)
VALUES ('레베카', 100000, 1, 0, '2024-01-20 19:30:00', 'disable');
INSERT INTO `performance` (name, price, round, type, start_date, is_reserve)
VALUES ('가나다', 100000, 1, 2, '2024-01-20 19:30:00', 'disable');
INSERT INTO `performance` (name, price, round, type, start_date, is_reserve)
VALUES ('원티드', 20000, 1, 1, '2024-01-20 19:30:00', 'disable');

INSERT INTO performance_seat_info VALUES
 (DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 1, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 2, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 3, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 4, 'enable', DEFAULT, DEFAULT);
