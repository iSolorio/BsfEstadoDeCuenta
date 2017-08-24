package mx.gob.bansefi.EstadoDeCuenta.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.gob.bansefi.EstadoDeCuenta.Client.ConsultaClient;
import mx.gob.bansefi.EstadoDeCuenta.Service.Login.LoginService;
import mx.gob.bansefi.EstadoDeCuenta.dto.EstadoDeCuentaDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.RequestGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.ResponseDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.ReqDatosCreditoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.ResDatosCreditoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosGral.ReqDatosGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosGral.ResDatosGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Login.LoginDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Logon.ResLogonDTO;

@Service
public class ConsultaService {
	/*
	 * Inyeccion de dependencias
	 */
	@Autowired
	private ConsultaClient consultaClient;
	@Autowired
	private Altaservice altaservice;
	@Autowired
	private LoginService loginService;
	/*
	 * Definicion de variables de clase
	 */
	@Value("${tcb.Username}")
	private String urlTcbUsername;
	@Value("${tcb.Password}")
	private String urlTcbPassword;
	@Value("${msj.error.general.errorServicioCliente}")
	private String urlErrorServicioCliente;

	/*
	 * Metodo principal en la cual se verifica si el cliente ya se encuentra
	 * logeado, si este aun no lo esta se inicia sesion automaticamente tomando
	 * los datos del properties
	 */
	public ResponseDTO principal(RequestGralDTO request, String accion, String integrante, String referencia) {
		EstadoDeCuentaDTO datos = null;
		ResponseDTO response = null;
		ReqDatosGralDTO datosGral = new ReqDatosGralDTO();
		LoginDTO login = new LoginDTO();
		ResLogonDTO resLogon = null;
		try {
			if (request != null && !request.getUsuario().equals("") && !request.getPassword().equals("")) {
				datosGral.setUsuario(request.getUsuario());
				datosGral.setPassword(request.getPassword());
				datosGral.setAccion(accion);
				datosGral.setIntegrante(integrante);
				datosGral.setIdInternoPe("");
				datosGral.setApePa("");
				datosGral.setApeMa("");
				datosGral.setNombre("");
				datosGral.setRazon("");
				datosGral.setReferencia(referencia);
				datos = principalConsulta(datosGral);
				if (datos != null && datos.getResDatosGral().getDatos().getESTATUS() == 0
						&& datos.getResDatosCredito().getDatos().getESTATUS() == 0) {
					response = altaservice.generacionReporte(datos);
				} else {
					response.setStatus("-1");
					response.setMensajeInterno(urlErrorServicioCliente+"public ResponseDTO generacionReporte(EstadoDeCuentaDTO request)");
				}
			} else {
				login.setUsername(urlTcbUsername);
				login.setPassword(urlTcbPassword);
				resLogon = loginService.login(login);
				if(resLogon != null && resLogon.getESTATUS() == 0){
					datosGral.setUsuario(urlTcbUsername);
					datosGral.setPassword(urlTcbPassword);
					datosGral.setAccion(accion);
					datosGral.setIntegrante(integrante);
					datosGral.setIdInternoPe("");
					datosGral.setApePa("");
					datosGral.setApeMa("");
					datosGral.setNombre("");
					datosGral.setRazon("");
					datosGral.setReferencia(referencia);
					datos = principalConsulta(datosGral);
					if (datos != null && datos.getResDatosGral().getDatos().getESTATUS() == 0
							&& datos.getResDatosCredito().getDatos().getESTATUS() == 0) {
						response = altaservice.generacionReporte(datos);
					} else {
						response.setStatus("-1");
						response.setMensajeInterno(urlErrorServicioCliente+"public ResponseDTO generacionReporte(EstadoDeCuentaDTO request)");
					}
				}else{
					response.setStatus("-1");
					response.setMensajeInterno(urlErrorServicioCliente+"public ResLogonDTO login(LoginDTO loginDTO)");
				}
			}
		} catch (Exception e) {

		}
		return response;
	}

	/*
	 * Metodo principal que realiza el consumo del cliente de consultaClient
	 */
	public EstadoDeCuentaDTO principalConsulta(ReqDatosGralDTO request) {
		ResDatosGralDTO datosGral = null;
		ResDatosCreditoDTO datosCredito = null;
		EstadoDeCuentaDTO response = new EstadoDeCuentaDTO();
		ReqDatosCreditoDTO req = new ReqDatosCreditoDTO();
		try {
			if (request != null && !request.getUsuario().equals("") && !request.getPassword().equals("")) {
				datosGral = consultaClient.consDatosGral(request);
				if (datosGral != null && datosGral.getDatos().getESTATUS() == 0) {
					response.setResDatosGral(datosGral);
					req.setUsuario(request.getUsuario());
					req.setPassword(request.getPassword());
					req.setCredito(datosGral.getCREDITO());
					req.setReferencia(datosGral.getREFER());
					datosCredito = consultaClient.consultaDatosCredito(req);
					if (datosCredito != null && datosCredito.getDatos().getESTATUS() == 0) {
						response.setResDatosCredito(datosCredito);
					} else {
						
					}
				} else {

				}
			} else {
				
			}
		} catch (Exception e) {

		}
		return response;
	}
}
