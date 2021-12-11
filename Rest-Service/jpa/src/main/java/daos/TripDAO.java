package daos;

import entities.Trip;
import entities.User;
import entities.UserTrip;
import org.hibernate.sql.ast.tree.from.MutatingTableReferenceGroupWrapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TemporalType;
import javax.transaction.SystemException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TripDAO {

    private EntityManager em;

//    @Resource
//    private UserTransaction trans;

    public TripDAO(EntityManager em) {
        this.em = em;
    }

    public Trip insert(Date departureDate, String departurePoint, String destinationPoint, long capacity, double price) {
        Trip trip = new Trip(departureDate, departurePoint, destinationPoint, capacity, price);

        try {
            em.persist(trip);
            em.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trip;
    }

    public List search(Date fromDate, Date toDate) {
        if (toDate == null) {
            Calendar c = Calendar.getInstance();
            c.setTime(fromDate);

            c.add(Calendar.DATE, 1);
            toDate = c.getTime();
        }
        System.out.println(fromDate + " - " + toDate);
        return em.createQuery("SELECT t FROM Trip t WHERE t.departureDate BETWEEN :fromDate AND :toDate ORDER BY t.departureDate DESC")
                .setParameter("fromDate", fromDate, TemporalType.DATE)
                .setParameter("toDate", toDate, TemporalType.DATE)
                .getResultList();
    }

    public void purchase(long tripId, long userId, double price, long place, Date purchaseDate) throws SystemException {
        try {
            //trans.begin();

            List userResult = em.createQuery("SELECT u FROM User u WHERE u.id = :userId")
                    .setMaxResults(1)
                    .setParameter("userId",userId)
                    .getResultList();

            if (userResult.size() > 0) {
                User user = (User) userResult.get(0);
                user.setWallet(user.getWallet() - price);
                em.merge(user);

                List tripResult = em.createQuery("SELECT t FROM Trip t WHERE t.id = :tripId")
                        .setMaxResults(1)
                        .setParameter("tripId",tripId)
                        .getResultList();

                if (tripResult.size() > 0) {
                    Trip trip = (Trip) tripResult.get(0);

                    UserTrip userTrip = new UserTrip(user, trip, place, purchaseDate);
                    em.persist(userTrip);

                    //trans.commit();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //trans.rollback();
        }
    }

    public List getUserTrips(long userId) {
        return em.createQuery("SELECT ut FROM UserTrip ut JOIN ut.trip t JOIN ut.user u WHERE u.id = :userId ORDER BY t.departureDate DESC")
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<UserTrip> getTripUsers(long tripId) {
        return em.createQuery("SELECT ut FROM UserTrip ut JOIN ut.trip t JOIN ut.user u WHERE t.id = :tripId")
                .setParameter("tripId", tripId)
                .getResultList();
    }

    public List<User> delete(long tripId) {
        List<User> users = new ArrayList<>();
        try {
            //trans.begin();

            Trip trip = em.find(Trip.class, tripId);
            if (trip == null) {
                throw new EntityNotFoundException("Can't find Trip for ID "
                        + tripId);
            }
            List list = em.createQuery("SELECT ut FROM UserTrip ut JOIN ut.trip t JOIN ut.user u WHERE t.id = :tripId")
                    .setParameter("tripId", tripId)
                    .getResultList();

            if (list.size() > 0) {
                for (UserTrip uTrip : (List<UserTrip>) list) {

                    User user = uTrip.getUser();
                    users.add(user);

                    user.setWallet(user.getWallet() + trip.getPrice());
                    em.merge(user);

                    em.remove(uTrip);
                }
            }

            em.remove(trip);
            //trans.commit();

        } catch (Exception e) {
            e.printStackTrace();
            //trans.rollback();
        }
        return users;
    }

    public List getUserTripsTop5() {
        return em.createQuery("SELECT u FROM User u JOIN u.userTrips ut GROUP BY u.id ORDER BY COUNT(ut.id) DESC")
                .setMaxResults(5)
                .getResultList();
    }

    public void removeUserTrip(long userTripId) {
        UserTrip uTrip = em.find(UserTrip.class, userTripId);
        if (uTrip == null) {
            throw new EntityNotFoundException("Can't find UserTrip for ID "
                    + userTripId);
        }

        User user = uTrip.getUser();
        if (user != null) {
            user.setWallet(user.getWallet() + uTrip.getTrip().getPrice());

            em.merge(user);
            em.flush();
        }

        em.remove(uTrip);
    }
}