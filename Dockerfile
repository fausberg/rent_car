FROM openjdk:11
RUN mkdir /app
WORKDIR /app
COPY target/rent_car-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT java -jar /app/rent_car-0.0.1-SNAPSHOT.jar