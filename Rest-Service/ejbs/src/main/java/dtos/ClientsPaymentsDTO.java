package dtos;

import java.util.ArrayList;

public class ClientsPaymentsDTO {
    private ArrayList<ClientPaymentDTO> clientsPaymentsDTO;


    public ClientsPaymentsDTO(ArrayList<ClientPaymentDTO> clientsPaymentsDTO) {
        this.clientsPaymentsDTO = clientsPaymentsDTO;
    }

    public ArrayList<ClientPaymentDTO> getClientsPaymentsDTO() {
        return clientsPaymentsDTO;
    }

    public void setClientsPaymentsDTO(ArrayList<ClientPaymentDTO> clientsPaymentsDTO) {
        this.clientsPaymentsDTO = clientsPaymentsDTO;
    }
}
