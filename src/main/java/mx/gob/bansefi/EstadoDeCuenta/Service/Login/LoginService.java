package mx.gob.bansefi.EstadoDeCuenta.Service.Login;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import mx.gob.bansefi.EstadoDeCuenta.Client.LoginClient;
import mx.gob.bansefi.EstadoDeCuenta.dto.AperturaPuesto.AperturaPuestoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.AperturaPuesto.AperturaTRNDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.AperturaPuesto.ReqAperturaPuestoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.AperturaPuesto.STDDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.AperturaPuesto.TRDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Login.LoginDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Login.ResAperturaPuestoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Logon.LogonDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Logon.ReqLogonDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Logon.ResLogonDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Logon.Usuario;
import mx.gob.bansefi.EstadoDeCuenta.utils.Util;
@Service
public class LoginService {
	/*
	 * Inyeccion de loginClient.
	 */
	@Autowired
	private LoginClient loginClient;
	@Autowired
	private Util util;
	
	public ResAperturaPuestoDTO login(LoginDTO loginDTO) {
		/*
		 * Consume servicio Logon para obtener el usuario ingresado
		 */
		LogonDTO logonDTO = new LogonDTO(loginDTO.getUsername(), loginDTO.getPassword(), "", "127.0.0.1", "S");
		ReqLogonDTO reqLogon = new ReqLogonDTO(logonDTO);
		ResLogonDTO resLogon = loginClient.logon(reqLogon);

		/*
		 * Se realiza la apertura de puesto de UANL.
		 */
		ResAperturaPuestoDTO resAperturaPuesto = null;
		if (resLogon != null && resLogon.getESTATUS().equals(0)) {
			resAperturaPuesto = aperturaPuesto(resLogon.getUsuario(), loginDTO);
		}
		return resAperturaPuesto;
	}

	/*
	 * Consumo del servicio apertura puesto.
	 */
	public ResAperturaPuestoDTO aperturaPuesto(Usuario usuario, LoginDTO loginDTO) {
		ResAperturaPuestoDTO resAperturaPuesto = null;
		/*
		 * Se crea request para iniciar la peticion del servicio
		 */
		Date now = new Date();
		String fechaHora = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(now);
		TRDTO trdto = new TRDTO(fechaHora.substring(0, 10), fechaHora.substring(11, 19), fechaHora.substring(0, 10),
				fechaHora.substring(11, 19), usuario.getVENTANILLA().toString(), loginDTO.getUsername().toString(),
				usuario.getENTIDAD(), usuario.getENTIDAD(), usuario.getCENTRO());
		STDDTO stddto = new STDDTO(usuario.getVENTANILLA().toString(), loginDTO.getUsername().toString(), "0", "VPU20MOU");
		AperturaTRNDTO aperturaTRNDTO = new AperturaTRNDTO(stddto, trdto, loginDTO.getPassword(),
				loginDTO.getPassword());
		AperturaPuestoDTO aperturaPuesto = new AperturaPuestoDTO(loginDTO.getUsername(), loginDTO.getPassword(),
				"127.0.0.1", aperturaTRNDTO);
		ReqAperturaPuestoDTO reqAperturaPuesto = new ReqAperturaPuestoDTO(aperturaPuesto);
		

		/*
		 * Se ejecuta la llamada del servicio para aperturar puesto.
		 */
		resAperturaPuesto = loginClient.aperturaPuesto(reqAperturaPuesto);
		return resAperturaPuesto;
	}
}
