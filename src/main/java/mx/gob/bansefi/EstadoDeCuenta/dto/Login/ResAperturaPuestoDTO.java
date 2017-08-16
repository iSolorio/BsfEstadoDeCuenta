package mx.gob.bansefi.EstadoDeCuenta.dto.Login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ResAperturaPuestoDTO {
	@Getter
	@Setter
	private String TRANID;
	@Getter
	@Setter
	private Long ESTATUS;
	@Getter
	@Setter
	private String CODIGO;
	@Getter
	@Setter
	private String MENSAJE;
	@Getter
	@Setter
	private String NUMTASK;
}
