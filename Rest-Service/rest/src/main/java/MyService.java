
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bos.ICurrencyEJB;
import dtos.CurrencyDTO;
import dtos.UserDTO;
import bos.IUserEJB;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Stateless
@Dependent
@Path("/myservice")
@Produces(MediaType.APPLICATION_JSON)
public class MyService {

    @EJB
    private IUserEJB userEJB;

    @EJB
    private ICurrencyEJB currencyEJB;


    @GET
    @Path("/user")
    public String GetAllUsers() {
        System.out.println("M1 executing....");
        return "M1 adasdexecuted...";
    }

    @POST
    @Path("/user")
    public UserDTO CreateUser(UserDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return userEJB.insert(dto);
    }

    @POST
    @Path("/currency")
    public CurrencyDTO CreateCurrency(CurrencyDTO dto){
        return currencyEJB.insert(dto);
    }
}