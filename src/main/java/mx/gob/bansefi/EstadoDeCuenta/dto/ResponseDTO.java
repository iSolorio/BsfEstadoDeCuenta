package mx.gob.bansefi.EstadoDeCuenta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
	@Getter @Setter
	private String mensajeInterno;
	@Getter @Setter
	private byte[] archivo;
	@Getter @Setter
	private String status;

}
