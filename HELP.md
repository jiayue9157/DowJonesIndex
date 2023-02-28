## Build
mvn clean install

## Run
mvn spring-boot:run

## UI
http://localhost:8080/
* View existing stock records
* Upload stock csv file
* Insert single stock
* Filter existing stock records by ticker

## Postman
* Filter existing stock records by ticker
http://localhost:8080/api/{ticker}
* Uploaded file
http://localhost:8080/files/{filename}