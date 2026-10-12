# Build the WAR with Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
COPY pages ./pages
RUN mvn -q -DskipTests clean package

# Run it on Tomcat 10.0.x (Jakarta Servlet 5.0, matches the app's dependency)
FROM tomcat:10.0-jdk17-temurin
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY --from=build /build/target/ROOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
