package mx.gob.bansefi.EstadoDeCuenta.utils;

import org.springframework.stereotype.Component;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.google.gson.Gson;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Component
public final class Util<T> {

	/*
	 * Definicion de variables de clase
	 */

	/*
	 * Metodo utilitario para convertir un json a un objeto.
	 */
	public Object jsonToObject(T objectRes, String json, ArrayList<String> nodos) throws ParseException {
		Gson gson = new Gson();
		JSONParser parser = new JSONParser();
		Object objRes = parser.parse(json);
		JSONObject jsonObject = (JSONObject) objRes;
		for (String nodo : nodos) {
			jsonObject = (JSONObject) jsonObject.get(nodo);
		}
		String jsonResponse = jsonObject.toJSONString();
		System.out.println(jsonResponse);
		try {
			return gson.fromJson(jsonResponse, ((T) objectRes).getClass());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Metodo utilitario para convertir un objeto a un json.
	 */
	public String objectToJson(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object);
	}

	/*
	 * Metodo utilitario para realizar llamada REST por el metodo POST
	 */
	public String callRestPost(Object obj, String uri) {
		String output = "";
		try {
			String input = objectToJson(obj);
			URL restServiceURL = new URL(uri);
			HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();
			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
			}
			BufferedReader responseBuffer = new BufferedReader(
					new InputStreamReader((httpConnection.getInputStream()), "UTF8"));
			String outputline;
			while ((outputline = responseBuffer.readLine()) != null) {
				output += outputline;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return output;
	}

    /*Metodo de compresion  de  byte array para el guardado en la base de datos*/
    public static byte[] comprimir(byte[] data) throws IOException {  
    	   Deflater deflater = new Deflater();  
    	   deflater.setInput(data);  
    	   ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);   
    	   deflater.finish();  
    	   byte[] buffer = new byte[1024];   
    	   while (!deflater.finished()) {  
    	    int count = deflater.deflate(buffer);
    	    outputStream.write(buffer, 0, count);   
    	   }  
    	   outputStream.close();  
    	   byte[] output = outputStream.toByteArray();  
    	   return output;  
    	  }
    /*Metodo de descompresiond e un byte array para la consulta de la base de datos*/
    public static byte[] decomprimir(byte[] data) throws IOException, DataFormatException {  
    	   Inflater inflater = new Inflater();   
    	   inflater.setInput(data);  
    	   ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);  
    	   byte[] buffer = new byte[1024];  
    	   while (!inflater.finished()) {  
    	    int count = inflater.inflate(buffer);  
    	    outputStream.write(buffer, 0, count);  
    	   }  
    	   outputStream.close();  
    	   byte[] output = outputStream.toByteArray();  
    	   return output;  
    	  }  
   


}
