package dtos;

import com.google.gson.Gson;

import java.io.Serializable;

public class CreditDTO implements Serializable {
    private static final long serialVersionUID = 1431637326261966166L;

    public long id;
    public double credit;

    public CreditDTO(long id, double credit) {
        this.id = id;
        this.credit = credit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String toRecord (Gson gson) {
        String schema = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int64\",\"optional\":false,\"field\":\"id\"},{\"type\":\"double\",\"optional\":false,\"field\":\"credit\"}],\"optional\":false,\"name\":\"clientcreditentity\"},\"payload\":%s}";
        return String.format(schema, gson.toJson(this).replaceAll("[\\x00-\\x09\\x11\\x12\\x14-\\x1F\\x7F]", ""));
    }
}
