INSERT INTO `performance` (name, price, round, type, start_date, is_reserve) VALUES
('레베카', 100000, 1, 0, '2024-01-20 19:30:00', 'disable'),
('태양의 서커스', 200000, 1, 1, '2024-02-03 09:00:00', DEFAULT),
('그날들', 150000, 1, 1, '2024-02-04 21:00:00', DEFAULT),
('살바도르 달리전', 8000, 1, 1, '2024-02-03 18:00:00', 'enable'),
('살바도르 달리전', 8000, 2, 1, '2024-02-03 18:00:00', 'enable'),
('살바도르 달리전', 8000, 3, 1, '2024-02-03 19:00:00', 'enable');

INSERT INTO performance_seat_info VALUES
(DEFAULT, (SELECT id FROM performance limit 1), 1, 'A', 1, 'enable', DEFAULT, DEFAULT),
(DEFAULT, (SELECT id FROM performance limit 1), 1, 'A', 2, 'enable', DEFAULT, DEFAULT),
(DEFAULT, (SELECT id FROM performance limit 1), 1, 'A', 3, 'enable', DEFAULT, DEFAULT),
(DEFAULT, (SELECT id FROM performance limit 1), 1, 'A', 4, 'enable', DEFAULT, DEFAULT);

INSERT INTO `user_info` VALUES
(DEFAULT, '박영서', '010-8962-1061'),
(DEFAULT, '테스터', '010-0012-0123');

INSERT INTO `reservation` VALUES
(DEFAULT, 1, 1, true, DEFAULT, DEFAULT),
(DEFAULT, 2, 2, false, DEFAULT, DEFAULT);