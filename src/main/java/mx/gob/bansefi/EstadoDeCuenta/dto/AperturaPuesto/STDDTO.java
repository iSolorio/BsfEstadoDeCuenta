package mx.gob.bansefi.EstadoDeCuenta.dto.AperturaPuesto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class STDDTO {
	@Getter
	@Setter
	private String ID_INTERNO_TERM_TN;
	@Getter
	@Setter
	private String ID_EMPL_AUT;
	@Getter
	@Setter
	private String NUM_SEC;
	@Getter
	@Setter
	private String COD_TX;
}
