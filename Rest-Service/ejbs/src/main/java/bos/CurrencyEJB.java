package bos;

import daos.CurrencyDAO;
import dtos.CurrencyDTO;
import dtos.CurrencysDTO;
import entities.Currency;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

@Stateless
public class CurrencyEJB implements ICurrencyEJB{

    @PersistenceContext(unitName = "ManageApp")
    EntityManager em;

    @Override
    public CurrencyDTO insert(CurrencyDTO dto){
        CurrencyDAO currencyDAO = new CurrencyDAO(em);
        Currency newCurrency = currencyDAO.insert(dto.getName(), dto.getExchangeRate());

        dto.setId(newCurrency.getId());
        return dto;
    }

    @Override
    public CurrencysDTO getallcurrencys() {
        CurrencyDAO currencyDAO = new CurrencyDAO(em);

        ArrayList<CurrencyDTO> currencysDTO = new ArrayList<>();
        for (Currency currency : currencyDAO.getallcurrencys())
        {
           CurrencyDTO currencyDTO = new CurrencyDTO(currency.getName(),currency.getExchangeRate());
           currencysDTO.add(currencyDTO);
        }
        return new CurrencysDTO(currencysDTO);
    }
}