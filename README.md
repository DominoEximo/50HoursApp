# Community Service Administration - BACKEND
Thesis

## Introduction
The project is my thesis for bachelors.
The goal of the project is to provide a REST API 
for the administration of community 
service for both students and institutions.


## Getting Started
Prerequisites  

Before you begin, ensure you have met the following requirements:

Java Development Kit (JDK) installed (version 19.0 or higher)  
Maven build tool installed (version 3.9 or higher)  

Installation
Follow these steps to install and set up the project:  

Clone the repository to your local machine:

```console
git clone https://github.com/DominoEximo/50HoursApp.git 
```
 
Navigate to the project directory:  
```console
cd 50HoursApp 
```

- [Change database from memory db in application.properties]

Running the Application  

To run the application locally, follow these steps:

```console
mvn spring-boot:run
```

The application will start and be accessible at http://localhost:8080.  

Running Tests  

To run tests for the application, use the following command:

```console
mvn test
```
## Architecture

**Components**:  
- **REST API Endpoints**: Expose endpoints for managing users, including:  
  - **USERS**
    - **'GET /users'**: Retrieve all users.  
    - **'GET /users/{id}**': Retrieve a user by ID.  
    - **'POST /users**': Create a new user.  
    - **'PUT /users/{id}**': Update an existing user.  
    - **'DELETE /users/{id}**': Delete a user.  
  - **INSTITUTIONS**
      - **'GET /institutions'**: Retrieve all institutions.
      - **'GET /institutions/{id}'**: Retrieve an institution by ID.
      - **'POST /institutions'**: Create a new institution.
      - **'PUT /institutions/{id}'**: Update an existing institution.
      - **'DELETE /institutions/{id}'**: Delete an institution.
  - **CONTRACTS**
      - **'GET /contracts'**: Retrieve all contracts.
      - **'GET /contracts/{id}'**: Retrieve a contract by ID.
      - **'POST /contracts'**: Create a new contract.
      - **'PUT /contracts/{id}'**: Update an existing contract.
      - **'DELETE /contracts/{id}'**: Delete a contract.
  - **JOBTYPES**
      - **'GET /jobTypes'**: Retrieve all jobTypes.
      - **'GET /jobTypes/{id}'**: Retrieve a jobType by ID.
      - **'POST /jobTypes'**: Create a new jobType.
      - **'PUT /jobTypes/{id}'**: Update an existing jobType.
      - **'DELETE /jobTypes/{id}'**: Delete a jobType.
  - **ROLES**
      - **'GET /roles'**: Retrieve all roles.
      - **'GET /roles/{id}'**: Retrieve a role by ID.
      - **'POST /roles'**: Create a new role.
      - **'PUT /roles/{id}'**: Update an existing role.
      - **'DELETE /roles/{id}'**: Delete a role.  
- **Controllers**: Handle incoming HTTP requests and delegate processing to the appropriate service methods.
- **Service Layer**: Contains business logic for managing users, such as validation, transformation, and interaction with the data access layer.
- **Data Access Layer (DAO)**: Interfaces with the database to perform CRUD operations on user entities.

**Layers**:
- **Presentation Layer**: Non-existent, as the focus is on providing data through RESTful endpoints rather than rendering user interfaces.
- **Application Layer**: Contains controllers and services, orchestrating interactions between incoming requests and data access objects.
- **Data Access Layer**: Interfaces with the database using Spring Data JPA repositories to perform CRUD operations on user entities.

**Interaction**:
- **Client Interaction**: Clients interact with the REST API by sending HTTP requests (GET, POST, PUT, DELETE) to the appropriate endpoints.  
- **Internal Interaction**: Controllers receive incoming requests and delegate processing to service methods. Service methods interact with the data access layer to perform database operations and return responses to controllers.

**Technologies**:  
- **Framework**: Built with Spring Boot, which provides a lightweight and opinionated framework for building RESTful applications in Java.  
- **Database**: Uses H2 as in-memory database for storing and retrieving user data.  
- **Dependencies**: Relies on Spring Data JPA for database access, Spring Web for exposing RESTful endpoints, and Spring Boot Starter Data MySQL for MySQL database integration.  

## Usage

**Users**

**Retrieve all users(GET /users)**

```console
GET /users HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==
```

**Retrieve a user with ID(GET /users/{id})**

```console
GET /users/1 HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==
```

