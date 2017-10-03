package mx.gob.bansefi.EstadoDeCuenta.dto.DB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ReqInsercionDTO {
	@Getter
	@Setter
	private String numSecAc;
	@Getter
	@Setter
	private String fechaDesde;
	@Getter
	@Setter
	private String fechaHasta;
	@Getter
	@Setter
	private byte[] archivo;
	@Getter
	@Setter
	private String periodo;
	@Getter
	@Setter
	private String userCreated;
	@Getter
	@Setter
	private String terminalCreated;
	@Getter
	@Setter
	private Integer processType;
}
