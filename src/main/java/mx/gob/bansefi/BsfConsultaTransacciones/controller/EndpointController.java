package mx.gob.bansefi.BsfConsultaTransacciones.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.gob.bansefi.BsfConsultaTransacciones.DTO.ReqConsultaGeneralDTO;
import mx.gob.bansefi.BsfConsultaTransacciones.DTO.ResConsultaGeneralDTO;


@RequestMapping("")
@RestController
public class EndpointController {

	/*Endpoint para probar*/
	@RequestMapping(value ="prueba", method = RequestMethod.POST)
	public ResConsultaGeneralDTO   pruebal(@RequestBody ReqConsultaGeneralDTO request){
		
			ResConsultaGeneralDTO res= new ResConsultaGeneralDTO("nada");
			return res;
			
	}
}
