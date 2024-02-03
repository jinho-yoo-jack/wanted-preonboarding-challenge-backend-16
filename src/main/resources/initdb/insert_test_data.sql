INSERT INTO performance (performance_name, price, round, type, start_date, reserved, performance_id)
VALUES ('레베카', 100000, 1, 0, '2024-01-20 19:30:00', false, UNHEX(REPLACE(UUID(), '-', '')));

INSERT INTO performance (performance_name, price, round, type, start_date, reserved, performance_id)
VALUES ('오페라의유령', 100000, 1, 0, '2024-01-20 19:30:00', false, UNHEX(REPLACE(UUID(), '-', '')));

INSERT INTO performance_seat_info (gate, reserved, line, round, seat, performance_id)
VALUES (1, false, 'A', 1, 1, (SELECT performance_id FROM wanted.performance limit 1))
     , (1, false, 'A', 1, 2, (SELECT performance_id FROM wanted.performance limit 1))
     , (1, false, 'A', 1, 3, (SELECT performance_id FROM wanted.performance limit 1))
     , (1, false, 'A', 1, 4, (SELECT performance_id FROM wanted.performance limit 1));
