export DOCKER_HOST=unix:///home/mati/.docker/desktop/docker.sock

docker network create pandatronik-net

#docker run --name pandatronik-rest --network pandatronik-net -p 8080:8080 com.pandatronik.rest/pandatronik.rest:1.0-SNAPSHOT
docker run --name pandatronik-rest --network pandatronik-net com.pandatronik.rest/pandatronik.rest:1.0-SNAPSHOT

docker build -t pandatronik-ui:1.0-SNAPSHOT .

docker run --name pandatronik-ui --network pandatronik-net -d --rm -p 4200:4200 pandatronik-ui:1.0-SNAPSHOT


**mysql**

docker build -f mysql.dockerfile -t pandatronik-mysql:1.0-SNAPSHOT .

docker run -e MYSQL_ROOT_PASSWORD=secret -it pandatronik-mysql:1.0-SNAPSHOT

docker exec -it <container-id> bash


###################################

**Build docker image**

docker build .

**Run docker image**

docker run -p 3000:3000 <image-id>

**List running containers**

docker ps

**Stop container**

docker stop <container-id>

**Interactive session inside container**

docker run -it node

**Dockerfile for node example**

```
FROM node

WORKDIR /app

COPY . /app

RUN npm install

EXPOSE 80

CMD ["node", "server.js"]
```

docker build .
docker run -p 3000:80 <container-id>
docker stop <container-id>

**Dockerfile for python example**

```
FROM python

WORKDIR /app

COPY . /app

CMD ["python", "rng.py"]
```

docker build .
docker run -it <image-id>
docker start -a -i <container-id>

**Deleting Images & Containers**

docker rm <container-id>
docker rmi <image-id>

// run in detach mode and remove after stopped
docker run -p 3000:80 -d --rm <image-id>

**Copying Files Into & From A Container**

docker cp dummy/. <container-id>:/test
docker cp <container-id>:/test dummy/

**Naming & Tagging Containers and Images**

docker run -p 3000:80 -d --rm --name <container-name> <image-id>

**Tag image**

docker tag node-app:latest mateusznowak/node-hello-world

docker push mateusznowak/node-hello-world

docker login
docker logout

docker pull mateusznowak/node-hello-world

docker build -t feedback-node .

**Volumes**

docker run -p 3000:80 -d --name feedback-app --rm feedback-node 

docker build -t feedback-node:volumes .

docker run -p 3000:80 -d --name feedback-app -v feedback:/app/feedback --rm feedback-node:volumes 

docker volume ls

**Bind Mounts**

docker run -d -p 3000:80 --rm --name feedback-app -v feedback:/app/feedback -v "/home/mati/code/docker/data-volumes-01-starting-setup:/app:ro" -v /app/temp -v /app/node_modules feedback-node:volumes 

docker volume create feedback-files
docker volume rm feedback-files

**Environment Variables**

docker run -d -p 3000:8000 --env PORT=8000 --rm --name feedback-app -v feedback:/app/feedback -v "/home/mati/code/docker/data-volumes-01-starting-setup:/app:ro" -v /app/temp -v /app/node_modules feedback-node:env


docker run -d -p 3000:8000 --env-file ./.env --rm --name feedback-app -v feedback:/app/feedback -v "/home/mati/code/docker/data-volumes-01-starting-setup:/app:ro" -v /app/temp -v /app/node_modules feedback-node:env

docker build -t feedback-node:dev --build-arg DEFAULT_PORT=8000 .

**MongoDB Container**

docker run -d --name mongodb mongo

docker run --name favorites -d --rm -p 3000:3000 favorites-node

docker container inspect mongodb

**Docker Networks**

docker network create favorites-net
docker network ls

docker run -d --name mongodb --network favorites-net mongo

docker run --name favorites --network favorites-net -d --rm -p 3000:3000 favorites-node

**Multi-Container App**

docker network ls
docker network create goals-net

docker run --name mongodb -v data:/data/db --rm -d --network goals-net -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=secret mongo


docker build -t goals-node .
docker run --name goals-backend -v /home/mati/code/docker/multi-01-starting-setup/backend:/app -v logs:/app/logs -v /app/node_modules --rm -d --network goals-net -p 4200:4200 goals-node

// network is not needed since react app runs in browser, we have to publish though port 4200 for goals-node 
docker build -t goals-react .
docker run -v /home/mati/code/docker/multi-01-starting-setup/frontend/src:/app/src --name goals-frontend --rm -p 3000:3000 -it goals-react

**Docker compose**

docker-compose up -d
docker-compose down -v

// force rebuild images
docker-compose up --build

// just build images
docker-compose build

**Utility Containers**

docker run -it -d node
docker exec -it <container-name>

docker run -it node npm init 

```
FROM node:20-alpine

WORKDIR /app 
```

docker build -t node-util .

docker run -it -v /home/mati/code/docker/docker-utilities/docker-complete:/app node-util npm init

```
FROM node:20-alpine

WORKDIR /app 

ENTRYPOINT [ "npm" ]
```

docker run -it -v /home/mati/code/docker/docker-utilities/docker-complete:/app mynpm init
docker run -it -v /home/mati/code/docker/docker-utilities/docker-complete:/app mynpm install express --save

docker-compose run --rm npm init

docker-compose run --rm composer create-project --prefer-dist laravel/laravel .

docker-compose up -d --build server

docker-compose up -d server php mysql

**AWS Example**

docker build -t node-dep-example .

docker run -d --rm --name node-dep -p 80:80 node-dep-example

**Docker Hub - running on AWS**

docker build -t node-dep-example-1 .
docker tag node-dep-example-1 mateusznowak/node-example-1
docker push mateusznowak/node-example-1

docker pull mateusznowak/node-example-1
docker run -d --rm -p 80:80 mateusznowak/node-example-1
