INSERT INTO `performance` (name, price, round, type, start_date, is_reserve) VALUES
('레베카', 100000, 1, 0, '2024-01-20 19:30:00', 'disable'),
('영웅', 100000, 1, 1, '2024-02-27 17:30:00', 'enable'),
('영웅', 100000, 2, 1, '2024-02-27 19:30:00', 'enable'),
('캣츠', 100000, 1, 1, '2024-02-29 19:30:00', 'disable'),
('노트르담의 곱추', 100000, 1, 1, '2024-01-21 19:30:00', 'disable'),
('레 미제라블', 100000, 1, 1, '2024-02-22 19:30:00', 'enable');


INSERT INTO performance_seat_info VALUES
(DEFAULT, (SELECT id FROM performance WHERE name = '레베카' limit 1), 1, 1, 'A', 1, 'enable', DEFAULT, DEFAULT),
(DEFAULT, (SELECT id FROM performance WHERE name = '레베카' limit 1), 1, 1, 'A', 2, 'enable', DEFAULT, DEFAULT),
(DEFAULT, (SELECT id FROM performance WHERE name = '레베카' limit 1), 1, 1, 'A', 3, 'enable', DEFAULT, DEFAULT),
(DEFAULT, (SELECT id FROM performance WHERE name = '레베카' limit 1), 1, 1, 'A', 4, 'enable', DEFAULT, DEFAULT),
(DEFAULT, (SELECT id FROM performance WHERE name = '영웅' AND round = 1 limit 1), 1, 1, 'A', 1, 'enable', DEFAULT, DEFAULT),
(DEFAULT, (SELECT id FROM performance WHERE name = '영웅' AND round = 1 limit 1), 1, 1, 'A', 2, 'enable', DEFAULT, DEFAULT),
(DEFAULT, (SELECT id FROM performance WHERE name = '영웅' AND round = 1 limit 1), 1, 1, 'A', 3, 'enable', DEFAULT, DEFAULT),
(DEFAULT, (SELECT id FROM performance WHERE name = '영웅' AND round = 1 limit 1), 1, 1, 'A', 4, 'enable', DEFAULT, DEFAULT),
(DEFAULT, (SELECT id FROM performance WHERE name = '영웅' AND round = 2 limit 1), 2, 1, 'B', 2, 'enable', DEFAULT, DEFAULT),
(DEFAULT, (SELECT id FROM performance WHERE name = '영웅' AND round = 2 limit 1), 2, 1, 'B', 3, 'enable', DEFAULT, DEFAULT),
(DEFAULT, (SELECT id FROM performance WHERE name = '영웅' AND round = 2 limit 1), 2, 1, 'B', 4, 'enable', DEFAULT, DEFAULT),
(DEFAULT, (SELECT id FROM performance WHERE name = '레 미제라블' AND round = 1 limit 1), 1, 1, 'A', 1, 'enable', DEFAULT, DEFAULT),
(DEFAULT, (SELECT id FROM performance WHERE name = '레 미제라블' AND round = 1 limit 1), 1, 1, 'A', 2, 'enable', DEFAULT, DEFAULT),
(DEFAULT, (SELECT id FROM performance WHERE name = '레 미제라블' AND round = 1 limit 1), 1, 1, 'A', 3, 'enable', DEFAULT, DEFAULT),
(DEFAULT, (SELECT id FROM performance WHERE name = '레 미제라블' AND round = 1 limit 1), 1, 1, 'A', 4, 'enable', DEFAULT, DEFAULT);

INSERT INTO user_info VALUES
(DEFAULT, 'TEST1', 'TEST1111', 'TEST1@TEST1.COM', '1111', '010-3232-1111', '2000-01-01 00:03:00', DEFAULT, 1),
(DEFAULT, 'TEST2', 'TEST2222', 'TEST2@TEST1.COM', '1111', '010-4242-1111', '2000-06-02 00:03:00', DEFAULT, 1),
(DEFAULT, 'TEST3', 'TEST3333', 'TEST3@TEST1.COM', '1111', '010-5252-1111', '2000-07-03 00:03:00', DEFAULT, 1);

INSERT INTO payment_card VALUES
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST1'), 100000, '신한카드', '1234567890', '07/24', "777"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST1'), 200000, '국민카드','3214567890', '07/24', "767"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST1'), 70000, '농협카드','4321567890', '07/24', "757"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST1'), 150000, '수협카드','1237654890', '07/24', "747"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST1'), 180000, '축협카드','1234560987', '07/24', "737"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST2'), 100000, '우리카드','0987654321', '07/24', "677"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST2'), 200000, '신한카드','1234654321', '07/24', "667"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST2'), 70000, '국민카드','5464254321', '07/24', "657"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST2'), 150000, '농협카드','9801654321', '07/24', "647"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST2'), 180000, '수협카드','4453654321', '07/24', "637"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST3'), 100000, '축협카드','0987667676', '07/24', "577"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST3'), 200000, '우리카드','1234690909', '07/24', "567"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST3'), 70000, '신한카드','5464212121', '07/24', "557"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST3'), 150000, '국민카드','9801634343', '07/24', "547"),
(DEFAULT, (SELECT user_uuid from user_info where name = 'TEST3'), 180000, '농협카드','4453660393', '07/24', "537");

UPDATE user_info
SET default_payment_code =
(
	SELECT id
	FROM payment_card
	WHERE user_uuid =
    (
		select *
		FROM
			(
				select user_uuid from user_info where name = 'TEST1' limit 1
			) u
		limit 1
	) limit 1
)
WHERE name = 'TEST1';

UPDATE user_info
SET default_payment_code =
(
	SELECT id
	FROM payment_card
	WHERE user_uuid =
    (
		select *
		FROM
			(
				select user_uuid from user_info where name = 'TEST2' limit 1
			) u
		limit 1
	) limit 1
)
WHERE name = 'TEST2';

UPDATE user_info
SET default_payment_code =
(
	SELECT id
	FROM payment_card
	WHERE user_uuid =
    (
		select *
		FROM
			(
				select user_uuid from user_info where name = 'TEST3' limit 1
			) u
		limit 1
	) limit 1
)
WHERE name = 'TEST3';