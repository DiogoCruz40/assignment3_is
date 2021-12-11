package bos;

import daos.CurrencyDAO;
import daos.UserDAO;
import dtos.CurrencyDTO;
import dtos.UserDTO;
import entities.Currency;
import entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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
}