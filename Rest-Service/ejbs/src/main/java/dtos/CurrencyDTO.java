package dtos;

import java.io.Serializable;

public class CurrencyDTO implements Serializable {
    private static final long serialVersionUID = 1684772375308677980L;

    private long id;
    private String name;
    private double exchangeRate;

    public CurrencyDTO() {};

    public CurrencyDTO(long id, String name, double exchangeRate) {
        this.id = id;
        this.name = name;
        this.exchangeRate = exchangeRate;
    }

    public CurrencyDTO(String name, double exchangeRate) {
        this.name = name;
        this.exchangeRate = exchangeRate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
