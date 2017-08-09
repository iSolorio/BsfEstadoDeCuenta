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

import org.assertj.core.util.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

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

	/* Metodo de conexion a la base de datos */
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

	/* metodo de insercion de unpdf a la base de datos */
	public void insertPDF(Connection conn, String filename, byte[] archivo) {
		int len;
		String query;
		PreparedStatement pstmt;

		try {
			PdfReader reader = new PdfReader(filename);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("compreso.pdf"), PdfWriter.VERSION_1_5);
			stamper.setFullCompression();
			stamper.close();
			File file = new File("compreso.pdf");
			System.out.println(file.length());
			FileInputStream fis = new FileInputStream(file);
			len = archivo.length;
			query = ("insert into EdoCtaArch VALUES(?,?,?,?)");
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, file.getName());
			pstmt.setString(2, "08/02/2017");
			pstmt.setString(3, "08/04/2017");
			pstmt.setBytes(4, archivo);

			// method to insert a stream of bytes
			// pstmt.setBinaryStream(4, fis, len);
			pstmt.executeUpdate();
			file.delete();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* metodo que sustrae los pdfs que se solicitan */
	public void getPDFData(Connection conn) {

		byte[] fileBytes;
		String query;

		try {
			PreparedStatement state;
			query = ("select archivo from EdoCtaArch where fechaDesde=? and fechaHasta=?");
			state = conn.prepareStatement(query);
			state = conn.prepareStatement(query);
			state.setString(1, "2");
			state.setString(2, "2");
			ResultSet rs = state.executeQuery();
			if (rs.next()) {
				fileBytes = rs.getBytes(1);
				OutputStream targetFile = new FileOutputStream("nuevo.pdf");
				targetFile.write(fileBytes);
				targetFile.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
