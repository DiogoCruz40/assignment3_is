package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "currencyentity")

public class Currency implements Serializable {
    private static final long serialVersionUID = -4711624429824407796L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "currency_id_seq")
    @SequenceGenerator(initialValue=1,name = "currency_id_seq", sequenceName = "currency_id_seq")
    @Column(name = "id",nullable = false)
    private long id;

    @Column(name="name", nullable=false, unique=false)
    private String name;

    @Column(name="exchangeRate", nullable=false, unique=false)
    private double ExchangeRate;

    public Currency() { }

    public Currency(String name, double exchangeRate) {
        this.name = name;
        ExchangeRate = exchangeRate;
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
        return ExchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        ExchangeRate = exchangeRate;
    }
}