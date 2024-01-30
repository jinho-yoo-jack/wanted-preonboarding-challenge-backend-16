DELETE FROM performance;
DELETE FROM performance_seat_info;

INSERT INTO `performance` (name, price, round, type, start_date, is_reserve)
VALUES ('ant', 100000, 1, 0, '2024-01-20 19:30:00', 'disable');

INSERT INTO performance_seat_info VALUES
(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 1, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 2, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 3, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 4, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 5, 'disable', DEFAULT, DEFAULT);

INSERT INTO `reservation` (performance_id, name, phone_number, round, gate, line, seat, created_at, updated_at) VALUES
((SELECT id FROM performance limit 1), 'KAI', '01012345678', 1, 1, 'A', 4, DEFAULT, DEFAULT);