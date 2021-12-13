package dtos;

import java.util.ArrayList;

public class ClientsCreditsDTO {
    private ArrayList<ClientCreditDTO> clientsCreditsDTO;

    public ClientsCreditsDTO(ArrayList<ClientCreditDTO> clientsCreditsDTO) {
        this.clientsCreditsDTO = clientsCreditsDTO;
    }

    public ArrayList<ClientCreditDTO> getClientsCreditsDTO() {
        return clientsCreditsDTO;
    }

    public void setClientsCreditsDTO(ArrayList<ClientCreditDTO> clientsCreditsDTO) {
        this.clientsCreditsDTO = clientsCreditsDTO;
    }
}
