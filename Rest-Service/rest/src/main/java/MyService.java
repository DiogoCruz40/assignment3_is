
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("/myservice")
@Produces(MediaType.APPLICATION_JSON)
public class MyService {

    @GET
    @Path("/test")
    public String method1() {
        System.out.println("M1 executing....");
        return "M1 adasdexecuted...";
    }
}