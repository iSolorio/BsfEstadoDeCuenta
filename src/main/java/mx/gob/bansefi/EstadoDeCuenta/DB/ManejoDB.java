package mx.gob.bansefi.EstadoDeCuenta.DB;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import mx.gob.bansefi.EstadoDeCuenta.dto.ResponseDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DB.ReqInsercionDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DB.ReqSeleccionDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DB.ResInsercionDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DB.ResSeleccionDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DB.EstadoCuentaDTO;
import mx.gob.bansefi.EstadoDeCuenta.dto.DB.ReporteDTO;
import mx.gob.bansefi.EstadoDeCuenta.utils.Util;

@Component
public class ManejoDB {
	/*
	 * Inyeccion de dependencias
	 */
	@SuppressWarnings("rawtypes")
	@Autowired
	private Util util;
	/*
	 * Definicion de variables de clase
	 */
	@Value("${database.url}")
	private String urlDatabase;
	@Value("${database.name}")
	private String databaseName;
	@Value("${database.usuario}")
	private String databaseUsuario;
	@Value("${database.password}")
	private String databasePassword;
	@Value("${query.insert}")
	private String urlQueryInsert;
	@Value("${query.consulta}")
	private String urlQueryconsulta;
	@Value("${database.pool}")
	private Integer urlMaximumpoolsize;
	@Value("${url.InsertaEstadoCuenta}")
	private String urlInsertaEstadoCuenta;
	@Value("${url.SeleccionEstadoCuenta}")
	private String urlSeleccionEstadoCuenta;
	@Value("${msj.error.bdInsercion}")
	private String errorbdInsercion;
	@Value("${msj.error.bdSeleccion}")
	private String errorbdSeleccion;
	@Value("${status.mensaje}")
	private String statusMensaje;
	@Value("${status.correcto}")
	private String statusCorrecto;
	@Value("${msj.Encontrado}")
	private String encontrado;
	@Value("${msj.Vacio}")
	private String vacio;
	@Value("${msj.error.general.errorServicioCliente}")
	private String errorGeneral;
	@Value("${tcb.InsertaEstadosCuentaResp}")
	private String InsertaEstadosCuentaResp;
	@Value("${tcb.ReporteInsertaEstadoCuenta}")
	private String ReporteInsertaEstadoCuenta;
	@Value("${tcb.EstadoCuenta}")
	private String nodoEstadoCuenta;
	@Value("${tcb.Reporte}")
	private String Reporte;

	private DataSource datasource;

	/*
	 * Metodo que inicializa el pool database
	 * https://stackoverflow.com/questions/43096192/spring-boot-application-
	 * properties-not-loaded
	 */
	public ManejoDB(@Value("${database.url}") String urlDatabase, @Value("${database.name}") String databaseName,
			@Value("${database.usuario}") String databaseUsuario,
			@Value("${database.password}") String databasePassword,
			@Value("${database.pool}") String urlMaximumpoolsize, @Value("${query.insert}") String urlQueryInsert,
			@Value("${query.consulta}") String urlQueryconsulta) {
		this.urlDatabase = urlDatabase;
		this.databaseName = databaseName;
		this.databaseUsuario = databaseUsuario;
		this.databasePassword = databasePassword;
		this.urlMaximumpoolsize = Integer.parseInt(urlMaximumpoolsize);
		this.urlQueryInsert = urlQueryInsert;
		this.urlQueryconsulta = urlQueryconsulta;
		if (datasource == null) {
			try {
				HikariConfig config = new HikariConfig();
				config.setJdbcUrl(urlDatabase);
				config.setUsername(databaseUsuario);
				config.setPassword(databasePassword);
				config.setMaximumPoolSize(Integer.parseInt(urlMaximumpoolsize));
				config.setAutoCommit(true);
				config.addDataSourceProperty("cachePrepStmts", "true");
				config.addDataSourceProperty("prepStmtCacheSize", "250");
				config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
				datasource = new HikariDataSource(config);
			} catch (Exception e) {

			}
		}

	}

	public DataSource getDataSource() {
		return datasource;
	}

