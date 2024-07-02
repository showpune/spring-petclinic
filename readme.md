# Sample Project for migration

## Check before run

### Compile the code
```
mvn package -Dmaven.test.skip=true
```
### Check the endpoint
access http://localhost:8080/actuator/info

## upgrade the code
```
mvn clean
mvn org.openrewrite.maven:rewrite-maven-plugin:run "-Drewrite.recipeArtifactCoordinates=org.openrewrite.recipe:rewrite-spring:RELEASE" "-Drewrite.activeRecipes=org.openrewrite.java.spring.boot3.SpringBoot3BestPractices"
```
This will upgrade the code to Spring Boot 3, but there is a bug, you need to remove the scope of dependencies

```xml
  <dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
</dependency>
```
Then execute

```
mvn package -Dmaven.test.skip=true
```

### Check the endpoint
access http://localhost:8080/actuator/info
