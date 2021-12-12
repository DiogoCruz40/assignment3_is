package dtos;

import java.io.Serializable;

public class CreditperClientDTO implements Serializable {
    private static final long serialVersionUID = 4479178514780184190L;

    private long id_client;
    private double valueofcredit;

    public CreditperClientDTO(long id_client, double valueofcredit) {
        this.id_client = id_client;
        this.valueofcredit = valueofcredit;
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
}

