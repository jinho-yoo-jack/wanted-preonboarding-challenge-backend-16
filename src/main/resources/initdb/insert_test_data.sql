INSERT INTO `performance` (name, price, round, type, start_date, is_reserve)
VALUES ('Rebecca', 100000, 1, 0, '2024-01-20 19:30:00', 'enable');

INSERT INTO `performance` (name, price, round, type, start_date, is_reserve)
VALUES ('Dracula', 120000, 2, 0, '2024-01-31 20:00:00', 'enable');

INSERT INTO performance_seat_info VALUES
 (DEFAULT, (SELECT id FROM performance WHERE name="Rebecca"), 1, 1, 'A', 1, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance WHERE name="Rebecca"), 1, 1, 'A', 2, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance WHERE name="Rebecca"), 1, 1, 'A', 3, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance WHERE name="Rebecca"), 1, 1, 'A', 4, 'enable', DEFAULT, DEFAULT);

INSERT INTO performance_seat_info VALUES
 (DEFAULT, (SELECT id FROM performance WHERE name="Dracula"), 2, 1, 'A', 1, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance WHERE name="Dracula"), 2, 1, 'A', 2, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance WHERE name="Dracula"), 2, 1, 'A', 3, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance WHERE name="Dracula"), 2, 1, 'A', 4, 'enable', DEFAULT, DEFAULT);

INSERT INTO user_info (user_name, phone_number, total_amount) VALUES ("name01", "010-1111-1111", 1000000);
INSERT INTO user_info (user_name, phone_number, total_amount) VALUES ("name02", "010-2222-2222", 2000000);

