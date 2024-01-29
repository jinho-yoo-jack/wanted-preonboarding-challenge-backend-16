INSERT INTO `performance` (name, price, round, type, start_date, is_reserve)
VALUES ('레베카', 100000, 1, 0, '2024-01-20 19:30:00', 'disable');
INSERT INTO `performance` (name, price, round, type, start_date, is_reserve)
VALUES ('웃는남자', 200000, 2, 0, '2024-01-30 19:30:00', 'enable');

INSERT INTO performance_seat_info VALUES
 (DEFAULT, (SELECT id FROM performance where name = '레베카'), 1, 1, 'A', 1, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance where name = '레베카'), 1, 1, 'A', 2, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance where name = '레베카'), 1, 1, 'A', 3, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance where name = '레베카'), 1, 1, 'A', 4, 'enable', DEFAULT, DEFAULT);

INSERT INTO performance_seat_info VALUES
(DEFAULT, (SELECT id FROM performance where name = '웃는남자'), 2, 3, 'B', 1, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance where name = '웃는남자'), 2, 3, 'B', 2, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance where name = '웃는남자'), 2, 3, 'B', 3, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance where name = '웃는남자'), 2, 3, 'B', 4, 'enable', DEFAULT, DEFAULT);
