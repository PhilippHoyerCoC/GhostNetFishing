# Project for the IU course "Programmierung von industriellen Informationssystemen mit Java EE"

This project runs on 
- Java 17
- Jakarta EE 9
- maven 3.9.4
- apache-tomee-plume-9.1.0 server

This server can be downloaded on 
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

## Start up the database container: \
docker run -p 3307:3306 --name=ghostnet-db --network=ghostnet -e MYSQL_ROOT_PASSWORD=geheim 
-e MYSQL_USER=ghostnet -e MYSQL_PASSWORD=geheim -e MYSQL_DATABASE=ghostnet -d mysql/mysql-server:latest

In case the port 3307 is already used on the target machine change the port in the docker run
command and adjust the port in the JdbcUrl of the tomee.xml provided below!

Connect to the database: \
mysql -h localhost -P 3307 --protocol=tcp -u ghostnet -p geheim

If you don't have mysql configured on your machine you can log in into the container via
"docker exec -it CONTAINER_NAME" and execute the mysql command from there.

## Adjust TomEE: 
- Adjust tomee.xml in conf folder of tomee
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
- Add mysql-connector-j-8.1.0.jar (adhered in the project root directory) to lib of tomee

## Start application:
The application was developed with Intellij. When you have the tomee server installed and configured properly as shown below you can set the tomee server as runtime. Also add the url http://localhost:8080/GhostNetFishing as startup url.

Also make sure to set the following environment variables:

DB_URL: jdbc:mysql://localhost:3307/ghostnet
DB_USERNAME: ghostnet
DB_PASSWORD: geheim

These variables are used to connect to the database an should not be hard coded. For production environments the values should be changed and not inserted into git without encryption!
If you start the application from your IDE make sure the environment variables are set at startup of the tomee server.
When you start the tomee server manually you can copy the setenv.sh or setenv.bat to the bin folder of the tomee server. Them the environment variables are automatically set when you start the server.

Build the application with maven: mvn clean install

Start the application manually:
- Start the tomee server with the startup.sh or startup.bat in the bin folder of the tomee server
- Copy the war file from the target folder of the project to the webapps folder of the tomee server
- Open the browser and go to http://localhost:8080/GhostNetFishing/index.xhtml