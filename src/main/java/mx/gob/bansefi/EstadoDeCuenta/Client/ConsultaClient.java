package mx.gob.bansefi.EstadoDeCuenta.Client;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.CatalogoDatosCreditoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.DatosCreditoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.ReqDatosCreditoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.ResDatosCreditoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosGral.ReqDatosGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosGral.ResDatosGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Login.ResAperturaPuestoDTO;
import mx.gob.bansefi.EstadoDeCuenta.utils.Util;

@Component
public class ConsultaClient {
	/*
	 * Inyeccion de dependencias
	 */
	@Autowired
	private Util util;
	/*
	 * Definicion de variables de clase
	 */
	@Value("${restServices.rootContext}")
	private String rootContext;
	@Value("${restServices.consultaDatosGenerales}")
	private String urlConsultaDatosGenerales;
	@Value("${restServices.consultaCredito}")
	private String urlConsultaCredito;
	@Value("${msj.error.general.errorServicioCliente}")
	private String urlErrorServicioCliente;

	/*
	 * Metodo para consultar datos generales
	 */
	public ResDatosGralDTO consDatosGral(ReqDatosGralDTO request) {
		ResDatosGralDTO response = new ResDatosGralDTO();
		ResAperturaPuestoDTO apePuesto = new ResAperturaPuestoDTO();
		try {
			String jsonRes = this.util.callRestPost(request, rootContext + urlConsultaDatosGenerales);
			if (!jsonRes.equals("")) {
				ArrayList<String> nodos = new ArrayList<String>();
				nodos.add("EjecutarResponse");
				nodos.add("EjecutarResult");
				apePuesto = (ResAperturaPuestoDTO) this.util.jsonToObject(apePuesto, jsonRes, nodos);
				if (apePuesto.getESTATUS() == 0) {
					nodos.add("ResponseBansefi");
					nodos.add("ResponseBansefi");
					response = (ResDatosGralDTO) this.util.jsonToObject(response, jsonRes, nodos);
					response.setDatos(apePuesto);
				} else {
					apePuesto.setESTATUS(-1l);
				}

			} else {
				apePuesto.setESTATUS(-1l);
				apePuesto.setMENSAJE(
						urlErrorServicioCliente + "public ResDatosGralDTO consPersonaMor(ReqDatosGralDTO request)");
			}
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		return response;
	}

	/*
	 * Metodo para consultar datos de credito
	 */
	public ResDatosCreditoDTO consultaDatosCredito(ReqDatosCreditoDTO request) {
		ResAperturaPuestoDTO apePuesto = new ResAperturaPuestoDTO();
		CatalogoDatosCreditoDTO catalogo = new CatalogoDatosCreditoDTO();
		ResDatosCreditoDTO response = new ResDatosCreditoDTO();
		DatosCreditoDTO datosCredito = new DatosCreditoDTO();
		try {
			String jsonRes = this.util.callRestPost(request, rootContext + urlConsultaCredito);
			String cadena = jsonRes.replaceAll("-", "_");
			if (!cadena.equals("")) {
				ArrayList<String> nodos = new ArrayList<String>();
				nodos.add("EjecutarResponse");
				nodos.add("EjecutarResult");
				apePuesto = (ResAperturaPuestoDTO) this.util.jsonToObject(apePuesto, cadena, nodos);
				response.setDatos(apePuesto);
				if (apePuesto.getESTATUS() == 0) {
					nodos.add("ResponseBansefi");
					catalogo = (CatalogoDatosCreditoDTO) this.util.jsonToObject(catalogo, cadena, nodos);
					datosCredito = catalogo.getResponseBansefi().get(0);
					datosCredito.setCAPITAL("$"+util.formato(catalogo.getResponseBansefi().get(0).getCAPITAL()));
					datosCredito.setCAPITAL_VENCIDO("$"+util.formato(catalogo.getResponseBansefi().get(0).getCAPITAL_VENCIDO()));
					datosCredito.setCAP_ENTREGADO("$"+util.formato(catalogo.getResponseBansefi().get(0).getCAP_ENTREGADO()));
					datosCredito.setCAP_VENCIDO("$"+util.formato(catalogo.getResponseBansefi().get(0).getCAP_VENCIDO()));
					datosCredito.setCAP_VIGENTE("$"+util.formato(catalogo.getResponseBansefi().get(0).getCAP_VIGENTE()));
					datosCredito.setINTERES_MORATORIO("$"+util.formato(catalogo.getResponseBansefi().get(0).getINTERES_MORATORIO()));
					datosCredito.setINTERES_VENCIDO("$"+util.formato(catalogo.getResponseBansefi().get(0).getINTERES_VENCIDO()));
					datosCredito.setINTERES_VIGENTE("$"+util.formato(catalogo.getResponseBansefi().get(0).getINTERES_VIGENTE()));
					datosCredito.setINT_MORA("$"+util.formato(catalogo.getResponseBansefi().get(0).getINT_MORA()));
					datosCredito.setINT_VENCIDO("$"+util.formato(catalogo.getResponseBansefi().get(0).getINT_VENCIDO()));
					datosCredito.setINT_VIG("$"+util.formato(catalogo.getResponseBansefi().get(0).getINT_VIG()));
					datosCredito.setIVA("$"+util.formato(catalogo.getResponseBansefi().get(0).getIVA()));
					datosCredito.setMONTO_DEUDA("$"+util.formato(catalogo.getResponseBansefi().get(0).getMONTO_DEUDA()));
					datosCredito.setMONTO_LINEA("$"+util.formato(catalogo.getResponseBansefi().get(0).getMONTO_LINEA()));
					datosCredito.setPAGO_CAPITAL("$"+util.formato(catalogo.getResponseBansefi().get(0).getPAGO_CAPITAL()));
					datosCredito.setPAGO_INTERES("$"+util.formato(catalogo.getResponseBansefi().get(0).getPAGO_INTERES()));
					datosCredito.setPAGO_IVA("$"+util.formato(catalogo.getResponseBansefi().get(0).getPAGO_IVA()));
					datosCredito.setSALDO("$"+util.formato(catalogo.getResponseBansefi().get(0).getSALDO()));
					datosCredito.setTASA(util.formato(catalogo.getResponseBansefi().get(0).getTASA())+"%");
					datosCredito.setTASA_MOR(util.formato(catalogo.getResponseBansefi().get(0).getTASA_MOR())+"%");
					datosCredito.setTASA_MORATORIA(util.formato(catalogo.getResponseBansefi().get(0).getTASA_MORATORIA())+"%");
					datosCredito.setTASA_ORDINARIA(util.formato(catalogo.getResponseBansefi().get(0).getTASA_ORDINARIA())+"%");
					//Fechas
					datosCredito.setFECHA_CONTRA(util.replace(catalogo.getResponseBansefi().get(0).getFECHA_CONTRA()));
					datosCredito.setFECHA_FINAL(util.replace(catalogo.getResponseBansefi().get(0).getFECHA_FINAL()));
					datosCredito.setFECHA_INCIAL(util.replace(catalogo.getResponseBansefi().get(0).getFECHA_INCIAL()));
					datosCredito.setVENCIMIENTO(util.replace(catalogo.getResponseBansefi().get(0).getVENCIMIENTO()));
					//Repeticiones
					datosCredito.setBIO_FECHA_INCIAL(datosCredito.getFECHA_INCIAL());
					datosCredito.setBIO_FECHA_FINAL(datosCredito.getFECHA_FINAL());
					datosCredito.setBIO_SALDO(datosCredito.getSALDO());
					datosCredito.setIO_FECHA_INCIAL(datosCredito.getFECHA_INCIAL());
					datosCredito.setIO_FECHA_FINAL(datosCredito.getFECHA_FINAL());
					datosCredito.setSALDO_FINAL(datosCredito.getSALDO());
					datosCredito.setPI_REVI_DE_TASA(datosCredito.getREVI_DE_TASA());
					datosCredito.setFC_FECHA_FINAL(datosCredito.getFECHA_FINAL());
					datosCredito.setPC_FECHA_FINAL(datosCredito.getFECHA_FINAL());
					datosCredito.setFI_FECHA_FINAL(datosCredito.getFECHA_FINAL());
					datosCredito.setPI_FECHA_FINAL(datosCredito.getFECHA_FINAL());
					datosCredito.setFIV_FECHA_FINAL(datosCredito.getFECHA_FINAL());
					datosCredito.setPIV_FECHA_FINAL(datosCredito.getFECHA_FINAL());
					datosCredito.setPC_PAGO_CAPITAL(datosCredito.getPAGO_CAPITAL());
					datosCredito.setPI_PAGO_INTERES(datosCredito.getPAGO_INTERES());
					datosCredito.setFI_PAGO_IVA(datosCredito.getPAGO_IVA());
					
					response.setDatosCredito(datosCredito);
				} else {
					apePuesto.setESTATUS(-1l);
				}

			} else {
				apePuesto.setESTATUS(-1l);
				apePuesto.setMENSAJE(urlErrorServicioCliente
						+ "public ResDatosCreditoDTO consultaDatosCredito(ReqDatosCreditoDTO request)");
			}
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		return response;
	}
}