	/* Metodo de insercion de un pdf a la base de datos */
	public String insertPDF(Connection conn, String id, String fechaInicio, String fechaFin, byte[] archivo, String usuario, String terminal, int tipo) {
		int len = 0;
		String query;
		String message = "";
		PreparedStatement pstmt;
		System.out.println("Hace insercion: " + archivo.length);
		try {
			byte[] insercion = util.comprimir(archivo);
			len = archivo.length;
			System.out.println("Tamaño del archivo: " + insercion.length);
			query = (urlQueryInsert);
			if (StringUtils.countOccurrencesOf(query, "?") == 8) {

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, id);
				pstmt.setString(2, fechaInicio);
				pstmt.setString(3, fechaFin);
				pstmt.setBytes(4, insercion);
				pstmt.setString(5, "bimestral");
				pstmt.setString(6, usuario);
				pstmt.setString(7, terminal);
				pstmt.setInt(8, tipo);
				System.out.println(pstmt);
				pstmt.executeUpdate();
				message = statusMensaje;

			} else {
				message = errorGeneral+errorbdInsercion;
			}
		} catch (Exception e) {
			e.printStackTrace();
			message =errorGeneral+errorbdInsercion;
		}
		return message;
	}
	@SuppressWarnings({ "static-access", "unchecked" })
	public String insertPDF(String id, String fechaInicio, String fechaFin, byte[] archivo, String usuario, String terminal, int tipo) {
		
		String message = "";
		String jsonResp;
		try {
			byte[] insercion = util.comprimir(archivo);
			System.out.println("Hace insercion: " + insercion.length);
			ReqInsercionDTO request= new ReqInsercionDTO(id, fechaInicio, fechaFin, insercion, "", usuario, terminal, tipo);
			ResInsercionDTO response=new ResInsercionDTO();
			jsonResp=util.callRestPost(request, urlInsertaEstadoCuenta);
			if(!jsonResp.equals(""))
			{	
				ArrayList<String> nodos = new ArrayList<String>();
				nodos.add(InsertaEstadosCuentaResp);
				nodos.add(ReporteInsertaEstadoCuenta);
				response=(ResInsercionDTO) util.jsonToObject(response, jsonResp, nodos);
				if(response.getResultado()==1)
				{
					message = statusMensaje;
				}
				else
				{
					message =errorGeneral+errorbdInsercion;
				}
			}
			else
			{
				message =errorGeneral+errorbdInsercion;
			}
			System.out.println("Tamaño del archivo: " + insercion.length);
		} catch (Exception e) {
			e.printStackTrace();
			message =errorGeneral+errorbdInsercion;
		}
		return message;
	}

	/* Metodo que sustrae los pdfs que se solicitan */
	public ResponseDTO getPDFData(Connection conn, String numSecAc, String fechaDesde, String fechaHasta, String nomArch) {
		ResponseDTO res = new ResponseDTO();
		byte[] fileBytes;
		String query = urlQueryconsulta;
		try {
			if (StringUtils.countOccurrencesOf(query, "?") == 3) {
				PreparedStatement state;
				System.out.println("entra");
				state = conn.prepareStatement(query);
				//state = conn.prepareStatement(query);
				state.setString(1, numSecAc);
				state.setString(2, fechaDesde);
				state.setString(3, fechaHasta);
				ResultSet rs = state.executeQuery();
				if (rs.next()) {
					fileBytes = rs.getBytes(1);
					fileBytes = util.decomprimir(fileBytes);
					OutputStream targetFile = new FileOutputStream(nomArch);
					targetFile.write(fileBytes);
					targetFile.close();
					res.setArchivo(fileBytes);
					res.setMensajeInterno(encontrado);
					res.setStatus(statusCorrecto);
				} else {
					res.setMensajeInterno(vacio);
				}
			} else {
				res.setMensajeInterno(errorGeneral+errorbdSeleccion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	@SuppressWarnings({ "unchecked", "static-access" })
	public ResponseDTO getPDFData( String numSecAc, String fechaDesde, String fechaHasta, String nomArch) {
		ResponseDTO res = new ResponseDTO();
		byte[] fileBytes;
		String jsonResp;
		ReqSeleccionDTO request= new ReqSeleccionDTO(numSecAc, fechaDesde, fechaHasta);
		EstadoCuentaDTO EstadoCuenta= new EstadoCuentaDTO();
		ResSeleccionDTO response= new ResSeleccionDTO();
		try {
				jsonResp=util.callRestPost(request, urlSeleccionEstadoCuenta);
				if (!jsonResp.equals("")) {
					ArrayList<String> nodos = new ArrayList<String>();
					nodos.add(this.nodoEstadoCuenta);
					nodos.add(Reporte);
					response=(ResSeleccionDTO) util.jsonToObject(response, jsonResp, nodos);//Es esta linea la que da problemas
					if(Objects.nonNull(response.getArchivo()))
					{
						fileBytes = util.decomprimir(response.getArchivo().get(0));
						OutputStream targetFile = new FileOutputStream(nomArch);
						targetFile.write(fileBytes);
						targetFile.close();
						res.setArchivo(fileBytes);
						res.setMensajeInterno(encontrado);
						res.setStatus(statusCorrecto);
					}
					else {
						res.setMensajeInterno(vacio);
					}
				} else {
					res.setMensajeInterno(vacio);
				}
		} catch (Exception e) {
			e.printStackTrace();
			res.setMensajeInterno(vacio);
		}
		return res;
	}
}