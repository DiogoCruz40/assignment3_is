package bos;

import dtos.*;
import entities.User;

import javax.ejb.Local;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Local
public interface IUserEJB {
    public UserDTO insert(UserDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException;
    public UserDTO checkusercredentials(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException;
    public boolean checkuseremail(String email);
    public User finduserbyid(Long id);
    public void removeuserbyid(Long id);
    public void removeuserbyemail(String email);
    public User finduserbyemail(String email);
    public void updateusername(Long id,String newname);
    public void updatepassword(Long id,String password) throws NoSuchAlgorithmException, InvalidKeySpecException;
    public void updatewallet(Long id,Double wallet);
    public UsersDTO getallusers();
    public UsersDTO getallmanagers();
    public ClientsCreditsDTO getcreditperclient();
    public ClientsPaymentsDTO getpaymentperclient();
    public ClientCreditPaymentDTO getbalanceofclient(String emailuser);
    public double gettotalcredits();
    public double GetTotalPayments();
}
