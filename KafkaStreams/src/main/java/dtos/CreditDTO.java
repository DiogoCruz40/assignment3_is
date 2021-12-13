package dtos;

import java.io.Serializable;

public class CreditDTO implements Serializable {
    private static final long serialVersionUID = 1431637326261966166L;

    public double credit;

    public CreditDTO(double credit) {
        this.credit = credit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
}
