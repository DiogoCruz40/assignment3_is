package dtos;

import java.io.Serializable;
import java.util.Date;

public class SearchTripsDTO implements Serializable {
    private static final long serialVersionUID = -8820291283519961805L;

    private Date fromDate;
    private Date toDate;

    public SearchTripsDTO() {};

    public SearchTripsDTO(Date fromDate, Date toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }
}
