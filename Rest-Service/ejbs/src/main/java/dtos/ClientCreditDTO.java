package dtos;

import java.io.Serializable;

public class ClientCreditDTO  implements Serializable {
    private static final long serialVersionUID = -6306163536111333094L;

    private UserDTO userDTO;
    private double credit;

    public ClientCreditDTO(UserDTO userDTO, double credit) {
        this.userDTO = userDTO;
        this.credit = credit;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
}
