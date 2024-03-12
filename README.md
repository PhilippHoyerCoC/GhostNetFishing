# Project for the IU course "Programmierung von industriellen Informationssystemen mit Java EE"

This project runs on 
- Java 17
- Jakarta EE 9
- maven 3.9.4
- apache-tomee-plume-9.1.0 server

This server can be downloaded at 
https://www.apache.org/dyn/closer.cgi/tomee/tomee-9.1.0/apache-tomee-9.1.0-plume.zip

The project was developed in Intellij IDEA so the .idea directory with the project
config was added to simplify the project startup from another dev. 

The decision for a TomEE server instead of a Tomcat server is because with a
Tomcat in Jakarta EE 9 you have to manually add all the JSF dependencies like
Mojarra manually via the pom.xml. And before you start configuring an application
server manually you can just use the application server directly. The Tomcat is a more
lightweight server but for this case the size of the container doesn't matter so a
TomEE is easier for project setup.
The database is not set up locally but in a container. This reduces the
chance of problems because of the local setup.

# Steps for setting up the project:

## Start the application containerized (adviced method)

### Build the ghostnet application docker image:
Go to the root directory of the project and execute the following command:
```
docker build --no-cache -t ghostnetfishing .
```
You don't need to have java installed on your local machine as this is a multistage build.

### Start the docker container with docker compose:
Go to the root directory of the project and execute the following command:
```
docker compose -f docker-compose.yaml up
```
This will start the application server and the database server.

### Start the containers manually:

#### Create the docker network:
```
docker network create ghostnet
```
#### Start up the database container:
```
docker run -p 3307:3306 --name=ghostnet-db --network=ghostnet -e MYSQL_ROOT_PASSWORD=geheim 
-e MYSQL_USER=ghostnet -e MYSQL_PASSWORD=geheim -e MYSQL_DATABASE=ghostnet -d mysql/mysql-server:8.0.32
```
The port forward from 3306 to 3307 is made so that the database can be accessed from outside the container if you want to check the database entries. 3307 was used for the port on localhost because 3306 is often already in use by another database. If you forward another port and want to connect to the database adjust the port when you run the mysql cammand.

Connect to the database:
```
mysql -h localhost -P 3307 --protocol=tcp -u ghostnet -p geheim
```

In case the port 3307 is already in use on the target machine, change the port in the docker run
command.

If you don't have mysql configured on your machine you can log in into the container via
```
docker exec -it CONTAINER_NAME sh
```
and execute the mysql command from there.

#### Start up the ghostnet application container:
```
docker run -p 8081:8080 --name ghostNetFishing --network=ghostnet ghostnetfishing:latest
```
Here again adjust the forwarded port 8081 if the port is already in use on your machine!

## Start the application plain (not inside a container)

### Setup Databse:
You can still start the mysql database in a container as described above. Otherwise there are plenty of ways to start a mysql database.
Make sure you use a mysql database with mysql-server 8.0.32, you name the database ghostnet and create a user with username ghostnet and
password geheim.
In case you want to use other credentials (highly recommended for production environment), you have to adjust parameters in the tomee.xml below.
After starting the database check the connectivity with the database with the following command:
```
mysql -h localhost -P 3307 --protocol=tcp -u ghostnet -p geheim
```

### Adjust TomEE: 
- If you start the ghostnet application without docker you have to adjust the tomee.xml file in the conf folder of the server 
to connect to the correct target database. If you started the database on another port than 3307 adjust it in the JdbcUrl.
```
<?xml version="1.0" encoding="UTF-8"?>
<tomee>
  <Resource id="MyDataSource" type="DataSource">
      JdbcDriver com.mysql.cj.jdbc.Driver
      JdbcUrl jdbc:mysql://localhost:3307/ghostnet
      UserName ghostnet
      Password geheim
      JtaManaged true
  </Resource>
</tomee>
```

- You also have to add the mysql-connector-j-8.1.0.jar (adhered in the project config directory) to lib of tomee.

### Start application:
The application was developed with Intellij. When you have the tomee server installed and configured properly as shown below you can set the tomee server as runtime. 
Also add the url http://localhost:8080/GhostNetFishing as startup url. Then you can start the application with the play button in the top right corner of Intellij.

Build the application with maven: mvn clean install

Start the application manually without IDE:
- Start the tomee server with the startup.sh or startup.bat in the bin folder of the tomee server
- Copy the war file from the target folder of the project to the webapps folder of the tomee server
- Open the browser and go to http://localhost:8080/GhostNetFishing/index.xhtml