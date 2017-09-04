package mx.gob.bansefi.EstadoDeCuenta.dto.DatosGral;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.EstadoDeCuenta.dto.Login.ResAperturaPuestoDTO;

@AllArgsConstructor
@NoArgsConstructor
public class ResDatosGralDTO{
	@Getter @Setter
	private ResAperturaPuestoDTO datos;
	@Getter @Setter
	private CatalogoDatosGralDTO creditos;
}
