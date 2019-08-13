package Fractal;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.function.Consumer;

import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.*;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
public class Application {
	static StringBuffer response;
	static BufferedReader in;
	 static String token;
	 static String categories="";
	static JsonNode jsonNode = null;
	 static String jsonString = null;
       static ObjectMapper objectMapper = new ObjectMapper();
       static JsonNode jsonNode2 = null;
       JsonNode jsonNode3 = null;
       JsonNode jsonNode4 = null;
       JsonNode jsonNode5 = null;
       static String jsonString2 = null;
       static String jsonString3 = null;
       String jsonString4 = null;
       String jsonString5 = null;
       static String line ="";
       static CloseableHttpClient client = HttpClients.createDefault();
      static String transactions = "";
      static  String CategoryName = "";
      static String CategoryId = "";
      static String result = "";
      static int responseCode;
  	static Properties prop = new Properties();
  	static String api = "";
  	static String partner = "";
  	static String categoryId;
  	static String category;
  	static String transactionId;
 public static void main(String[] args) throws IOException{
	 ClassLoader classloader = Thread.currentThread().getContextClassLoader();
 	InputStream is = classloader.getResourceAsStream("fractal.properties");
 	prop.load(is);
	 System.out.println(getToken());
	 System.out.println(getCategories());
	 System.out.println(getTransactions());
	 System.out.println(changeCategory());
}

public static String getToken() {
	 try {
		 	//System.out.println(retrieveApi());
			URL url = new URL("https://sandbox.askfractal.com/token");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("x-api-key", retrieveApi());
			conn.setRequestProperty("x-partner-id",retrievePartner());

			OutputStream os = conn.getOutputStream();
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;


			while ((output = br.readLine()) != null) {
		
				jsonString = output.toString();
		        jsonNode = objectMapper.readTree(jsonString);
				
		        token = "Bearer " + jsonNode.findValue("access_token").toString().replace("\"","");
		
			}

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		 }
	 
	 		return token;

		}

public static String getCategories() {
	try {
    HttpClient client = new DefaultHttpClient();
    HttpGet request = new HttpGet("https://sandbox.askfractal.com/categories");
    request.addHeader("Content-Type", "application/json");
    request.addHeader("x-api-key", retrieveApi());
    request.addHeader("x-partner-id", retrievePartner());
    request.addHeader("Authorization", getToken());
  
    HttpResponse response = client.execute(request);
    BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));

    while ((line = rd.readLine()) != null) {
     // System.out.println(line);
      jsonString2 = line.toString();
      jsonNode = objectMapper.readTree(jsonString2);
      JsonNode categoryIdNode = jsonNode.path("results");
      if (categoryIdNode.isArray()) 
      {
    	  for (int i = 0; i < categoryIdNode.size()-1; i++) 
          {
    		 String regex = "\\[|\\]";
        	 String categoriesIds = categoryIdNode.findValuesAsText("categoryId").toString().replaceAll(regex, "").replace(" ", "");
        	 String[] items = categoriesIds.split(",");
        	 
             // categories = categories + categoryId;
              //just for testing 
             categories = items[1].toString();
              
              
          }
      }

    }
  } catch (MalformedURLException e) {

	e.printStackTrace();

  } catch (IOException e) {
	e.printStackTrace();
}
	return categories;
}


	

public static String getTransactions(){
	try {
	HttpClient client = new DefaultHttpClient();
	 System.out.println(getCategories());
	HttpGet request = new HttpGet("https://sandbox.askfractal.com/categories/" + getCategories() + "/transactions" );
	request.addHeader("Content-Type", "application/json");
    request.addHeader("x-api-key", retrieveApi());
    request.addHeader("x-partner-id", retrievePartner());
	request.addHeader("Authorization", getToken());
	 HttpResponse response = client.execute(request);
	    BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));

	    while ((line = rd.readLine()) != null) {
	      jsonString2 = line.toString();
	      jsonNode = objectMapper.readTree(jsonString2);
	      categoryId = jsonNode.findValue("categoryId").toString();
	      category = jsonNode.findValue("category").toString();
	     transactionId = jsonNode.findValue("transactionId").toString().replace("\"", "");
	    transactions = transactionId;
	  
	    }  
	    } catch (MalformedURLException e) {

	    	e.printStackTrace();

	      } catch (IOException e) {
	    	e.printStackTrace();
	    }
	
	return transactions;
}

public static int changeCategory() {
	try {
	 URL url = new URL("https://sandbox.askfractal.com/categories/");
     HttpURLConnection connection = (HttpURLConnection) url.openConnection();
     connection.setRequestMethod("PUT");
     connection.setDoOutput(true);
     connection.setRequestProperty("Accept", "application/json");
     connection.setRequestProperty("Content-Type", "application/json");
     connection.setRequestProperty("x-api-key", retrieveApi());
     connection.setRequestProperty("x-partner-id", retrievePartner());
     connection.setRequestProperty("Authorization", getToken());
     OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
    // JSONObject js = new JSONObject();
   //  js.put("transactionId", "testFM");
  //   js.put("categoryId", "a6hg1");
 //    String transactionId = js.get("transactionId").toString();
     osw.write(String.format("{\"transactionId\":testFM,\"categoryId\":a6hg1}").replace("\"", ""));
  //   osw.write(String.format("{\"transactionId\" : " + transactionId+ ",\"categoryId\":a6hg1}"));
     osw.flush();
     osw.close();
     responseCode = connection.getResponseCode();
     System.out.println(getTransactions());
     
   // System.err.println(connection.getResponseCode());
	} catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {
		e.printStackTrace();
	}
	
	return responseCode;
}

public  static String retrieveApi() {
	api = prop.getProperty("fractal.api").toString();
	return api.replace("\"","");
	
}

public static String retrievePartner() {
	partner = prop.getProperty("fractal.partner").toString();
	return partner.replace("\"","");
	
}
}