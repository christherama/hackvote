# hackvote
This application represents a study of JWT and Spring Data REST.

## Running the application
Running the app is done with the `bootRun` Gradle task:

```groovy
./gradlew bootRun
```
Then, in a browser or Postman, you'll be able to make requests to http://localhost:8080.

## Authentication
There are several users added at boot time, all with the password "password":
- user1
- user2
- user3
- user4

Use one of the above usernames & passwords to request a JSON Web Token (JWT) via cURL or Postman:

```
POST /auth
{
  "username":"user1",
  "password":"password"
}
```

The response includes a token that encapsulates the user's permissions, for example:

```javascript
{
  "token": "eIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIs1MX0.gW7mKiQ-ar"
}
```

This token should be included in the `Authorization` header of subsequent requests, for example:

```
GET /api/v1/ideas
Authorization: eIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIs1MX0.gW7mKiQ-ar
```