**Create a new user(POST /users)**
```console
POST /users HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==
Content-Type: application/json

{
  "id": 3,
  "username": "testuser2211",
  "firstName" : "User3",
  "lastName" : "User3",
  "email": "testtestest@gmail.com",
  "phoneNumber": "(+36) 232-1234",
  "birthDate": "2024-03-11T12:06:28.319+00:00",
  "gender": "f",
  "password": "testpassword",
  "coordinatorEmail": "TESTCOORDINATOR2@email.test",
  "coordinatorPhone": "testcordphone2",
  "roles": [
    {
      "id": 2,
      "name": "USER"
    },
    {
      "id": 1,
      "name": "BACKOFFICE"
    }
  ],
  "omid": "OMTEST2",
  "ikszcoordinator": "TESTCOORDINATOR2",
  "location" : {

  "name": "Example Location",
  "country": "Hungary",
  "street": "Nyíregyháza",
  "lat": null, 
  "lon": null
}
}
```

**Update an existing user(PUT /users/{id})**
```console
PUT /users HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==
Content-Type: application/json

{
  "id": 3,
  "username": "testuser",
  "firstName" : "User3",
  "lastName" : "User3",
  "email": "testtestest@gmail.com",
  "phoneNumber": "(+36) 232-1234",
  "birthDate": "2024-03-11T12:06:28.319+00:00",
  "gender": "f",
  "password": "testpassword",
  "coordinatorEmail": "TESTCOORDINATOR2@email.test",
  "coordinatorPhone": "testcordphone2",
  "roles": [
    {
      "id": 2,
      "name": "USER"
    },
    {
      "id": 1,
      "name": "BACKOFFICE"
    }
  ],
  "omid": "OMTEST2",
  "ikszcoordinator": "TESTCOORDINATOR2",
  "location" : {

  "name": "Example Location",
  "country": "Hungary",
  "street": "Nyíregyháza",
  "lat": null, 
  "lon": null
}
}
```

**Delete a user(DELETE /users/{id})**
```console
DELETE /users/1 HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==
```
**Institutions**

**Retrieve all institutions(GET /institutions)**

```console
GET /institutions HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==
```

**Retrieve an institution with ID(GET /institutions/{id})**

```console
GET /institutions/1 HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==
```

**Create a new institution(POST /institutions)**
```console
POST /institutions HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==
Content-Type: application/json

{
      "id": 2,
      "name": "TestInstitution",
      "type": null,
      "location": {
        "id": 2,
        "name": null,
        "country": "Hungary",
        "street": "Budapest",
        "lat": 47.497913,
        "lon": 19.040236
      },
      "contact": null,
      "description": {
        "id": 2,
        "text": "This is a test Description for the purpose of testing this description",
        "links": [
          "https://outlook.office.com/mail/",
          "https://www.youtube.com/watch?v=gEFM5EoE4x4"
        ],
        "pictures": []
      }
    }
```

**Update an existing institution(PUT /institutions/{id})**
```console
PUT /institutions HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==
Content-Type: application/json

{
      "id": 2,
      "name": "TestInstitution2",
      "type": null,
      "location": {
        "id": 2,
        "name": null,
        "country": "Hungary",
        "street": "Budapest",
        "lat": 47.497913,
        "lon": 19.040236
      },
      "contact": null,
      "description": {
        "id": 2,
        "text": "This is a test Description for the purpose of testing this description",
        "links": [
          "https://outlook.office.com/mail/",
          "https://www.youtube.com/watch?v=gEFM5EoE4x4"
        ],
        "pictures": []
      }
    }
```

**Delete an institution(DELETE /institutions/{id})**
```console
DELETE /institutions/2 HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==
```

**Set up test institution(GET /institutions/setUpMockedData)**
```console
DELETE /institutions/setUpMockedData HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==
```

**Contracts**

**Retrieve all contracts(GET /contracts)**

```console
GET /contracts HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==
```

**Retrieve a contract with ID(GET /contracts/{id})**

```console
GET /contracts/1 HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==
```

