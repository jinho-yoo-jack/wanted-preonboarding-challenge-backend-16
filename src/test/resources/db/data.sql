INSERT INTO `users` (name, phone_number)
VALUES ('원티드', '010-1112-2223');

INSERT INTO `performance` (name, price, round, type, start_date, is_reserve)
VALUES ('레베카', 100000, 1, 0, '2024-01-20 19:30:00', 'enable');

INSERT INTO `performance` (id, name, price, round, type, start_date, is_reserve)
VALUES (DEFAULT, '공연 이름', 10000, 1, 1, '2024-01-01 20:00:00', 'disable');

INSERT INTO `performance_seat_info` VALUES
(1, (SELECT id FROM performance WHERE name = '레베카' limit 1), 1, 1, 'A', 1, 'enable', DEFAULT, DEFAULT)
                                         ,(2, (SELECT id FROM performance WHERE name = '레베카' limit 1), 1, 1, 'A', 2, 'enable', DEFAULT, DEFAULT)
                                         ,(3, (SELECT id FROM performance WHERE name = '레베카' limit 1), 1, 1, 'A', 3, 'disable', DEFAULT, DEFAULT)
                                         ,(4, (SELECT id FROM performance WHERE name = '레베카' limit 1), 1, 1, 'A', 4, 'disable', DEFAULT, DEFAULT);

INSERT INTO `performance_seat_info` VALUES
(5, (SELECT id FROM performance WHERE name = '공연 이름' limit 1), 1, 1, 'A', 1, 'enable', DEFAULT, DEFAULT)
                                         ,(6, (SELECT id FROM performance WHERE name = '공연 이름' limit 1), 1, 1, 'A', 2, 'enable', DEFAULT, DEFAULT)
                                         ,(7, (SELECT id FROM performance WHERE name = '공연 이름' limit 1), 1, 1, 'A', 3, 'disable', DEFAULT, DEFAULT)
                                         ,(8, (SELECT id FROM performance WHERE name = '공연 이름' limit 1), 1, 1, 'A', 4, 'disable', DEFAULT, DEFAULT);