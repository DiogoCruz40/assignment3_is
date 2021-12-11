package bos;

import daos.UserDAO;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Singleton
public class HelperEJB implements IHelperEJB {

    @PersistenceContext(unitName = "ManageApp")
    EntityManager em;

    private static boolean updaterequired = false;

    @Override
    @Schedule(second = "*/10", minute = "*", hour = "*", persistent = false)
    public void updatehashusers() throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        UserDAO userDAO = new UserDAO(em);
        if (!updaterequired) {
            userDAO.updatehashusers();
            updaterequired = true;
        }
    }
}
