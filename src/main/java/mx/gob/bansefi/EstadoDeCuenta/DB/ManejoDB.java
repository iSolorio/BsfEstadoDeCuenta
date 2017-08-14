package mx.gob.bansefi.EstadoDeCuenta.DB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.castor.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import mx.gob.bansefi.EstadoDeCuenta.dto.ResponseDTO;
import mx.gob.bansefi.EstadoDeCuenta.utils.Util;

@Component
public class ManejoDB {

	@Autowired
	private Util util;
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

	private DataSource datasource;

	/*
	 * Este metodo inicializa el pool database
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

	/* metodo de insercion de unpdf a la base de datos */
	public String insertPDF(Connection conn, String id, byte[] archivo) {
		int len;
		String query;
		PreparedStatement pstmt;
		System.out.println("Hace insercion" + archivo.length);

		try {
			byte[] insercion = util.comprimir(archivo);
			len = 0;
			len = archivo.length;
			System.out.println("Tamaño del archivo:" + insercion.length);
			query = (urlQueryInsert);
			if (StringUtils.countOccurrencesOf(query, "?") == 4) {

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, id);
				pstmt.setString(2, "2017-02-05");
				pstmt.setString(3, "2017-04-05");
				pstmt.setBytes(4, insercion);
				System.out.println(pstmt);
				pstmt.executeUpdate();
				return "Exito en la operacion";

			} else {
				return "El query no cumple con los requerimientos especificados en la tabla";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Ocurrio un error durante el guardado";
	}

	/* metodo que sustrae los pdfs que se solicitan */
	public ResponseDTO getPDFData(Connection conn,String fechaDesde,String fechaHasta) {
		ResponseDTO res = new ResponseDTO();
		byte[] fileBytes;
		String query = urlQueryconsulta;
		System.out.println("Hace Consulta");

		try {
			if (StringUtils.countOccurrencesOf(query, "?") == 2) {
				PreparedStatement state;
				state = conn.prepareStatement(query);
				state = conn.prepareStatement(query);
				state.setString(1, fechaDesde);
				state.setString(2, fechaHasta);
				ResultSet rs = state.executeQuery();
				if (rs.next()) {
					fileBytes = rs.getBytes(1);
					fileBytes = util.decomprimir(fileBytes);
					OutputStream targetFile = new FileOutputStream("nuevo.pdf");
					targetFile.write(fileBytes);
					targetFile.close();
					res.setArchivo(fileBytes);
					res.setMensajeInterno("Encontrado");
				} else {
					res.setMensajeInterno("Vacio");
				}
			} else {
				res.setMensajeInterno("El query no cumple con los requerimientos de la tabla");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}