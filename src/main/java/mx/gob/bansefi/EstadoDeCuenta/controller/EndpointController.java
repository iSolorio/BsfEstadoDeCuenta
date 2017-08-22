package mx.gob.bansefi.EstadoDeCuenta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import mx.gob.bansefi.EstadoDeCuenta.Service.ConsultaService;
import mx.gob.bansefi.EstadoDeCuenta.dto.RequestGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.ResponseDTO;

@RequestMapping("")
@RestController
public class EndpointController {
	/*
	 * Inyeccion de dependencias
	 */
	@Autowired
	private ConsultaService consultaService;

	/*
	 * Endpoint principal
	 */
	@RequestMapping(value = "inicioEstadoDeCuenta", method = RequestMethod.POST)
	public ResponseDTO inicioEstadoDeCuenta(@RequestBody RequestGralDTO request) {
		return consultaService.principal(request, "2", "007607350", "1");
	}
}
