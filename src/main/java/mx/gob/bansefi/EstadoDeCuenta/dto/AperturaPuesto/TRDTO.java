package mx.gob.bansefi.EstadoDeCuenta.dto.AperturaPuesto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class TRDTO {
	@Getter
	@Setter
	private String FECHA_PERFIL;
	@Getter
	@Setter
	private String HORA_PERFIL;
	@Getter
	@Setter
	private String FECHA_PC;
	@Getter
	@Setter
	private String HORA_PC;
	@Getter
	@Setter
	private String ID_INTERNO_TERM_TN;
	@Getter
	@Setter
	private String ID_INTERNO_EMPL_EP;
	@Getter
	@Setter
	private String COD_NRBE_EN_FSC;
	@Getter
	@Setter
	private String COD_NRBE_EN;
	@Getter
	@Setter
	private String COD_INTERNO_UO;
}
