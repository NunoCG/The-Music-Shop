# The-Music-Shop

### **Microservices project using Java Spring Boot, Docker, Postgres and ActiveMQ**

<br />

This project has 5 services running individually in 5 seperate Docker containers:

- A music store app (REST API), which allows clients to register and allows customer and stock management.
- A Postegres database to store all the necessary data.
- A rental service, which allows clients to rent vinyls from the music store app.
- A discount service which communicates with the rental and the music store services only through Message Queueing.
- A container running ActiveMQ.

<br />

**To run this project you just need to compose the docker-compose.yml file on the root of the project:**

  ```yml
  docker compose --build -d
  ```
