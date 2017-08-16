package mx.gob.bansefi.EstadoDeCuenta.dto.Logon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ReqLogonDTO {
	@Getter
	@Setter
	private LogonDTO Ejecutar;
}
