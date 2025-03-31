# The-Swap API

# Overview 
**Purpose:** facilitate the process of "The swap".  
**Target Audience:** Dr Kapuire  
**Version:** v1

# Authentication
**Authentication type:** JWT

**Purpose for chosen Authentication type:** offers token-based, stateless authentication. This will work well with the REST API.

**Permissions**
- The user can send a file with fields GroupNo(Integer),Consultant/ClientName(String),Student#(Integer),Surname(String),FullName(String) and attendance rate%(double) in this order.
# Endpoint summary and details
**device Endpoints**
1. Sending file  
   **Endpoint:** POST api/v1/swap

