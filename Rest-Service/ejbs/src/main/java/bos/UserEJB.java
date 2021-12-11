package bos;

import daos.UserDAO;
import dtos.UserDTO;
import entities.User;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Stateless
public class UserEJB implements IUserEJB{

    @PersistenceContext(unitName = "ManageApp")
    EntityManager em;

    @Override
    public UserDTO insert(UserDTO dto) throws NoSuchAlgorithmException, InvalidKeySpecException {
        UserDAO userDAO = new UserDAO(em);
        User newUser = userDAO.insert(dto.getEmailuser(), dto.getNomeuser(), dto.getPassword(), dto.isManager());

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
            dto.setManager(userEntity.isManager());
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
}