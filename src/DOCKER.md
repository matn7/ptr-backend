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


docker-compose up
docker-compose down

***

**kubernetes**

**pandatronik-backend**

docker tag com.pandatronik.rest/pandatronik.rest:1.0-SNAPSHOT mateusznowak/kub-ptr-backend:latest
docker push mateusznowak/kub-ptr-backend:latest

**pandatronik-mysql**

docker tag pandatronik-mysql:1.0-SNAPSHOT mateusznowak/kub-ptr-mysql:latest
docker push mateusznowak/kub-ptr-mysql:latest

**pandatronik-ui**

docker tag pandatronik-ui:1.0-SNAPSHOT mateusznowak/kub-ptr-ui:latest
docker push mateusznowak/kub-ptr-ui:latest


kubectl apply -f=ptr-mysql-deployment.yaml -f=ptr-mysql-service.yaml
kubectl apply -f=ptr-backend-deployment.yaml -f=ptr-backend-service.yaml
kubectl apply -f=ptr-ui-deployment.yaml -f=ptr-ui-service.yaml

kubectl delete -f=ptr-ui-deployment.yaml -f=ptr-ui-service.yaml
kubectl delete -f=ptr-backend-deployment.yaml -f=ptr-backend-service.yaml
kubectl delete -f=ptr-mysql-deployment.yaml -f=ptr-mysql-service.yaml

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

**Multi Container App - running on AWS**

docker build -t goals-node ./backend/
docker tag goals-node mateusznowak/goals-node
docker push mateusznowak/goals-node

***

**Kubernetes**

docker build -t kub-first-app .
docker tag kub-first-app mateusznowak/kub-first-app
docker push mateusznowak/kub-first-app

- Create Deployment Object

kubectl create deployment first-app --image=mateusznowak/kub-first-app
kubectl get deployments
kubectl get pods
kubectl delete deployment first-app

- Create Service Object

kubectl expose deployment first-app --type=LoadBalancer --port 8080
kubectl get services

- Restarting Containers

- Autoscaling

kubectl scale deployment/first-app --replicas=3
kubectl get pods

- Updating Deployments

docker tag kub-first-app mateusznowak/kub-first-app:2
docker push mateusznowak/kub-first-app:2
kubectl set image deployment/first-app kub-first-app=mateusznowak/kub-first-app:2
kubectl rollout status deployment/first-app

- Deployment Rollbacks & History

kubectl rollout undo deployment/first-app
kubectl rollout status deployment/first-app
kubectl rollout history deployment/first-app
kubectl rollout history deployment/first-app --revision=3

kubectl rollout undo deployment/first-app --to-revision=1

kubectl delete service first-app
kubectl delete deployment first-app

- Adding Pod and Containers Specs

kubectl apply -f=deployment.yaml
kubectl get deployments
kubectl get pods

- Creating a service Declaratively

kubectl apply -f=service.yaml
kubectl get services

- Updating & Deleting Resources

kubectl apply -f=deployment.yaml
kubectl delete -f=deployment.yaml -f=service.yaml

kubectl apply -f=master-deployment.yaml

- Delete by Selector / Label

kubectl delete -f=master-deployment.yaml
kubectl apply -f=deployment.yaml -f=service.yaml
kubectl delete deployments,services -l group=example

**Data & Volumes with Kubernetes**

docker-compose up -d --build

docker build -t mateusznowak/kub-data-demo . 

kubectl apply -f=service.yaml -f=deployment.yaml

- Using a Claim in Pod

kubectl get sc
kubectl apply -f=host-pv.yaml
kubectl apply -f=host-pvc.yaml
kubectl get pv
kubectl get pvc

- Environment Variables & ConfigMaps

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: data-store-env
data:
  folder: 'story'
```

kubectl apply -f=environment.yaml
kubectl get configmap

kubectl apply -f=deployment.yaml

**Kubernetes Networking**

docker-compose up -d --build

docker build -t mateusznowak/kub-demo-users .
docker push mateusznowak/kub-demo-users

kubectl apply -f=users-deployment.yaml
kubectl get deployment
kubectl get pods

kubectl apply -f=users-service.yaml
kubectl get service

docker build -t mateusznowak/kub-demo-auth .
docker push mateusznowak/kub-demo-auth

kubectl apply -f=auth-service.yaml -f=auth-deployment.yaml
kubectl get services

kubectl get namespaces

docker build -t mateusznowak/kub-demo-tasks .
docker push mateusznowak/kub-demo-tasks

kubectl apply -f=tasks-service.yaml -f=tasks-deployment.yaml

docker build -t mateusznowak/kub-demo-frontend .
docker run -p 8888:80 --rm -d mateusznowak/kub-demo-frontend

docker push mateusznowak/kub-demo-frontend

kubectl apply -f=frontend-service.yaml -f=frontend-deployment.yaml

cd /home/mati/code/docker/kub-network-01-starting-setup/kubernetes
kubectl delete -f=auth-service.yaml -f=auth-deployment.yaml
kubectl delete -f=tasks-service.yaml -f=tasks-deployment.yaml
kubectl delete -f=frontend-service.yaml -f=frontend-deployment.yaml
kubectl delete -f=users-deployment.yaml -f=users-service.yaml






