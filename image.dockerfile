FROM openjdk:11.0
WORKDIR /app
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8787
COPY ./target/spring-petclinic-2.6.0-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "spring-petclinic-2.6.0-SNAPSHOT.jar"]
EXPOSE 8080
EXPOSE 8787