INSERT INTO `performance` (name, price, round, type, start_date, is_reserve)
VALUES ('레베카', 100000, 1, 0, '2024-01-20 19:30:00', 'disable');

INSERT INTO performance_seat_info VALUES
 (DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 1, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 2, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 3, 'enable', DEFAULT, DEFAULT)
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 4, 'enable', DEFAULT, DEFAULT);

INSERT INTO user_info VALUES
(DEFAULT, 'TEST1', 'TEST1111', 'TEST1@TEST1.COM', '1111', '010-3232-1111', '2000-01-01 00:03:00', DEFAULT),
(DEFAULT, 'TEST2', 'TEST2222', 'TEST2@TEST1.COM', '1111', '010-4242-1111', '2000-06-02 00:03:00', DEFAULT),
(DEFAULT, 'TEST3', 'TEST3333', 'TEST3@TEST1.COM', '1111', '010-5252-1111', '2000-07-03 00:03:00', DEFAULT);

INSERT INTO payment_card VALUES
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST1'), 100000, '1234567890', '07/24', "777"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST1'), 200000, '3214567890', '07/24', "767"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST1'), 70000, '4321567890', '07/24', "757"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST1'), 150000, '1237654890', '07/24', "747"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST1'), 180000, '1234560987', '07/24', "737"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST2'), 100000, '0987654321', '07/24', "677"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST2'), 200000, '1234654321', '07/24', "667"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST2'), 70000, '5464254321', '07/24', "657"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST2'), 150000, '9801654321', '07/24', "647"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST2'), 180000, '4453654321', '07/24', "637"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST3'), 100000, '0987667676', '07/24', "577"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST3'), 200000, '1234690909', '07/24', "567"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST3'), 70000, '5464212121', '07/24', "557"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST3'), 150000, '9801634343', '07/24', "547"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST3'), 180000, '4453660393', '07/24', "537");
