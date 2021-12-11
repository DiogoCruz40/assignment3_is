package bos;

import dtos.PurchaseTripDTO;
import dtos.SearchTripsDTO;
import dtos.TripDTO;
import dtos.UserDTO;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface ITripEJB {
    public TripDTO insert(TripDTO dto);
    public List<TripDTO> search(SearchTripsDTO dto);
    public List<TripDTO> getUserTrips(long userId);
    public void purchase(PurchaseTripDTO dto);
    public List<UserDTO> getTripUsers(long id);
    public List<UserDTO> getUserTripsTop5();
    public List<UserDTO> delete(long tripId);
    public void removeUserTrip(long userTripId);
}
