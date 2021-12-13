package dtos;

import java.io.Serializable;

public class CurrencyDTO implements Serializable {
    private static final long serialVersionUID = 1684772375308677980L;

    private long id;
    private String name;
    private float exchangerate;

    public CurrencyDTO() {};

    public CurrencyDTO(long id, String name, float exchangeRate) {
        this.id = id;
        this.name = name;
        this.exchangerate = exchangeRate;
    }

    public CurrencyDTO(String name, float exchangeRate) {
        this.name = name;
        this.exchangerate = exchangeRate;
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

    public float getExchangeRate() {
        return exchangerate;
    }

    public void setExchangeRate(float exchangeRate) {
        this.exchangerate = exchangeRate;
    }
}
