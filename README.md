
# Commonwealth Casualty Company AWS Resources Dashboard Backend
## California State University, Los Angeles.

### By Misael Corvera, Jateni Dida, Alexander Horejsi, Yi Wang, and Zac You.
### Faculty Advisor : Dr. Chengyu Sun.

### Deploy Instructions:

The project requires the following enviroment variable:

1. DATABASE_URL
2. DATABASE_USERNAME
3. DATABASE_PASSWORD
4. JTW_SECRET
5. AWS_ACCESS_KEY_ID (Use same key from the fronted)
6. AWS_SECRET_ACCESS_KEY 
7. AWS_REGION

If you choose to hard core the values, take a look at the application.properties.sample and GlobalVariables.java in order to see a sample.

Once all enviroment variable are added, you can go to the project directory on the cmd and run "mvn package". This will create a JAR file that can be deploy to a server.