**Create a new contract(POST /contracts)**
```console
POST /contracts HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==
Content-Type: application/json

"id": 1,
    "student": {
      "id": 1,
      "username": "user",
      "firstName": "User",
      "lastName": "User",
      "email": "test@gmail.com",
      "phoneNumber": "(+36) 232-2222",
      "birthDate": "2024-04-12T16:04:36.763+00:00",
      "gender": "m",
      "password": "$2a$10$2a2CoQsrj6YneFDJ.aFEeOIY5pmpeR/Ij9BSy2sBaNOacovT4EmgK",
      "coordinatorEmail": "TESTCOORDINATOR@email.test",
      "coordinatorPhone": "testcordphone",
      "preferedJobs": [],
      "roles": [
        {
          "id": 2,
          "name": "USER"
        }
      ],
      "location": {
        "id": 1,
        "name": null,
        "country": "Hungary",
        "street": "Nyíregyháza",
        "lat": 47.95539,
        "lon": 21.71671
      },
      "omid": "OMTEST",
      "ikszcoordinator": "TESTCOORDINATOR"
    },
    "institution": null,
    "startDate": "2024-04-05",
    "endDate": "2024-10-05",
    "completed": false
  }
```

**Update an existing contract(PUT /contracts/{id})**
```console
PUT /contracts HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==
Content-Type: application/json

{
    "id": 1,
    "student": {
      "id": 1,
      "username": "user",
      "firstName": "User",
      "lastName": "User",
      "email": "test@gmail.com",
      "phoneNumber": "(+36) 232-2222",
      "birthDate": "2024-04-12T16:04:36.763+00:00",
      "gender": "m",
      "password": "$2a$10$2a2CoQsrj6YneFDJ.aFEeOIY5pmpeR/Ij9BSy2sBaNOacovT4EmgK",
      "coordinatorEmail": "TESTCOORDINATOR@email.test",
      "coordinatorPhone": "testcordphone",
      "preferedJobs": [],
      "roles": [
        {
          "id": 2,
          "name": "USER"
        }
      ],
      "location": {
        "id": 1,
        "name": null,
        "country": "Hungary",
        "street": "Nyíregyháza",
        "lat": 47.95539,
        "lon": 21.71671
      },
      "omid": "OMTEST",
      "ikszcoordinator": "TESTCOORDINATOR"
    },
    "institution": null,
    "startDate": "2024-04-05",
    "endDate": "2024-10-05",
    "completed": true
  }
```

**Delete a contract(DELETE /contracts/{id})**
```console
DELETE /contracts/1 HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==
```

**Set up test contract(GET /contracts/setUpMockedData)**
```console
DELETE /contracts/setUpMockedData HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==
```

**Roles**

**Retrieve all roles(GET /roles)**

```console
GET /roles HTTP/1.1
Host: localhost:8080
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
```

**Retrieve a role with ID(GET /roles/{id})**

```console
GET /roles/1 HTTP/1.1
Host: localhost:8080
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
```

**Create a new role(POST /roles)**
```console
POST /roles HTTP/1.1
Host: localhost:8080
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
Content-Type: application/json

  {
    "id": 3,
    "name": "TEST"
  }
```

**Update an existing role(PUT /roles/{id})**
```console
PUT /roles HTTP/1.1
Host: localhost:8080
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
Content-Type: application/json

  {
    "id": 3,
    "name": "TEST2"
  }
```

**Delete a role(DELETE /roles/{id})**
```console
DELETE /roles/3 HTTP/1.1
Host: localhost:8080
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
```

**Job Types**

**Retrieve all job types(GET /jobTypes)**

```console
GET /jobTypes HTTP/1.1
Host: localhost:8080
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
```

**Retrieve a job Type with ID(GET /jobTypes/{id})**

```console
GET /jobTypes/1 HTTP/1.1
Host: localhost:8080
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
```

**Create a new job type(POST /jobTypes)**
```console
POST /jobTypes HTTP/1.1
Host: localhost:8080
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
Content-Type: application/json

  {
    "id": 1,
    "name": "test"
  }
```

**Update an existing jobType(PUT /jobTypes/{id})**
```console
PUT /jobTypes HTTP/1.1
Host: localhost:8080
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
Content-Type: application/json

  {
    "id": 1,
    "name": "test2"
  }
```

**Delete a job type(DELETE /jobTypes/{id})**
```console
DELETE /jobTypes/1 HTTP/1.1
Host: localhost:8080
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
```

## Deployment

**Deploying the Application**  
- Artifact Generation: Build the project using Maven to generate the executable JAR file or WAR file.

```console
mvn clean package
```

**Copying Files**:   
- Transfer the generated artifact (JAR or WAR file) to the deployment server. You can use tools like SCP, FTP, or simply copy the file manually.
```console
scp target/yourproject.jar user@your_server_ip:/path/to/deployment/directory
```

**Starting the Application**:   
- Once the artifact is copied to the deployment server, start the application using the following command:


```console
java -jar /path/to/deployment/directory/yourproject.jar
```

