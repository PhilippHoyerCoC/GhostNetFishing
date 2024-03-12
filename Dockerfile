# Set up build environment
FROM maven:3.9.1-eclipse-temurin-17-focal AS build
# Copy the source code to the container
RUN mkdir -p app
WORKDIR /app
COPY src /app/src
COPY pom.xml /app
# Build the application
RUN mvn -f /app/pom.xml clean package

# Create run container
FROM tomee:9-jre17-plume
# Copy the previously built WAR file to the webapps directory
COPY --from=build /app/target/GhostNetFishing.war /usr/local/tomee/webapps/
# Copy the configured tomee.xml file to the conf directory
COPY config/tomee-docker.xml /usr/local/tomee/conf/tomee.xml
# Copy the missing mysql-connector jar file to the lib directory
COPY config/mysql-connector-j-8.1.0.jar /usr/local/tomee/lib/
# Expose the port TomEE runs on
EXPOSE 8080
# Set the command to start the TomEE server
CMD ["catalina.sh", "run"]