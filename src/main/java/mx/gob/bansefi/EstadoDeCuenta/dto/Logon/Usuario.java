package mx.gob.bansefi.EstadoDeCuenta.dto.Logon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
	@Getter
	@Setter
	private String USUARIO;
	@Getter
	@Setter
	private String NOMBRE;
	@Getter
	@Setter
	private String ENTIDAD;
	@Getter
	@Setter
	private String CENTRO;
	@Getter
	@Setter
	private Integer VENTANILLA;
}
