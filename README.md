# Graduation project
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend. See more in expression of requirements: [graduation_en](/graduation_en.md) or [graduation_ru](/graduation_ru.md)

## Table of Content

* [Install & Run](#install--run)
* [REST API](#rest-api)
    + [User](#user)
      - [User Profile API](#user-profile-api)
      - [User Vote API](#user-vote-api)
      - [User Restaurant API](#user-restaurant-api)
    + [Admin](#admin)
      - [Admin Profile API](#admin-profile-api)
      - [Admin Vote API](#admin-vote-api)
      - [Admin Restaurant API](#admin-restaurant-api)
* [Requirements](#requirements)

## Install & Run

* See [Requirements](#requirements)
* Clone this repository from git and go to app dir:\
`git clone https://github.com/dimio/topjava_graduation.git lunch-vm`\
`cd lunch-vm`
* For quick test: deploy it with maven (DB is stored in RAM)\
`mvn clean package -P test cargo:run`\
all data will be destroyed after app is stopping
* For usage permanently (DB is stored in project dir):
    - install and first run with next command:\
    `mvn clean package -P install cargo:run`
    - resuming app usage after stopping available with this command:\
    `mvn package cargo:run`

Project will be deployed at *lunch_vm* application context

## REST API

Base path for this project (deployed into application context *lunch_vm*): `localhost:8080/lunch_vm/rest/`

### User

Authority: all authorized users (`/register` endpoint is available to unregistered users)

#### User Profile API:
Base URL: `localhost:8080/lunch_vm/rest`

|Endpoint|HTTP Verb|Functionality|Example (curl command)|Success Response Code|
|---|:---:|---|---|:---:|
|/profile/register|POST|Register a new user|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/profile/register -X POST -H 'Content-Type: application/json; charset=UTF-8' -d '{"name":"newName", "email":"newemail@ya.ru", "password":"newPassword"}'|201|
|/profile|GET|Get user profile|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/profile/ --user newemail@ya.ru:newPassword|200|
|/profile|PUT|Update user profile|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/profile -X PUT -H 'Content-Type:application/json;charset=UTF-8' --user newemail@ya.ru:newPassword -d '{"name":"New userName", "email":"newemail@yandex.ru", "password":"otherPassword"}'|204|
|/profile|DELETE|Delete user profile|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/profile -X DELETE --user newemail@yandex.ru:otherPassword|204|

<sub>[Back to ToC](#table-of-content)</sub>

#### User Vote API:
Base URL: `localhost:8080/lunch_vm/rest`

**NOTE:** *by default decision time is 11:00 AM*

|Endpoint|HTTP Verb|Functionality|Example (curl command)|Success Response Code|
|---|:---:|---|---|:---:|
|/votes|GET|Get all own votes|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/votes --user user@yandex.ru:password|200|
|/votes?date={date}|GET|Get own vote for specified date|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/votes?date=$(date +%Y-%m-%d) --user user@yandex.ru:password|200|
|/votes/restaurant/{restaurantId}|GET|Get votes for restaurant today|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/votes/restaurant/100002 --user user@yandex.ru:password|200|
|/votes/restaurant/{restaurantId}?dateFrom={date}&dateTo={date}|GET|Get votes for restaurant with dates between (blank date is equal today)|curl -w "\t%{http_code}\n" -s "http://localhost:8080/lunch_vm/rest/votes/restaurant/100002?dateFrom=2019-06-27&dateTo=$(date +%Y-%m-%d)" --user user@yandex.ru:password|200|
|/votes/restaurant/{restaurantId}|POST, PUT|To vote (only new votes of today accepted) for restaurant with restaurantId|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/votes/restaurant/100003 -X POST --user admin@gmail.com:admin|201|
|/votes/restaurant/{restaurantId}|PUT, POST|Change the vote today for another restaurant (**before decision time is end**)|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/votes/restaurant/100003 -X PUT --user user@yandex.ru:password|200|
|/votes/restaurant/{restaurantId}|PUT, POST|Change the vote today for another restaurant (**after decision time is end**)|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/votes/restaurant/100003 -X PUT --user user@yandex.ru:password|422|
|/votes|DELETE|Delete a today vote (**before decision time is end**)|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/votes -X DELETE --user user@yandex.ru:password|204|
|/votes|DELETE|Delete a today vote (**after decision time is end**)|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/votes -X DELETE --user user@yandex.ru:password|422|

<sub>[Back to ToC](#table-of-content)</sub>

#### User Restaurant API:
Base URL: `localhost:8080/lunch_vm/rest`

|Endpoint|HTTP Verb|Functionality|Example (curl command)|Success Response Code|
|---|:---:|---|---|:---:|
|/restaurants|GET|Get all restaurants with their menu (with menu items), which have a lunch menu for today|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/restaurants --user user@yandex.ru:password|200|
|/restaurants/{restaurantId}|GET|Get restaurant with specified ID|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/restaurants/100002 --user user@yandex.ru:password|200|
|/restaurants/byName?name={restaurantName}|GET|Get restaurant with specified name|curl -w "\t%{http_code}\n" -s "http://localhost:8080/lunch_vm/rest/restaurants/byName?name=Mc'Downalds" --user user@yandex.ru:password|200|
|/restaurants/{restaurantId}/menus|GET|Get all menus for restaurant|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/restaurants/100002/menus --user user@yandex.ru:password|200|
|/restaurants/{restaurantId}/menus/{menuId}|GET|Get menu (without menu items) for restaurant|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/restaurants/100002/menus/100005 --user user@yandex.ru:password|200|
|/restaurants/{restaurantId}/menus/{menuId}/meals|GET|Get all menu items only (i.e. meals) for menu|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/restaurants/100002/menus/100005/meals --user user@yandex.ru:password|200|
|/restaurants/{restaurantId}/menus/{menuId}/meals/{mealId}|GET|Get menu item (meal) for menu|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/restaurants/100002/menus/100005/meals/100018 --user user@yandex.ru:password|200|

<sub>[Back to ToC](#table-of-content)</sub>


### Admin:

Authority: admins only (who has role `ROLE_ADMIN`)

#### Admin Profile API:
Base URL: `localhost:8080/lunch_vm/rest/admin`

|Endpoint|HTTP Verb|Functionality|Example (curl command)|Success Response Code|
|---|:---:|---|---|:---:|
|/users|POST|Create new user|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/users -X POST -H 'Content-Type: application/json; charset=UTF-8' --user admin@gmail.com:admin -d '{"name":"newName", "email":"newemail@gmail.com", "password":"newPassword"}'|201|
|/users|GET|Get all users|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/users --user admin@gmail.com:admin|200|
|/users/{id}|GET|Get user profile with specified ID|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/users/100000 --user admin@gmail.com:admin|200|
|/users/by?email={email}|GET|Get user with specified email|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/users/by?email=admin@gmail.com --user admin@gmail.com:admin|200|
|/users/{id}|PUT|Change user profile|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/users/100000 -X PUT -H 'Content-Type:application/json;charset=UTF-8' --user admin@gmail.com:admin -d '{"name":"Updated User name", "email":"newmail@yandex.ru", "password":"password"}'|204|
|/users/{id}|DELETE|Delete user profile|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/users/100000 -X DELETE --user admin@gmail.com:admin|204|

<sub>[Back to ToC](#table-of-content)</sub>

#### Admin Vote API:
Base URL: `localhost:8080/lunch_vm/rest/admin`

|Endpoint|HTTP Verb|Functionality|Example (curl command)|Success Response Code|
|---|:---:|---|---|:---:|
|/votes/user/{userId}|GET|Get all user votes|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/votes/user/100001 --user admin@gmail.com:admin|200|
|/votes/user/{userId}?date={date}|GET|Get user vote for specified date|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/votes/user/100001?date=2019-06-27 --user admin@gmail.com:admin|200|
|/votes/restaurant/{restaurantId}|GET|Get all votes for restaurant|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/votes/restaurant/100002 --user admin@gmail.com:admin|200|

<sub>[Back to ToC](#table-of-content)</sub>

#### Admin Restaurant API:
Base URL: `localhost:8080/lunch_vm/rest/admin`

|Endpoint|HTTP Verb|Functionality|Example (curl command)|Success Response Code|
|---|:---:|---|---|:---:|
|/restaurants|GET|Get all restaurants|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants --user admin@gmail.com:admin|200|
|/restaurants?sort={sort}&direction={direction}|GET|Get all restaurants sorted by "sort" and "direction" ASC or DESC (default is ASC)|curl -w "\t%{http_code}\n" -s "http://localhost:8080/lunch_vm/rest/admin/restaurants?sort=name&direction=desc" --user admin@gmail.com:admin|200|
|/restaurants|POST|Add new restaurant|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants -X POST -H 'Content-Type: application/json; charset=UTF-8' --user admin@gmail.com:admin -d '{"name":"newRestaurant"}'|201|
|/restaurants/{restaurantId}|PUT|Update restaurant with specified ID|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants/100002 -X PUT -H 'Content-Type:application/json;charset=UTF-8' --user admin@gmail.com:admin -d '{"name":"New restaurant name"}'|204|
|/restaurants/{restaurantId}|DELETE|Delete restaurant with specified ID|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants/100002 -X DELETE --user admin@gmail.com:admin|204|
|/restaurants/{restaurantId}/menus|POST|Add new menu to restaurant|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants/100003/menus -X POST -H 'Content-Type: application/json; charset=UTF-8' --user admin@gmail.com:admin -d '{"actual": "'$(date +%Y-%m-%d)'"}'|201|
|/restaurants/{restaurantId}/menus/{menuId}|PUT|Update menu for restaurant|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants/100003/menus/100008 -X PUT -H 'Content-Type: application/json; charset=UTF-8' --user admin@gmail.com:admin -d '{"actual": "2020-03-03"}'|204|
|/restaurants/{restaurantId}/menus/{menuId}|DELETE|Delete restaurant menu|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants/100003/menus/100008 -X DELETE --user admin@gmail.com:admin|204|
|/restaurants/{restaurantId}/menus/{menuId}/meals|POST|Add new menu item (meal) to menu|curl -w "\t%{http_code}\n" -s 'http://localhost:8080/lunch_vm/rest/admin/restaurants/100004/menus/100011/meals' -X POST -H 'Content-Type: application/json; charset=UTF-8' --user admin@gmail.com:admin -d '{"name":"Coffee Latte", "price":355}'|201|
|/restaurants/{restaurantId}/menus/{menuId}/meals/{mealId}|PUT|Update menu item|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants/100004/menus/100011/meals/100029 -X PUT -H 'Content-Type: application/json; charset=UTF-8' --user admin@gmail.com:admin -d '{"name":"Coffee", "price":533}'|204|
|/restaurants/{restaurantId}/menus/{menuId}/meals/{mealId}|DELETE|Delete menu item|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants/100004/menus/100011/meals/100029 -X DELETE --user admin@gmail.com:admin|204|

<sub>[Back to ToC](#table-of-content)</sub>

## Requirements

* JDK 11 or later (you can use a free [Oracle OpenJDK](https://openjdk.java.net/) or another)
* [Apache Maven](https://maven.apache.org/) for build and run the project

<sub>[Back to ToC](#table-of-content)</sub>
