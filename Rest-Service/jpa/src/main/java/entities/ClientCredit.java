package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "clientcreditentity")
public class ClientCredit implements Serializable {
    private static final long serialVersionUID = 5924863701751733872L;

    @Id
    @Column(name = "id",nullable = false)
    private long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    @Column(name="credit", nullable=false, unique=false)
    private double credit;

    public ClientCredit() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
}