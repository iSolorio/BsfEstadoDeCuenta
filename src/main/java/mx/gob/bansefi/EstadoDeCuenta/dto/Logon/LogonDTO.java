package mx.gob.bansefi.EstadoDeCuenta.dto.Logon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class LogonDTO {
	@Getter
	@Setter
	private String USUARIO;
	@Getter
	@Setter
	private String PASSWORD;
	@Getter
	@Setter
	private String NEWPASSWORD;
	@Getter
	@Setter
	private String IP;
	@Getter
	@Setter
	private String FORZADO;
}
