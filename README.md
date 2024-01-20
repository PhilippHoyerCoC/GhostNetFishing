Project for the IU course "Programmierung von 
industriellen Informationssystemen mit Java EE"

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
Mojarra manually via the pom.xml and before you start configuring an application
server manually you can just use the application server directly. The Tomcat is more
lightweight server but for this case the size of the container doesn't matter so a
TomEE is much easier for project setup.

Start up the database container:
docker run -p 3307:3306 --name=ghostnet-db --network=ghostnet -e MYSQL_ROOT_PASSWORD=geheim 
-e MYSQL_USER=ghostnet -e MYSQL_PASSWORD=geheim -e MYSQL_DATABASE=ghostnet -d mysql/mysql-server:latest

Connect to the database: mysql -h localhost -P 3306 --protocol=tcp -u ghostnet -p

Adjust TomEE: 
- Adjust tomee.xml in conf folder of tomee
<?xml version="1.0" encoding="UTF-8"?>
<tomee>
  <Resource id="MyDataSource" type="DataSource">
      JdbcDriver com.mysql.cj.jdbc.Driver
      JdbcUrl jdbc:mysql://localhost:3306/ghostnet
      UserName root
      <!--Password geheim-->
      JtaManaged true
  </Resource>
</tomee>

- Add mysql-connector-j-8.1.0.jar to lib of tomee