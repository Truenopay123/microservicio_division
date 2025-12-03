package mx.edu.uteq.idgs13.microservicio_division;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroservicioDivisionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioDivisionApplication.class, args);
	}

}
