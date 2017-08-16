package mx.gob.bansefi.EstadoDeCuenta.dto.AperturaPuesto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class AperturaTRNDTO {
	@Getter
	@Setter
	private STDDTO STD_TRN_I_PARM_V;
	@Getter
	@Setter
	private TRDTO TR_APERTURA_PUESTOS_EVT_Y;
	@Getter
	@Setter
	private String PASS;
	@Getter
	@Setter
	private String PASSN01;
}
