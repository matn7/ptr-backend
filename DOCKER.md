# Docker

**Run centos**

```
docker run -d centos
docker run -d centos tail -f /dev/null

# shell in
docker exec -it <CONTAINER_NAME> bash

yum install java
```

```
# Backend
docker build -t pandatronik-docker .

docker run -d -p 8080:8080 pandatronik-docker 
# --- to use mysql on local machine (no docker localhost)
docker run --network="host" -d -p 8080:8080 pandatronik-docker

# Frontend
docker build -t pandatronik-ui .

docker run -d -p 4200:4200 pandatronik-ui

# Nginx
docker build -t pandatronik-ui-nginx .
docker run -d -p 8082:80 pandatronik-ui-nginx
```