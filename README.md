There are two sub-projects updateMicroservice and retrieveMicroservice, and use postgres to store customers' perferences. Each custemer will has a unique userId, and userId is primaray key in perference table as well.

preinstall environment:
1. docker
2. docker-compose
3. gradle

How to build it and run it?
1. go to root folder GTAssessment
2. gradle clean
3. gradle build
   (it will pass unit test and then genrate two jar ./TGTAssessment/updateMicroservice/build/libs/TGTAssessment-update-service.jar and ./TGTAssessment/retrieveMicroservice/build/libs/TGTAssessment-retrieve-service.jar)
4. docker-compose -f docker-compose.yml build
5. docker-compose -f docker-compose.yml up -d

swagger-ui:
retriever: http://localhost:8898/swagger-ui.html
updater: http://localhost:8899/swagger-ui.html

# How to check if table is existed? If postgresdb is empty, and spring boot will initalize table by defined DAO.
docker exec -it postgresql bash -c "export PGPASSWORD='root'; psql -h postgresql -U root -d tgt_db -c '\d'"
# Check perference table's schema
docker exec -it postgresql bash -c "export PGPASSWORD='root'; psql -h postgresql -U root -d tgt_db -c '\d perference;'"
# How to check what data there are in postgresdb.
docker exec -it postgresql bash -c "export PGPASSWORD='root'; psql -h postgresql -U root -d tgt_db -c 'select * from perference;'"


kubernetes:
There is some configuration need to setup on kuberntes before deploy it, such as database related information, secrets variable, ingress hostname, etc.
# How to setup your db's username and password as secrets in kubernetes.
kubectl create secret generic tgt-db-secret --from-literal=db_username=${YOUR_DB_USERNAME} --from-literal=db_password='${YOUR_DB_PASSWORD}'

following command to 
kubectl apply -n ${YOUR_K8S_NAMESPACE} -f kubernetes/update-microservice.yml
kubectl apply -n ${YOUR_K8S_NAMESPACE} -f kubernetes/retrieve-microservice.yaml