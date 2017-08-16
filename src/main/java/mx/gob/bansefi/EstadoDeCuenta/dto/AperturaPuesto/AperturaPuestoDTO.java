package mx.gob.bansefi.EstadoDeCuenta.dto.AperturaPuesto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class AperturaPuestoDTO {
	@Getter
	@Setter
	private String USERHEADER;
	@Getter
	@Setter
	private String PASSHEADER;
	@Getter
	@Setter
	private String IPHEADER;
	@Getter
	@Setter
	private AperturaTRNDTO ITR_APERTURA_PUESTOS_TRN;
}
