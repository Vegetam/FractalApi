import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;

import Fractal.Application;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;



public class testApi {
	
	@Test
	public void getTokenForbidden() throws Exception {
		HttpUriRequest request = new HttpPost( "https://sandbox.askfractal.com/token");
		 
	    // When
	    HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );	  	
		assertThat(
			      httpResponse.getStatusLine().getStatusCode(),
			      equalTo(HttpStatus.SC_FORBIDDEN));
	}
	
	@Test
	public void getRealToken() throws Exception {
		HttpUriRequest request = new HttpPost( "https://sandbox.askfractal.com/token");
		 request.addHeader("Content-Type", "application/json");
		 request.addHeader("x-api-key", "sc95WrlAbA1sKWEG6CPSn51ETM3PEe6K6xOlDXwe");
		 request.addHeader("x-partner-id", "5111acc7-c9b3-4d2a-9418-16e97b74b1e6");

	    // When
	    HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );	  	
		assertThat(
			      httpResponse.getStatusLine().getStatusCode(),
			      equalTo(HttpStatus.SC_CREATED));
	}
	
	@Test
	public void getTransactions() throws Exception {
		HttpUriRequest request = new HttpGet("https://sandbox.askfractal.com/categories/a6hg1/transactions");
		 request.addHeader("Content-Type", "application/json");
		 request.addHeader("x-api-key", "sc95WrlAbA1sKWEG6CPSn51ETM3PEe6K6xOlDXwe");
		 request.addHeader("x-partner-id", "5111acc7-c9b3-4d2a-9418-16e97b74b1e6");
		 request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzYW5kYm94VXNlciIsIm5hbWUiOiJGcmFjQm94IiwiaWF0IjoxNTE2MjM5MDIyLCJleHBpcmVzIjoxODAwfQ.A-Xk_RwJu3BZQ7gsUgq7nK4UPJpqIKJtxbBxkz2eJU4");
	    // When
	    HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );	  	
		assertThat(
			      httpResponse.getStatusLine().getStatusCode(),
			      equalTo(HttpStatus.SC_OK));
	}

}
