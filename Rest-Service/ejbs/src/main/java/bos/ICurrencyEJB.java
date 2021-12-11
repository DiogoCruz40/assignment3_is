package bos;

import dtos.CurrencyDTO;
import javax.ejb.Remote;

@Remote
public interface ICurrencyEJB {
    public CurrencyDTO insert(CurrencyDTO dto);
}
