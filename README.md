There are two sub-projects updateMicroservice and retrieveMicroservice, and use postgres to store customers' perferences. Each custemer will has a unique userId, and userId is primaray key in perference table as well.

# Pre-install environment:
1. docker
2. docker-compose
3. gradle

# How to build it and run it?
1. go to root folder TGTAssessment
2. ```gradle clean```
3. ```gradle build```
   (it will pass unit test and then genrate two jar ./TGTAssessment/updateMicroservice/build/libs/TGTAssessment-update-service.jar and ./TGTAssessment/retrieveMicroservice/build/libs/TGTAssessment-retrieve-service.jar)
4. ```docker-compose -f docker-compose.yml build```
5. ```docker-compose -f docker-compose.yml up -d```
6. ```docker-compose -f docker-compose.yml down``` (stop services)

# Swagger-ui
retriever: http://localhost:8898/swagger-ui.html<br>
updater: http://localhost:8899/swagger-ui.html

# API endpoint
- retrieve microservice:<br>
\- GET /v1/api/heartbeat => healthcheck for monitoring.<br>
\- GET /v1/api/perferences/userId/{userId} => retrieve userId/customerId's perferences, for eample what email pattern/template they prefered, like ```'Good morning, [name]. I am pleasure to share a infomration for you. [content] <img width="100" height="50" src=[log.img]<br><br> Your sincerely, TG techology'``` with html and css, all bracket could be replace to customerized content by email seneder service. Response only reply customer is interested, no sms meant they are not prefered, more detail data struture  as floowing:<img width="984" alt="image" src="https://user-images.githubusercontent.com/23376300/129463860-57b49ad2-7bd6-440b-8f29-7ac962b76cdd.png"><br>
- update microservice:<br>
\- GET /v1/api/heartbeat => healthcheck for monitoring.<br>
\- UPDATE /v1/api/perferences/userId/{userId} => create/update userId's perferences. About request body, not put sms into body if customer is not interesed in this way. the field of name is using to replace template's bracket, could be 'Dear valued' or title come with customer's name. more detail as following.<br>
\- DELETE /v1/api/perferences/userId/{userId} => delete userId' perferences, userId might not be customer anymore. Database will not clean record, only update deleted flag as true to identify it is non-existed customer. retrieve-api will only retrieve perference with deleted=false.<img width="984" alt="image" src="https://user-images.githubusercontent.com/23376300/129463856-ebdb9539-4e02-4a42-8789-53382a0ab08c.png">

# Check database/table data
\- How to check if table is existed? If postgresdb is empty, and spring boot will initalize table by defined DAO.
```
docker exec -it postgresql bash -c "export PGPASSWORD='root'; psql -h postgresql -U root -d tgt_db -c '\d'"
```
\- Check perference table's schema
```
docker exec -it postgresql bash -c "export PGPASSWORD='root'; psql -h postgresql -U root -d tgt_db -c '\d perference;'"
```
\- How to check what data there are in postgresdb?
```
docker exec -it postgresql bash -c "export PGPASSWORD='root'; psql -h postgresql -U root -d tgt_db -c 'select * from perference;'"
```


# Kubernetes
There is some configuration need to setup on kuberntes before deploy it, such as database related information, secrets variable, ingress hostname, etc.
- How to setup your db's username and password as secrets in kubernetes.<br>
```
kubectl create secret generic tgt-db-secret --from-literal=db_username=${YOUR_DB_USERNAME} --from-literal=db_password='${YOUR_DB_PASSWORD}'
```
- How to deploy<br>
```
kubectl apply -n ${YOUR_K8S_NAMESPACE} -f kubernetes/update-microservice.yml
kubectl apply -n ${YOUR_K8S_NAMESPACE} -f kubernetes/retrieve-microservice.yaml
```
