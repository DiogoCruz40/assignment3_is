import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dtos.CreditPaymentDTO;
import dtos.CurrencyDTO;
import dtos.UserDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
Credit {
    ManagerId
    ClientId
    CurrencyId
    Value (negativo)        -20 -10 -30
    Date
}
Payment {
     ManagerId
     ClientId
     CurrencyId
     Value (positivo)       +5 +15 +40
     Date
}
*/

public class Clients {

    private static CopyOnWriteArrayList<UserDTO> clients;
    private static CopyOnWriteArrayList<UserDTO> managers;
    private static CopyOnWriteArrayList<CurrencyDTO> currencies;

    public static void main(String[] args) throws Exception {
        clients = new CopyOnWriteArrayList<>();
        managers = new CopyOnWriteArrayList<>();
        currencies = new CopyOnWriteArrayList<>();

        Gson gson = new Gson();

        Consumer consumerusers = new Consumer(clients, managers, currencies,true);
        consumerusers.start();

        Consumer consumercurrency = new Consumer(clients, managers, currencies,false);
        consumercurrency.start();

        // create instance for properties to access producer and consumer configs
        Properties props = new Properties();

        //Assign localhost id
        props.put("bootstrap.servers", "localhost:9092");

        //Set acknowledgements for producer requests.
        props.put("acks", "all");

        //If the request fails, the producer can automatically retry,
        props.put("retries", 0);

        //Specify buffer size in config
        props.put("batch.size", 16384);

        //Reduce the no of requests less than 0
        props.put("linger.ms", 1);

        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("buffer.memory", 33554432);

        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        while (true) {
            if (!managers.isEmpty() && !clients.isEmpty() && !currencies.isEmpty()) {

                Random rand = new Random();

                UserDTO randomManager = managers.get(rand.nextInt(managers.size()));
                UserDTO randomClient = clients.get(rand.nextInt(clients.size()));
                CurrencyDTO randomCurrency = currencies.get(rand.nextInt(currencies.size()));

                double value = (rand.nextDouble() - rand.nextDouble()) * 100;

                Date date = new Date(System.currentTimeMillis());
                CreditPaymentDTO dto = new CreditPaymentDTO(
                        randomManager.getId(),
                        randomClient.getId(),
                        randomCurrency.getId(),
                        value,
                        randomCurrency.getExchangeRate(),
                        date);

                String topic = value > 0 ? "Payments" : "Credits";
                producer.send(new ProducerRecord<>(topic, null, gson.toJson(dto).toString()));

                System.out.println("Sent to topic: "+topic);
            }

            Thread.sleep(1000);

            System.out.println(managers.size());
            System.out.println(clients.size());
            System.out.println(currencies.size());
        }
    }

    private static class Consumer extends Thread {

        private CopyOnWriteArrayList<UserDTO> clients;
        private CopyOnWriteArrayList<UserDTO> managers;
        private CopyOnWriteArrayList<CurrencyDTO> currencies;
        private boolean isuser;

        public Consumer (CopyOnWriteArrayList<UserDTO> clients,CopyOnWriteArrayList<UserDTO> managers, CopyOnWriteArrayList<CurrencyDTO> currencies,boolean isuser) {
            this.clients = clients;
            this.managers = managers;
            this.currencies = currencies;
            this.isuser = isuser;
        }

        @Override
        public void run() {
            Gson gson = new Gson();

            //consumer
            Properties props = new Properties();

            //Assign localhost id
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

            //Set acknowledgements for producer requests.
            props.put("acks", "all");

            //If the request fails, the producer can automatically retry,
            props.put("retries", 0);

            //Specify buffer size in config
            props.put("batch.size", 16384);

            //Reduce the no of requests less than 0
            props.put("linger.ms", 1);

            //The buffer.memory controls the total amount of memory available to the producer for buffering.
            props.put("buffer.memory", 33554432);

            props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
            props.put(ConsumerConfig.CLIENT_ID_CONFIG, "your_client_id");
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

            props.put("key.deserializer",
                    "org.apache.kafka.common.serialization.StringDeserializer");

            props.put("value.deserializer",
                    "org.apache.kafka.common.serialization.StringDeserializer");

            if(isuser) {

                KafkaConsumer<String, String> usersconsumer = new KafkaConsumer<>(props);
                usersconsumer.subscribe(Collections.singletonList("DBInfo-userentity"));

                while (true) {
                    ConsumerRecords<String,String> records = usersconsumer.poll(Duration.ofMillis(Long.MAX_VALUE));

                    for (ConsumerRecord<String, String> record : records) {
//                        System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                        JsonObject obj = gson.fromJson(String.valueOf(record.value()), JsonObject.class);
                        UserDTO user = gson.fromJson(obj.get("payload"), UserDTO.class);
                        if (user.isIsmanager()) {
                            managers.add(user);
                        } else {
                            clients.add(user);
                        }
                    }
                    usersconsumer.commitAsync();

                }
            } else {
                KafkaConsumer<String, String> currencyconsumer = new KafkaConsumer<>(props);
                currencyconsumer.subscribe(Collections.singletonList("DBInfo-currencyentity"));
                while (true)
                {
                    ConsumerRecords<String, String> records = currencyconsumer.poll(Duration.ofMillis(Long.MAX_VALUE));

                    for (ConsumerRecord<String, String> record : records) {
//                        System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                        JsonObject obj = gson.fromJson(String.valueOf(record.value()), JsonObject.class);
                        CurrencyDTO currency = gson.fromJson(obj.get("payload"), CurrencyDTO.class);
                        currencies.add(currency);
                    }
                    currencyconsumer.commitAsync();
                }
            }
        }
    }
}