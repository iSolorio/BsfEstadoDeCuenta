package mx.gob.bansefi.EstadoDeCuenta.dto.DB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class EstadoCuentaDTO {
	@Getter
	@Setter
	private ReporteDTO Reporte;
}
