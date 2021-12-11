package dtos;

import java.io.Serializable;
import java.util.Date;

public class TripDTO implements Serializable {
    private static final long serialVersionUID = -8820291283519961805L;

    private long id;
    private Date departureDate;
    private String departurePoint;
    private String destinationPoint;
    private long capacity;
    private double price;
    private long place;
    private long userTripId;

    public TripDTO() {};
    public TripDTO(long id, Date departureDate, String departurePoint, String destinationPoint, long capacity, double price) {
        this.id = id;
        this.departureDate = departureDate;
        this.departurePoint = departurePoint;
        this.destinationPoint = destinationPoint;
        this.capacity = capacity;
        this.price = price;
    }
    public TripDTO(Date departureDate, String departurePoint, String destinationPoint, long capacity, double price) {
        this.departureDate = departureDate;
        this.departurePoint = departurePoint;
        this.destinationPoint = destinationPoint;
        this.capacity = capacity;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getDeparturePoint() {
        return departurePoint;
    }

    public void setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
    }

    public String getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(String destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getPlace() {
        return place;
    }

    public void setPlace(long place) {
        this.place = place;
    }

    public long getUserTripId() {
        return userTripId;
    }

    public void setUserTripId(long userTripId) {
        this.userTripId = userTripId;
    }
}
