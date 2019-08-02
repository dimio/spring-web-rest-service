# Graduation project
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

## REST API
Base path: `localhost:8080/rest`

### User
Register a new user: 

`curl -s -X POST -H http://localhost:8080/rest/profile/register 'Content-Type: application/json; charset=UTF-8' -d '{"name" : "newName", "email" : "newemail@ya.ru", "password" : "newPassword"}'`

##### User profile:
API path: `/rest/profile`

* Get profile:
`curl -s http://localhost:8080/rest/profile/ --user user@yandex.ru:password`

* Update profile:
`profile curl -s -X PUT http://localhost:8080/rest/profile --user user@yandex.ru:password -d '{"name":"New name", "email":"user@yandex.ru", "password":"password"}' -H 'Content-Type:application/json;charset=UTF-8'`

* Delete profile: `curl -s -X DELETE http://localhost:8080/rest/profile --user user@yandex.ru:password`

## Admin:

##### Users:
API path: `/rest/admin/users`

* Create new user:
`curl -s -X POST -H http://localhost:8080/rest/admin/users 'Content-Type: application/json; charset=UTF-8' -d '{"name" : "newName", "email" : "newemail@ya.ru", "password" : "newPassword"}'`

* Get all users:
`curl -s http://localhost:8080/rest/admin/users --user admin@gmail.com:admin`

* Get user profile with specified ID:
`curl -s http://localhost:8080/rest/admin/users/100000 --user admin@gmail.com:admin`

* Change user profile with specified ID:
`curl -s -X PUT http://localhost:8080/rest/admin/users/100000 --user admin@gmail.com:admin -d '{"name":"New name", "email":"newmail@yandex.ru", "password":"password"}' -H 'Content-Type:application/json;charset=UTF-8'`

* Delete user with specified ID:
`curl -s -X DELETE http://localhost:8080/rest/admin/users/100000 --user admin@gmail.com:admi`

* Get user with specified email:
`curl -s http://localhost:8080/rest/admin/users/by?email=user@yandex.ru --user admin@gmail.com:admin`