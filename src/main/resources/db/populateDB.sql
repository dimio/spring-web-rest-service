-- noinspection SqlWithoutWhereForFile

DELETE
FROM user_roles;
DELETE
FROM votes;
DELETE
FROM users;
DELETE
FROM restaurants;
DELETE
FROM menus;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001),
       ('ROLE_USER', 100001);

INSERT INTO restaurants(name)
-- ID's: 100002, 100003
VALUES ('McDownalds'),
       ('Dock Clownalds');

INSERT INTO menus(restaurant_id, actual, dishes, price_int, price_fract)
VALUES (100002, '2019-06-27', 'A, B', 99, 99),
       (100002, '2019-06-28', 'A, C', 101, 55),
       (100002, '2019-06-29', 'A, D', 101, 55),

       (100003, '2019-06-27', 'B, C', 100, 00),
       (100003, '2019-06-28', 'B, D', 98, 30),
       (100003, '2019-06-29', 'B, A', 98, 30);

INSERT INTO votes (user_id, restaurant_id, date)
VALUES (100000, 100002, '2019-06-27'),
       (100001, 100002, '2019-06-27'),
       (100000, 100002, '2019-06-28'),
       (100001, 100003, '2019-06-28');
