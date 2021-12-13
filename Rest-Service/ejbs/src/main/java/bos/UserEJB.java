package bos;

import daos.UserDAO;
import dtos.*;
import entities.ClientCredit;
import entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

@Stateless
public class UserEJB implements IUserEJB{

    @PersistenceContext(unitName = "ManageApp")
    EntityManager em;

    @Override
    public UserDTO insert(UserDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException {
        UserDAO userDAO = new UserDAO(em);
        User newUser = userDAO.insert(dto.getEmailuser(), dto.getNomeuser(), dto.getPassword(), dto.getIsManager());

        dto.setId(newUser.getId());
        return dto;
    }

    @Override
    public UserDTO checkusercredentials(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException  {
        UserDAO userDAO = new UserDAO(em);
        var userEntity = userDAO.checkusercredentials(email, password);

        UserDTO dto = null;

        if (userEntity != null) {
            dto = new UserDTO();
            dto.setId(userEntity.getId());
            dto.setEmailuser(userEntity.getEmailuser());
            dto.setNomeuser(userEntity.getNomeuser());
            dto.setIsManager(userEntity.isManager());
        }

        return dto;
    }

    @Override
    public boolean checkuseremail(String email) {
        UserDAO userDAO = new UserDAO(em);
        return userDAO.checkuseremail(email);
    }

    @Override
    public User finduserbyid(Long id) {
        UserDAO userDAO = new UserDAO(em);
        return userDAO.finduserbyid(id);
    }

    @Override
    public void removeuserbyid(Long id) {
        UserDAO userDAO = new UserDAO(em);
        userDAO.removeuserbyid(id);
    }

    @Override
    public void removeuserbyemail(String email) {
        UserDAO userDAO = new UserDAO(em);
        userDAO.removeuserbyemail(email);
    }

    @Override
    public User finduserbyemail(String email) {
        UserDAO userDAO = new UserDAO(em);
       return userDAO.finduserbyemail(email);
    }

    @Override
    public void updateusername(Long id, String newname) {
        UserDAO userDAO = new UserDAO(em);
        userDAO.updateusername(id,newname);
    }

    @Override
    public void updatepassword(Long id, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        UserDAO userDAO = new UserDAO(em);
        userDAO.updatepassword(id, password);
    }

    @Override
    public void updatewallet(Long id, Double wallet) {
        UserDAO userDAO = new UserDAO(em);
        userDAO.updatewallet(id,wallet);
    }

    @Override
    public UsersDTO getallusers() {
        UserDAO userDAO = new UserDAO(em);

        ArrayList<UserDTO> usersDTO = new ArrayList<>();
        for (User user : userDAO.getallusers())
        {
            UserDTO userDTO = new UserDTO(user.getId(),user.getEmailuser(),user.getNomeuser());
            usersDTO.add(userDTO);
        }
        return new UsersDTO(usersDTO);
    }

    @Override
    public UsersDTO getallmanagers() {
        UserDAO userDAO = new UserDAO(em);

        ArrayList<UserDTO> usersDTO = new ArrayList<>();
        for (User user : userDAO.getallmanagers())
        {
            UserDTO userDTO = new UserDTO(user.getId(),user.getEmailuser(),user.getNomeuser());
            usersDTO.add(userDTO);
        }
        return new UsersDTO(usersDTO);
    }

    @Override
    public ClientsCreditsDTO getcreditperclient() {
        UserDAO userDAO = new UserDAO(em);
        ArrayList<ClientCreditDTO> clientsCreditsDTO = new ArrayList<>();
        for (ClientCredit clientCredit : userDAO.getcreditperclient())
        {
            UserDTO userDTO = new UserDTO(clientCredit.getUser().getId(),clientCredit.getUser().getEmailuser(),clientCredit.getUser().getNomeuser());
            ClientCreditDTO clientCreditDTO = new ClientCreditDTO(userDTO,clientCredit.getCredit());
            clientsCreditsDTO.add(clientCreditDTO);
        }
        return new ClientsCreditsDTO(clientsCreditsDTO);
    }

    @Override
    public ClientsPaymentsDTO getpaymentperclient() {
        UserDAO userDAO = new UserDAO(em);
        ArrayList<ClientPaymentDTO> clientsPaymentsDTO = new ArrayList<>();

        for (ClientCredit clientCredit : userDAO.getpaymentperclient())
        {
            UserDTO userDTO = new UserDTO(clientCredit.getUser().getId(),clientCredit.getUser().getEmailuser(),clientCredit.getUser().getNomeuser());
           ClientPaymentDTO clientpaymentDTO= new ClientPaymentDTO(userDTO,clientCredit.getPayment());
            clientsPaymentsDTO.add(clientpaymentDTO);
        }
        return new ClientsPaymentsDTO(clientsPaymentsDTO);
    }

    @Override
    public ClientCreditPaymentDTO getbalanceofclient(String emailuser) {
        UserDAO userDAO = new UserDAO(em);
       ClientCredit clientCredit = userDAO.getbalanceofclient(emailuser);
       if(clientCredit != null)
       {
           UserDTO userDTO = new UserDTO(clientCredit.getUser().getId(),clientCredit.getUser().getEmailuser(),clientCredit.getUser().getNomeuser());
           return new ClientCreditPaymentDTO(userDTO,clientCredit.getCredit() + clientCredit.getPayment());
       }
       return null;
    }

    @Override
    public double gettotalcredits() {
        UserDAO userDAO = new UserDAO(em);
        return userDAO.gettotalcredits();
    }

    @Override
    public double GetTotalPayments() {
        UserDAO userDAO = new UserDAO(em);
        return userDAO.GetTotalPayments();
    }

    @Override
    public ClientHighestDebtDTO GetHighestDebtClient() {
        UserDAO userDAO = new UserDAO(em);
      ClientCredit clientCredit = userDAO.GetHighestDebtClient();
      if(clientCredit != null)
      {
      UserDTO userDTO = new UserDTO(clientCredit.getUser().getId(),clientCredit.getUser().getEmailuser(),clientCredit.getUser().getNomeuser());
      return new ClientHighestDebtDTO(userDTO,clientCredit.getCredit() + clientCredit.getPayment());
      }
      return null;
    }

}