# Graduation project
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**. See more: [graduation_en](https://github.com/dimio/topjava_graduation/blob/master/graduation_en.md) or [graduation_ru](https://github.com/dimio/topjava_graduation/blob/master/graduation_ru.md) .

## REST API
Base path: `localhost:8080/rest`
Base path for this project: `localhost:8080/rest/topjava_graduation/`

### User
Register a new user: 

`curl -s -X POST http://localhost:8080/topjava_graduation/rest/profile/register -H 'Content-Type: application/json; charset=UTF-8' -d '{"name" : "newName", "email" : "newemail@ya.ru", "password" : "newPassword"}'`

##### User profile:
API path: `topjava_graduation/rest/profile`

* Get profile:
`curl -s http://localhost:8080/topjava_graduation/rest/profile/ --user user@yandex.ru:password`

* Update profile:
`curl -s -X PUT http://localhost:8080/topjava_graduation/rest/profile -d '{"name":"New name", "email":"user@yandex.ru", "password":"password"}' -H 'Content-Type:application/json;charset=UTF-8' --user user@yandex.ru:password`

* Delete profile: `curl -s -X DELETE http://localhost:8080/topjava_graduation/rest/profile --user user@yandex.ru:password`

##### Vote:
API path: `topjava_graduation/rest/profile/votes`

* To vote for restaurant with specified ID:
`curl -s -X POST http://localhost:8080/topjava_graduation/rest/profile/votes/100002 --user user@yandex.ru:password`

* Change the vote (before decision time is end):
`curl -s -X PUT http://localhost:8080/topjava_graduation/rest/profile/votes/100003 --user user@yandex.ru:password`

* Get vote for current date:
`curl -s http://localhost:8080/topjava_graduation/rest/profile/votes --user user@yandex.ru:password`

* Get vote for specified date:
`curl -s http://localhost:8080/topjava_graduation/rest/profile/votes?date=2019-06-27 --user user@yandex.ru:password`

* Get all votes:
`curl -s http://localhost:8080/topjava_graduation/rest/profile/votes/all --user user@yandex.ru:password`

* Delete vote (before decision time is end):
`curl -s -X DELETE http://localhost:8080/topjava_graduation/rest/profile/votes --user user@yandex.ru:password`

##### Restaurant:
API path: `topjava_graduation/rest/profile/restaurants`

* Get all restaurants:
`curl -s http://localhost:8080/topjava_graduation/rest/profile/restaurants/all --user user@yandex.ru:password`

* Get restaurant with specified ID:
`curl -s http://localhost:8080/topjava_graduation/rest/profile/restaurants/100002 --user user@yandex.ru:password`

* Get menus on today for restaurant with specified ID:
`curl -s http://localhost:8080/topjava_graduation/rest/profile/restaurants/100002/menu --user user@yandex.ru:password`

* Get menus on specified date for restaurant with specified ID:
`curl -s http://localhost:8080/topjava_graduation/rest/profile/restaurants/100002/menu?date=2019-06-27 --user user@yandex.ru:password`

### Admin:

##### Users:
API path: `/rest/admin/users`

* Create new user:
`curl -s http://localhost:8080/topjava_graduation/rest/admin/users -X POST -H 'Content-Type: application/json; charset=UTF-8' -d '{"name" : "newName", "email" : "newemail@ya.ru", "password" : "newPassword"}' --user admin@gmail.com:admin`

* Get all users:
`curl -s http://localhost:8080/topjava_graduation/rest/admin/users --user admin@gmail.com:admin`

* Get user profile with specified ID:
`curl -s http://localhost:8080/topjava_graduation/rest/admin/users/100000 --user admin@gmail.com:admin`

* Change user profile with specified ID:
`curl -s -X PUT http://localhost:8080/topjava_graduation/rest/admin/users/100000 -d '{"name":"New name", "email":"newmail@yandex.ru", "password":"password"}' -H 'Content-Type:application/json;charset=UTF-8' --user admin@gmail.com:admin`

* Delete user with specified ID:
`curl -s -X DELETE http://localhost:8080/topjava_graduation/rest/admin/users/100000 --user admin@gmail.com:admin`

* Get user with specified email:
`curl -s http://localhost:8080/topjava_graduation/rest/admin/users/by?email=user@yandex.ru --user admin@gmail.com:admin`

##### Vote:
API path: `topjava_graduation/rest/admin/votes`

* Get all votes:
`curl -s http://localhost:8080/topjava_graduation/rest/admin/votes --user admin@gmail.com:admin`

* Get all votes for user with specified user ID:
`curl -s http://localhost:8080/topjava_graduation/rest/admin/votes/user/100000/all --user admin@gmail.com:admin`

* Get vote for current date and user with specified user ID:
`curl -s http://localhost:8080/topjava_graduation/rest/admin/votes/user/100000 --user admin@gmail.com:admin`

* Get vote for specified date and user with specified user ID:
`curl -s http://localhost:8080/topjava_graduation/rest/admin/votes/user/100000?date=2019-06-27 --user admin@gmail.com:admin`

* Get all votes for restaurant with specified restaurant ID:
`curl -s http://localhost:8080/topjava_graduation/rest/admin/votes/restaurant/100002/all --user admin@gmail.com:admin`

* Get votes for current date and restaurant with specified restaurant ID:
`curl -s http://localhost:8080/topjava_graduation/rest/admin/votes/restaurant/100002 --user admin@gmail.com:admin`

* Get votes for specified date and restaurant with specified restaurant ID:
`curl -s http://localhost:8080/topjava_graduation/rest/admin/votes/restaurant/100002?date=2019-06-27 --user admin@gmail.com:admin`

##### Restaurant:
API path: `topjava_graduation/rest/admin/restaurants`

* Add new restaurant:
`curl -s http://localhost:8080/topjava_graduation/rest/admin/restaurants -X POST -H 'Content-Type: application/json; charset=UTF-8' -d '{"name":"newRestaurant"}' --user admin@gmail.com:admin`

* Update restaurant with specified ID:
`curl -s -X PUT http://localhost:8080/topjava_graduation/rest/admin/restaurants/100002 -d '{"name":"New name"}' -H 'Content-Type:application/json;charset=UTF-8' --user admin@gmail.com:admin`

* Delete restaurant with specified ID:
`curl -s -X DELETE http://localhost:8080/topjava_graduation/rest/admin/restaurants/100002 --user admin@gmail.com:admin`

* Add new menu to restaurant with specified ID:
`curl -s http://localhost:8080/topjava_graduation/rest/admin/restaurants/100002 -X POST -H 'Content-Type: application/json; charset=UTF-8' -d '{"name":"newMenu", "dishes":"newDishes", "priceInt":10, "priceFract":88}' --user admin@gmail.com:admin`

* Update menu with specified ID to restaurant with specified ID:
`curl -s -X PUT http://localhost:8080/topjava_graduation/rest/admin/restaurants/100002/100004 -d '{"name":"UpdatedName", "added":"2019-06-27", "dishes":"new", "priceInt":999, "priceFract":3}' -H 'Content-Type:application/json;charset=UTF-8' --user admin@gmail.com:admin`

* Delete menu with specified ID to restaurant with specified ID:
`curl -s -X DELETE http://localhost:8080/topjava_graduation/rest/admin/restaurants/100002/100004 --user admin@gmail.com:admin`