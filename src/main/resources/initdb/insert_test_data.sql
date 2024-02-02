INSERT INTO `performance` (name, price, round, type, start_date, is_reserve)
VALUES ('레베카', 100000, 1, 1, '2024-01-20 19:30:00', 'enable');

INSERT INTO performance_seat_info VALUES
 (DEFAULT, (SELECT performance_id FROM performance  ORDER BY id desc LIMIT 1), 1, 1, 'A', 1, 'disable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT performance_id FROM performance  ORDER BY id desc LIMIT 1), 1, 1, 'A', 2, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT performance_id FROM performance  ORDER BY id desc LIMIT 1), 1, 1, 'A', 3, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT performance_id FROM performance  ORDER BY id desc LIMIT 1), 1, 1, 'A', 4, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT performance_id FROM performance  ORDER BY id desc LIMIT 1), 1, 1, 'B', 1, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT performance_id FROM performance  ORDER BY id desc LIMIT 1), 1, 1, 'B', 2, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT performance_id FROM performance  ORDER BY id desc LIMIT 1), 1, 1, 'B', 3, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT performance_id FROM performance  ORDER BY id desc LIMIT 1), 1, 1, 'B', 4, 'enable', DEFAULT, DEFAULT);

INSERT INTO `performance` (name, price, round, type, start_date, is_reserve)
VALUES ('오페라의유령', 120000, 1, 1, '2024-01-25 19:30:00', 'enable');

INSERT INTO performance_seat_info VALUES
 (DEFAULT, (SELECT performance_id FROM performance  ORDER BY id desc LIMIT 1), 1, 2, 'A', 1, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT performance_id FROM performance  ORDER BY id desc LIMIT 1), 1, 2, 'A', 2, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT performance_id FROM performance  ORDER BY id desc LIMIT 1), 1, 2, 'A', 3, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT performance_id FROM performance  ORDER BY id desc LIMIT 1), 1, 2, 'A', 4, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT performance_id FROM performance  ORDER BY id desc LIMIT 1), 1, 2, 'B', 1, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT performance_id FROM performance  ORDER BY id desc LIMIT 1), 1, 2, 'B', 2, 'disable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT performance_id FROM performance  ORDER BY id desc LIMIT 1), 1, 2, 'B', 3, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT performance_id FROM performance  ORDER BY id desc LIMIT 1), 1, 2, 'B', 4, 'enable', DEFAULT, DEFAULT);

INSERT INTO reservation (performance_id , name,phone_number,round,gate,line,seat)(SELECT A.id AS performance_id, '이수인' AS name, '010-0000-0000' AS phone_number, A.round, B.gate, B.line,B.seat FROM performance A JOIN performance_seat_info B ON A.id = B.performance_id  WHERE A.name = '레베카' AND B.line = 'A' AND B.seat = '1');
INSERT INTO reservation (performance_id , name,phone_number,round,gate,line,seat)(SELECT A.id AS performance_id, '이수인' AS name, '010-0000-0000' AS phone_number, A.round, B.gate, B.line,B.seat FROM performance A JOIN performance_seat_info B ON A.id = B.performance_id  WHERE A.name = '오페라의유령' AND B.line = 'B' AND B.seat = '2');
