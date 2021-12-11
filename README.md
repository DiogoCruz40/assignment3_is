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

###Copy the the source and sink files to config (new terminal):
$ `cp -a /workspace/assignment3/KafkaStreams/src/main/java/connection/. /opt/kafka_2.13-2.8.1/config/
`

###Create the connection:
$ `/opt/kafka_2.13-2.8.1/bin/connect-standalone.sh /opt/kafka_2.13-2.8.1/config/connect-standalone.properties /opt/kafka_2.13-2.8.1/config/connect-jdbc-source-filipe.properties /opt/kafka_2.13-2.8.1/config/connect-jdbc-sink-filipe.properties`

###Run method:

1. First consumer
2. then, Kafka streams
3. finnally, the Producer

#Resources:
https://eai-course.blogspot.com/2019/11/how-to-configure-kafka-connectors.html <br>
https://eai-course.blogspot.com/2018/11/playing-with-kafka-streams.html