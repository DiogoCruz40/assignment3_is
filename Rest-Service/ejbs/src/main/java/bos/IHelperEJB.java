package bos;

import javax.ejb.Remote;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Remote
public interface IHelperEJB {

    public void updatehashusers() throws NoSuchAlgorithmException, InvalidKeySpecException;
}
