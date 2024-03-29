# Create Test User

**Method: POST**

**URI**

- http://localhost:8080/api/v1/register

**Payload**

```json
{
    "username": "matek_1991",
    "password": "M@tekSzmatek1!",
    "confirmPassword": "M@tekSzmatek1",
    "email": "mateusz.nowak@pandatronik.com",
    "firstName": "Mateusz",
    "lastName": "Nowak"
}
```

# Generate Authentication Token (Login)

**Method: POST**

**URI**

- http://localhost:8080/api/v1/login

**Payload**

```json
{
    "username": "matek_1991", 
    "password": "M@tekSzmatek1!"
}
```

# Create Day

**Method: POST**

**URI**

- http://localhost:8080/api/v1/{username}/days/

**Headers**

- Authorization: Bearer TOKEN

**Payload**

```json
{
    "body": "Test Nowy",
    "rateDay": "75",
    "startDate": "2024-03-23",
    "postedOn": "2024-03-23T22:34:59"
}
```

