package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "userentity")

public class User implements Serializable {
    private static final long serialVersionUID = -4485892666667323664L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_id_seq")
    @SequenceGenerator(initialValue=4,name = "user_id_seq", sequenceName = "user_id_seq")
    @Column(name = "userid",nullable = false)
    private long id;

    @Column(name="emailuser", nullable=false, unique=true)
    private String emailuser;

    @Column(name="nomeuser", nullable=false, unique=false)
    private String nomeuser;

    @Column(name="password", nullable=false, unique=false)
    private String password;

    @Column(name="isManager", nullable=false, unique=false)
    private boolean isManager;

    @Column(name="wallet", nullable=false, unique=false)
    private double wallet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserTrip> userTrips;

    public User() { }

    public User(String emailuser, String nomeuser, String password, boolean isManager,double wallet) {
        super();
        this.nomeuser = nomeuser;
        this.password = password;
        this.emailuser= emailuser;
        this.isManager = isManager;
        this.wallet = wallet;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(Double wallet) {
        this.wallet = wallet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeuser() {
        return nomeuser;
    }

    public void setNomeuser(String nomeuser) {
        this.nomeuser = nomeuser;
    }

    public String getEmailuser() {
        return emailuser;
    }

    public void setEmailuser(String emailuser) {
        this.emailuser = emailuser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isManager() { return isManager; }

    public void setManager(boolean isManager) { this.isManager = isManager; }

    public void setUserTrips(List<UserTrip> userTrips) {
        this.userTrips = userTrips;
    }
    public List<UserTrip> getUserTrips() {
        return userTrips;
    }
}