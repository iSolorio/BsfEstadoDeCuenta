package mx.gob.bansefi.EstadoDeCuenta.dto.DatosGral;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class DatosGralDTO {
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
}
