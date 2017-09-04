package mx.gob.bansefi.EstadoDeCuenta.utils;

import org.springframework.stereotype.Component;
import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.gob.bansefi.EstadoDeCuenta.dto.DatosCredito.DatosCreditoDTO;

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
    	byte[] output=null;
    	output = outputStream.toByteArray();  
    	return output;  
    }  
   
    /*
     * Metodo para eliminar ceros
     */
    public String removeZeros(String cadena)
	{
		String cadenaLimpia = "";
		if (cadena.contains(".")) {
			cadenaLimpia = String.valueOf(Float.valueOf(cadena));
		} else {
			cadenaLimpia = String.valueOf(Integer.parseInt(cadena));
		}
		return cadenaLimpia;
	}
    
    /*Metodo para  iterar datos, solo funciona con clases publicas*/
	public DatosCreditoDTO limpiar(DatosCreditoDTO datos) {
		DatosCreditoDTO myStuff = new DatosCreditoDTO();
		Field[] fields = myStuff.getClass().getDeclaredFields();
		for (Field f : fields) {
			Class t = f.getType();
			try {
				Object v = f.get(datos);
				f.set(datos, this.removeZeros((String) f.get(datos)));
			} catch (IllegalArgumentException e) {

				e.printStackTrace();
			} catch (IllegalAccessException e) {

				e.printStackTrace();
			}
		}
		return datos;
	}
	
	/*
	 * Metodo para dar formato a los datos que representan cantidades
	 */
	public String formatoCant(String request){
		String response = "";
		if(request != ""){
			String format = request.replaceFirst("^0*", "");
			if(format.length()>0){
				String subInt = format.substring(0, format.length()-2);
				String subFloat = format.substring(format.length()-2);
				response = subInt+"."+subFloat;
			}else{
				response = "0.00";
			}
		}else{
			response = "0.00";
		}
		return "$"+response;
	}
	
	/*
	 * Metodo para dar formato a los datos que representan porcentajes
	 */
	public String formatoPorc(String request){
		String response = "";
		if(request != ""){
			String format = request.replaceFirst("^0*", "");
			if(format.length()>0){
				String subInt = format.substring(0, format.length()-2);
				String subFloat = format.substring(format.length()-2);
				response = subInt+"."+subFloat;
			}else{
				response = "0.00";
			}
		}else{
			response = "0.00";
		}
		return "00"+response+"%";
	}
	
	/*
	 * Metodo patra reemplazar caracteres
	 */
	public String replace(String request){
		String response = "";
		if(request != null && request != ""){
			String dia = request.substring(8);
			String mes = request.substring(5, 7);
			String anio = request.substring(0, 4);
			response = dia+"/"+mes+"/"+anio;
		}else{
			response = "00/00/0000";
		}
		return response;
	}
}
