package mx.gob.bansefi.EstadoDeCuenta.controller;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.gob.bansefi.EstadoDeCuenta.Service.Altaservice;
import mx.gob.bansefi.EstadoDeCuenta.Service.ConsultaService;
import mx.gob.bansefi.EstadoDeCuenta.dto.RequestAltaDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.EstadoDeCuentaDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.RequestGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.ResponseDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.ReqDatosCreditoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.ResDatosCreditoDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosGral.ReqDatosGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosGral.ResDatosGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.batch.ReqBatchDTO;

@RequestMapping("")
@RestController
public class EndpointController {
	/*
	 * Inyeccion de dependencias
	 */
	@Autowired
	private ConsultaService consultaService;
	@Autowired //or @Inject
	private Altaservice altaservice;
	
	/*
	 * Endpoint de consukta de creditos
	 */
	@RequestMapping(value = "consultaCreditos", method = RequestMethod.POST)
	public ResDatosGralDTO consultaCreditos(@RequestBody ReqDatosGralDTO request) {
		return consultaService.creditos(request);
	}
	
	/*
	 * Endpoint de consukta de productos
	 */
	@RequestMapping(value = "consultaProducto", method = RequestMethod.POST)
	public ResDatosCreditoDTO consultaProducto(@RequestBody ReqDatosCreditoDTO request) {
		return consultaService.productos(request);
	}
	
	/*
	 * Endpoint de busqueda del periodo en la base de datos y en caso de no encontrarlo lo registra
	 */
	@RequestMapping(value = "consultaAltaEdoCuenta", method = RequestMethod.POST)
	public ResponseDTO consultaAltaEdoCuenta(@RequestBody RequestAltaDTO request) {
			return altaservice.generacionReporte(request);
	}
	@RequestMapping(value="batch",method=RequestMethod.POST)
	public void batch(/*@RequestBody*/ ReqBatchDTO request)
	{
		consultaService.batch(null);
	}
}
