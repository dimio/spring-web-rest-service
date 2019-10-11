-- noinspection SqlWithoutWhereForFile

DELETE
FROM user_roles;
DELETE
FROM votes;
DELETE
FROM users;
DELETE
FROM meals;
DELETE
FROM menus;
DELETE
FROM restaurants;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
-- ID's: 100000, 100001
VALUES ('ROLE_USER', (SELECT ID FROM USERS WHERE NAME = 'User')),
       ('ROLE_ADMIN', (SELECT ID FROM USERS WHERE NAME = 'Admin')),
       ('ROLE_USER', (SELECT ID FROM USERS WHERE NAME = 'Admin'));

INSERT INTO restaurants(name)
-- ID's: 100002, 100003, 100004
VALUES ('Mc''Downalds'),
       ('Dock Clownalds'),
--        no menus for actual date
       ('Not this time');

INSERT INTO menus(restaurant_id, actual)
VALUES ((SELECT ID FROM RESTAURANTS WHERE NAME = 'Mc''Downalds'), '2019-06-27'),
       ((SELECT ID FROM RESTAURANTS WHERE NAME = 'Mc''Downalds'), '2019-06-28'),
       ((SELECT ID FROM RESTAURANTS WHERE NAME = 'Mc''Downalds'), now()),

       ((SELECT ID FROM RESTAURANTS WHERE NAME = 'Dock Clownalds'), '2019-06-27'),
       ((SELECT ID FROM RESTAURANTS WHERE NAME = 'Dock Clownalds'), '2019-06-28'),
       ((SELECT ID FROM RESTAURANTS WHERE NAME = 'Dock Clownalds'), now()),

       ((SELECT ID FROM RESTAURANTS WHERE NAME = 'Not this time'), '2019-06-27');

INSERT INTO votes (user_id, restaurant_id, date)
VALUES ((SELECT USERS.ID FROM USERS WHERE NAME = 'User'),
        (SELECT ID FROM RESTAURANTS WHERE NAME = 'Mc''Downalds'), '2019-06-27'),
       ((SELECT USERS.ID FROM USERS WHERE NAME = 'Admin'),
        (SELECT ID FROM RESTAURANTS WHERE NAME = 'Mc''Downalds'), '2019-06-27'),
       ((SELECT USERS.ID FROM USERS WHERE NAME = 'User'),
        (SELECT ID FROM RESTAURANTS WHERE NAME = 'Mc''Downalds'), '2019-06-28'),
       ((SELECT USERS.ID FROM USERS WHERE NAME = 'Admin'),
        (SELECT ID FROM RESTAURANTS WHERE NAME = 'Dock Clownalds'), '2019-06-28'),
       ((SELECT USERS.ID FROM USERS WHERE NAME = 'User'),
        (SELECT ID FROM RESTAURANTS WHERE NAME = 'Mc''Downalds'), now()),
       ((SELECT USERS.ID FROM USERS WHERE NAME = 'Admin'),
        (SELECT ID FROM RESTAURANTS WHERE NAME = 'Dock Clownalds'), now());

-- Mc'Downalds meals
INSERT INTO meals (menu_id, name, price)
VALUES ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Mc''Downalds')
           AND m.ACTUAL = '2019-06-27'), 'Big Muck', 10.96 * 100),
       ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Mc''Downalds')
           AND m.ACTUAL = '2019-06-27'), 'Moo Duck', 5.75 * 100),
       ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Mc''Downalds')
           AND m.ACTUAL = '2019-06-28'), 'Big Muck', 10.96 * 100),
       ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Mc''Downalds')
           AND m.ACTUAL = '2019-06-28'), 'Glue Cola', 1.00 * 100),
       ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Mc''Downalds')
           AND m.ACTUAL = current_date), 'Big Muck', 10.96 * 100),
       ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Mc''Downalds')
           AND m.ACTUAL = current_date), 'What the Funk', 100.50 * 100);

-- Dock Clownalds meals
INSERT INTO meals (menu_id, name, price)
VALUES ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Dock Clownalds')
           AND m.ACTUAL = '2019-06-27'), 'Burger', 2.96 * 100),
       ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Dock Clownalds')
           AND m.ACTUAL = '2019-06-27'), 'Tea', 0.75 * 100),
       ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Dock Clownalds')
           AND m.ACTUAL = '2019-06-28'), 'Coffee', 1.5 * 100),
       ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Dock Clownalds')
           AND m.ACTUAL = '2019-06-28'), 'Sandwich', 3.00 * 100),
       ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Dock Clownalds')
           AND m.ACTUAL = current_date), 'Soup', 8.96 * 100);

-- Not this time meals
INSERT INTO meals (menu_id, name, price)
VALUES ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Not this time')
           AND m.ACTUAL = '2019-06-27'), 'Roast beef', 10.55 * 100);