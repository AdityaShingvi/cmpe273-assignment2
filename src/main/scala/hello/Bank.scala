package hello
import javax.validation.constraints.NotNull
class Bank(val aname : String, val rnum : String, val anum : String){
  
  private var Id = "p"
  //@NotNull
  private var account_name = aname
  @NotNull
  private var routing_number = rnum
  @NotNull
  private var account_number = anum
  
   def this()={
    this(null,null,null)
  }
  
  
  def setbankId(cardId : String) : Unit = {Id = cardId  }
  def setaccountname(name : String) : Unit = {account_name = name}
  //def setcardNumber(cardNumber : String) : Unit = {cardnumber = cardNumber  }
  //def setcardname : Unit ={card_name = cardname}
  def getcardId : String = {return Id}
  def getaccount_name : String = {return account_name}
  def getrouting_number : String = {return routing_number  }
  def getaccount_number : String = {return account_number }

}