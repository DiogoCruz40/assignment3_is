package dtos;

import java.io.Serializable;

public class CreditsDTO implements Serializable {
    private static final long serialVersionUID = 4479178514780184190L;

    private long id_manager;
    private long id_client;
    private double valueofcredit;
    private double exchangeRate;

    public CreditsDTO(long id_manager, long id_client, double valueofcredit, double exchangeRate) {
        this.id_manager = id_manager;
        this.id_client = id_client;
        this.valueofcredit = valueofcredit;
        this.exchangeRate = exchangeRate;
    }

    public long getId_client() {
        return id_client;
    }

    public void setId_client(long id_client) {
        this.id_client = id_client;
    }

    public double getValueofcredit() {
        return valueofcredit;
    }

    public void setValueofcredit(double valueofcredit) {
        this.valueofcredit = valueofcredit;
    }

    public long getId_manager() {
        return id_manager;
    }

    public void setId_manager(long id_manager) {
        this.id_manager = id_manager;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}

