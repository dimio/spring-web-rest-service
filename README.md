# Graduation project
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**. See more in expression of requirements: [graduation_en](https://github.com/dimio/topjava_graduation/blob/master/graduation_en.md) or [graduation_ru](https://github.com/dimio/topjava_graduation/blob/master/graduation_ru.md) .

## REST API
Base path: `localhost:8080/rest`
Base path for this project (deployed into "topjava_graduation"): `localhost:8080/topjava_graduation/rest/`

Simple API checking (run this shell command in the project directory after project deploy):
```
sh -e <(grep '^`curl' README.md | cut -f2 -d '`')
```

### User
Register a new user:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s -X POST http://localhost:8080/topjava_graduation/rest/profile/register -H 'Content-Type: application/json; charset=UTF-8' -d '{"name":"newName", "email":"newemail@ya.ru", "password":"newPassword"}'`

##### User profile:
API path: `topjava_graduation/rest/profile`

* Get profile:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/profile/ --user user@yandex.ru:password`

* Update profile:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s -X PUT http://localhost:8080/topjava_graduation/rest/profile -d '{"name":"New name", "email":"userNew@yandex.ru", "password":"password"}' -H 'Content-Type:application/json;charset=UTF-8' --user user@yandex.ru:password`

* Delete profile:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s -X DELETE http://localhost:8080/topjava_graduation/rest/profile --user newemail@ya.ru:newPassword`

##### Vote:
API path: `topjava_graduation/rest/profile/votes`

* To vote for restaurant with specified ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s -X POST http://localhost:8080/topjava_graduation/rest/profile/votes/100002 --user userNew@yandex.ru:password`

* Change the vote (before decision time is end):
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s -X PUT http://localhost:8080/topjava_graduation/rest/profile/votes/100003 --user userNew@yandex.ru:password`

* Get vote for current date:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/profile/votes --user userNew@yandex.ru:password`

* Get vote for specified date:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/profile/votes?date=2019-06-27 --user userNew@yandex.ru:password`

* Get all votes:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/profile/votes/all --user userNew@yandex.ru:password`

* Delete vote (before decision time is end):
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s -X DELETE http://localhost:8080/topjava_graduation/rest/profile/votes --user userNew@yandex.ru:password`

##### Restaurant:
API path: `topjava_graduation/rest/profile/restaurants`

* Get all restaurants:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/profile/restaurants/all --user userNew@yandex.ru:password`

* Get restaurant with specified ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/profile/restaurants/100002 --user userNew@yandex.ru:password`

* Get menus on today for restaurant with specified ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/profile/restaurants/100002/menu --user userNew@yandex.ru:password`

* Get menus on specified date for restaurant with specified ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/profile/restaurants/100002/menu?date=2019-06-27 --user userNew@yandex.ru:password`

### Admin:

##### Users:
API path: `/rest/admin/users`

* Create new user:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/admin/users -X POST -H 'Content-Type: application/json; charset=UTF-8' -d '{"name":"newName", "email":"newemail@ya.ru", "password":"newPassword"}' --user admin@gmail.com:admin`

* Get all users:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/admin/users --user admin@gmail.com:admin`

* Get user profile with specified ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/admin/users/100000 --user admin@gmail.com:admin`

* Get user with specified email:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/admin/users/by?email=admin@gmail.com --user admin@gmail.com:admin`

* Change user profile with specified ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s -X PUT http://localhost:8080/topjava_graduation/rest/admin/users/100000 -d '{"name":"Updated User name", "email":"newmail@yandex.ru", "password":"password"}' -H 'Content-Type:application/json;charset=UTF-8' --user admin@gmail.com:admin`

* Delete user with specified ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s -X DELETE http://localhost:8080/topjava_graduation/rest/admin/users/100000 --user admin@gmail.com:admin`

##### Vote:
API path: `topjava_graduation/rest/admin/votes`

* Get all votes:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/admin/votes --user admin@gmail.com:admin`

* Get all votes for user with specified user ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/admin/votes/user/100001/all --user admin@gmail.com:admin`

* Get vote for current date and user with specified user ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/admin/votes/user/100001 --user admin@gmail.com:admin`

* Get vote for specified date and user with specified user ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/admin/votes/user/100001?date=2019-06-27 --user admin@gmail.com:admin`

* Get all votes for restaurant with specified restaurant ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/admin/votes/restaurant/100002/all --user admin@gmail.com:admin`

* Get votes for current date and restaurant with specified restaurant ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/admin/votes/restaurant/100002 --user admin@gmail.com:admin`

* Get votes for specified date and restaurant with specified restaurant ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/admin/votes/restaurant/100002?date=2019-06-27 --user admin@gmail.com:admin`

##### Restaurant:
API path: `topjava_graduation/rest/admin/restaurants`

* Get all menus for restaurant with specified ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/admin/restaurants/100002/all --user admin@gmail.com:admin`

* Add new restaurant:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/admin/restaurants -X POST -H 'Content-Type: application/json; charset=UTF-8' -d '{"name":"newRestaurant"}' --user admin@gmail.com:admin`

* Update restaurant with specified ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s -X PUT http://localhost:8080/topjava_graduation/rest/admin/restaurants/100002 -d '{"name":"New menu"}' -H 'Content-Type:application/json;charset=UTF-8' --user admin@gmail.com:admin`

* Delete restaurant with specified ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s -X DELETE http://localhost:8080/topjava_graduation/rest/admin/restaurants/100002 --user admin@gmail.com:admin`

* Add new menu to restaurant with specified ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s http://localhost:8080/topjava_graduation/rest/admin/restaurants/100003 -X POST -H 'Content-Type: application/json; charset=UTF-8' -d '{"name":"newMenu", "dishes":"newDishes", "priceInt":10, "priceFract":88}' --user admin@gmail.com:admin`

* Update menu with specified ID to restaurant with specified ID (it also allows you to transfer the menu to another restaurant):
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s -X PUT http://localhost:8080/topjava_graduation/rest/admin/restaurants/100003/100004 -d '{"name":"updatedMenu", "dishes":"updatedDishes", "priceInt":999, "priceFract":3}' -H 'Content-Type:application/json;charset=UTF-8' --user admin@gmail.com:admin`

* Delete menu with specified ID to restaurant with specified ID:
`curl -w "\tSTATUS: %{http_code} %{url_effective}\n\n" -s -X DELETE http://localhost:8080/topjava_graduation/rest/admin/restaurants/100003/100006 --user admin@gmail.com:admin`