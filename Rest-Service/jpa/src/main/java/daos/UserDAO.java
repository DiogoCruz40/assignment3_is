package daos;

import entities.User;

import static security.PasswordSecurity.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public class UserDAO {

    private EntityManager em;

    public UserDAO(EntityManager em) {
        this.em = em;
    }

    public User insert(String email, String nome, String password, boolean isManager) throws NoSuchAlgorithmException, InvalidKeySpecException {

        String passwordhashed = generateStrongPasswordHash(password);
        User user = new User(email,nome,passwordhashed,isManager,0.0);

        try {
            em.persist(user);
            em.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public User finduserbyid(long id)
    {
        return em.find(User.class,id);
    }

    public boolean checkuseremail(String email)
    {
        Query query = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.emailuser LIKE :email")
                 .setParameter("email",email);

        long result = (Long) query.getSingleResult();
        //System.out.println("checkuseremail result for "+email+": "+result);

        if (result > 0) {
            return true;
        }
        return false;
    }

    public User checkusercredentials(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        List result = em.createQuery("SELECT u FROM User u WHERE u.emailuser LIKE :email")
                .setMaxResults(1)
                .setParameter("email",email)
                .getResultList();

        if (result.size() > 0) {
            User user = (User) result.get(0);
            if(validatePassword(password,user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public void removeuserbyid(long id)
    {
        User user = finduserbyid(id);
        em.remove(user);
        em.flush();
    }

    public void removeuserbyemail(String email)
    {
        User user = em.createQuery(
                "SELECT u FROM User u WHERE u.emailuser LIKE :email", User.class).setParameter("email", email).getSingleResult();

        em.remove(user);
        em.flush();
    }

    public User finduserbyemail(String email)
    {
        User user = em.createQuery(
                "SELECT u FROM User u WHERE u.emailuser LIKE :email", User.class).setParameter("email", email).getSingleResult();
        return user;
    }

    public void updateusername(Long id,String newname)
    {
        User user = finduserbyid(id);
        user.setNomeuser(newname);
        em.merge(user);
        em.flush();
    }

    public void updatepassword(Long id,String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user = finduserbyid(id);
        String passwordhashed = generateStrongPasswordHash(password);
        user.setPassword(passwordhashed);
        em.merge(user);
        em.flush();
    }

    public void updatewallet(Long id,Double wallet)
    {
        User user = finduserbyid(id);
        user.setWallet(wallet);
        em.merge(user);
        em.flush();
    }

    public void updatehashusers() throws NoSuchAlgorithmException, InvalidKeySpecException {
        List<User> result = em.createQuery("SELECT u FROM User u WHERE u.isManager = :manager").setParameter("manager",true).getResultList();

        for(User user : result)
        {
            String passwordhashed = generateStrongPasswordHash(user.getPassword());
            user.setPassword(passwordhashed);
            em.merge(user);
            em.flush();
        }
    }
}
