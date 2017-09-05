package mx.gob.bansefi.EstadoDeCuenta.Service;

import java.util.concurrent.Future;

import mx.gob.bansefi.EstadoDeCuenta.dto.RequestAltaDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.ResponseDTO;

public interface GeneracionReporte {
	Future<ResponseDTO> generacionReporte(RequestAltaDTO request);
}
