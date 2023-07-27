az spring app deploy -s zhiyongli-lab-asa-e -g zhiyongli -n spring-petclinic-native --source-path . --build-env BP_NATIVE_IMAGE=true BP_JVM_VERSION=17 BP_MAVEN_BUILD_ARGUMENTS="-Dmaven.test.skip=true --no-transfer-progress package -Pnative" --build-cpu 4 --build-memory 12Gi --builder native


az spring app deploy -s zhiyongli-lab-asa-e -g zhiyongli -n spring-petclinic --source-path . --build-env BP_JVM_VERSION=17 --build-cpu 4 --build-memory 12Gi