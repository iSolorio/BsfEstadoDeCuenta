package mx.gob.bansefi.EstadoDeCuenta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.gob.bansefi.EstadoDeCuenta.Service.Altaservice;
import mx.gob.bansefi.EstadoDeCuenta.dto.RequestGralDTO;

@RequestMapping("")
@RestController
public class EndpointController {
	@Autowired
	private Altaservice altaService;
	
	/*Endpoint para probar*/
	@RequestMapping(value ="consultaEstadoCuenta", method = RequestMethod.POST)
	public void pruebal(@RequestBody RequestGralDTO request){
		
			altaService.altaEstado(request);
	}
}
