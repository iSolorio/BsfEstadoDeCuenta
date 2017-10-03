package mx.gob.bansefi.EstadoDeCuenta.dto.DB;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ResSeleccionDTO {
	@Getter
	@Setter
	private ArrayList<byte[]> archivo;
}
