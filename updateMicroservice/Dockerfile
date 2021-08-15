# Alpine Linux with OpenJDK JRE
FROM adoptopenjdk/openjdk11:latest

RUN mkdir -p /home/workspaces
WORKDIR /home/workspaces
COPY build/libs/TGTAssessment-update-service.jar TGTAssessment-update-service.jar 
CMD java -jar TGTAssessment-update-service.jar
