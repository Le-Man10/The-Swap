# The-Swap API

# Overview 
**Purpose:** facilitate the process of "The swap".  
**Target Audience:** Dr Kapuire  
**Version:** v1

# Authentication
**Authentication type:** JWT

**Purpose for chosen Authentication type:** offers token-based, stateless authentication. This will work well with the REST API.

**Permissions**
- The user can send a file with fields GroupNo(Integer),Consultant/ClientName(String),Student#(Integer),Surname(String),FullName(String) and attendance rate%(integer) in this order.
# Endpoint summary and details
**device Endpoints**
1. Sending file  
   **Endpoint:** POST api/v1/swap
   **body:** Excel file  
   **Response:** Excel file
2. Login  
**Endpoint:** Get /api/v1/login
**body:**
```
{
   "email":String,
   "password":String
}
```

**Response:**
```
{
   "message": String(you should expect a generated jwt token for that user's session here)
}
```

# Error handling
this is the format in which will be used to send errors to the frontend  
```
{
   "error": Integer,
   "message": String
}
```
