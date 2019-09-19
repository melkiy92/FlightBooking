# run proxy to connect to remote db
./cloud_sql_proxy -dir=/cloudsql -instances=wise-arena-250513:europe-west3:flight-booking=tcp:3306 -credential_file=Json.json &

# run .jar file
java -jar flight-booking.jar;