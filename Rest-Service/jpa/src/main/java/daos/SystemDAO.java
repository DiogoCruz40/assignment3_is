package daos;

import entities.Trip;
import entities.User;
import entities.UserTrip;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SystemDAO {

    private EntityManager em;

    public SystemDAO(EntityManager em) {
        this.em = em;
    }

    public double TotalRevenuesoftheday()
    {
        // today
        Calendar date = new GregorianCalendar();
        Date datetoday = null;
        Date datetomorrow = null;
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        datetoday = date.getTime();
        // next day
        date.add(Calendar.DAY_OF_MONTH, 1);
        datetomorrow = date.getTime();

        List list = em.createNativeQuery("select sum (tripentity.price) from tripentity join usertripentity on tripentity.tripid=usertripentity.tripid " +
                        "where (departuredate >= :fromDate) AND (departuredate < :toDate )")
                .setParameter("fromDate", datetoday, TemporalType.DATE)
                .setParameter("toDate", datetomorrow, TemporalType.DATE)
                .getResultList();

       if (list.get(0) != null)
       {
           return (double) list.get(0);
       }
       return 0.0;

    }

    public List getManagers() {
        return em.createQuery("SELECT u FROM User u WHERE u.isManager = true").getResultList();
    }
}
