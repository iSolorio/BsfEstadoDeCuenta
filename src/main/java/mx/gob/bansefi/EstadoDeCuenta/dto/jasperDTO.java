package mx.gob.bansefi.EstadoDeCuenta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class jasperDTO {

	@Getter @Setter
	private String idInterno;
	@Getter @Setter
	private String cuenta;
	@Getter @Setter
	private String descripcion;
}
