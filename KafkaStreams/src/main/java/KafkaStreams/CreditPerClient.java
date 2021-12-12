package KafkaStreams;

import com.google.gson.Gson;
import dtos.CreditperClientDTO;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueMapper;

import java.io.IOException;
import java.util.Properties;

public class CreditPerClient {

    public static void main(String[] args) throws InterruptedException, IOException {

        // Configurations
        java.util.Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streamtoresultstopics");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        StreamsBuilder builder = new StreamsBuilder();
        final String topicName = "";
        final String outtopicname = "CreditperClient";

        KStream<String, String> linescredito = builder.stream(topicName); //Collection<String> topics
        //Get the credits per client

        KStream<String, String> resultStream = linescredito.mapValues(value ->
                new Gson().fromJson(value, CreditperClientDTO.class).get("after").getAsJsonObject().toString());
        resultStream.to(outtopicname, Produced.with(Serdes.String(),Serdes.String()));

        /* too merge streams
                KStream<String, Long> rockSongs = builder.stream(topicName);
                KStream<String,  Long> classicalSongs = builder.stream(topicName);
                KStream<String,  Long> allSongs = rockSongs.merge(classicalSongs);
        */

        //Run Stream
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        //
    }

}
