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

        // Configurations
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streamtoresultstopics");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

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

        //Get the credits per client.

        //receber linhas do topico creditos (topicName):
        KStream<String, CreditPaymentDTO> linesCredito = builder.stream("Credits", Consumed.with(Serdes.String(), creditPaymentDTOSerde));
        //as linhas ficam em linescredito

        linesCredito.to(outtopicname,  Produced.with(Serdes.String(), creditPaymentDTOSerde));

//        KTable<Long, CreditDTO> outline_credit = linesCredito.map((k, v) -> {
//                        System.out.println(v.getId_client() + "asdasda");
//                        return new KeyValue<>(v.getId_client(),new CreditDTO(v.getValue() + (v.getValue() * v.getExchangerate())));
//                    })
//                    .groupByKey()
//                    .reduce((newval, oldval) -> {
//                        newval.setCredit(newval.getCredit() + oldval.getCredit());
//                        System.out.println(newval.getCredit());
//                        return newval;
//                    });
//
//        outline_credit.toStream().to(outtopicname,  Produced.with(Serdes.Long(), creditDTOSerde));


        //Run Stream
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        System.out.println("Press enter when ready...");
        System.in.read();
        System.out.println("enter pressed");

        Thread.sleep(5000L);

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

