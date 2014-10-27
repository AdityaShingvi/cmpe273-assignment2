package hello
import javax.validation.constraints.NotNull
class Web(@NotNull val weburl : String, val weblogin : String, val pass : String){
  
  private var Id = "p"
  @NotNull
  private var url = weburl
  @NotNull
  private var login = weblogin
  @NotNull
  private var password = pass
  
   def this()={
    this(null, null, null)
  }
  
  
  def setwebId(webId : String) : Unit = {Id = webId  }
  //def setcardNumber(cardNumber : String) : Unit = {cardnumber = cardNumber  }
  //def setcardname : Unit ={card_name = cardname}
  def getwebId : String = {return Id}
  def geturl : String = {return url}
  def getlogin : String = {return login  }
  def getpassword : String = {return password }

}

