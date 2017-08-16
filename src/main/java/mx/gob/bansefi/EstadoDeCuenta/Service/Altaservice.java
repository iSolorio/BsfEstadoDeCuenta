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
import mx.gob.bansefi.EstadoDeCuenta.Service.Login.LoginService;
import mx.gob.bansefi.EstadoDeCuenta.dto.OperacionesDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.RequestGralDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.ResponseDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.jasperDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Login.LoginDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.Login.ResAperturaPuestoDTO;
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
	private LoginService loginService;
	@Autowired
	private Util util;
	/*
	 * Definicion de variables de clase
	 */
	@Value("${tcb.Username}")
	private String urlTcbUsername;
	@Value("${tcb.Password}")
	private String urlTcbPassword;

	DataSource dataSource = null;
	ByteArrayOutputStream out = null;

	// ManejoDB db= new ManejoDB();
	public Altaservice() {
		out = new ByteArrayOutputStream();
	}
	
	public ResponseDTO principal(RequestGralDTO request){
		ResponseDTO aaa = null;
		LoginDTO loginDTO = new LoginDTO();
		try{
			if(request != null && !request.getUsuario().equals("")){
				aaa = generacionReporte(request);
			}else{
				System.out.println(urlTcbUsername+", "+urlTcbPassword);
			}
		}catch(Exception e){
			
		}
		return aaa;
	}

	/*
	 * Metodo que se encarga de hacer una consulta de datos en la base de datos,
	 * en caso de no encontrarlo hace un alta de estado de cuenta con el timbre
	 * del sat.
	 */
	@Async
	public ResponseDTO generacionReporte(RequestGralDTO request) {
		if (this.dataSource == null) {
			dataSource = manejodb.getDataSource();
		}
		// String jsonRes =
		// this.util.callRestPost(reqAltaAccionistaFuncionarioDTO,
		// rootContext+urlAltaAccionistaFuncionarios);
		ResponseDTO res = new ResponseDTO();
		OperacionesDTO op = new OperacionesDTO("08/04/2017", "facturacion capital", "125.88", "");
		OperacionesDTO op2 = new OperacionesDTO("08/04/2017", "pago de capital", "", "125.88");
		ArrayList<OperacionesDTO> aaa = new ArrayList<OperacionesDTO>();
		aaa.add(op);
		aaa.add(op2);
		ArrayList<jasperDTO> datos = new ArrayList<jasperDTO>();
		List<jasperDTO> lista = new ArrayList<jasperDTO>();
		lista.add(new jasperDTO("Juan Carlos", "Sta Rosalia", "ABCD123456ABCV1", "08/02/2017", "08/04/2017", "59",
				"08/04/2017", "$2384.14", "$0.00", "3", "$165.25", "$185.15", "08/02/2017", "08/04/2017", "$2384",
				"08/02/2017", "08/04/2017", "$30.03", "$0.00", "$0.00", "$0.84", "$2384", "CREDITO MAS AHORRO",
				"0349176172", "1003 Sucursal Empresarial", "40.00%", "Pesos Mexicanos", "$2500", "$0.00", "08/12/2019",
				"009.990%", "00.00%", "PERIODOS REALIES", "PERIODOS RELAES", "$0.00", "08/04/2017", "", "", "", "",
				""));

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("operaciones", aaa);
		String id = "3";

		try {
			Connection con = dataSource.getConnection();
			res=manejodb.getPDFData(con,"2017-02-05","2017-04-05");
			if(res.getMensajeInterno().equals("Vacio"))
			{
				JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("EstadoDeCuenta.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros,
						new JRBeanCollectionDataSource(lista));
				out.reset();
				JasperExportManager.exportReportToPdfStream(jasperPrint, out);

				res.setArchivo(out.toByteArray());
				JasperExportManager.exportReportToPdfFile(jasperPrint,
						"llenado.pdf");
				res.setMensajeInterno(manejodb.insertPDF(con, id, res.getArchivo()));
				con.close();
			}
			else
			{
				if(res.getMensajeInterno().equals("El query no cumple con los requerimientos de la tabla"))
				{
					res.setStatus("0");
					return res;
				}
				
			}

		} catch (JRException | SQLException e) {
			// TODO Auto-generated catch block
			res.setMensajeInterno("Errores:"+e.getMessage());
		}
		System.out.println(res.getMensajeInterno());
		return res;

		}
}
