package mx.gob.bansefi.EstadoDeCuenta.DB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
	 public Connection dbConnect() {
	        try {
	            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            Connection conn = DriverManager.getConnection(urlDatabase, databaseUsuario, databasePassword);
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
	 public void getPDFData(Connection conn) {
         
	        byte[] fileBytes;
	        String query;
	        try {
	            query = 
	              "select data from tableimage where filename like '%.pdf%'";
	            Statement state = conn.createStatement();
	            ResultSet rs = state.executeQuery(query);
	            if (rs.next()) {
	                fileBytes = rs.getBytes(1);
	                OutputStream targetFile=  new FileOutputStream(
	                        "d://servlet//jdbc//newtest.pdf");
	                targetFile.write(fileBytes);
	                targetFile.close();
	            }
	             
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
