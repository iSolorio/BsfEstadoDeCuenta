package mx.gob.bansefi.EstadoDeCuenta.dto.batch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ReqBatchDTO 
{
	@Getter
	@Setter
	private byte[] archivo;
}
