### AWS Startup Template For Spring Boot Developers
#### Welcome to AWS Full-Stack Developer Template: Swagger UI + Spring Boot + Terraform + Kubernetes + Keycloak Oauth2 Authorization Server + Github Actions + Local Docker Build and Start Environment + Integration Tests with TestContainers + Spring Cloud Gateway + Spring Cloud Stream + Dispatcher Pattern + AWS SSL Certificate + External DNS + AWS Load Balancer Controller + Spring Cloud Kubernetes + Grafana Observability Stack

#### Local Docker Environment Setup:

```
sh docker-start.sh
```

- this script will build docker images and start environment with your code changes

- Warning! Make sure that all kafka containers are successful are started successfully! (see `Kafka Containers Troubleshooting` below)

- open `localhost:9000` in your Browser and switch between `Order` and `Customer` Microservices

- Warning! If Swagger UI fails to load on the first try, please, refresh the page!

- Warning! Sometimes switching between `Order` and `Customer` doesn't refresh Swagger UI completely and you might see wrong REST endpoints: just refresh the page and continue

- Warning! Sometimes REST endpoints return `504 Gateway Timeout`, just retry the REST API endpoint again

- click "Authorize" and use admin/admin or user/user for credentials (clientId should be `twitter-app`)

- create Customer on `Customer` page (see `json-files/customer.json` as example)

- create Order on `Order` page (see `json-files/order.json` as example: restaurants with these ids are already created with sql init scripts)

- Warning! If Kafka or Schema Registry has errors, you will see error "Customer with this id doesn't exist", because customer, created in the previous step, was not propagated by Kafka. Please, make sure that your Kafka Containers are running correctly (see  `Kafka Containers Troubleshooting`)

- if order is created successfully you will receive 200 response with `tracking_id`

- find the order by `tracking_id` copied from the previous response. 

- If you see the order and it has status `PAID`, then Kafka is configured correctly.

- Congratulations! You successfuly tested `Food Ordering System` Saga transactions and CQRS!

- See this README file for AWS Infrastructure Setup: **https://github.com/greeta-twitter-01/twitter-infra**


### Remote Debugging

![Configuration to debug a containerized Java application from IntelliJ IDEA](documentation/06-14.png)

- if you want to debug your AWS kubernetes services remotely, use port forwarding:

```
kubectl port-forward 8002:8002
```


#### Kafka Containers Troubleshooting

- Check containers in the following order:
- `Kafka Server (cp-server)`
- `Init Kafka (cp-kafka)`: container should finish creation of kafka topics successfully and then exit
- `Schema Registry (cp-schema-registry)`
- If everything is sussessful, containers `cp-server` and `cp-schema-registry` should be running without errors and container `cp-kafka` should finish its job and exit without errors.
- if any of the containers has exited with errors, run `docker-compose up -d` again
- if any of the containers stopped responding (check the logs), remove container (docker stop, docker rm ) and run `docker-compose up -d` again
- For example, if `kafka-to-elastic-service` stopped with errors, just run `docker-compose up -d` again
