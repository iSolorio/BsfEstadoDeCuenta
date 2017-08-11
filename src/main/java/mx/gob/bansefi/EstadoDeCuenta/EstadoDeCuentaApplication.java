package mx.gob.bansefi.EstadoDeCuenta;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import mx.gob.bansefi.EstadoDeCuenta.DB.ManejoDB;

@SpringBootApplication
@EnableAsync
public class EstadoDeCuentaApplication {
	@Autowired
	private ManejoDB conexiones;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EstadoDeCuentaApplication.class, args);

	}

	@Bean
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("GithubLookup-");
		executor.initialize();
		return executor;
	}
}
