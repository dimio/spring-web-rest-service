# Graduation project
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

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
`curl -s -X PUT http://localhost:8080/topjava_graduation/rest/profile --user user@yandex.ru:password -d '{"name":"New name", "email":"user@yandex.ru", "password":"password"}' -H 'Content-Type:application/json;charset=UTF-8'`

* Delete profile: `curl -s -X DELETE http://localhost:8080/topjava_graduation/rest/profile --user user@yandex.ru:password`

##### Vote:
API path: `topjava_graduation/rest/profile/votes`

* To vote for restaurant with specified ID:
`curl -s -X POST http://localhost:8080/topjava_graduation/rest/profile/votes/100002 --user user@yandex.ru:password`

* Change the vote:
`curl -s -X PUT http://localhost:8080/topjava_graduation/rest/profile/votes/100003 --user user@yandex.ru:password`

* Get vote for current date:
`curl -s http://localhost:8080/topjava_graduation/rest/profile/votes --user user@yandex.ru:password`

* Get vote for specified date:
`curl -s http://localhost:8080/topjava_graduation/rest/profile/votes?date=2019-06-27 --user user@yandex.ru:password`

* Get all votes:
`curl -s http://localhost:8080/topjava_graduation/rest/profile/votes/all --user user@yandex.ru:password`

### Admin:

##### Users:
API path: `/rest/admin/users`

* Create new user:
`curl -s -X POST -H http://localhost:8080/topjava_graduation/rest/admin/users 'Content-Type: application/json; charset=UTF-8' -d '{"name" : "newName", "email" : "newemail@ya.ru", "password" : "newPassword"}'`

* Get all users:
`curl -s http://localhost:8080/topjava_graduation/rest/admin/users --user admin@gmail.com:admin`

* Get user profile with specified ID:
`curl -s http://localhost:8080/topjava_graduation/rest/admin/users/100000 --user admin@gmail.com:admin`

* Change user profile with specified ID:
`curl -s -X PUT http://localhost:8080/topjava_graduation/rest/admin/users/100000 --user admin@gmail.com:admin -d '{"name":"New name", "email":"newmail@yandex.ru", "password":"password"}' -H 'Content-Type:application/json;charset=UTF-8'`

* Delete user with specified ID:
`curl -s -X DELETE http://localhost:8080/topjava_graduation/rest/admin/users/100000 --user admin@gmail.com:admi`

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