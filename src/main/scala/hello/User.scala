package hello
import scala.beans._
import java.util.concurrent.atomic.AtomicLong
import javax.validation.constraints.NotNull
import org.springframework.data.annotation.Id;
import java.util.ArrayList
object User {

}

class User(val user_email : String, @NotNull val pass: String) {

  @Id
  private var User_Id : String = null;
  @NotNull
  private var email  = user_email  
  @NotNull
  private var password: String = pass
  var cardmap : Map[String, Card] = Map()
  var webmap : Map[String, Web] = Map()
  var bankmap : Map[String, Bank] = Map()
  var time : Int = 0;
  private var cardcounter = 0
  private var bankcounter = 0
  private var webcounter = 0
  var card = new Card("testName", "333", "12.324.5")
  var web = new Web("testName", "333", "12.324.5")
  var bank = new Bank("testName", "333", "12.324.5")
  private var created_at : String = null
  def this()={
    this(null,null)
  }
 
  def getUserId : String = (return User_Id)
  def getpassword : String = { return password}
  def getemail : String = { return email}
  def getcreated_at : String = {return created_at}
  def setcreated_at(currenttime : String) : Unit = {created_at = currenttime}
  def settime(currenttime : Int) : Unit = {time = currenttime}
  def setUser_email(email : String) : Unit = {this.email = email}
  def setUser_Pass(pass : String) : Unit = {this.password = pass}
  def setUserId (Id : String): Unit = {User_Id = Id}
  def makecardmap (usercard : Card) : Card = {
  this.card = usercard
  this.cardcounter += 1
  var cardId = "c-" + this.cardcounter.toString()
  card.setcardId(cardId)
  this.cardmap = cardmap + (cardId -> card)
  return this.card
  }
  
  def makewebmap (userweb : Web) : Web = {
    web = userweb
	this.webcounter += 1
    var webId = "l-" + webcounter.toString()
    web.setwebId(webId)
    webmap = webmap + (webId -> web)
    return web
 }
  
  def makebankmap (userbank : Bank) : Bank = {
    bank = userbank
    this.bankcounter += 1
    var bankId = "b-" + bankcounter.toString()
    bank.setbankId(bankId)
    bankmap = bankmap + (bankId -> bank)
    return bank
  }
 }