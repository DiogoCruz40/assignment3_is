package kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CurrencyDTO;
import dtos.UserDTO;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
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

    private static CopyOnWriteArrayList<UserDTO> users;
    private static CopyOnWriteArrayList<CurrencyDTO> currencies;
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws Exception {
        users = new CopyOnWriteArrayList<>();
        currencies = new CopyOnWriteArrayList<>();


        Consumer consumerusers = new Consumer(users, currencies,true);
        consumerusers.start();

        Consumer consumercurrency = new Consumer(users, currencies,false);
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
                "org.apache.kafka.common.serialization.LongSerializer");



      //  producercredits.close();

        // producer 2
        //Assign topicName to string variable
        String topicName2 = "Payments";

        Producer<String, Long> producerpayments = new KafkaProducer<>(props);
        //gson object to string
      //  String jsonString = gson.toJson(new CurrencyDTO());
        //producerpayments.send(new ProducerRecord<String, Long>(topicName, Integer.toString(i), (long) i));

    }

    private static class Consumer extends Thread {

        private CopyOnWriteArrayList<UserDTO> users;
        private CopyOnWriteArrayList<CurrencyDTO> currencies;
        private boolean isuser;

        public Consumer (CopyOnWriteArrayList<UserDTO> users, CopyOnWriteArrayList<CurrencyDTO> currencies,boolean isuser) {
            this.users = users;
            this.currencies = currencies;
            this.isuser = isuser;
        }

        @Override
        public void run() {
            //consumer
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
                    "org.apache.kafka.common.serialization.LongSerializer");

            if(isuser)
            {
                org.apache.kafka.clients.consumer.Consumer<String, UserDTO> usersconsumer = new KafkaConsumer<>(props);
                usersconsumer.subscribe(Collections.singletonList("currencyentity"));
                while (true) {
                    ConsumerRecords<String,UserDTO> records = usersconsumer.poll(Duration.ofMillis(Long.MAX_VALUE));

                    for (ConsumerRecord<String, UserDTO> record : records) {
                        //System.out.println(record.key() + " => " + record.value());
                       UserDTO userDTO = gson.fromJson(String.valueOf(record), UserDTO.class);
                        System.out.println(userDTO);
                    }

                    // producer 1

                    //Assign topicName to string variable
                    String topicName = "Credits";
                    Producer<String, Long> producercredits = new KafkaProducer<>(props);

                    //gson object to string
                    String jsonString = gson.toJson(new CurrencyDTO());

                    // producercredits.send(new ProducerRecord<String, Long>(topicName, Integer.toString(i), String));


                }
                }
            else {
                org.apache.kafka.clients.consumer.Consumer<String, CurrencyDTO> currencyconsumer = new KafkaConsumer<>(props);
                currencyconsumer.subscribe(Collections.singletonList("currencyentity"));
                while (true)
                {
                    ConsumerRecords<String, CurrencyDTO> recordscurrency = currencyconsumer.poll(Duration.ofMillis(Long.MAX_VALUE));
                    for (ConsumerRecord<String, CurrencyDTO> record : recordscurrency) {
                        //gson string to object
                        CurrencyDTO currencyDTO = gson.fromJson(String.valueOf(record), CurrencyDTO.class);
                        System.out.println(currencyDTO);
                    }
                }
            }
    }
    }
}