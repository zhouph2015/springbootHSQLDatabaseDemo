Introduction:
This is a Spring boot application for embedded HyperSQL database with java8. it build with Maven and provide endpoint 
to add, query and delete user in a users table of HyperSQL database

How to run the project:
GitHub Repository: https://github.com/zhouph2015/springbootHSQLDatabaseDemo.git

1. git clone https://github.com/zhouph2015/springbootlearningDemo.git to your local machine
2. import the project to your IDE like Eclipse
3. Right click project->gradle->Refresh Gradle Project
4. Right clock project->Run as->Spring boot App

Curl command to test this project:
1. get all users from the database
curl localhost:8080/api/users  

2. add a new user
curl localhost:8080/api/user?"userId=1003&userName=Tom&userEmail=Tom2012@gmail.com&address=Austin"
if add user with userId already exist, it will return a error message

3. query a user with userId
curl localhost:8080/api/user/1003

4. delete user with userId
curl -X DELETE "localhost:8080/api/user/1003"


