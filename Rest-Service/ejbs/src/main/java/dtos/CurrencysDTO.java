package dtos;

import java.util.ArrayList;

public class CurrencysDTO {
    private ArrayList<CurrencyDTO> currencysDTO;


    public CurrencysDTO(ArrayList<CurrencyDTO> currencysDTO) {
        this.currencysDTO = currencysDTO;
    }

    public ArrayList<CurrencyDTO> getCurrencysDTO() {
        return currencysDTO;
    }

    public void setCurrencysDTO(ArrayList<CurrencyDTO> currencysDTO) {
        this.currencysDTO = currencysDTO;
    }
}
