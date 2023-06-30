az spring app create --service zhiyongli-lab-asc-e  --resource-group zhiyongli --name petclinic-3x-native
az spring app deploy --name petclinic-3x-native --source-path . --service zhiyongli-lab-asc-e --resource-group zhiyongli --build-env BP_NATIVE_IMAGE=true BP_JVM_VERSION=17 BP_MAVEN_BUILD_ARGUMENTS="-Dmaven.test.skip=true -Pnative package" --build-cpu 4 --build-memory 12Gi --builder native

az spring app create --service zhiyongli-lab-asc-e  --resource-group zhiyongli --name petclinic-3x
az spring app deploy --name petclinic-3x --source-path . --service zhiyongli-lab-asc-e --resource-group zhiyongli --build-env BP_JVM_VERSION=17

az spring app create --service zhiyongli-lab-asc-e  --resource-group zhiyongli --name petclinic-3x-jar
az spring app deploy --name petclinic-3x-jar --artifact-path ./target/spring-petclinic-3.0.0-SNAPSHOT.jar --service zhiyongli-lab-asc-e --resource-group zhiyongli --build-env BP_JVM_VERSION=17
