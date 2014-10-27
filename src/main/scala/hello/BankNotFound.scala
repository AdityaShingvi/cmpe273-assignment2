package hello

import java.lang.Exception
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import scala.collection.JavaConversions._

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The bank for the requested routing number not found")
class BankNotFound(error: String) extends RuntimeException(error)
