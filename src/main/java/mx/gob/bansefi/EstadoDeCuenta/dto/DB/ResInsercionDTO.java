package mx.gob.bansefi.EstadoDeCuenta.dto.DB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ResInsercionDTO {
	@Getter
	@Setter
	private Integer Resultado;
	@Getter
	@Setter
	private String Descripcion;
}
