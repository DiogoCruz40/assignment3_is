package bos;

import dtos.CurrencyDTO;
import dtos.CurrencysDTO;

import javax.ejb.Local;

@Local
public interface ICurrencyEJB {
    public CurrencyDTO insert(CurrencyDTO dto);
    public CurrencysDTO getallcurrencys();
}
