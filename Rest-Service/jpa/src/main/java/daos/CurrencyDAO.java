package daos;

import entities.Currency;
import entities.User;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO {

    private EntityManager em;

    public CurrencyDAO(EntityManager em) {
        this.em = em;
    }

    public Currency insert(String name, double exchangeRate){
        Currency currency = new Currency(name,exchangeRate);

        em.persist(currency);
        em.flush();

        return currency;
    }

    public User finduserbyid(long id)
    {
        return em.find(User.class,id);
    }

    public ArrayList<Currency> getallcurrencys()
    {
        List<Currency> result = em.createQuery("SELECT u FROM Currency u").getResultList();
        ArrayList<Currency> currencies = new ArrayList<>();
        for (Currency currency : result)
            currencies.add(currency);
        return currencies;
    }

}
