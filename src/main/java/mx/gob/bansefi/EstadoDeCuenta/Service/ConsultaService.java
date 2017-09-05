package mx.gob.bansefi.EstadoDeCuenta.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import mx.gob.bansefi.EstadoDeCuenta.Client.ConsultaClient;
import mx.gob.bansefi.EstadoDeCuenta.Service.Login.LoginService;
import mx.gob.bansefi.EstadoDeCuenta.dto.RequestAltaDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.EstadoDeCuentaDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.RequestGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.ResponseDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.ReqDatosCreditoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.ResDatosCreditoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosGral.CatalogoDatosGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosGral.ReqDatosGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosGral.ResDatosGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Login.LoginDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Login.ResAperturaPuestoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Logon.ResLogonDTO;

@Service
public class ConsultaService {
	/*
	 * Inyeccion de dependencias
	 */
	@Autowired
	private ConsultaClient consultaClient;
	@Autowired//or @Inject
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
	 * Metodo de inicio en la cual se verifica si el cliente ya se encuentra
	 * logeado, si este aun no lo esta se inicia sesion automaticamente tomando
	 * los datos del properties
	 */
	public ResDatosGralDTO creditos(ReqDatosGralDTO request) {
		ResDatosGralDTO response = null;
		LoginDTO login = new LoginDTO();
		ResLogonDTO resLogon = null;
		ReqDatosGralDTO datosGral = new ReqDatosGralDTO();
		datosGral.setIdInternoPe((request.getIdInternoPe() == null) ? "" : request.getIdInternoPe());
		datosGral.setEntidad((request.getEntidad() == null) ? "" : request.getEntidad());
		datosGral.setTerminal((request.getTerminal() == null) ? "" : request.getTerminal());
		datosGral.setAccion((request.getAccion() == null) ? "" : request.getAccion());
		datosGral.setIntegrante((request.getIntegrante() == null) ? "" : request.getIntegrante());
		datosGral.setNombre((request.getNombre() == null) ? "" : request.getNombre());
		datosGral.setApePa((request.getApePa() == null) ? "" : request.getApePa());
		datosGral.setApeMa((request.getApeMa() == null) ? "" : request.getApeMa());
		datosGral.setReferencia((request.getReferencia() == null) ? "" : request.getReferencia());
		datosGral.setRazon((request.getRazon() == null) ? "" : request.getRazon());
		try {
			if (!request.getUsuario().equals("") && !request.getPassword().equals("")) {
				datosGral.setUsuario((request.getUsuario() == null) ? "" : request.getUsuario());
				datosGral.setPassword((request.getPassword() == null) ? "" : request.getPassword());
				response = principalConsulta(datosGral);
			} else {
				login.setUsername((urlTcbUsername == null) ? "" : urlTcbUsername);
				login.setPassword((urlTcbPassword == null) ? "" : urlTcbPassword);
				resLogon = loginService.login(login);
				if (resLogon != null && resLogon.getESTATUS() == 0) {
					datosGral.setUsuario((urlTcbUsername == null) ? "" : urlTcbUsername);
					datosGral.setPassword((urlTcbPassword == null) ? "" : urlTcbPassword);
					response = principalConsulta(datosGral);
				} else {
					ResAperturaPuestoDTO mensaje= new ResAperturaPuestoDTO();
					mensaje.setMENSAJE("Ocurrio un error ");
					response.setDatos(mensaje);
				}
			}
		} catch (Exception e) {
			CatalogoDatosGralDTO catalogos = new CatalogoDatosGralDTO();
			ResAperturaPuestoDTO mensaje= new ResAperturaPuestoDTO();
			mensaje.setMENSAJE("Ocurrio un error :"+e.getMessage());
			response.setDatos(mensaje);
		}
		return response;
	}

	/*
	 * Metodo que realiza la consulta de los creditos de una persona
	 */
	public ResDatosGralDTO principalConsulta(ReqDatosGralDTO request) {
		ResDatosGralDTO response = new ResDatosGralDTO();
		try {
			if (!request.getUsuario().equals("") && !request.getPassword().equals("")) {
				response = consultaClient.consDatosGral(request);
			} else {
				System.out.println("Error");
			}
		} catch (Exception e) {

		}
		return response;
	}
	
	/*
	 * Metodo que realiza la consulta de los productos de los creditos de una persona
	 */
	public ResDatosCreditoDTO productos(ReqDatosCreditoDTO request) {
		ResDatosCreditoDTO response = null;
		try {
			if (!request.getUsuario().equals("") && !request.getPassword().equals("")) {
				response = consultaClient.consultaDatosCredito(request);
			} else {
				System.out.println("Error");
			}
		} catch (Exception e) {

		}
		return response;
	}
}
