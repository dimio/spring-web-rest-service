-- noinspection SqlWithoutWhereForFile

DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM menus;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE vote_seq RESTART WITH 1000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001),
       ('ROLE_USER', 100001);

INSERT INTO restaurants(name)
VALUES ('McDownalds'),
       ('Dock Clownalds');

INSERT INTO menus(restaurant_id, added, dishes, price_int, price_fract)
VALUES (100002, '2019-06-27 10:00:00', 'A, B', 99, 99),
       (100002, '2019-06-27 10:02:00', 'B, C', 89, 00),
       (100002, '2019-06-28 10:00:00', 'A, C', 101, 55),
       (100002, '2019-06-28 10:02:00', 'A, D', 99, 01),

       (100003, '2019-06-27 10:00:00', 'A, B', 89, 99),
       (100003, '2019-06-27 10:02:00', 'B, C', 100, 00),
       (100003, '2019-06-28 10:00:00', 'A, D', 101, 55),
       (100003, '2019-06-28 10:02:00', 'B, D', 98, 30);

INSERT INTO votes (user_id, restaurant_id, date_time)
VALUES (100000, 100002, '2019-06-27 10:00:00'),
       (100001, 100002, '2019-06-27 10:30:00'),
       (100000, 100002, '2019-06-28 10:00:00'),
       (100001, 100003, '2019-06-28 10:59:00');
