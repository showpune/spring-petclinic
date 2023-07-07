echo "$1"
docker pull "$1"
docker image tag "$1" zhiyongliacr.azurecr.io/supply_chain/petclinic-prod-bundle:latest
docker image push zhiyongliacr.azurecr.io/supply_chain/petclinic-prod-bundle:latest
