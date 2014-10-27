package main.scala.hello

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.authentication._
import com.mongodb.MongoClient
//remove if not needed
import scala.collection.JavaConversions._

@Configuration
class SpringMongoConfig {

  @Bean
  def mongoTemplate(): MongoTemplate = {
	val mongoTemplate = new MongoTemplate(new MongoClient("ds049180.mongolab.com:49180"), "cmpe-273", new UserCredentials("aditya","aditya"))
    mongoTemplate
  }
}