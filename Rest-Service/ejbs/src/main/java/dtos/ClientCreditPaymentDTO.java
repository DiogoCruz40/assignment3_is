package dtos;

import java.io.Serializable;

public class ClientCreditPaymentDTO implements Serializable {
    private static final long serialVersionUID = -6301052213851659841L;

    private UserDTO userDTO;
    private double balance;

    public ClientCreditPaymentDTO(UserDTO userDTO, double balance) {
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