# Graduation project
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

## API
Base path: `topjava_graduation/rest`

Path: `/path` (relative to base path)

Request format: *Method* `/path` *[Request parameters] [(Request body)] : [Response body]*

### User
Base path: `/profile`

Register a new user: 
*POST* `/register` *(UserTo) : User*

User profile:
*GET* `/id` : UserTo

### Admin:
