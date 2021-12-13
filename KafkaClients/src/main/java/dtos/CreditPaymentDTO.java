package dtos;

import java.io.Serializable;
import java.util.Date;

public class CreditPaymentDTO implements Serializable {
    private static final long serialVersionUID = 4479178514780184190L;

    private long id_manager;
    private long id_client;
    private long id_currency;
    private double value;
    private float exchangerate;
    private Date date;

    public CreditPaymentDTO(long id_manager, long id_client, long id_currency, double value, float exchangeRate, Date date) {
        this.id_manager = id_manager;
        this.id_client = id_client;
        this.id_currency = id_currency;
        this.value = value;
        this.exchangerate = exchangeRate;
        this.date = date;
    }

    public long getId_client() {
        return id_client;
    }

    public void setId_client(long id_client) {
        this.id_client = id_client;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getId_manager() {
        return id_manager;
    }

    public void setId_manager(long id_manager) {
        this.id_manager = id_manager;
    }

    public double getExchangerate() {
        return exchangerate;
    }

    public void setExchangerate(float exchangerate) {
        this.exchangerate = exchangerate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId_currency() {
        return id_currency;
    }

    public void setId_currency(long id_currency) {
        this.id_currency = id_currency;
    }
}

