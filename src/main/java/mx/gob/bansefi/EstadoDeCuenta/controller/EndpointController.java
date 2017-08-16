package mx.gob.bansefi.EstadoDeCuenta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.gob.bansefi.EstadoDeCuenta.Service.Altaservice;
import mx.gob.bansefi.EstadoDeCuenta.Service.Login.LoginService;
import mx.gob.bansefi.EstadoDeCuenta.dto.RequestGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.ResponseDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Login.LoginDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Login.ResAperturaPuestoDTO;

@RequestMapping("")
@RestController
public class EndpointController {
	@Autowired
	private Altaservice altaService;
	@Autowired
	private LoginService loginService;

	/*Endpoint para probar*/
	@RequestMapping(value ="consultaEstadoCuenta", method = RequestMethod.POST)
	public ResponseDTO pruebal(@RequestBody RequestGralDTO request){
			return altaService.generacionReporte(request);
	}
	
	/*Endpoint para probar*/
	@RequestMapping(value ="login", method = RequestMethod.POST)
	public ResAperturaPuestoDTO login(@RequestBody LoginDTO request){
		return loginService.login(request);	
	}
	
	/*Endpoint para probar*/
	@RequestMapping(value ="prueba", method = RequestMethod.POST)
	public ResponseDTO prueba(@RequestBody RequestGralDTO request){
			return altaService.principal(request);
	}
}
