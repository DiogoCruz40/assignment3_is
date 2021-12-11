package kafka;

import dtos.CurrencyDTO;
import dtos.UserDTO;

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

public class Client {

    private static CopyOnWriteArrayList<UserDTO> users;
    private static CopyOnWriteArrayList<CurrencyDTO> currencies;

    public static void main(String[] args) throws Exception {
        users = new CopyOnWriteArrayList<>();
        currencies = new CopyOnWriteArrayList<>();

        Consumer consumer = new Consumer(users, currencies);
        consumer.start();

        // producer


    }

    private static class Consumer extends Thread {

        private CopyOnWriteArrayList<UserDTO> users;
        private CopyOnWriteArrayList<CurrencyDTO> currencies;

        public Consumer (CopyOnWriteArrayList<UserDTO> users, CopyOnWriteArrayList<CurrencyDTO> currencies) {
            this.users = users;
            this.currencies = currencies;
        }

        @Override
        public void run() {
            //consumer
        }
    }
}
