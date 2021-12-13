package KafkaStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dtos.CreditDTO;
import dtos.CreditPaymentDTO;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.examples.pageview.JsonPOJODeserializer;
import org.apache.kafka.streams.examples.pageview.JsonPOJOSerializer;
import org.apache.kafka.streams.kstream.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CreditsperClient {

    public static void main(String[] args) throws InterruptedException, IOException {
        Gson gson = new Gson();

        // Configurations
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "creditStream");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());


        StreamsBuilder builder = new StreamsBuilder();
        final String creditsOutTopicName = "CreditsPerClient";

        Map<String, Object> serdeProps = new HashMap<>();
        final Serializer<CreditPaymentDTO> creditPaymentDTOSerializer = new JsonPOJOSerializer<>();
        serdeProps.put("JsonPOJOClass", CreditPaymentDTO.class);
        creditPaymentDTOSerializer.configure(serdeProps, false);

        final Deserializer<CreditPaymentDTO> creditPaymentDTODeserializer = new JsonPOJODeserializer<>();
        serdeProps.put("JsonPOJOClass", CreditPaymentDTO.class);
        creditPaymentDTODeserializer.configure(serdeProps, false);

        final Serde<CreditPaymentDTO> creditPaymentDTOSerde = Serdes.serdeFrom(creditPaymentDTOSerializer, creditPaymentDTODeserializer);

        serdeProps = new HashMap<>();
        final Serializer<CreditDTO> creditDTOSerializer = new JsonPOJOSerializer<>();
        serdeProps.put("JsonPOJOClass", CreditDTO.class);
        creditDTOSerializer.configure(serdeProps, false);

        final Deserializer<CreditDTO> creditDTODeserializer = new JsonPOJODeserializer<>();
        serdeProps.put("JsonPOJOClass", CreditDTO.class);
        creditDTODeserializer.configure(serdeProps, false);

        final Serde<CreditDTO> creditDTOSerde = Serdes.serdeFrom(creditDTOSerializer, creditDTODeserializer);
//        KStream<String, CreditPaymentDTO> linesCredito = builder.stream("Credits", Consumed.with(Serdes.String(), creditPaymentDTOSerde));

        KStream<Long, String> linesCredito = builder.stream("Credits");

        KTable<Long, String> outline_credit = linesCredito.map((k, v) -> {
                        CreditPaymentDTO dto = gson.fromJson(String.valueOf(v), CreditPaymentDTO.class);

                        double value = dto.getValue() + (dto.getValue() * dto.getExchangerate());
//                        System.out.println(value);

                        CreditDTO credit = new CreditDTO(dto.getId_client(), value , true);


                        return new KeyValue<>(k,gson.toJson(credit));
                    })
                    .groupByKey()
                    .reduce((newval, oldval) -> {
                        CreditDTO dtoNewVal = gson.fromJson(String.valueOf(newval), CreditDTO.class);
                        CreditDTO dtoOldVal = gson.fromJson(String.valueOf(oldval), CreditDTO.class);

                        dtoNewVal.setCredit(dtoNewVal.getCredit() + dtoOldVal.getCredit());

                        return gson.toJson(dtoNewVal);
                    });

        outline_credit.toStream().map((k, v) -> {
            CreditDTO dto = gson.fromJson(String.valueOf(v), CreditDTO.class);

            System.out.println(dto.toRecord(gson));

            return new KeyValue<>(dto.getId(), dto.toRecord(gson));
        }).to(creditsOutTopicName);

        //Run Stream
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        /**********************************************************************************/

        props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "paymentStream");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        builder = new StreamsBuilder();
        KStream<Long, String> linesPayment = builder.stream("Payments");

        KTable<Long, String> outline_payment = linesPayment.map((k, v) -> {
                    CreditPaymentDTO dto = gson.fromJson(String.valueOf(v), CreditPaymentDTO.class);

                    double value = dto.getValue() + (dto.getValue() * dto.getExchangerate());
//                    System.out.println(value);

                    CreditDTO credit = new CreditDTO(dto.getId_client(), value , false);


                    return new KeyValue<>(k,gson.toJson(credit));
                })
                .groupByKey()
                .reduce((newval, oldval) -> {
                    CreditDTO dtoNewVal = gson.fromJson(String.valueOf(newval), CreditDTO.class);
                    CreditDTO dtoOldVal = gson.fromJson(String.valueOf(oldval), CreditDTO.class);

                    dtoNewVal.setPayment(dtoNewVal.getPayment() + dtoOldVal.getPayment());

                    return gson.toJson(dtoNewVal);
                });

        outline_payment.toStream().map((k, v) -> {
            CreditDTO dto = gson.fromJson(String.valueOf(v), CreditDTO.class);

            System.out.println(dto.toRecord(gson));

            return new KeyValue<>(dto.getId(), dto.toRecord(gson));
        }).to(creditsOutTopicName);

        //Run Stream
        KafkaStreams streams2 = new KafkaStreams(builder.build(), props);
        streams2.start();

        System.out.println("Press enter when ready...");
        System.in.read();
        //Thread.sleep(3_000_000);
        System.out.println("enter pressed");

        streams.close();
        streams2.close();
    }
    public static double getValue(String jsonString)
    {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString,JsonObject.class);
        CreditPaymentDTO creditsDTO = gson.fromJson(jsonObject.get("payload"),CreditPaymentDTO.class);
        return creditsDTO.getValue() + (creditsDTO.getValue()*creditsDTO.getExchangerate());
    }
}

