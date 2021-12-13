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
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streamtoresultstopics");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());


        StreamsBuilder builder = new StreamsBuilder();
        final String outtopicname = "CreditsPerClient";

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

//        linesCredito.map((k, v) -> {
//            CreditPaymentDTO dto = gson.fromJson(String.valueOf(v), CreditPaymentDTO.class);
//            CreditDTO credit = new CreditDTO(dto.getValue() + (dto.getValue() * dto.getExchangerate()));
//            return new KeyValue<>(dto.getId_client(),gson.toJson(credit));
//        })
//        .foreach((k, v) -> {
//            System.out.println(k +" -> " + v);
//        });

        KTable<Long, String> outline_credit = linesCredito.map((k, v) -> {
                        CreditPaymentDTO dto = gson.fromJson(String.valueOf(v), CreditPaymentDTO.class);
                        CreditDTO credit = new CreditDTO(dto.getId_client(), dto.getValue() + (dto.getValue() * dto.getExchangerate()));

                        return new KeyValue<>(k,gson.toJson(credit));
                    })
                    .groupByKey()
                    .reduce((newval, oldval) -> {
                        CreditDTO dtoNewVal = gson.fromJson(String.valueOf(newval), CreditDTO.class);
                        CreditDTO dtoOldVal = gson.fromJson(String.valueOf(oldval), CreditDTO.class);

                        dtoNewVal.setCredit(dtoNewVal.getCredit() + dtoOldVal.getCredit());

                        return gson.toJson(dtoNewVal);
                    });

//        outline_credit.toStream().to(outtopicname,  Produced.with(Serdes.Long(), creditDTOSerde));
        outline_credit.toStream().map((k, v) -> {
            CreditDTO dto = gson.fromJson(String.valueOf(v), CreditDTO.class);

            System.out.println(dto.toRecord(gson));

            return new KeyValue<>(k, dto.toRecord(gson));
        }).to(outtopicname);

        //Run Stream
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        System.out.println("Press enter when ready...");
        System.in.read();
        //Thread.sleep(3_000_000);
        System.out.println("enter pressed");

        streams.close();

        //depois passar para keytable
        // mapear linescredito (da kstream de antes) para uma tabela
//        KTable<String, Double> tablecredito = linesCredito.mapValues(new ValueMapper<String, Double>() {
//            @Override
//            public Double apply(String s) {
//                //funcao em q a string s é o valor da mensagem do topico
//                System.out.println("string pagamento : "+ s);
//                GsonBuilder builder = new GsonBuilder();
//                builder.setPrettyPrinting();
//                Gson gson = builder.create();
//                //passar s para objeto com gson -- transformar em pagamento/credito
//                //dados ficam no objeto~logo posso fazer creditos.getpreco(); para aceder aos dados
//                //fazer conversao da moeda?
//
//                //retornar o valor que queremos usar na funcao kafka para todas as keys ( somar no caso do reduce)
//
//                return preco;//vamos somar os precos das cenas
//            }
//        }).groupByKey(Grouped.with(Serdes.String(),Serdes.Double())).reduce((old, newval)-> old + newval);//reduce a somar valores (tmb da p subtrair, multiplicar etc)
//        //pegar na ktable e por as linhas resultantes do reduce para o topico resultados
//        tablecredito.toStream().to(outtopicname, Produced.with(Serdes.String(), Serdes.Double()));
//        //Produced.with(Serdes.tipo da key, Serdes.tipo do valor)
//
//        //join 2 tables
//        KTable<Key, Pair<Value1, Value2>> creditsperclient =
//                tablecurrency.join(tablecredito, (value1, value2) -> new Pair<Value1,Value2>(value1,value2));

        /* too merge streams
                KStream<String, Long> rockSongs = builder.stream(topicName);
                KStream<String,  Long> classicalSongs = builder.stream(topicName);
                KStream<String,  Long> allSongs = rockSongs.merge(classicalSongs);
        */

        //
    }
    public static double getValue(String jsonString)
    {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString,JsonObject.class);
        CreditPaymentDTO creditsDTO = gson.fromJson(jsonObject.get("payload"),CreditPaymentDTO.class);
        return creditsDTO.getValue() + (creditsDTO.getValue()*creditsDTO.getExchangerate());
    }
}

