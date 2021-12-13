package daos;

import entities.ClientCredit;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import static security.PasswordSecurity.generateStrongPasswordHash;
import static security.PasswordSecurity.validatePassword;

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

    public ArrayList<User> getallusers()
    {
        List<User> result = em.createQuery("SELECT u FROM User u WHERE u.isManager = :manager").setParameter("manager",false).getResultList();
        ArrayList<User> users = new ArrayList<>();
        for (User user : result)
            users.add(user);
        return users;
    }

    public ArrayList<User> getallmanagers()
    {
        List<User> result = em.createQuery("SELECT u FROM User u WHERE u.isManager = :manager").setParameter("manager",true).getResultList();
        ArrayList<User> users = new ArrayList<>();
        for (User user : result)
            users.add(user);
        return users;
    }

    public ArrayList<ClientCredit> getcreditperclient()
    {
        List<ClientCredit> result = em.createQuery("select c from ClientCredit c JOIN c.user u where u.isManager=:manager").setParameter("manager",false).getResultList();

        ArrayList<ClientCredit> clientsCredits = new ArrayList<>();
        for (ClientCredit clientCredit : result)
        {
            clientsCredits.add(clientCredit);
        }
        return clientsCredits;
    }


    public ArrayList<ClientCredit> getpaymentperclient()
    {
        List<ClientCredit> result = (List<ClientCredit>) em.createQuery("select c from ClientCredit c JOIN c.user u where u.isManager=:manager").setParameter("manager",false).getResultList();

        ArrayList<ClientCredit> clientsCredits = new ArrayList<>();
        for (ClientCredit clientCredit : result)
        {
            clientsCredits.add(clientCredit);
        }
        return clientsCredits;
    }

    public ClientCredit getbalanceofclient(String emailuser)
    {
        List<ClientCredit> result = em.createQuery("select c from ClientCredit c JOIN c.user u where u.emailuser=:emailuser and u.isManager=:manager").setParameter("emailuser",emailuser).setParameter("manager",false).getResultList();
        if(!result.isEmpty())
        {
            return result.get(0);
        }
        return null;
    }

    public double gettotalcredits()
    {
        List<ClientCredit> result = em.createQuery("select u from ClientCredit u JOIN u.user c").getResultList();
        double creditos = 0;
        for (ClientCredit clientCredit : result)
        {
           creditos = clientCredit.getCredit() + creditos;
        }
        return creditos;
    }
    public double GetTotalPayments()
    {
        List<ClientCredit> result = em.createQuery("select u from ClientCredit u JOIN u.user c").getResultList();
        double payment = 0;

        for (ClientCredit clientCredit : result)
        {
           payment = clientCredit.getPayment() + payment;
        }

        return payment;
    }

    public ClientCredit GetHighestDebtClient()
    {
        List<ClientCredit> result = em.createQuery("select c from ClientCredit c JOIN c.user u").getResultList();
        if(!result.isEmpty()) {
            double payment, credit = 0;
            ClientCredit clientCreditmaxdebt = result.get(0);
            payment = result.get(0).getPayment();
            credit = result.get(0).getCredit();

            for (ClientCredit clientCredit : result)
            {
                if ((payment + credit) > (clientCredit.getPayment() + clientCredit.getCredit()))
                {
                    payment = clientCredit.getPayment();
                    credit = clientCredit.getCredit();
                    clientCreditmaxdebt = clientCredit;
                }
            }
            return clientCreditmaxdebt;

        }
        return null;
    }
}
