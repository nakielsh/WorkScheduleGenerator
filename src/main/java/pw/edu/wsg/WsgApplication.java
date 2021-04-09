package pw.edu.wsg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.edu.wsg.employee.Employee;

import java.util.ArrayList;
import java.util.List;



@SpringBootApplication
public class WsgApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsgApplication.class, args);
	}


}
