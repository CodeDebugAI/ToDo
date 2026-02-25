# 📝 Task Management API (Spring Boot + JWT)

A RESTful Task Management API built using Spring Boot with JWT Authentication.

---

## 🚀 Base URL

```
http://localhost:9090
```

---

# 🔐 Authentication

All protected endpoints require a JWT token.

You can generate a token using the `/auth/login` endpoint.

Once you have the token, include it in every protected request:

```
Authorization: Bearer <your_jwt_token>
```

Example:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

⚠️ Notes:
- Do NOT add quotes around the token.
- There must be a space between `Bearer` and the token.
- Token must not be expired.

---

# 🛠 Generating JWT Token (Login)

Use the **POST /auth/login** endpoint to obtain a JWT token.

### Endpoint

```
POST /auth/login
```

### Headers

```
Content-Type: application/json
```

### Request Body Example

```json
{
  "username": "user",
  "password": "password123"
}
```

### Example cURL

```bash
curl --location --request POST 'http://localhost:9090/auth/login' \
--header 'Content-Type: application/json' \
--data '{
  "username": "user",
  "password": "password123"
}'
```

### Successful Response Example

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzcyMDMxNTYyLCJleHAiOjE3NzIwMzUxNjJ9.62ZJAByZ1NX1lpRXLDJd0zP5d9zoAA1YVOsXUG_0P4E"
}
```

> Use the value of `token` in your `Authorization` header for all protected endpoints.

---

# 📌 API Endpoints

---

## 1️⃣ Create Task

### Method
POST

### Endpoint
```
/api/tasks
```

### Headers
```
Authorization: Bearer <token>
Content-Type: application/json
```

### Request Body
```json
{
  "title": "Sample Task",
  "description": "Task description",
  "completed": false
}
```

### Example cURL
```bash
curl --location --request POST 'http://localhost:9090/api/tasks' \
--header 'Authorization: Bearer <token>' \
--header 'Content-Type: application/json' \
--data '{
  "title": "Sample Task",
  "description": "Task description",
  "completed": false
}'
```

---

## 2️⃣ Get All Tasks

### Method
GET

### Endpoint
```
/api/tasks
```

### Example cURL
```bash
curl --location --request GET 'http://localhost:9090/api/tasks' \
--header 'Authorization: Bearer <token>'
```

---

## 3️⃣ Get Task By ID

### Method
GET

### Endpoint
```
/api/tasks/{id}
```

### Example
```
/api/tasks/1
```

### Example cURL
```bash
curl --location --request GET 'http://localhost:9090/api/tasks/1' \
--header 'Authorization: Bearer <token>'
```

---

## 4️⃣ Update Task

### Method
PATCH

### Endpoint
```
/api/tasks/{id}
```

### Headers
```
Authorization: Bearer <token>
Content-Type: application/json
```

### Request Body Example
```json
{
  "completed": true
}
```

### Example cURL
```bash
curl --location --request PATCH 'http://localhost:9090/api/tasks/1' \
--header 'Authorization: Bearer <token>' \
--header 'Content-Type: application/json' \
--data '{
  "completed": true
}'
```

---

## 5️⃣ Delete Task

### Method
DELETE

### Endpoint
```
/api/tasks/{id}
```

### Example
```
/api/tasks/1
```

### Example cURL
```bash
curl --location --request DELETE 'http://localhost:9090/api/tasks/1' \
--header 'Authorization: Bearer <token>'
```

---

# ⚠️ Common HTTP Errors

| Status Code | Meaning |
|-------------|----------|
| 400 | Bad Request (Invalid JSON) |
| 401 | Unauthorized (Missing/Invalid Token) |
| 403 | Forbidden (Access Denied) |
| 404 | Resource Not Found |
| 500 | Internal Server Error |

---

# 🛠 How to Run the Application

1. Clone the repository
2. Navigate to project directory
3. Run:

```
mvn spring-boot:run
```

4. Application will start at:

```
http://localhost:9090
```

---

# 📚 HTTP Methods Summary

| Method | Purpose |
|--------|----------|
| GET | Retrieve data |
| POST | Create new resource |
| PATCH | Partially update resource |
| DELETE | Remove resource |

---

# ✅ Important Notes

- JWT token must be valid and not expired.
- Always use `Content-Type: application/json` for POST and PATCH.
- This API is stateless (no session storage).
- CSRF should be disabled for JWT-based APIs.

---

Happy Coding 🚀