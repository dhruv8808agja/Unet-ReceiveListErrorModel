import org.arl.unet.*
import org.arl.unet.phy.*

subscribe phy




sendString = { list = "helloworld", count = 1 ->
  println " Sending the string: ${list}"
  
  count.times{ phy << new TxFrameReq(to: 2, type: DATA, data: list)
    println "List sent successfully..."

    }
}

enableM = { count = 1 ->
  count.times{ 
      RecieveListErrorModel.m_enable = true
    }
}

disableM = { count = 1 ->
  count.times{
      RecieveListErrorModel.m_enable = false
    }
}