package mx.gob.bansefi.EstadoDeCuenta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

public class OperacionesDTO {
	@Getter @Setter
	private String fechaop;
	@Getter @Setter
	private String conceptoop;
	@Getter @Setter
	private String cargoop;
	@Getter @Setter
	private String abonoop;
}
