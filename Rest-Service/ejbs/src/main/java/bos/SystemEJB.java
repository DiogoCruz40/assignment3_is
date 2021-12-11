package bos;

import daos.SystemDAO;
import daos.UserDAO;
import entities.User;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Singleton
public class SystemEJB implements ISystemEJB{

    @PersistenceContext(unitName = "ManageApp")
    EntityManager em;

    @Resource(name="java:jboss/mail/gmail")
    private Session mailSession;

    @Override
    public double TotalRevenuesoftheday() {
        SystemDAO systemDAO = new SystemDAO(em);
        return systemDAO.TotalRevenuesoftheday();
    }

    @Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
    public void atSchedule() throws InterruptedException {
        SystemDAO systemDAO = new SystemDAO(em);
        double revenues = systemDAO.TotalRevenuesoftheday();

        List<User> managers = systemDAO.getManagers();
        if (managers.size() > 0) {
            try {
                MimeMessage m = new MimeMessage(mailSession);
                Address from = new InternetAddress("wildflycoorp@gmail.com");

                Address[] to = new InternetAddress[managers.size()];
                for (int i = 0; i < managers.size(); i++) {
                    to[i] = new InternetAddress(managers.get(i).getEmailuser());
                }

                m.setFrom(from);
                m.setRecipients(Message.RecipientType.TO, to);
                m.setSubject("Total Revenues Of The Day");
                m.setSentDate(new java.util.Date());
                m.setContent("Daily summary of the revenues of the day: "+revenues,"text/plain");
                Transport.send(m);
            }
            catch (javax.mail.MessagingException e)
            {
                e.printStackTrace();
            }
        }
    }

}
