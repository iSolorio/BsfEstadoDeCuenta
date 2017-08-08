package mx.gob.bansefi.EstadoDeCuenta.DB;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ManejoDB {
	@Value("${database.usuario}")
	private String databaseUsuario;
	@Value("${database.password}")
	private String databasePassword;
	@Value("${database.name}")
	private String databaseName;
	@Value("${url.database}")
	private String urlDatabase;
	/*Metodo de conexion a la base de datos*/
	 public Connection dbConnect(String db_connect_string,
	            String db_userid, String db_password) {
	        try {
	            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            Connection conn = DriverManager.getConnection(
	                    db_connect_string, db_userid, db_password);
	             
	            System.out.println("connected");
	            return conn;
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	 public void insertPDF(Connection conn,String filename) {
	        int len;
	        String query;
	        PreparedStatement pstmt;
	         
	        try {
	            File file = new File(filename);
	            FileInputStream fis = new FileInputStream(file);
	            len = (int)file.length();
	            query = ("insert into TableImage VALUES(?,?,?)");
	            pstmt = conn.prepareStatement(query);
	            pstmt.setString(1,file.getName());
	            pstmt.setInt(2, len);
	             
	            //method to insert a stream of bytes
	            pstmt.setBinaryStream(3, fis, len); 
	            pstmt.executeUpdate();
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
