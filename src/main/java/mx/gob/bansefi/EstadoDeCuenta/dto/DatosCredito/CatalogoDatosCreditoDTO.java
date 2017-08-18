package mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class CatalogoDatosCreditoDTO {
	@Getter @Setter
	private ArrayList<DatosCreditoDTO> ResponseBansefi;
}
