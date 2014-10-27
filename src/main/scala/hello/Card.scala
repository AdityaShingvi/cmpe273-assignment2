package hello
import javax.validation.constraints.NotNull

class Card(val cardna : String, val number : String, val exp_date : String){
  
  private var Id = "p"
  @NotNull
  private var card_name = cardna
  @NotNull
  private var card_number = number
  private var expiration_date = exp_date
  
   def this()={
    this(null,null,null)
  }
  
  
  def setcardId(cardId : String) : Unit = {Id = cardId  }
  def setcardNumber(cardNumber : String) : Unit = {card_number = cardNumber  }
  //def setcard_name : Unit ={card_name = cardname}
  def getcardId : String = {return Id}
  def getcard_number : String = {return card_number}
  def getexpiration_date : String = {return expiration_date  }
  def getcard_name : String = {return card_name }

}