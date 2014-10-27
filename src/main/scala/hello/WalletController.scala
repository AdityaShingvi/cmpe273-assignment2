package hello

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype._
import org.springframework.boot.autoconfigure._
import org.springframework.web.bind.annotation._
import java.util.concurrent.atomic.AtomicLong
import org.springframework.http.{HttpHeaders, ResponseEntity}
import org.springframework.web.bind.annotation.{ResponseBody, RequestMapping, RequestParam, RestController}
import javax.ws.rs.core._
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter
import org.springframework.http.HttpStatus
import collection.JavaConversions._
import java.util.ArrayList
import collection.JavaConversions._
import javax.validation.Valid
import org.springframework.validation.BindingResult
import org.joda.time.DateTime
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import main.scala.hello.SpringMongoConfig


@RestController
@Configuration
@EnableAutoConfiguration
@ComponentScan
class WalletController {
	val ctx = new AnnotationConfigApplicationContext(classOf[SpringMongoConfig])
	val mongoTransaction = ctx.getBean("mongoTemplate").asInstanceOf[MongoOperations]
	val users = new ArrayList[User]()
    var user=new User();
	var user_map: Map[String, User] = Map();
	
	
  @RequestMapping(value = Array("/api/v1/users"), method = Array(RequestMethod.POST), headers = Array("content-type=application/json"), consumes = Array("application/json"))
  def userCreation(@Valid @RequestBody user : User, result: BindingResult):User = {
  val userSearchQuery = new Query();
  userSearchQuery.addCriteria(Criteria.where("_id").is("")); 
  var userCounter = mongoTransaction.count(new Query,classOf[User])
  userCounter = userCounter+1
  this.user = user
  var uid = "U-" + userCounter.toString()
  user.setUserId(uid)
  val currentTime = DateTime.now;
  this.user.setcreated_at(currentTime.toString)
  users.add(this.user)
  mongoTransaction.insert(user)
  return user
   
}
 @RequestMapping(value=Array("api/v1/users/{userid}"), method=Array(RequestMethod.GET))//,headers = Array("content-type=application/json"))
	def ViewUsers(@PathVariable("userid")  userId:String,@RequestHeader(value="If-None-Match", required=false) Etag: String):ResponseEntity[_]={
	val userSearchQuery = new Query();
	userSearchQuery.addCriteria(Criteria.where("_id").is(userId)); 
	val userTest2 = mongoTransaction.findOne(userSearchQuery, classOf[User])
	var tag: String = Etag
	var cc: CacheControl = new CacheControl()
    cc.setMaxAge(500)
    var etag: EntityTag = new EntityTag(Integer.toString(userTest2.hashCode()));
    println(etag);
	var responseHeader: HttpHeaders = new HttpHeaders	
	responseHeader.setCacheControl(cc.toString())
	responseHeader.add("Etag", etag.getValue())
	if(etag.getValue().equalsIgnoreCase(tag)){
		new ResponseEntity[String]( null, responseHeader, HttpStatus.NOT_MODIFIED )   
    } else {
    	new ResponseEntity[User]( userTest2, responseHeader, HttpStatus.OK )  
    }
	}
 
    @RequestMapping(value = Array("/api/v1/users/{userid}"), method = Array(RequestMethod.PUT), headers = Array("content-type=application/json"), consumes = Array("application/json"))
	def upduser(@PathVariable("userid")  userId:String ,@RequestBody user : User ):User={
    var tempUsr = new User()
	val currentTime = DateTime.now;
	user.setUserId(userId)
	user.setcreated_at(currentTime.toString)
	mongoTransaction.save(user)
	val userSearchQuery = new Query();
	userSearchQuery.addCriteria(Criteria.where("_id").is(userId)); 
	tempUsr = mongoTransaction.findOne(userSearchQuery, classOf[User])
    return tempUsr
	}
    
   @RequestMapping(value = Array("/api/v1/users/{userid}/idcards"), method = Array(RequestMethod.POST), headers = Array("content-type=application/json"), consumes = Array("application/json"))
    def idcards( @PathVariable("userid")  userId:String ,@Valid @RequestBody usercard : Card,result: BindingResult): Card = {
    if (result.hasErrors()) {
      throw new ParameterMissingException(result.toString)
    } 
    else 
    {  
      val userSearchQuery = new Query();
		userSearchQuery.addCriteria(Criteria.where("_id").is(userId)); 
		val userTest2 = mongoTransaction.findOne(userSearchQuery, classOf[User])
		userTest2.makecardmap(usercard)
		mongoTransaction.save(userTest2)
		return usercard
    }
 }
   
   @RequestMapping(value=Array("/api/v1/users/{userid}/idcards"), method=Array(RequestMethod.GET), produces = Array("application/json"), headers=Array("content-type=application/json"))
	def viewcards(@PathVariable("userid")  userId:String ):java.util.Map[String,Card]={
    val userSearchQuery = new Query();
	userSearchQuery.addCriteria(Criteria.where("_id").is(userId)); 
	val userTest2 = mongoTransaction.findOne(userSearchQuery, classOf[User])
    return userTest2.cardmap
   }
   
