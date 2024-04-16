
# User management service

This service is responsible for user management in the payment microservices system. Here are some important details:

## Features

- User registration.

## TODO

- Authentication and authorization (JWT).
- Password recovery.

## Technologies Used

- Programming language: Java
- Database: PostgreSQL 
- Web framework: Spring Boot

## Configuration

1. Clone this repository.
2. Install dependencies with Maven
3. Configure environment variables (e.g., secret keys, database connections).
4. Run the service locally for testing.

## API Reference

#### Get all users

- **Retrieve Users**: This endpoint allows fetching user information. The response includes HATEOAS links for related actions.

```http
  GET /api/users
```

- **Expected Response**

```json
{
        "id": "b38ff2ae-13b9-418d-a3b3-80cec49e7335",
        "username": "Matheus.Grego",
        "full_name": "Matheus VL Grego",
        "document": "123.456.789-10",
        "balance": 0.00,
        "address": "Rua joão e Maria",
        "email": "Matheus@gmail.com",
        "phone_number": "31980159840",
        "userType": "USER",
        "documentType": "CPF",
        "createdAt": "2024-03-25T18:48:03.669676Z",
        "updatedAt": null,
        "links": [
            {
                "rel": "self",
                "href": "http://host/api/users/b38ff2ae-13b9-418d-a3b3-80cec49e7335"
            }
        ]
    },

```

#### Get user

- **Retrieve User**: This endpoint allows fetching user information. The response includes HATEOAS links for related actions.


```http
  GET /api/users/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |

- **Expected Response**

```json
{
        "id": "b38ff2ae-13b9-418d-a3b3-80cec49e7335",
        "username": "Matheus.Grego",
        "full_name": "Matheus VL Grego",
        "document": "123.456.789-10",
        "balance": 0.00,
        "address": "Rua joão e Maria",
        "email": "Matheus@gmail.com",
        "phone_number": "31980159840",
        "userType": "USER",
        "documentType": "CPF",
        "createdAt": "2024-03-25T18:48:03.669676Z",
        "updatedAt": null,
    "_links": {
        "Users list": {
            "href": "http://host:8080/api/users/"
        }
    }
}
```



#### Create user

- **Create User**: This endpoint allows the creation of a new user in the system.

```http
  POST /api/users
```

| Parameter   | Type     | Description                       |
| :---------- | :------- | :-------------------------------- |
| `username`  | `string` | **Required**. User's username     |
| `full_name` | `string` | User's full name                   |
| `document`  | `string` | User's document (e.g., CPF, CNPJ) |
| `address`   | `string` | User's address                    |
| `email`     | `string` | User's email address              |
| `phone_number` | `string` | User's phone number            |
| `pwd`       | `string` | User's password                  |
| `userType`  | `string` | User type (e.g., "USER", "MERCHANT") |
| `documentType` | `string` | Document type (e.g., "CPF", "CNPJ") |

- **Password constraints**:
At least 8 characters.
At least one uppercase letter.
At least one lowercase letter.
At least one numeric digit.
At least one special character.
Does not contain any whitespace.

### Example Request Body
```json
{
    "username": "Matheus.Grego",
    "full_name": "Matheus V DL GREGO",
    "document": "595.095.370-39",
    "address": "Rua João e Maria",
    "email": "Matheus@Gmail.com",
    "phone_number": "31980159840",
    "pwd": "12345678aM%",
    "userType": "USER",
    "documentType": "CPF"
}
```
- **Expected Response**
After successful user creation, the server should respond with status 201 (Created):

```json
{
    "path": "/api/users",
    "status": 201,
    "messages": "User successfully created",
    "date": "2024-04-16T19:37:22.226509100Z"
}
```

General end points possible errors:

- Missing fields
- Invalid format
- User not found



