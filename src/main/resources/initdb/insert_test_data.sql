INSERT INTO `performance` (name, price, round, type, start_date, is_reserve)
VALUES ('Rebecca', 100000, 1, 0, '2024-01-20 19:30:00', 'enable');

INSERT INTO `performance` (name, price, round, type, start_date, is_reserve)
VALUES ('Les Miserables', 100000, 2, 0, '2024-01-20 19:30:00', 'enable');

INSERT INTO performance_seat_info VALUES
 (DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 1, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 2, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 3, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 4, 'enable', DEFAULT, DEFAULT);

