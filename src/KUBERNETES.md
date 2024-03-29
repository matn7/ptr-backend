# Kubernetes

kubectl create deployment hello-world-rest-api --image=in28min/hello-world-rest-api:0.0.1.RELEASE

**expose to outside world**

kubectl expose deployment hello-world-rest-api --type=LoadBalancer --port=8080

**commands**

kubectl get events

kubectl get pods

kubectl get replicaset

kubectl get service

kubectl describe pod <POD_ID>

**replicaset**

kubectl get replicaset

kubectl get pods -o wide

kubectl delete pods hello-world-rest-api-7b85b6b8f8-tdc6k

kubectl scale deployment hello-world-rest-api --replicas=3

kubectl get pods

kubectl get events --sort-by=.metadata.creationTimestamp

kubectl explain replicaset

**deployment**

kubectl set image deployment hello-world-rest-api hello-world-rest-api=DUMMY_IMAGE:TEST

kubectl set image deployment hello-world-rest-api hello-world-rest-api=in28min/hello-world-rest-api:0.0.2.RELEASE

kubectl delete pod hello-world-rest-api-554489b68f-7jmdp

**service**

kubectl get services

**master node**

kubectl get componentstatuses

**gcloud**`

gcloud auth login

docker push mateusznowak/mmv3-currency-exchange-service:0.0.11-SNAPSHOT
docker push mateusznowak/mmv3-currency-conversion-service:0.0.11-SNAPSHOT

mvn spring-boot:build-image -DskipTests


kubectl create deployment currency-exchange --image=mateusznowak/mmv3-currency-exchange-service:0.0.11-SNAPSHOT

kubectl expose deployment currency-exchange --type=LoadBalancer --port=8000


kubectl create deployment currency-conversion --image=mateusznowak/mmv3-currency-conversion-service:0.0.11-SNAPSHOT

kubectl expose deployment currency-conversion --type=LoadBalancer --port=8100


kubectl get deployment currency-exchange -o yaml >> deployment.yaml
kubectl get service currency-exchange -o yaml >> service.yaml

kubectl apply -f deployment.yaml
kubectl diff -f deployment.yaml

kubectl get pods

kubectl delete all -l app=currency-exchange
kubectl delete all -l app=currency-conversion


kubectl apply -f deployment.yaml

**config map**

kubectl create configmap currency-conversion --from-literal=CURRENCY_EXCHANGE_URI=http://currency-exchange
kubectl get configmap

kubectl get configmap currency-conversion -o yaml

kubectl get configmap currency-conversion -o yaml >> configmap.yaml


**deployments**

kubectl rollout history deployment currency-conversion

kubectl rollout history deployment currency-exchange

kubectl rollout undo deployment currency-exchange --to-revision=1


**liveness, readiness probes**

**autoscaling**

kubectl scale deployment currency-exchange --replicas=2
kubectl autoscale deployment currency-exchange --min=1 --max=3 --cpu-percent=70 

kubectl top pod

kubectl delete hpa currency-exchange








