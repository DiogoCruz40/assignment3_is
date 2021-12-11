package dtos;

import java.io.Serializable;
import java.util.Date;

public class PurchaseTripDTO implements Serializable {
    private static final long serialVersionUID = -7218052993780122316L;

    private long tripId;
    private long place;
    private double price;
    private long userId;
    private Date purchaseDate;

    public PurchaseTripDTO() {};

    public PurchaseTripDTO(long tripId, long place, double price, long userId, Date purchaseDate) {
        this.tripId = tripId;
        this.place = place;
        this.price = price;
        this.userId = userId;
        this.purchaseDate = purchaseDate;
    }

    public long getTripId() {
        return tripId;
    }

    public long getPlace() {
        return place;
    }

    public double getPrice() {
        return price;
    }

    public long getUserId() {
        return userId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }
}
