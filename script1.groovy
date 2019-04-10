import org.arl.unet.*
import org.arl.unet.phy.*

subscribe phy




sendString = { list = "helloworld", count = 1 ->
  println " Sending the string: ${list}"
  
  count.times{ phy << new TxFrameReq(to: 2, type: DATA, data: list)
    println "List sent successfully..."

    }
}



