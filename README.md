# berkut-mobile-app
**to launch Postgres container:**
docker  container run --detach 
--name test-postgres-container 
-p 5433:5432 
-e POSTGRES_PASSWORD=password 
-e POSTGRES_USER=Moldir 
-e  POSTGRES_DB=berkut 
-d postgres:latest

**build the image for berkut app:** 
docker build -t berkut-app .

**build the berkut app container:**
docker container run 
--name=berkut-app-container 
-p 8090:8080 
--link test-postgres-container:postgres 
-d --rm 6dd8537ded27