  @RequestMapping(value=Array("/api/v1/users/{userid}/idcards/{card_id}"), method=Array(RequestMethod.DELETE), headers=Array("content-type=application/json"))@ResponseStatus(HttpStatus.NO_CONTENT)
	def deletecards(@PathVariable("userid")  userId:String, @PathVariable("card_id")  cardId:String ):Unit={
    val userSearchQuery = new Query();
	userSearchQuery.addCriteria(Criteria.where("_id").is(userId)); 
	val userTest = mongoTransaction.findOne(userSearchQuery, classOf[User])
   userTest.cardmap -= cardId
   mongoTransaction.save(userTest)
   }
   
   @RequestMapping(value = Array("/api/v1/users/{userid}/weblogins"), method = Array(RequestMethod.POST), headers = Array("content-type=application/json"), consumes = Array("application/json"))
       def webloginscards( @PathVariable("userid")  userId:String ,@Valid @RequestBody userlogin : Web,result: BindingResult): Web = {
    if (result.hasErrors()) {
      throw new ParameterMissingException(result.toString)
    } 
    else 
    {
      val userSearchQuery = new Query();
      userSearchQuery.addCriteria(Criteria.where("_id").is(userId)); 
      val tempUsr = mongoTransaction.findOne(userSearchQuery, classOf[User])
      tempUsr.makewebmap(userlogin)
      mongoTransaction.save(tempUsr)
      return tempUsr.web
    }  
   }
   
   @RequestMapping(value=Array("/api/v1/users/{userid}/weblogins"), method=Array(RequestMethod.GET), produces = Array("application/json"), headers=Array("content-type=application/json"))
	def viewweb(@PathVariable("userid")  userId:String ):java.util.Map[String,Web]={
    val userSearchQuery = new Query();
	userSearchQuery.addCriteria(Criteria.where("_id").is(userId)); 
	val tempUsr = mongoTransaction.findOne(userSearchQuery, classOf[User])
    return tempUsr.webmap
   }
   
   @RequestMapping(value=Array("/api/v1/users/{userid}/weblogins/{login_id}"), method=Array(RequestMethod.DELETE), headers=Array("content-type=application/json"))@ResponseStatus(HttpStatus.NO_CONTENT)
	def deleteweb(@PathVariable("userid")  userId:String, @PathVariable("login_id")  loginId:String ):Unit={
    val userSearchQuery = new Query();
	userSearchQuery.addCriteria(Criteria.where("_id").is(userId)); 
	val tempUsr = mongoTransaction.findOne(userSearchQuery, classOf[User])
    tempUsr.webmap -= loginId
    mongoTransaction.save(tempUsr)
  }
   
    @RequestMapping(value = Array("/api/v1/users/{userid}/bankaccounts"), method = Array(RequestMethod.POST), headers = Array("content-type=application/json"), consumes = Array("application/json"))
    def userbank( @PathVariable("userid")  userId:String ,@Valid @RequestBody bank : Bank,result: BindingResult): Bank = {
    if (result.hasErrors()) {
      throw new ParameterMissingException(result.toString)
    } 
    else 
    {
      var routing = bank.getrouting_number
      var dotest: RoutingInfo = new RoutingInfo()
  	  var bankName = dotest.testRoute(routing)
  	  bank.setaccountname(bankName)
      val userSearchQuery = new Query();
      userSearchQuery.addCriteria(Criteria.where("_id").is(userId)); 
      val tempUsr = mongoTransaction.findOne(userSearchQuery, classOf[User])
	  tempUsr.makebankmap(bank)
      mongoTransaction.save(tempUsr)
      return tempUsr.bank
    }  
   }
    
   @RequestMapping(value=Array("/api/v1/users/{userid}/bankaccounts"), method=Array(RequestMethod.GET), produces = Array("application/json"), headers=Array("content-type=application/json"))
	def viewbank(@PathVariable("userid")  userId:String ):java.util.Map[String,Bank]={
    val userSearchQuery = new Query();
	userSearchQuery.addCriteria(Criteria.where("_id").is(userId)); 
	val tempUsr = mongoTransaction.findOne(userSearchQuery, classOf[User])
    return tempUsr.bankmap
   }
   
   @RequestMapping(value=Array("/api/v1/users/{userid}/bankaccounts/{ba_id}"), method=Array(RequestMethod.DELETE), headers=Array("content-type=application/json"))@ResponseStatus(HttpStatus.NO_CONTENT)
	def deletebank(@PathVariable("userid")  userId:String, @PathVariable("ba_id")  baId:String ):Unit={
    val userSearchQuery = new Query();
	userSearchQuery.addCriteria(Criteria.where("_id").is(userId)); 
	val tempUsr = mongoTransaction.findOne(userSearchQuery, classOf[User])
    tempUsr.bankmap -= baId
    mongoTransaction.save(tempUsr)
   }
}