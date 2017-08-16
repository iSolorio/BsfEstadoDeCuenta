package mx.gob.bansefi.EstadoDeCuenta.dto.Logon;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class CatalogoUsuarioDTO {
	@Getter
	@Setter
	ArrayList<Usuario> ResponseBansefi;
}
