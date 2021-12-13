#Assignment3_is
This is a description of how to use kafka streams and deploy a Rest Service.

#Rest Service

###Run:
$ `COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose build` <br>
$ `docker-compose up -d `

###Go to command line container, create a terminal and execute:
$ `cd workspace/assignment3/Rest-Service && mvn clean install wildfly:deploy`

#Kafka Streams

###First initialize zookeeper server (new terminal):
$ `/opt/kafka_2.13-2.8.1/bin/zookeeper-server-start.sh /opt/kafka_2.13-2.8.1/config/zookeeper.properties`

###Them initialize kafka server (new terminal):
$ `/opt/kafka_2.13-2.8.1/bin/kafka-server-start.sh /opt/kafka_2.13-2.8.1/config/server.properties`

##Start the source and sink connection

###Copy the source and sink files to config (new terminal):
$ `cp -a /workspace/assignment3/KafkaStreams/src/main/java/connection/. /opt/kafka_2.13-2.8.1/config/
`
###Copy the jdbc libs to kafka libs:
$ `cp -a /workspace/assignment3/KafkaStreams/src/main/java/libs/. /opt/kafka_2.13-2.8.1/libs/
`

###Create the connection:
$ `/opt/kafka_2.13-2.8.1/bin/connect-standalone.sh /opt/kafka_2.13-2.8.1/config/connect-standalone.properties /opt/kafka_2.13-2.8.1/config/connect-jdbc-source-db.properties /opt/kafka_2.13-2.8.1/config/connect-jdbc-sink-db.properties`

###Run method:

1. First consumer
2. then, Kafka streams
3. finnally, the Producer

#To execute the application:

###Package KafkaClients, KafkaStreams:
$ `cd /workspace/assignment3/KafkaClients && mvn clean package && cd /workspace/assignment3/KafkaStreams && mvn clean package`

###Execute the app:
$ `java -jar /workspace/assignment3/KafkaClients/target/KafkaClients-jar-with-dependencies.jar /workspace/assignment3/KafkaStreams/target/KafkaStreams-1.0-SNAPSHOT-jar-with-dependencies.jar`

#See results using Postman:

##Inserir moeda:
$ `http://localhost:8080/rest/services/myservice/currency`
####BODY:
`{
"name": "dollar8",
"exchangeRate": 1.5
}`
##Inserir user:
$ `http://localhost:8080/rest/services/myservice/user`
####BODY:
`{
"emailuser": "client2@test.com",
"nomeuser": "cliient",
"password": "password123",
"isManager": false
}`
##Get creditos por cliente:
$ `http://localhost:8080/rest/services/myservice/creditperclient`

##Get pagamentos por cliente:
$ `http://localhost:8080/rest/services/myservice/paymentperclient`

##Get o balanço de um cliente pelo email:
$ `http://localhost:8080/rest/services/myservice/balanceofaclient/${emailuser}` \
\
**Nota:** Substituir a variável por um email de cliente

##Get a soma de creditos total:
$ `http://localhost:8080/rest/services/myservice/sumofcredits`

##Get a soma de pagamentos total:
$ `http://localhost:8080/rest/services/myservice/sumofpayments`

##Get balanço total:
$ `http://localhost:8080/rest/services/myservice/totalbalance`

#Resources:
https://eai-course.blogspot.com/2019/11/how-to-configure-kafka-connectors.html <br>
https://eai-course.blogspot.com/2018/11/playing-with-kafka-streams.html

#Usefull commands:
### List available topics:  
$ `/opt/kafka_2.13-2.8.1/bin/kafka-topics.sh --bootstrap-server=localhost:9092 --list`

### Create a consumer:
$ `/opt/kafka_2.13-2.8.1/bin/kafka-console-consumer.sh --topic DBInfo-currencyentity --from-beginning --bootstrap-server localhost:9092`

### Create a producer:
$ `/opt/kafka_2.13-2.8.1/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic kafka_topic`