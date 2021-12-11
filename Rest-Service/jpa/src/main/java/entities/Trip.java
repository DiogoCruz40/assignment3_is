package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tripentity")
public class Trip implements Serializable{
    private static final long serialVersionUID = -3095683834614888960L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "tripid", nullable = false, unique = true)
    private long id;

    @Column(name="departuredate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureDate;

    @Column(name="departurepoint", nullable = false)
    private String departurePoint;

    @Column(name="destinationPoint", nullable = false)
    private String destinationPoint;

    @Column(name="capacity", nullable = false)
    private long capacity;

    @Column(name="price", nullable = false)
    private double price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trip")
    private List<UserTrip> userTrips;

    public Trip() { super(); };
    public Trip(Date departureDate, String departurePoint, String destinationPoint, long capacity, double price) {
        super();

        this.departureDate = departureDate;
        this.departurePoint = departurePoint;
        this.destinationPoint = destinationPoint;
        this.capacity = capacity;
        this.price = price;
    }

    public long getId() {
        return id;
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

    public List<UserTrip> getUserTrips() {
        return userTrips;
    }

    public void setUserTrips(List<UserTrip> userTrips) {
        this.userTrips = userTrips;
    }
}