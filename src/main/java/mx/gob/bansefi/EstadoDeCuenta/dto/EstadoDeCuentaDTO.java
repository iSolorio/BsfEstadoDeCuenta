package mx.gob.bansefi.EstadoDeCuenta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.ResDatosCreditoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosGral.ResDatosGralDTO;

@AllArgsConstructor
@NoArgsConstructor
public class EstadoDeCuentaDTO {
	@Getter
	@Setter
	private ResDatosGralDTO resDatosGral;
	@Getter
	@Setter
	private ResDatosCreditoDTO resDatosCredito;
}
