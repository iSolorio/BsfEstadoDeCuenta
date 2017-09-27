package mx.gob.bansefi.EstadoDeCuenta.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import mx.gob.bansefi.EstadoDeCuenta.Client.ConsultaClient;
import mx.gob.bansefi.EstadoDeCuenta.Service.Login.LoginService;
import mx.gob.bansefi.EstadoDeCuenta.dto.RequestAltaDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.EstadoDeCuentaDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.RequestGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.ResponseDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.DatosCreditoDTO;
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

	@Autowired // or @Inject
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
	@Value("${status.correcto}")
	private String statusCorrecto;

	/*
	 * Metodo de inicio en la cual se verifica si el cliente ya se encuentra
	 * logeado, si este aun no lo esta se inicia sesion automaticamente tomando los
	 * datos del properties
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
				if (resLogon != null && resLogon.getESTATUS().equals(statusCorrecto)) {
					datosGral.setUsuario((urlTcbUsername == null) ? "" : urlTcbUsername);
					datosGral.setPassword((urlTcbPassword == null) ? "" : urlTcbPassword);
					response = principalConsulta(datosGral);
				} else {
					ResAperturaPuestoDTO mensaje = new ResAperturaPuestoDTO();
					mensaje.setMENSAJE(urlErrorServicioCliente);
					response.setDatos(mensaje);
				}
			}
		} catch (Exception e) {
			CatalogoDatosGralDTO catalogos = new CatalogoDatosGralDTO();
			ResAperturaPuestoDTO mensaje = new ResAperturaPuestoDTO();
			mensaje.setMENSAJE(urlErrorServicioCliente + e.getMessage());
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
	 * Metodo que realiza la consulta de los productos de los creditos de una
	 * persona
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

	/* proceso batch, construccion */
	public void batch(byte[] archivo) {
		System.out.println("Entra");
		try {
			Path path = Paths.get("creditos.txt");
			archivo = Files.readAllBytes(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Optional<byte[]> opcion = Optional.of(archivo);
		List<String> lineas;
		ResDatosCreditoDTO creditos;
		if (opcion.isPresent()) {
			LoginDTO login = new LoginDTO();
			ResLogonDTO resLogon = null;
			ReqDatosGralDTO datosGral = new ReqDatosGralDTO();
			login.setUsername((urlTcbUsername == null) ? "" : urlTcbUsername);
			login.setPassword((urlTcbPassword == null) ? "" : urlTcbPassword);
			resLogon = loginService.login(login);
			@SuppressWarnings("resource")
			BufferedReader bfReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(archivo)));
			lineas = bfReader.lines().collect(Collectors.toList());
			for (String linea : lineas) {
				System.out.println(linea);
				linea=linea.replaceFirst("﻿", "");
				ReqDatosCreditoDTO cuenta = new ReqDatosCreditoDTO(urlTcbUsername, urlTcbPassword,
						linea, "1");
				creditos = productos(cuenta);
				System.out.println(linea);
				for(int i=0;i<creditos.getDatosCredito().size();i++)
				{
					altaservice.generacionReporte(creditos.getDatosCredito().get(i));
				}
			}
			

		}
	}
}
