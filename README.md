### AWS Startup Template For Spring Boot Developers
#### Welcome to AWS Full-Stack Developer Template: Swagger UI + Spring Boot + Terraform + Kubernetes + Keycloak Oauth2 Authorization Server + Github Actions + Local Docker Build and Start Environment + Integration Tests with TestContainers + Spring Cloud Gateway + Spring Cloud Stream + Dispatcher Pattern + AWS SSL Certificate + External DNS + AWS Load Balancer Controller + Spring Cloud Kubernetes + Grafana Observability Stack

#### Local Docker Environment Setup:

```
sh docker-start.sh
```

- this script will build kafka, elastic and other third-party docker images and start environment with your code changes

- Warning! Make sure that all kafka containers are successful are started successfully! (see `Kafka Containers Troubleshooting` below)

```
sh docker-app-start.sh
```

- this script will build spring boot docker images and start environment with your code changes

- Warning! Make sure that all kafka containers are successful are started successfully, before running this script! (see `Kafka Containers Troubleshooting` below)

```
sh docker-app-restart.sh
```

- this script will restart spring boot docker images without rebuilding them (use `sh docker-app-start.sh` if you want to rebuild docker images after code changes)

- For example, if `kafka-to-elastic-service` stops with errors, run `sh docker-app-restart` to restart all spring boot containers (or use `docker-compose -f docker-app-compose.yml up -d` if you don't want to restart all spring boot containers)

- If `twitter-to-kafka` service starts successfully, it will start generating mock twitter messages and add them to Kafka `twitter-topic`

- If `kafka-to-elastic` service starts successfully, it will consume messages from Kafka `twitter-topic` and add them to `Elasticsearch` indexed documents (see the docker logs)

- If `kafka-streams` service starts successfully, it will consume messages from Kafka `twitter-topic` and add them to Kafka `twitter-analytics-topic`

- If `analytics` service starts successfully, it will consume messages from Kafka `twitter-analytics-topic` and insert records to `twitter-analytics` PostgreSQL Table

- open `localhost:9000` in your Browser and switch between `Analytics`, `Elastic Query` and `Kafka Streams` Microservices

- Warning! If Swagger UI fails to load on the first try, please, refresh the page!

- Warning! Sometimes switching between services doesn't refresh Swagger UI completely and you might see wrong REST endpoints: just refresh the page and continue

- Warning! Sometimes REST endpoints return `504 Gateway Timeout`, `405 Method not Allowed` or other errors, just retry the REST API endpoint again

- click "Authorize" and use admin/admin or user/user for credentials (clientId should be `twitter-app`)

- find word count for any word on `Analytics` page (see the list of words in `MockKafkaStreamRunner`)

- find word count for any word on `Kafka Streams` page (see the list of words in `MockKafkaStreamRunner`)

- find all documents or document by id on `Elastic Query` page

- Congratulations! You successfuly tested `Twitter Analytics` Microservices with CQRS!

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
