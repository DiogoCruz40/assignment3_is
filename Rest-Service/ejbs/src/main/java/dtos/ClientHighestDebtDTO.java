package dtos;

import java.io.Serializable;

public class ClientHighestDebtDTO implements Serializable {
    private static final long serialVersionUID = -7014044047043729436L;

    private UserDTO userDTO;
    private double balance;

    public ClientHighestDebtDTO(UserDTO userDTO, double balance) {
        this.userDTO = userDTO;
        this.balance = balance;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
