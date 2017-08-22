package mx.gob.bansefi.EstadoDeCuenta.Service;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.gob.bansefi.EstadoDeCuenta.utils.Util;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import mx.gob.bansefi.EstadoDeCuenta.DB.ManejoDB;
import mx.gob.bansefi.EstadoDeCuenta.dto.EstadoDeCuentaDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.ResponseDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.DatosCreditoDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class Altaservice {
	/*
	 * Inyeccion de dependencias
	 */
	@Autowired
	private ManejoDB manejodb;
	@Autowired
	private Util util;
	/*
	 * Definicion de variables de clase
	 */
	@Value("${msj.error.general.errorServicioCliente}")
	private String urlErrorServicioCliente;

	DataSource dataSource = null;
	ByteArrayOutputStream out = null;

	// ManejoDB db= new ManejoDB();
	public Altaservice() {
		out = new ByteArrayOutputStream();
	}

	/*
	 * Metodo que se encarga de hacer una consulta de datos en la base de datos,
	 * en caso de no encontrarlo hace un alta de estado de cuenta con el timbre
	 * del sat.
	 */
	@Async
	public ResponseDTO generacionReporte(EstadoDeCuentaDTO request) {
		if (this.dataSource == null) {
			dataSource = manejodb.getDataSource();
		}
		DatosCreditoDTO datosCred = new DatosCreditoDTO();
		datosCred = request.getResDatosCredito().getLista().get(0);
		datosCred.setRFC(request.getResDatosGral().getRFC());
		datosCred.setBIO_FECHA_INCIAL(request.getResDatosCredito().getLista().get(0).getFECHA_INCIAL());
		datosCred.setBIO_FECHA_FINAL(request.getResDatosCredito().getLista().get(0).getFECHA_FINAL());
		datosCred.setBIO_SALDO(request.getResDatosCredito().getLista().get(0).getSALDO());
		datosCred.setIO_FECHA_INCIAL(request.getResDatosCredito().getLista().get(0).getFECHA_INCIAL());
		datosCred.setIO_FECHA_FINAL(request.getResDatosCredito().getLista().get(0).getFECHA_FINAL());
		datosCred.setSALDO_FINAL(request.getResDatosCredito().getLista().get(0).getSALDO());
		datosCred.setTIPOCTA(request.getResDatosGral().getPRODUCTO());
		datosCred.setPI_REVI_DE_TASA(request.getResDatosCredito().getLista().get(0).getREVI_DE_TASA());
		datosCred.setFC_FECHA_FINAL(request.getResDatosCredito().getLista().get(0).getFECHA_FINAL());
		datosCred.setPC_FECHA_FINAL(request.getResDatosCredito().getLista().get(0).getFECHA_FINAL());
		datosCred.setFI_FECHA_FINAL(request.getResDatosCredito().getLista().get(0).getFECHA_FINAL());
		datosCred.setPI_FECHA_FINAL(request.getResDatosCredito().getLista().get(0).getFECHA_FINAL());
		datosCred.setPIV_FECHA_FINAL(request.getResDatosCredito().getLista().get(0).getFECHA_FINAL());
		datosCred.setFIV_FECHA_FINAL(request.getResDatosCredito().getLista().get(0).getFECHA_FINAL());
		datosCred.setPC_PAGO_CAPITAL(request.getResDatosCredito().getLista().get(0).getPAGO_CAPITAL());
		datosCred.setPI_PAGO_INTERES(request.getResDatosCredito().getLista().get(0).getPAGO_INTERES());
		datosCred.setFI_PAGO_IVA(request.getResDatosCredito().getLista().get(0).getPAGO_IVA());
		ResponseDTO res = new ResponseDTO();
		List<DatosCreditoDTO> lista = new ArrayList<DatosCreditoDTO>();
		lista.add(datosCred);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("operaciones", null);
		String id = request.getResDatosCredito().getLista().get(0).getCREDITO()
				+ request.getResDatosCredito().getLista().get(0).getFECHA_FINAL();
		String fechaInicio = request.getResDatosCredito().getLista().get(0).getFECHA_INCIAL();
		String fechaFin = request.getResDatosCredito().getLista().get(0).getFECHA_FINAL();
		String nomArch = request.getResDatosCredito().getLista().get(0).getCREDITO() + "_"
				+ request.getResDatosCredito().getLista().get(0).getNOMBRETO() + "_"
				+ request.getResDatosCredito().getLista().get(0).getFECHA_FINAL() + ".pdf";
		// String id = "1";

		try {
			Connection con = dataSource.getConnection();
			res = manejodb.getPDFData(con, "2012_12_03", "2013_02_03", nomArch);
			if (res.getMensajeInterno().equals("Vacio")) {
				JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("EstadoDeCuenta.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros,
						new JRBeanCollectionDataSource(lista));
				out.reset();
				JasperExportManager.exportReportToPdfStream(jasperPrint, out);

				res.setArchivo(out.toByteArray());
				JasperExportManager.exportReportToPdfFile(jasperPrint, nomArch);
				res.setMensajeInterno(manejodb.insertPDF(con, id, fechaInicio, fechaFin, res.getArchivo()));
				con.close();
			} else {
				if (res.getMensajeInterno().equals("El query no cumple con los requerimientos de la tabla")) {
					res.setStatus("0");
					return res;
				}
			}

		} catch (JRException | SQLException e) {
			// TODO Auto-generated catch block
			res.setMensajeInterno("Errores:" + e.getMessage());
		}
		System.out.println(res.getMensajeInterno());
		return res;

	}
}
