package hello

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.http.HttpStatus
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.List
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.http.{HttpHeaders, ResponseEntity}
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.client.RestTemplate


@RunWith(classOf[SpringJUnit4ClassRunner])
@SpringApplicationConfiguration(classes = Array(classOf[Application]))
@WebAppConfiguration
class RoutingInfo {

  @Test
  def testRoute(route: String): String = {
	var certCheck : SSLCertCheck = new SSLCertCheck()	  
  	certCheck.disableChecks()
  	var URL = "https://www.routingnumbers.info/api/data.json?rn="+route
    var restTemplate = new RestTemplate()
    var response = restTemplate.getForEntity(URL, classOf[String])
    var objectMapper: ObjectMapper = new ObjectMapper();
   	var routinginforesponse: JsonNode = objectMapper.readTree(response.getBody());
   	var statusCode = routinginforesponse.get("code").toString()
   	
   	// if (statusCode == "400")
  	//  {
  	  //  var responseHeader: HttpHeaders = new HttpHeaders
  	 //   new ResponseEntity[String]( null, responseHeader, HttpStatus.NOT_FOUND )   
  	 // }
    
   	if (statusCode == "200") {
    		var name = routinginforesponse.get("customer_name").asText()
    		return name
	} 
	else {
		throw new BankNotFound("Not found")
	}
	return null
  }

}