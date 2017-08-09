package mx.gob.bansefi.EstadoDeCuenta.Service;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.gob.bansefi.EstadoDeCuenta.DB.ManejoDB;
import mx.gob.bansefi.EstadoDeCuenta.dto.RequestGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.jasperDTO;
import mx.gob.bansefi.EstadoDeCuenta.utils.Util;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class Altaservice {
	@Autowired
	private ManejoDB manejodb;
	@Autowired
	private Util util;
	@Value("${url.database}")
	private String urlDataBase;
	/* servicio basico de prueba */
	public static byte[] report;
	ByteArrayOutputStream out = new ByteArrayOutputStream();

	public void altaEstado(RequestGralDTO request) {

		System.out.println("Funciona");
		// util.callRestPost( request, uri);
		Connection con = manejodb.dbConnect();
		ArrayList<jasperDTO> datos = new ArrayList<jasperDTO>();
		List<jasperDTO> lista = new ArrayList<jasperDTO>();
		// lista.add(new jasperDTO("1", "2", "3"));
		lista.add(new jasperDTO("Juan Carlos", "Sta Rosalia", "ABCD123456ABCV1", "08/02/2017", "08/04/2017", "59", "08/04/2017", "$2384.14", "$0.00", "3", "$165.25", "$185.15", "08/02/2017", "08/04/2017", "$2384", "08/02/2017", "08/04/2017", "$30.03", "$0.00", "$0.00", "$0.84", "$2384",
				"CREDITO MAS AHORRO", "0349176172", "1003 Sucursal Empresarial", "40.00%", "Pesos Mexicanos", "$2500", "$0.00", "08/12/2019", "009.990%", "00.00%", "PERIODOS REALIES", "PERIODOS RELAES", "$0.00", "08/04/2017", "08/04/2017", "Facturacion capital", "$125.88", "", "", "", "", "", ""));

		Map<String, Object> parametros = new HashMap<String, Object>();
		// parametros.put("Descripcion",lista.get(0).getDescripcion());
		// datos.add(new jasperDTO("4","5","6"));
		try {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("EstadoDeCuenta.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros,
					new JRBeanCollectionDataSource(lista));
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);
			report = out.toByteArray();
			JasperExportManager.exportReportToPdfFile(jasperPrint,
					"C:\\Users\\appWhere\\Documents\\Proyecto\\BsfEstadoDeCuenta\\reporteejemplo.pdf");
			manejodb.insertPDF(con, "C:\\Users\\AppWhere\\Documents\\plantillaestadodecuenta.pdf", report);
			manejodb.getPDFData(con);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
