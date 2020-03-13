# Graduation project
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend. See more in expression of requirements: [graduation_en](https://github.com/dimio/topjava_graduation/blob/master/graduation_en.md) or [graduation_ru](https://github.com/dimio/topjava_graduation/blob/master/graduation_ru.md)

## REST API
Base path: `localhost:8080/rest`

Base path for this project (deployed into application context *topjava_graduation*): `localhost:8080/topjava_graduation/rest/`


### User

Authority: all authorized users (`/register` endpoint is available to unregistered users)

#### Profile API:
Base URL: `localhost:8080/topjava_graduation/rest`

|Endpoint|HTTP Verb|Functionality|Example (curl command)|Success Response Code|
|---|:---:|---|---|:---:|
|/profile/register|POST|Register a new user|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/profile/register -X POST -H 'Content-Type: application/json; charset=UTF-8' -d '{"name":"newName", "email":"newemail@ya.ru", "password":"newPassword"}'|201|
|/profile|GET|Get user profile|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/profile/ --user newemail@ya.ru:newPassword|200|
|/profile|PUT|Update user profile|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/profile -X PUT -H 'Content-Type:application/json;charset=UTF-8' --user newemail@ya.ru:newPassword -d '{"name":"New userName", "email":"newemail@yandex.ru", "password":"otherPassword"}'|204|
|/profile|DELETE|Delete user profile|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/profile -X DELETE --user newemail@yandex.ru:otherPassword|204|

#### Vote API:
Base URL: `localhost:8080/topjava_graduation/rest`

**NOTE:** *by default decision time is 11:00 AM*

|Endpoint|HTTP Verb|Functionality|Example (curl command)|Success Response Code|
|---|:---:|---|---|:---:|
|/votes|GET|Get all own votes|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/votes --user user@yandex.ru:password|200|
|/votes?date={date}|GET|Get own vote for specified date|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/votes?date=$(date +%Y-%m-%d) --user user@yandex.ru:password|200|
|/votes/restaurant/{restaurantId}?dateFrom={date}&dateTo={date}|GET|Get votes for restaurant with dates between (blank date is equal today)|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/votes/restaurant/100002?dateFrom=2019-06-27&dateTo=$(date +%Y-%m-%d) --user user@yandex.ru:password|200|
|/votes/restaurant/{restaurantId}|POST, PUT|To vote (only new votes of today accepted) for restaurant with restaurantId|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/votes/restaurant/100003 -X POST --user admin@gmail.com:admin|201|
|/votes/restaurant/{restaurantId}|PUT, POST|Change the vote today for another restaurant (**before decision time is end**)|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/votes/restaurant/100003 -X PUT --user user@yandex.ru:password|200|
|/votes/restaurant/{restaurantId}|PUT, POST|Change the vote today for another restaurant (**after decision time is end**)|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/votes/restaurant/100003 -X PUT --user user@yandex.ru:password|422|
|/votes|DELETE|Delete a today vote (**before decision time is end**)|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/votes -X DELETE --user user@yandex.ru:password|204|
|/votes|DELETE|Delete a today vote (**after decision time is end**)|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/votes -X DELETE --user user@yandex.ru:password|422|

#### Restaurant API:
Base URL: `localhost:8080/topjava_graduation/rest`

|Endpoint|HTTP Verb|Functionality|Example (curl command)|Success Response Code|
|---|:---:|---|---|:---:|
|/restaurants|GET|Get all restaurants with their menu, which have a lunch menu for current date|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/restaurants --user user@yandex.ru:password|200|
|/restaurants/{restaurantId}|GET|Get restaurant with specified ID|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/profile/restaurants/100002 --user user@yandex.ru:password|200|
|/restaurants/byName?name={restaurantName}|GET|Get restaurant with specified name|curl -w "\t%{http_code}" -s 'http://localhost:8080/topjava_graduation/rest/restaurants/?name=Mc\\'Downalds' --user user@yandex.ru:password|200|
|/restaurants/{restaurantId}/menus|GET|Get all menus for restaurant|curl -w "\t%{http_code}" 'http://localhost:8080/topjava_graduation/rest/profile/restaurants/100002/menus --user user@yandex.ru:password|200|
|/restaurants/{restaurantId}/menus/{menuId}|GET|Get menu (with menu items) for restaurant|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/restaurants/100002/menus/100005 --user user@yandex.ru:password|200|
|/restaurants/{restaurantId}/menus/{menuId}/meals|GET|Get all menu items only (i.e. meals) for menu|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/profile/restaurants/100002/menus/100005/meals --user user@yandex.ru:password|200|
|/restaurants/{restaurantId}/menus/{menuId}/meals/{mealId}|GET|Get menu item (meal) for menu|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/restaurants/100002/menus/100005/meals/100018 --user user@yandex.ru:password|200|


### Admin:

Authority: admins only (who has role `ROLE_ADMIN`)

#### Profile API:
Base URL: `localhost:8080/topjava_graduation/rest/admin`

|Endpoint|HTTP Verb|Functionality|Example (curl command)|Success Response Code|
|---|:---:|---|---|:---:|
|/users|POST|Create new user|curl -w "\t%{http_code}" http://localhost:8080/topjava_graduation/rest/admin/users -X POST -H 'Content-Type: application/json; charset=UTF-8' -d '{"name":"newName", "email":"newemail@gmail.com", "password":"newPassword"}' --user admin@gmail.com:admin|201|
|/users|GET|Get all users|curl -w "\t%{http_code}" http://localhost:8080/topjava_graduation/rest/admin/users --user admin@gmail.com:admin|200|
|/users/{id}|GET|Get user profile with specified ID|curl -w "\t%{http_code}" http://localhost:8080/topjava_graduation/rest/admin/users/100000 --user admin@gmail.com:admin|200|
|/users/by?email={email}|GET|Get user with specified email|curl -w "\t%{http_code}" http://localhost:8080/topjava_graduation/rest/admin/users/by?email=admin@gmail.com --user admin@gmail.com:admin|200|
|/users/{id}|PUT|Change user profile|curl -w "\t%{http_code}" -X PUT http://localhost:8080/topjava_graduation/rest/admin/users/100000 -d '{"name":"Updated User name", "email":"newmail@yandex.ru", "password":"password"}' -H 'Content-Type:application/json;charset=UTF-8' --user admin@gmail.com:admin|204|
|/users/{id}|DELETE|Delete user profile|curl -w "\t%{http_code}" -X DELETE http://localhost:8080/topjava_graduation/rest/admin/users/100000 --user admin@gmail.com:admin|204|

#### Vote API:
Base URL: `localhost:8080/topjava_graduation/rest/admin`

|Endpoint|HTTP Verb|Functionality|Example (curl command)|Success Response Code|
|---|:---:|---|---|:---:|
|/votes/user/{userId}|GET|Get all user votes|curl -w "\t%{http_code}" http://localhost:8080/topjava_graduation/rest/admin/votes/user/100001 --user admin@gmail.com:admin|200|
|/votes/user/{userId}?date={date}|GET|Get user vote for specified date|curl -w "\t%{http_code}" http://localhost:8080/topjava_graduation/rest/admin/votes/user/100001?date=2019-06-27 --user admin@gmail.com:admin|200|
|/votes/restaurant/{restaurantId}|GET|Get all votes for restaurant|curl -w "\t%{http_code}" http://localhost:8080/topjava_graduation/rest/admin/votes/restaurant/100002 --user admin@gmail.com:admin|200|
|/votes/restaurant/{restaurantId}?date={date}|GET|Get votes for restaurant and for specified date|curl -w "\t%{http_code}" http://localhost:8080/topjava_graduation/rest/admin/votes/restaurant/100002?date=2019-06-27 --user admin@gmail.com:admin|200|

#### Restaurant API:
Base URL: `localhost:8080/topjava_graduation/rest/admin`

|Endpoint|HTTP Verb|Functionality|Example (curl command)|Success Response Code|
|---|:---:|---|---|:---:|
|/restaurants|GET|Get all restaurants|curl -w "\t%{http_code}" http://localhost:8080/topjava_graduation/rest/admin/restaurants --user admin@gmail.com:admin|200|
|/restaurants|POST|Add new restaurant|curl -w "\t%{http_code}" http://localhost:8080/topjava_graduation/rest/admin/restaurants -X POST -H 'Content-Type: application/json; charset=UTF-8' -d '{"name":"newRestaurant"}' --user admin@gmail.com:admin|201|
|/restaurants/{restaurantId}|PUT|Update restaurant with specified ID|curl -w "\t%{http_code}" http://localhost:8080/topjava_graduation/rest/admin/restaurants -X PUT http://localhost:8080/topjava_graduation/rest/admin/restaurants/100002 -d '{"name":"New restaurant name"}' -H 'Content-Type:application/json;charset=UTF-8' --user admin@gmail.com:admin|204|
|/restaurants/{restaurantId}|DELETE|Delete restaurant with specified ID|curl -w "\t%{http_code}" http://localhost:8080/topjava_graduation/rest/admin/restaurants -X DELETE http://localhost:8080/topjava_graduation/rest/admin/restaurants/100002 --user admin@gmail.com:admin|204|
|/restaurants/{restaurantId}/menus|POST|Add new menu to restaurant|curl -w "\t%{http_code}" http://localhost:8080/topjava_graduation/rest/admin/restaurants/100003/menus -X POST -H 'Content-Type: application/json; charset=UTF-8' --user admin@gmail.com:admin -d '{"actual": "'$(date +%Y-%m-%d)'", "meals": []}'|201|
|/restaurants/{restaurantId}/menus/{menuId}|PUT|Update menu for restaurant|curl -w "\t%{http_code}" http://localhost:8080/topjava_graduation/rest/admin/restaurants/100003/menus/100004 -X PUT -H 'Content-Type: application/json; charset=UTF-8' --user admin@gmail.com:admin -d '{"actual": "2020-03-03", "meals": []}'|204|
|/restaurants/{restaurantId}/menus/{menuId}|DELETE|Delete restaurant menu|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/admin/restaurants/100003/menus/100004 -X DELETE --user admin@gmail.com:admin|204|
|/restaurants/{restaurantId}/menus/{menuId}/meals|POST|Add new menu item (meal) to menu|curl -w "\t%{http_code}" -s 'http://localhost:8080/topjava_graduation/rest/admin/restaurants/100004/menus/100011/meals' -X POST -H 'Content-Type: application/json; charset=UTF-8' --user admin@gmail.com:admin -d '{"name":"Coffee Latte", "price":355}'|201|
|/restaurants/{restaurantId}/menus/{menuId}/meals/{mealId}|PUT|Update menu item|curl -w "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/admin/restaurants/100003/menus/100011/meals/100029 -X PUT -H 'Content-Type: application/json; charset=UTF-8' --user admin@gmail.com:admin -d '{"name":"Coffee", "price":533}'|204|
|/restaurants/{restaurantId}/menus/{menuId}/meals/{mealId}|DELETE|Delete menu item|curl "\t%{http_code}" -s http://localhost:8080/topjava_graduation/rest/admin/restaurants/100003/menus/100011/meals/100029 -X DELETE --user admin@gmail.com:admin|204|
