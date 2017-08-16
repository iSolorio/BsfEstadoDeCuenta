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
	private String ENTIDAD; // 0166
	@Getter
	@Setter
	private String CENTRO; // 0001
	@Getter
	@Setter
	private Integer VENTANILLA; // Terminal
}
