package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "usertripentity")
public class UserTrip implements Serializable{
    private static final long serialVersionUID = 4320065905800683801L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "usertripid", nullable = false, unique = true)
    private long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "tripid")
    private Trip trip;

    @Column(name = "place")
    private long place;

    @Column(name="purchaseDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDate;

    public UserTrip() { super(); }
    public UserTrip(User user, Trip trip, long place, Date purchaseDate) {
        super();

        this.user = user;
        this.trip = trip;
        this.place = place;
        this.purchaseDate = purchaseDate;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public long getPlace() {
        return place;
    }

    public void setPlace(long place) {
        this.place = place;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}