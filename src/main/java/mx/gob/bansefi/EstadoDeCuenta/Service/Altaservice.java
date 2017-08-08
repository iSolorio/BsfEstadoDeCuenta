package mx.gob.bansefi.EstadoDeCuenta.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.gob.bansefi.EstadoDeCuenta.DB.ManejoDB;
import mx.gob.bansefi.EstadoDeCuenta.utils.Util;

@Service
public class Altaservice {
	@Autowired
	private ManejoDB manejodb;
	@Autowired
	private Util util;
	@Value("${url.database}")
	private String urlDataBase;
	public void consultaEstado()
	{
		System.out.println("Funciona");
	}
}
