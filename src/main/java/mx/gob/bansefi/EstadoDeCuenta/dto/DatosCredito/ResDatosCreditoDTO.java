package mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.gob.bansefi.EstadoDeCuenta.dto.Login.ResAperturaPuestoDTO;

@AllArgsConstructor
@NoArgsConstructor
public class ResDatosCreditoDTO {
	@Getter @Setter
	private ResAperturaPuestoDTO datos;
	@Getter @Setter
	private ArrayList<DatosCreditoDTO> datosCredito;
}
