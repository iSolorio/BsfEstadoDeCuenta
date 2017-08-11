package mx.gob.bansefi.EstadoDeCuenta.DB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.castor.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import mx.gob.bansefi.EstadoDeCuenta.dto.ResponseDTO;
import mx.gob.bansefi.EstadoDeCuenta.utils.Util;

@Component
public class ManejoDB {
	@Value("${database.usuario}")
	private  String databaseUsuario;
	@Value("${database.password}")
	private  String databasePassword;
	@Value("${database.name}")
	private String databaseName;
	@Value("${database.url}")
	private   String urlDatabase;
	@Value("${database.pool}")
	private  int databasePool;
	@Value("${query.insercion}")
	private static String queryInsercion;
	@Value("${query.consulta}")
	private static String queryConsulta;
	
	
	private DataSource datasource;

	@Autowired
	private Util util;
	/*Este metodo inicializa el pool database*/
	public  DataSource getDataSource()
    {
			
            if(datasource == null)
            {
                HikariConfig config = new HikariConfig();
	            config.setJdbcUrl( urlDatabase);
	            config.setUsername(databaseUsuario);
	            config.setPassword(databasePassword);
	            config.setMaximumPoolSize(100);
	            config.setAutoCommit(false);
	            config.addDataSourceProperty("cachePrepStmts", "true");
	            config.addDataSourceProperty("prepStmtCacheSize", "250");
	            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
	            datasource = new HikariDataSource(config);
            }

            return datasource;

    }


	/* metodo de insercion de unpdf a la base de datos */
	public String insertPDF(Connection conn, String id, byte[] archivo) {
		int len;
		String query;
		PreparedStatement pstmt;

		try {
			archivo=util.comprimir(archivo);
			len = archivo.length;
			System.out.println("Tamaño del archivo:"+len);
			query = (queryInsercion);
			if(StringUtils.countOccurrencesOf(queryInsercion, "?")<4)
			{
				
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, id);
				pstmt.setString(2, "09/02/2017");
				pstmt.setString(3, "09/04/2017");
				pstmt.setBytes(4, archivo);
				pstmt.executeUpdate();
				return "Exito en la operacion";
			}
			else
			{
				return "El query no cumple con los requerimientos especificados en la tabla";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Ocurrio un error durante el guardado";
	}


	
	
	
	
	/*metodo que sustrae los pdfs que se solicitan*/
	 public ResponseDTO getPDFData(Connection conn) {
         ResponseDTO res=new ResponseDTO();
	        byte[] fileBytes;
	        String query=queryConsulta;
	       
	         
	        try {
	        	if(StringUtils.countOccurrencesOf(query, "?")<2) 
	        	{
					PreparedStatement state;
					state = conn.prepareStatement(query);
					state = conn.prepareStatement(query);
					state.setString(1, "09/02/2017");
					state.setString(2, "09/04/2017");
					ResultSet rs = state.executeQuery();
					if (rs.next()) 
					{
						fileBytes = rs.getBytes(1);
						fileBytes = util.decomprimir(fileBytes);
						OutputStream targetFile = new FileOutputStream("nuevo.pdf");
						targetFile.write(fileBytes);
						targetFile.close();
						res.setArchivo( fileBytes);
						res.setMensajeInterno("Encontrado");
			        }
					else
					{
						res.setMensajeInterno("Vacio");
					}
	        	}
	        	else
	        	{
	        		res.setMensajeInterno("El query no cumple con los requerimientos de la tabla");
	        	}
	        	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }



}
