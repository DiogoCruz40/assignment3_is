import bos.ICurrencyEJB;
import bos.IUserEJB;
import dtos.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    @Path("/users")
    public UsersDTO GetAllUsers() {
        return userEJB.getallusers();
    }

    @GET
    @Path("/managers")
    public UsersDTO GetAllManagers() {
        return userEJB.getallmanagers();
    }

    @GET
    @Path("/currencys")
    public CurrencysDTO GetAllCurrencys() {
        return currencyEJB.getallcurrencys();

    }

    @GET
    @Path("/creditperclient")
    public ClientsCreditsDTO GetCreditPerClient() {
        return userEJB.getcreditperclient();
    }

    @GET
    @Path("/paymentperclient")
    public ClientsPaymentsDTO GetPaymentPerClient() {
        return userEJB.getpaymentperclient();
    }

    @GET
    @Path("/balanceofaclient/{emailuser}")
    public ClientCreditPaymentDTO GetBalanceofaClient(@PathParam("emailuser") String emailuser) {
        return userEJB.getbalanceofclient(emailuser);
    }

    @GET
    @Path("/sumofcredits")
    public double gettotalcredits() {
        return userEJB.gettotalcredits();
    }

    @GET
    @Path("/sumofpayments")
    public double GetTotalPayments() {
        return userEJB.GetTotalPayments();
    }

    @GET
    @Path("/totalbalance")
    public double GetTotalBalance() {
        return userEJB.gettotalcredits() + userEJB.GetTotalPayments();
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