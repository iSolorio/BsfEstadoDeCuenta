package mx.gob.bansefi.EstadoDeCuenta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class RequestGralDTO {

	@Getter
	@Setter
	private String usuario;
	@Getter
	@Setter
	private String password;
	@Getter
	@Setter
	private String idInternoPe;
	@Getter
	@Setter
	private String entidad;
	@Getter
	@Setter
	private String terminal;
}
