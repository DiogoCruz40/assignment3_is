package bos;

import daos.TripDAO;
import dtos.PurchaseTripDTO;
import dtos.SearchTripsDTO;
import dtos.TripDTO;
import dtos.UserDTO;
import entities.Trip;
import entities.User;
import entities.UserTrip;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.transaction.SystemException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TripEJB implements ITripEJB{

    @PersistenceContext(unitName = "ManageApp")
    EntityManager em;

    @Override
    public TripDTO insert(TripDTO dto){
        TripDAO tripDAO = new TripDAO(em);
        Trip newTrip = tripDAO.insert(
                dto.getDepartureDate(),
                dto.getDeparturePoint(),
                dto.getDestinationPoint(),
                dto.getCapacity(),
                dto.getPrice()
        );

        dto.setId(newTrip.getId());
        return dto;
    }

    @Override
    public List<TripDTO> search(SearchTripsDTO dto) {
        TripDAO tripDAO = new TripDAO(em);

        List<TripDTO> trips = new ArrayList<>();
        List<Trip> result = tripDAO.search(dto.getFromDate(), dto.getToDate());
        for (Trip trip : result) {
            System.out.println(trip.getId());
            trips.add(new TripDTO(
                    trip.getId(),
                    trip.getDepartureDate(),
                    trip.getDeparturePoint(),
                    trip.getDestinationPoint(),
                    trip.getCapacity(),
                    trip.getPrice()
            ));
        }

        return trips;
    }

    @Override
    public List<TripDTO> getUserTrips(long userId) {
        TripDAO tripDAO = new TripDAO(em);

        List<TripDTO> trips = new ArrayList<>();
        List<UserTrip> result = tripDAO.getUserTrips(userId);

        for (UserTrip userTrip : result) {
            System.out.println(userTrip.getTrip().getId());

            Trip trip = userTrip.getTrip();
            TripDTO dto = new TripDTO(
                    trip.getId(),
                    trip.getDepartureDate(),
                    trip.getDeparturePoint(),
                    trip.getDestinationPoint(),
                    trip.getCapacity(),
                    trip.getPrice());

            dto.setPlace(userTrip.getPlace());
            dto.setUserTripId(userTrip.getId());

            trips.add(dto);
        }

        return trips;
    }

    @Override
    public void purchase(PurchaseTripDTO dto) {
        TripDAO tripDAO = new TripDAO(em);
        try {
            tripDAO.purchase(dto.getTripId(), dto.getUserId(), dto.getPrice(), dto.getPlace(), dto.getPurchaseDate());
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<UserDTO> getTripUsers(long id) {
        TripDAO tripDAO = new TripDAO(em);

        List<UserDTO> users = new ArrayList<>();
        List<UserTrip> result = tripDAO.getTripUsers(id);

        for (UserTrip userTrip : result) {
          //  System.out.println(userTrip.getTrip().getId());

            User user = userTrip.getUser();
            UserDTO dto = new UserDTO(user.getId(),user.getEmailuser(),user.getNomeuser(), userTrip.getPlace());

            users.add(dto);
        }

        return users;
    }

    @Override
    public List<UserDTO> getUserTripsTop5() {

        TripDAO tripDAO = new TripDAO(em);

        List<UserDTO> users = new ArrayList<>();
        List<User> result = tripDAO.getUserTripsTop5();

        for (User user : result) {
            UserDTO dto = new UserDTO(user.getId(),user.getEmailuser(),user.getNomeuser());
            users.add(dto);
        }

        return users;
    }

    @Override
    public List<UserDTO> delete(long tripId) {
        TripDAO tripDAO = new TripDAO(em);

        List<UserDTO> users = new ArrayList<>();
        List<User> result = tripDAO.delete(tripId);

        for (User user : result) {
            System.out.println(user.getId());
            users.add(new UserDTO(user.getId(),user.getEmailuser(),user.getNomeuser()));
        }
        return users;
    }

    @Override
    public void removeUserTrip(long userTripId) {
        TripDAO tripDAO = new TripDAO(em);
        tripDAO.removeUserTrip(userTripId);
    }
}