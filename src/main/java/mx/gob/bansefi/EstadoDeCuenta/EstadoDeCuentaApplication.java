package mx.gob.bansefi.EstadoDeCuenta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EstadoDeCuentaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EstadoDeCuentaApplication.class, args);
	}
}
