package mx.gob.bansefi.EstadoDeCuenta.dto.DatosGral;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class CatalogoDatosGralDTO {
	@Getter @Setter
	private ArrayList<DatosGralDTO> ResponseBansefi;  
}
