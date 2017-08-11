package mx.gob.bansefi.EstadoDeCuenta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class jasperDTO {
	@Getter
	@Setter
	private String nombre;
	@Getter
	@Setter
	private String domicilio;
	@Getter
	@Setter
	private String rfc;
	@Getter
	@Setter
	private String periodo1;
	@Getter
	@Setter
	private String periodo2;
	@Getter
	@Setter
	private String numDias;
	@Getter
	@Setter
	private String fechaCorte;
	@Getter
	@Setter
	private String saldoInicial;
	@Getter
	@Setter
	private String dispEnPeriodo;
	@Getter
	@Setter
	private String numAbonos;
	@Getter
	@Setter
	private String montoDeAbonos;
	@Getter
	@Setter
	private String capitalFact;
	@Getter
	@Setter
	private String baseIntOrd;
	@Getter
	@Setter
	private String baseIntOrd1;
	@Getter
	@Setter
	private String baseIntOrd2;
	@Getter
	@Setter
	private String intOrd;
	@Getter
	@Setter
	private String intOrd1;
	@Getter
	@Setter
	private String intOrd2;
	@Getter
	@Setter
	private String intMoraPer;
	@Getter
	@Setter
	private String comisionPer;
	@Getter
	@Setter
	private String ivaPer;
	@Getter
	@Setter
	private String saldoFinal;
	@Getter
	@Setter
	private String tipoCta;
	@Getter
	@Setter
	private String numDeCredito;
	@Getter
	@Setter
	private String sucursal;
	@Getter
	@Setter
	private String cat;
	@Getter
	@Setter
	private String moneda;
	@Getter
	@Setter
	private String lineaDeCred;
	@Getter
	@Setter
	private String disLineaDeCred;
	@Getter
	@Setter
	private String fechaDeVenc;
	@Getter
	@Setter
	private String tasaIntOrd;
	@Getter
	@Setter
	private String tasaIntMor;
	@Getter
	@Setter
	private String formaPago;
	@Getter
	@Setter
	private String pagoDeInt;
	@Getter
	@Setter
	private String totalPagar;
	@Getter
	@Setter
	private String fechaLimite;
	@Getter
	@Setter
	private String folioAclaracion;
	@Getter
	@Setter
	private String numDeCta;
	@Getter
	@Setter
	private String fechaCargo;
	@Getter
	@Setter
	private String descripcion;
	@Getter
	@Setter
	private String cargo;
}
