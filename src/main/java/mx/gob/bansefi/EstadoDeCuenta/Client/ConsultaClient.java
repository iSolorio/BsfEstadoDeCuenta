package mx.gob.bansefi.EstadoDeCuenta.Client;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.CatalogoDatosCreditoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.DatosCreditoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.ReqDatosCreditoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.ResDatosCreditoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosGral.CatalogoDatosGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosGral.DatosGralDTO;
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
		CatalogoDatosGralDTO catalogoDatosGral = new CatalogoDatosGralDTO();
		try {
			String jsonRes = this.util.callRestPost(request, rootContext + urlConsultaDatosGenerales);
			if (!jsonRes.equals("")) {
				ArrayList<String> nodos = new ArrayList<String>();
				nodos.add("EjecutarResponse");
				nodos.add("EjecutarResult");
				apePuesto = (ResAperturaPuestoDTO) this.util.jsonToObject(apePuesto, jsonRes, nodos);
				response.setDatos(apePuesto);
				if (apePuesto.getESTATUS() == 0) {
					nodos.add("ResponseBansefi");
					catalogoDatosGral = (CatalogoDatosGralDTO) this.util.jsonToObject(catalogoDatosGral, jsonRes, nodos);
					response.setCreditos(catalogoDatosGral);
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
		ResDatosCreditoDTO response = new ResDatosCreditoDTO();
		ResAperturaPuestoDTO apePuesto = new ResAperturaPuestoDTO();
		ArrayList<DatosCreditoDTO> listadatosCredito = new ArrayList<DatosCreditoDTO>();
		CatalogoDatosCreditoDTO catalogo = new CatalogoDatosCreditoDTO();
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
					int tamanoLista = catalogo.getResponseBansefi().size();
					if(tamanoLista > 0){
						for(int i = 0; i < catalogo.getResponseBansefi().size();i++){
							datosCredito = catalogo.getResponseBansefi().get(i);
							datosCredito.setCAPITAL((catalogo.getResponseBansefi().get(i).getCAPITAL() == null) ? "" : util.formatoCant(catalogo.getResponseBansefi().get(i).getCAPITAL()));
							datosCredito.setCAPITAL_VENCIDO((catalogo.getResponseBansefi().get(i).getCAPITAL_VENCIDO() == null) ? "" : util.formatoCant(catalogo.getResponseBansefi().get(i).getCAPITAL_VENCIDO()));
							datosCredito.setCAP_ENTREGADO((catalogo.getResponseBansefi().get(i).getCAP_ENTREGADO() == null) ? "" : util.formatoCant(catalogo.getResponseBansefi().get(i).getCAP_ENTREGADO()));
							datosCredito.setCAP_VENCIDO((catalogo.getResponseBansefi().get(i).getCAP_VENCIDO() == null) ? "" : util.formatoCant(catalogo.getResponseBansefi().get(i).getCAP_VENCIDO()));
							datosCredito.setCAP_VIGENTE((catalogo.getResponseBansefi().get(i).getCAP_VIGENTE() == null) ? "" : util.formatoCant(catalogo.getResponseBansefi().get(i).getCAP_VIGENTE()));
							datosCredito.setINTERES_MORATORIO((catalogo.getResponseBansefi().get(i).getINTERES_MORATORIO() == null) ? "" : util.formatoCant(catalogo.getResponseBansefi().get(i).getINTERES_MORATORIO()));
							datosCredito.setINTERES_VENCIDO((catalogo.getResponseBansefi().get(i).getINTERES_VENCIDO()== null) ? "" : util.formatoCant(catalogo.getResponseBansefi().get(i).getINTERES_VENCIDO()));
							datosCredito.setINTERES_VIGENTE((catalogo.getResponseBansefi().get(i).getINTERES_VIGENTE() == null) ? "" : util.formatoCant(catalogo.getResponseBansefi().get(i).getINTERES_VIGENTE()));
							datosCredito.setINT_MORA((catalogo.getResponseBansefi().get(i).getINT_MORA() == null) ? "" : util.formatoCant(catalogo.getResponseBansefi().get(i).getINT_MORA()));
							datosCredito.setINT_VENCIDO((catalogo.getResponseBansefi().get(i).getINT_VENCIDO() == null) ? "" :util.formatoCant(catalogo.getResponseBansefi().get(i).getINT_VENCIDO()));
							datosCredito.setINT_VIG((catalogo.getResponseBansefi().get(i).getINT_VIG() == null) ? "" : util.formatoCant(catalogo.getResponseBansefi().get(i).getINT_VIG()));
							datosCredito.setIVA((catalogo.getResponseBansefi().get(i).getIVA()== null) ? "" : util.formatoCant(catalogo.getResponseBansefi().get(i).getIVA()));
							datosCredito.setMONTO_DEUDA((catalogo.getResponseBansefi().get(i).getMONTO_DEUDA() == null) ? "" : util.formatoCant(catalogo.getResponseBansefi().get(i).getMONTO_DEUDA()));
							datosCredito.setMONTO_LINEA((catalogo.getResponseBansefi().get(i).getMONTO_LINEA() == null) ? "" : util.formatoCant(catalogo.getResponseBansefi().get(i).getMONTO_LINEA()));
							datosCredito.setPAGO_CAPITAL((catalogo.getResponseBansefi().get(i).getPAGO_CAPITAL() == null) ? "" : util.formatoCant(catalogo.getResponseBansefi().get(i).getPAGO_CAPITAL()));
							datosCredito.setPAGO_INTERES((catalogo.getResponseBansefi().get(i).getPAGO_INTERES() == null) ? "" : util.formatoCant(catalogo.getResponseBansefi().get(i).getPAGO_INTERES()));
							datosCredito.setPAGO_IVA((catalogo.getResponseBansefi().get(i).getPAGO_IVA() == null) ? "" : util.formatoCant(catalogo.getResponseBansefi().get(i).getPAGO_IVA()));
							datosCredito.setSALDO((catalogo.getResponseBansefi().get(i).getSALDO() == null) ? "" : util.formatoCant(catalogo.getResponseBansefi().get(i).getSALDO()));
							datosCredito.setTASA((catalogo.getResponseBansefi().get(i).getTASA() == null) ? "" : util.formatoPorc(catalogo.getResponseBansefi().get(i).getTASA()));
							datosCredito.setTASA_MOR((catalogo.getResponseBansefi().get(i).getTASA_MOR() == null) ? "" :util.formatoPorc(catalogo.getResponseBansefi().get(i).getTASA_MOR()));
							datosCredito.setTASA_MORATORIA((catalogo.getResponseBansefi().get(i).getTASA_MORATORIA() == null) ? "" : util.formatoPorc(catalogo.getResponseBansefi().get(i).getTASA_MORATORIA()));
							datosCredito.setTASA_ORDINARIA((catalogo.getResponseBansefi().get(i).getTASA_ORDINARIA() == null) ? "" : util.formatoPorc(catalogo.getResponseBansefi().get(i).getTASA_ORDINARIA()));
							//Fechas
							datosCredito.setFECHA_CONTRA((catalogo.getResponseBansefi().get(i).getFECHA_CONTRA() == null) ? "" : util.replace(catalogo.getResponseBansefi().get(i).getFECHA_CONTRA()));
							datosCredito.setFECHA_FINAL((catalogo.getResponseBansefi().get(i).getFECHA_FINAL() == null) ? "" : util.replace(catalogo.getResponseBansefi().get(i).getFECHA_FINAL()));
							datosCredito.setFECHA_INCIAL((catalogo.getResponseBansefi().get(i).getFECHA_INCIAL() == null) ? "" : util.replace(catalogo.getResponseBansefi().get(i).getFECHA_INCIAL()));
							datosCredito.setVENCIMIENTO((catalogo.getResponseBansefi().get(i).getVENCIMIENTO() == null) ? "" : util.replace(catalogo.getResponseBansefi().get(i).getVENCIMIENTO()));
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
							//Datos de los cuales no son cantidades ni reprecentan tasas
							datosCredito.setAPOD_LEGAL((catalogo.getResponseBansefi().get(i).getAPOD_LEGAL() == null) ? "" : catalogo.getResponseBansefi().get(0).getAPOD_LEGAL());
							datosCredito.setTIPO_CONTRATO((catalogo.getResponseBansefi().get(i).getTIPO_CONTRATO() == null) ? "" : catalogo.getResponseBansefi().get(0).getTIPO_CONTRATO());
							datosCredito.setFECHA_CORTE((catalogo.getResponseBansefi().get(i).getFECHA_CORTE() == null) ? "" : catalogo.getResponseBansefi().get(0).getFECHA_CORTE());
							datosCredito.setTIPO_CRED((catalogo.getResponseBansefi().get(i).getTIPO_CRED() == null) ? "" : catalogo.getResponseBansefi().get(0).getTIPO_CRED());
							//Aun no definidos
							datosCredito.setDomicilio((datosCredito.getDomicilio()==null) ? "" : "");
							datosCredito.setDispEnPeriodo((datosCredito.getDispEnPeriodo()==null) ? "" : "");
							datosCredito.setNumAbonos((datosCredito.getNumAbonos()==null) ? "" : "");
							datosCredito.setMontoDeAbonos((datosCredito.getMontoDeAbonos()==null) ? "" : "");
							datosCredito.setComisionPer((datosCredito.getComisionPer()==null) ? "" : "");
							datosCredito.setSucursal((datosCredito.getSucursal()==null) ? "" : "");
							datosCredito.setCat((datosCredito.getCat()==null) ? "" : "");
							datosCredito.setMoneda((datosCredito.getMoneda()==null) ? "" : "");
							datosCredito.setDisLineaDeCred((datosCredito.getDisLineaDeCred()==null) ? "" : "");
							datosCredito.setTotalPagar((datosCredito.getTotalPagar()==null)? "" : "");
							datosCredito.setFechaLimite((datosCredito.getFechaLimite()==null) ? "" : "");
							
							listadatosCredito.add(datosCredito);
						}
						response.setDatosCredito(listadatosCredito);
					}else{
						apePuesto.setESTATUS(-1l);
						response.setDatos(apePuesto);
					}
					
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
