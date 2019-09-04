FROM openjdk:8
ADD target/flight-booking.jar flight-booking.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "flight-booking.jar"]
