package dtos;

import java.util.ArrayList;

public class UsersDTO {

    private ArrayList<UserDTO> usersDTO;

    public UsersDTO(ArrayList<UserDTO> usersDTO) {
        this.usersDTO = usersDTO;
    }

    public ArrayList<UserDTO> getUsersDTO() {
        return usersDTO;
    }

    public void setUsersDTO(ArrayList<UserDTO> usersDTO) {
        this.usersDTO = usersDTO;
    }
}
