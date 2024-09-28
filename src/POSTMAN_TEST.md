# Create Test User

**Method: POST**

**URI**

- http://localhost:8080/api/v1/register

**Payload**

```json
{
    "username": "Username",
    "password": "Password",
    "confirmPassword": "Password",
    "email": "username@pandatronik.com",
    "firstName": "Name",
    "lastName": "LastName"
}
```

# Generate Authentication Token (Login)

**Method: POST**

**URI**

- http://localhost:8080/api/v1/login

**Payload**

```json
{
    "username": "Name", 
    "password": "Password"
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

