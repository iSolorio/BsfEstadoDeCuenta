package mx.gob.bansefi.EstadoDeCuenta.Service;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import mx.gob.bansefi.EstadoDeCuenta.utils.Util;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import mx.gob.bansefi.EstadoDeCuenta.DB.ManejoDB;
import mx.gob.bansefi.EstadoDeCuenta.dto.RequestAltaDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.ResponseDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class Altaservice  {
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
	@Value("${url.File.Jasper.Name}")
	private String urlFileJasperName;

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
	
	public ResponseDTO generacionReporte(RequestAltaDTO request)  {
		if (this.dataSource == null) {
			dataSource = manejodb.getDataSource();
		}
		ResponseDTO response = new ResponseDTO();
		ResponseDTO res = new ResponseDTO();
		List<RequestAltaDTO> lista = new ArrayList<RequestAltaDTO>();
		lista.add(request);
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("operaciones", null);
		String numSecAc = request.getCREDITO() +"_" + request.getFECHA_FINAL().replaceAll("/", "-");
		String fechaInicio = request.getFECHA_INCIAL().replaceAll("/", "-");
		String fechaFin = request.getFECHA_FINAL().replaceAll("/", "-");
		String nomArch = request.getCREDITO() + "_" + request.getNOMBRETO().replaceAll(" ", "") + "_"
				+ request.getFECHA_FINAL().replaceAll("/", "-") + ".pdf";
		
		try {
			Connection con = dataSource.getConnection();
			res = manejodb.getPDFData(con, numSecAc, fechaInicio, fechaFin, nomArch);
			if (res.getMensajeInterno().equals("Vacio")) {
				JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(urlFileJasperName);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(lista));
				out.reset();
				JasperExportManager.exportReportToPdfStream(jasperPrint, out);
				res.setArchivo(out.toByteArray());
				JasperExportManager.exportReportToPdfFile(jasperPrint, nomArch);
				res.setMensajeInterno(manejodb.insertPDF(con, numSecAc, fechaInicio, fechaFin, res.getArchivo(), request.getUsuario(), request.getTerminal(), 1));
				
			} else {
				if (res.getMensajeInterno().equals("El query no cumple con los requerimientos de la tabla")) {
					res.setStatus("0");
				}
				
			}
			con.close();
		} catch (JRException | SQLException e) {
			// TODO Auto-generated catch block
			res.setMensajeInterno("Errores:" + e.getMessage());
		}
		System.out.println("SS");
		
		return res;
	}
}
