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
	private String CREDITO;
	@Getter @Setter
    private String PRODUCTO;
	@Getter @Setter
    private String CLIENTE;
	@Getter @Setter
    private String RFC;
	@Getter @Setter
    private String REFER;
	@Getter @Setter
	private ResAperturaPuestoDTO datos;
}
