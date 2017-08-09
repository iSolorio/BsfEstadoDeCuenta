package mx.gob.bansefi.EstadoDeCuenta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import mx.gob.bansefi.EstadoDeCuenta.DB.ManejoDB;

@SpringBootApplication
public class EstadoDeCuentaApplication {
	@Autowired
	private ManejoDB conexiones;
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EstadoDeCuentaApplication.class, args);
		
	}
}
