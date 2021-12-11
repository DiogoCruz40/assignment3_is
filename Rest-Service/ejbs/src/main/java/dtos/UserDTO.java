package dtos;


import java.io.Serializable;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = -1151657620074187511L;

    private long id;
    private String emailuser;
    private String nomeuser;
    private String password;
    private boolean isManager;
    private long place;

    public UserDTO() {};
    public UserDTO(String emailuser, String nomeuser, String password, boolean isManager) {
        this.emailuser = emailuser;
        this.nomeuser = nomeuser;
        this.password = password;
        this.isManager = isManager;
    }

    public UserDTO(long id, String emailuser, String nomeuser, long place) {
        this.id = id;
        this.emailuser = emailuser;
        this.nomeuser = nomeuser;
        this.place = place;
    }

    public UserDTO(long id, String emailuser, String nomeuser) {
        this.id = id;
        this.emailuser = emailuser;
        this.nomeuser = nomeuser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmailuser() {
        return emailuser;
    }

    public void setEmailuser(String emailuser) {
        this.emailuser = emailuser;
    }

    public String getNomeuser() {
        return nomeuser;
    }

    public void setNomeuser(String nomeuser) {
        this.nomeuser = nomeuser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public long getPlace() {
        return place;
    }

    public void setPlace(long place) {
        this.place = place;
    }
}
