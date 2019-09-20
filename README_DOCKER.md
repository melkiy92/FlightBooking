## How to run FlightBooking inside docker container

1. Clone project
2. Open branch you wnat to execute
3. Create env variables POSTGRE_USERNAME=postgres and POSTGRE_USER_PASSWORD=postgres
4. Clean and package project by maven
5. Make sure that flight-booking.jar was created inside of the target directory
6. Start docker
7. Go to directory of the project
8. Execute: "docker build -t fb .", that will create/update image with name fb (short from flight booking)
9. Execute: "docker run --name fb -p *AnyFreeLocalPort*:8080 fb", that will create container "fb" and run it on your local port
10. If having problems with 9th step make sure you've removed old container (to do this execute: "docker stop fb", "docker system prune", "y".