mvn clean
mvn org.openrewrite.maven:rewrite-maven-plugin:run "-Drewrite.recipeArtifactCoordinates=org.openrewrite.recipe:rewrite-spring:RELEASE" "-Drewrite.activeRecipes=org.openrewrite.java.spring.boot3.SpringBoot3BestPractices"
mvn package -Dmaven.test.skip=true
