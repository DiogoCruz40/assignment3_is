package is3.Rest.Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServiceApplication {

	public static void main(String[] args) {
		System.out.println("passei aqui");
		SpringApplication.run(RestServiceApplication.class, args);
		System.out.println("passei aqui 2 ");
	}

}
