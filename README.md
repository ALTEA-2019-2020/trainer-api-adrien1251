# Trainer API

This API is implemented to simulate different pokemon trainers 

# Implementation 

This API is available on this link : `https://trainer-api-adrien1251.herokuapp.com/`

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