# Trainer API

This API is implemented to simulate different pokemon trainers 

# Configuration 

This project run with postgres database 

## Set env variable 
Different environment variable need to be set : 

`DB_HOST`: host of the postgres database
 
`DB_PORT`: the port of postgres database

`DB_NAME`: the name of your database

`DB_USER_NAME`: user name to connect to your database

`DB_PASSWORD` : password to connect to your database

`USER` : the user to connect to the spring boot app

`PASSWORD`: the password to connect to the spring boot app
 
## Run the project 

After configure all env variable do
`mvn clean install && mvn spring-boot:run`

The application is started on port 8081

# Implementation 

This API is available on this link : [`https://trainer-api-adrien1251.herokuapp.com/`](https://trainer-api-adrien1251.herokuapp.com/)

Available routes:

`GET trainers/`                 : Get all trainers  

`GET trainers/<name>`           : Get specific trainer

`POST trainers/ {<trainer>}`    : Create specific trainer

`PUT trainers/  {<trainer>}`    : Update specific trainer

`DELETE trainers/<name>`        : delete specific trainer


Trainer implementation 
```
{
    "name": <name>
    "team": [
        {
            "pokemonTypeId": <id>
            "level": <level>
        },
        {
            ...
        }
    ]
}
```