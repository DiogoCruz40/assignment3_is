//package KafkaStreams;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import dtos.CreditsDTO;
//import org.antlr.v4.runtime.misc.Pair;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.KafkaStreams;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.StreamsConfig;
//import org.apache.kafka.streams.kstream.*;
//
//import java.io.IOException;
//import java.util.Properties;
//
//public class CreditPerClient {
//
//    public static void main(String[] args) throws InterruptedException, IOException {
//
//        // Configurations
//        java.util.Properties props = new Properties();
//        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streamtoresultstopics");
//        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
//        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
//        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
//        StreamsBuilder builder = new StreamsBuilder();
//        final String outtopicname = "CreditperClient";
//
//        //Get the credits per client.
//
//        //receber linhas do topico creditos (topicName):
//        KStream<String,String>  linesCredito   = builder.stream("CreditsTopic");
//        //as linhas ficam em linescredito
//
//        //receber linhas do topico currency (topicName):
//        KStream<String,String>  linescurrency  = builder.stream("currencyentity");
//        //as linhas ficam em linescurrency
//
//        //depois passar para keytable
//        // mapear linescredito (da kstream de antes) para uma tabela
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
//
//        /* too merge streams
//                KStream<String, Long> rockSongs = builder.stream(topicName);
//                KStream<String,  Long> classicalSongs = builder.stream(topicName);
//                KStream<String,  Long> allSongs = rockSongs.merge(classicalSongs);
//        */
//
//        //Run Stream
//        KafkaStreams streams = new KafkaStreams(builder.build(), props);
//        streams.start();
//
//        //
//    }
//
//}
