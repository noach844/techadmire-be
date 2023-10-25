package techadmire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("techadmire.models")
public class TechAdmireBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechAdmireBeApplication.class, args);
	}

}
