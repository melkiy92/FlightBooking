## How to run FlightBooking inside docker container

1. Clone project
2. Open branch you wnat to execute
3. Clean and package project by maven
4. Make sure that flight-booking.jar was created inside of the target directory
5. Start docker
6. Go to directory of the project
7. Execute: "docker build -t fb .", that will create/update image with name fb (short from flight booking)
8. Execute: "docker run --name fb -p *AnyFreeLocalPort*:8080 fb", that will create container "fb" and run it on your local port
9. If having problems with 8th step make sure you've remover old container (to do this execute: "docker stop fb", "docker system prune", "y".