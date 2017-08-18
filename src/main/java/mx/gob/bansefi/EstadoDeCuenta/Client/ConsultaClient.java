package mx.gob.bansefi.EstadoDeCuenta.Client;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import mx.gob.bansefi.EstadoDeCuenta.dto.RequestGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.CatalogoDatosCreditoDTO;
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
			String cadena = jsonRes.replaceAll(" ", "");
			if (!jsonRes.equals("")) {
				ArrayList<String> nodos = new ArrayList<String>();
				nodos.add("EjecutarResponse");
				nodos.add("EjecutarResult");
				apePuesto = (ResAperturaPuestoDTO) this.util.jsonToObject(apePuesto, cadena, nodos);
				if (apePuesto.getESTATUS() == 0) {
					nodos.add("ResponseBansefi");
					nodos.add("ResponseBansefi");
					response = (ResDatosGralDTO) this.util.jsonToObject(response, cadena, nodos);
					response.setDatos(apePuesto);
				} else {
					apePuesto.setESTATUS(null);
				}

			} else {
				apePuesto.setESTATUS(null);
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
		try {
			String jsonRes = this.util.callRestPost(request, rootContext + urlConsultaCredito);
			String cadena = jsonRes.replaceAll("-", "_").replaceAll(" ", "");
			if (!cadena.equals("")) {
				ArrayList<String> nodos = new ArrayList<String>();
				nodos.add("EjecutarResponse");
				nodos.add("EjecutarResult");
				apePuesto = (ResAperturaPuestoDTO) this.util.jsonToObject(apePuesto, cadena, nodos);
				response.setDatos(apePuesto);
				if (apePuesto.getESTATUS() == 0) {
					nodos.add("ResponseBansefi");
					catalogo = (CatalogoDatosCreditoDTO) this.util.jsonToObject(catalogo, cadena, nodos);
					response.setLista(catalogo.getResponseBansefi());
				} else {
					apePuesto.setESTATUS(null);
				}

			} else {
				apePuesto.setESTATUS(null);
				apePuesto.setMENSAJE(urlErrorServicioCliente
						+ "public ResDatosCreditoDTO consultaDatosCredito(ReqDatosCreditoDTO request)");
			}
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		return response;
	}
}
