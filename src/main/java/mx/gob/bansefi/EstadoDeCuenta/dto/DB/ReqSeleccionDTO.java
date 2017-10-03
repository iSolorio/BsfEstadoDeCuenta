package mx.gob.bansefi.EstadoDeCuenta.dto.DB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ReqSeleccionDTO {
	@Getter
	@Setter
	public String numSecAc;
	@Getter
	@Setter
	public String fechaDesde;
	@Getter
	@Setter
	public String fechaHasta;
}
