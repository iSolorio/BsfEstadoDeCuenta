package mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class DatosCreditoDTO {
	@Getter @Setter
	private String CREDITO;
	@Getter @Setter
	private String NOMBRETO;
	@Getter @Setter
	private String APOD_LEGAL;
	@Getter @Setter
	private String TIPO_CONTRATO;
	@Getter @Setter
	private String FECHA_CONTRA;
	@Getter @Setter
	private String MONTO_LINEA;
	@Getter @Setter
	private String FECHA_CORTE;
	@Getter @Setter
	private String MONTO_DEUDA;
	@Getter @Setter
	private String TIPO_CRED;
	@Getter @Setter
	private String VENCIMIENTO;
	@Getter @Setter
	private String TASA;
	@Getter @Setter
	private String CAP_ENTREGADO;
	@Getter @Setter
	private String REVI_DE_TASA;
	@Getter @Setter
	private String TASA_MOR;
	@Getter @Setter
	private String CAP_VIGENTE;
	@Getter @Setter
	private String CAP_VENCIDO;
	@Getter @Setter
	private String INT_VIG;
	@Getter @Setter
	private String INT_VENCIDO;
	@Getter @Setter
	private String INT_MORA;
	@Getter @Setter
	private String FECHA_INCIAL;
	@Getter @Setter
	private String FECHA_FINAL;
	@Getter @Setter
	private String DIAS_INTER;
	@Getter @Setter
	private String CAPITAL;
	@Getter @Setter
	private String CAPITAL_VENCIDO;
	@Getter @Setter
	private String TASA_ORDINARIA;
	@Getter @Setter
	private String TASA_MORATORIA;
	@Getter @Setter
	private String INTERES_VIGENTE;
	@Getter @Setter
	private String INTERES_VENCIDO;
	@Getter @Setter
	private String INTERES_MORATORIO;
	@Getter @Setter
	private String IVA;
	@Getter @Setter
	private String SALDO;
	@Getter @Setter
	private String PAGO_CAPITAL;
	@Getter @Setter
	private String PAGO_INTERES;
	@Getter @Setter
	private String PAGO_IVA;
	
	//--------------------
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
