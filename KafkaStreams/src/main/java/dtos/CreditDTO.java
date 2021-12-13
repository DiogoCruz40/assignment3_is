package dtos;

import com.google.gson.Gson;

import java.io.Serializable;

public class CreditDTO implements Serializable {
    private static final long serialVersionUID = 1431637326261966166L;

    public long id;
    public double credit;
    public double payment;

    public CreditDTO(long id, double value, boolean isCredit) {
        this.id = id;
        if (isCredit) {
            this.credit = value;
            //this.payment = null;
        } else {
            //this.credit = null;
            this.payment = value;
        }
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

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public String toRecord (Gson gson) {
        String schema = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int64\",\"optional\":false,\"field\":\"id\"},{\"type\":\"double\",\"optional\":true,\"field\":\"credit\"}],\"optional\":false,\"name\":\"clientcreditentity\"},\"payload\":%s}";
        if (this.credit == 0) {
            schema = "{\"schema\":{\"type\":\"struct\",\"fields\":[{\"type\":\"int64\",\"optional\":false,\"field\":\"id\"},{\"type\":\"double\",\"optional\":true,\"field\":\"payment\"}],\"optional\":false,\"name\":\"clientcreditentity\"},\"payload\":%s}";
        }
        return String.format(schema, gson.toJson(this).replaceAll("[\\x00-\\x09\\x11\\x12\\x14-\\x1F\\x7F]", ""));
    }
}
