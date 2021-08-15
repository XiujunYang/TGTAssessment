# Alpine Linux with OpenJDK JRE
FROM adoptopenjdk/openjdk11:latest

RUN mkdir -p /home/workspaces
WORKDIR /home/workspaces
COPY build/libs/TGTAssessment-retrieve-service.jar TGTAssessment-retrieve-service.jar
CMD java -jar TGTAssessment-retrieve-service.jar
