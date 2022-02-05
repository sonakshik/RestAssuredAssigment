package RestAssuredTrial;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;


public class RestAssuredDemo {

	String baseURI="https://simple-tool-rental-api.glitch.me";
	int toolId;
	String accessToken;
	String orderId;
	
	@Test
	public void api1()   {
		  toolId = given()
		.baseUri(baseURI)		 
		.contentType(ContentType.JSON)
		.when()	
		.get("/tools")
		.then()
		.statusCode(200)
		.and()
		.extract()
		.response()
		.path("id[0]");
			System.out.println("toolId="+toolId);		
			
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void api2()   {
		JSONObject obj = new JSONObject();
        obj.put("clientName", "Postman");
        obj.put("clientEmail", "sonakshi.kpsit@gmail.com");
		  accessToken = given()
		.baseUri(baseURI)
		.contentType(ContentType.JSON)
		.body(obj)
		.when()		
		.post("/api-clients")
		.then()
		.statusCode(201)
		.and()
		.extract()
		.response()
		.path("accessToken");
			System.out.println("accessToken="+accessToken);		
			
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void api3()   {
		JSONObject obj = new JSONObject();
        obj.put("toolId", toolId);
        obj.put("customerName", "Sonakshi");
        obj.put("comment", "Sonakshi created order");
		String orderId=given()
		.baseUri(baseURI)
		.contentType(ContentType.JSON)
		.header("Authorization", accessToken)
		.accept(ContentType.JSON)
		.body(obj)
		.when()		
		.post("/orders")
		.then()		
		.statusCode(201)	
		.assertThat().body("created", equalTo(true))
		.and()
		.extract()
		.response()
		.path("orderId");
		System.out.println("Order ID="+orderId);
		
			
	}
}
 
	