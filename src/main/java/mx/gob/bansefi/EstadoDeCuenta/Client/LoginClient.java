package mx.gob.bansefi.EstadoDeCuenta.Client;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import mx.gob.bansefi.EstadoDeCuenta.dto.AperturaPuesto.ReqAperturaPuestoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Login.ResAperturaPuestoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Logon.CatalogoUsuarioDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Logon.ReqLogonDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Logon.ResLogonDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Logon.Usuario;
import mx.gob.bansefi.EstadoDeCuenta.utils.Util;

@Component
public class LoginClient {
	/*
	 * Definicion de variables de clase
	 */
	@Value("${rest.uri.wsBsfPlataformaPrincipal}")
	private String urlWsBsfPlataformaPrincipal;
	@Value("${rest.uri.wsBsfPlataformaPrincipal.login.logofnsf}")
	private String urlLoginLogofnsf;
	@Value("${rest.uri.wsBsfPlataformaPrincipal.login.aperturaPuesto}")
	private String urlLoginAperturaPuesto;
	/*
	 * Inyeccion de loginClient.
	 */
	@Autowired
	private Util util;
	/*
	 * Metodo para consumir servicio Logon de RACF y obtener permisos del
	 * usuario.
	 */
	public ResLogonDTO logon(ReqLogonDTO req) {
		ResLogonDTO res = null;
		try {
			String jsonRes = this.util.callRestPost(req, urlWsBsfPlataformaPrincipal + urlLoginLogofnsf);
			res = new ResLogonDTO();
			if (!jsonRes.equals("")) {
				ArrayList<String> nodos = new ArrayList<String>();
				nodos.add("EjecutarResponse");
				nodos.add("EjecutarResult");
				res = (ResLogonDTO) this.util.jsonToObject(res, jsonRes, nodos);
				if (res.getESTATUS() == 0) {
					nodos.add("ResponseBansefi");
					CatalogoUsuarioDTO catalogo = new CatalogoUsuarioDTO(); 
                	Usuario usuario = new Usuario();
                	catalogo = (CatalogoUsuarioDTO) this.util.jsonToObject(catalogo, jsonRes, nodos);
                	usuario.setUSUARIO(catalogo.getResponseBansefi().get(0).getUSUARIO());
                	usuario.setNOMBRE(catalogo.getResponseBansefi().get(0).getNOMBRE());
                	usuario.setENTIDAD(catalogo.getResponseBansefi().get(0).getENTIDAD());
                	usuario.setCENTRO(catalogo.getResponseBansefi().get(0).getCENTRO());
                	usuario.setVENTANILLA(catalogo.getResponseBansefi().get(0).getVENTANILLA());
                	res.setUsuario(usuario);
				}
			} else {
				res.setESTATUS(-1);
				res.setMENSAJE("Ocurrio un error al consumir el servicio|logon(ReqLogonDTO req)");
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo para consumir servicio apertura de puesto de UANL.
	 */
	public ResAperturaPuestoDTO aperturaPuesto(ReqAperturaPuestoDTO req) {
		ResAperturaPuestoDTO res = null;
		try {
			String jsonRes = this.util.callRestPost(req, urlWsBsfPlataformaPrincipal+urlLoginAperturaPuesto);
			res = new ResAperturaPuestoDTO();
			if (!jsonRes.equals("")) {
				ArrayList<String> nodos = new ArrayList<String>();
				nodos.add("EjecutarResponse");
				nodos.add("EjecutarResult");

				/*
				 * Se genera manualmente el DTO de response debido a que son
				 * demasiados datos que no se necesitan para el proceso de
				 * login.
				 */
				Gson gson = new Gson();
				JSONParser parser = new JSONParser();
				Object objRes = parser.parse(jsonRes);
				JSONObject jsonObject = (JSONObject) objRes;
				for (String nodo : nodos) {
					jsonObject = (JSONObject) jsonObject.get(nodo);
				}
				res.setCODIGO((String) jsonObject.get("CODIGO"));
				res.setESTATUS((Long) jsonObject.get("ESTATUS"));
				res.setNUMTASK((String) jsonObject.get("NUMTASK"));
				res.setMENSAJE((String) jsonObject.get("MENSAJE"));
				res.setTRANID((String) jsonObject.get("TRANID"));
			} else {
				res.setESTATUS(-1l);
				res.setMENSAJE("Ocurrio un error al consumir el servicio|aperturaPuesto(ReqAperturaPuestoDTO req)");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return res;
	}
}
