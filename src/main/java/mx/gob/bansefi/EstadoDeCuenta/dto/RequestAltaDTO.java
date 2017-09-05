package mx.gob.bansefi.EstadoDeCuenta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class RequestAltaDTO {
	@Getter
	@Setter
	private String CREDITO;
	@Getter
	@Setter
	private String NOMBRETO;
	@Getter
	@Setter
	private String APOD_LEGAL;
	@Getter
	@Setter
	private String TIPO_CONTRATO;
	@Getter
	@Setter
	private String FECHA_CONTRA;
	@Getter
	@Setter
	private String MONTO_LINEA;
	@Getter
	@Setter
	private String FECHA_CORTE;
	@Getter
	@Setter
	private String MONTO_DEUDA;
	@Getter
	@Setter
	private String TIPO_CRED;
	@Getter
	@Setter
	private String VENCIMIENTO;
	@Getter
	@Setter
	private String TASA;
	@Getter
	@Setter
	private String CAP_ENTREGADO;
	@Getter
	@Setter
	private String REVI_DE_TASA;
	@Getter
	@Setter
	private String TASA_MOR;
	@Getter
	@Setter
	private String CAP_VIGENTE;
	@Getter
	@Setter
	private String CAP_VENCIDO;
	@Getter
	@Setter
	private String INT_VIG;
	@Getter
	@Setter
	private String INT_VENCIDO;
	@Getter
	@Setter
	private String INT_MORA;
	@Getter
	@Setter
	private String FECHA_INCIAL;
	@Getter
	@Setter
	private String FECHA_FINAL;
	@Getter
	@Setter
	private String DIAS_INTER;
	@Getter
	@Setter
	private String CAPITAL;
	@Getter
	@Setter
	private String CAPITAL_VENCIDO;
	@Getter
	@Setter
	private String TASA_ORDINARIA;
	@Getter
	@Setter
	private String TASA_MORATORIA;
	@Getter
	@Setter
	private String INTERES_VIGENTE;
	@Getter
	@Setter
	private String INTERES_VENCIDO;
	@Getter
	@Setter
	private String INTERES_MORATORIO;
	@Getter
	@Setter
	private String IVA;
	@Getter
	@Setter
	private String SALDO;
	@Getter
	@Setter
	private String PAGO_CAPITAL;
	@Getter
	@Setter
	private String PAGO_INTERES;
	@Getter
	@Setter
	private String PAGO_IVA;
	// -------------------------------
	@Getter
	@Setter
	private String BIO_FECHA_INCIAL;
	@Getter
	@Setter
	private String BIO_FECHA_FINAL;
	@Getter
	@Setter
	private String BIO_SALDO;
	@Getter
	@Setter
	private String IO_FECHA_INCIAL;
	@Getter
	@Setter
	private String IO_FECHA_FINAL;
	@Getter
	@Setter
	private String SALDO_FINAL;
	@Getter
	@Setter
	private String PI_REVI_DE_TASA;
	@Getter
	@Setter
	private String FC_FECHA_FINAL;
	@Getter
	@Setter
	private String PC_FECHA_FINAL;
	@Getter
	@Setter
	private String FI_FECHA_FINAL;
	@Getter
	@Setter
	private String PI_FECHA_FINAL;
	@Getter
	@Setter
	private String PIV_FECHA_FINAL;
	@Getter
	@Setter
	private String FIV_FECHA_FINAL;
	@Getter
	@Setter
	private String PC_PAGO_CAPITAL;
	@Getter
	@Setter
	private String PI_PAGO_INTERES;
	@Getter
	@Setter
	private String FI_PAGO_IVA;
	// -------------------------------
	@Getter
	@Setter
	private String TIPOCTA;
	@Getter
	@Setter
	private String domicilio;
	@Getter
	@Setter
	private String RFC;
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
	private String comisionPer;
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
	private String disLineaDeCred;
	@Getter
	@Setter
	private String totalPagar;
	@Getter
	@Setter
	private String fechaLimite;
	
	@Getter 
	@Setter
	private String numSecAc;
	@Getter
	@Setter
	private String usuario;
	@Getter
	@Setter
	private String terminal;
}
