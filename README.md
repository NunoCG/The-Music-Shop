# The-Music-Shop

### **Microservices project using Java Spring Boot, Docker and Postgres**

<br />

**This project has 3 services running individually in 3 seperate Docker containers:**

- A music store app (REST API), which allows clients to register and allows customer and stock management.
- A Postegres database to store all the necessary data.
- A rental service, which allows clients to rent vinyls from the music store app.

<br />

**Project Structure:**

- Inside the "app" folder is the music store web app, where is possible to manage clients and vinyls (only through calls to API endpoints).
- Inside the "rental" folder is the rental service, where clients can rent vinyls that the store has or will have. (again, only through calls to API endpoints).
- Both of this services have their own Dockerfile and both are connected to the same postgres database service, which is running on a seperate Docker container.

<br />

# Process after cloning

## **1 - Run this command to build you springboot app and generate the jar:**
	
	mvn clean install -DskipTests=true

**Note:** We want to skip the tests because all the tests related to the database will fail because we will run the 
	postgres container in the next step.
  
## **2 - After that we need to compose the images that we designed in the docker-compose.yml file. So run this command:**
	
	docker compose up -d
  
**Note:** -d is the Detached mode: Tells to run containers in the background. In the first time run the command without the -d to better see all the logs. If you want to use -d and run the logs latter using:

  docker logs -f container

## **3- Than your springboot app, rental service and the postgres db will be all running in 3 differente containers.**
  
Now test all the API endpoints on Postman.

<br />

# Extra:

## **4 - To check everything is working open a different terminal and run:**
  
  docker ps

**Note:** Make sure if it shows 3 different conatiners running.

## **5 - Than we want to enter inside the postgres container and enter our database to verify if our database and our tables were really created. So run:**
  
  docker exec -it container psql -U postgres database

**Note:** -U postgres --> is the default username for a postgras database server, if you defined a specific username, change it to yours.

## **6 - Than we can run: \dt to see every table created inside our database, and run SQL scripts.**

(Still working on it)
