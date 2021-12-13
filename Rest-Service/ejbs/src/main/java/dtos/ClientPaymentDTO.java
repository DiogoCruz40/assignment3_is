package dtos;

import java.io.Serializable;

public class ClientPaymentDTO implements Serializable {
    private static final long serialVersionUID = 8452370114883028783L;

    private UserDTO userDTO;
    private double payment;

    public ClientPaymentDTO(UserDTO userDTO, double payment) {
        this.userDTO = userDTO;
        this.payment = payment;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }


    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }
}
