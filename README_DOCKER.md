## How to run FlightBooking inside docker container

1. Clone project
2. Open branch you want to execute
3. Create env variables POSTGRE_USERNAME=******** and POSTGRE_USER_PASSWORD=********
4. Clean and package project by maven
5. Make sure that flight-booking.jar was created inside of the target directory
6. Start docker
7. Go to directory of the project
8. Execute: "docker-compose build", that will create/update images
9. Execute: "docker-compose up", that will create containers and run them on your local port
10. Go to 192.168.99.100:8085 (on Windows) or localhost:8085 (on Unix)

** to restart application go to console and press Ctrl+C to stop containers, write "docker-compose down" and repeat step 9