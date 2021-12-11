package daos;

import entities.Currency;
import entities.User;

import javax.persistence.EntityManager;

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

}
