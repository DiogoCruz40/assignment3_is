package dtos;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1913447723886831336L;

    private long id;
    private String emailuser;
    private String nomeuser;
    private String password;
    private boolean isManager;

    public UserDTO() {};
    public UserDTO(String emailuser, String nomeuser, String password, boolean isManager) {
        this.emailuser = emailuser;
        this.nomeuser = nomeuser;
        this.password = password;
        this.isManager = isManager;
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

    public boolean getIsManager() {
        return isManager;
    }

    public void setIsManager(boolean manager) {
        isManager = manager;
    }
}
