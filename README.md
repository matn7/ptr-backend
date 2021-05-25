# Docker

**Delete all docker images**

```
docker rmi $(docker images -a)
```

**List docker images**

```
docker images -a
```

**Start hello-world image**

```
docker run hello-world
```

### Running Mongo DB Docker container

```
docker run mongo

docker run -d mongo

docker stop <CONTAINER_ID>

docker run -p 27017:27017 -d mongo

docker logs -f <CONTAINER_ID>

docker image inspect mongo
```

```
mvn spring-boot:run
```

### Assign storage

```
docker run -p 27017:27017 -d mongo
docker run -p 27017:27017 -d -v [PATH]/dockerdata/mongo:/data/db mongo
```

### RabbitMQ

```
docker run -d --hostname guru-rabbit --name some-rabbit -p 8080:15672 -p 5671:5671 -p 5672:5672 rabbitmq:3-management 
```

### MySQL

```
netstat -tulpn | grep LISTEN

docker run --name guru-mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=yes  -v /home/mati/udemy_courses/docker-for-java-developers/dockerdata/mysql:/var/lib/mysql -p 3306:3306 -d mysql
```

### Cleanup

**Cleaning Up Images**

```
docker kill $(docker ps -q)

docker rm $(docker ps -a -q)

# Remove a Docker Image
docker rmi <IMAGE_NAME>

# Delete Untagged (dangling) Images
docker rmi $(docker images -q -f dangling=true)

# Delete All Images
docker rmi $(docker images -q)
```

**Cleaning Up Volumes**

```
# Remove all dangling (no associated with container) volumes
docker volume rm $(docker volume ls -f dangling=true -q)
```
























