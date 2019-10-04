#openjdk 8 environment
FROM openjdk:8

#ports to expose
#EXPOSE 8080

#add REST API's .jar
ADD target/flight-booking.jar flight-booking.jar

#add .json for proxy database connection
ADD Json.json Json.json

#add script to run proxy and API's .jar
ADD script.sh script.sh

#DB username
#ENV POSTGRE_USERNAME = ${POSTGRE_USERNAME}

#DB user password
#ENV POSTGRE_USER_PASSWORD = ${POSTGRE_USER_PASSWORD}

# update apt-get and install wget to install cloud_sql_proxy
RUN apt-get update; apt-get -y install wget

# install cloud_sql_proxy give it 777 rights, ..., give 775 rights to script and run it
CMD wget https://dl.google.com/cloudsql/cloud_sql_proxy.linux.amd64 -O cloud_sql_proxy; mkdir /cloudsql; chmod 777 /cloud_sql_proxy; chmod 777 /cloudsql; chmod 775 /script.sh; ./script.sh;
#tail -f /dev/null