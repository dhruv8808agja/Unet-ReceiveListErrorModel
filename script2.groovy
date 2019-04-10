import org.arl.unet.*
import org.arl.unet.phy.*

subscribe phy


send2 = {list = [], count = 1 -> 
  count.times{

            System.out.println "\n_______________________________________________________\n"    

            String rec = ""
            for(int ss: list)
            rec = rec + (char)ss


            Sender sender = new Sender()
            RecieveListErrorModel REmodel = sender.send(rec)
            REmodel.enable()
            REmodel.DoCorrupt()


            System.out.println "This is node2: \nDetails of Message received are as follows:\nData Received: "+list+"\nThe recieved message is - " + rec+"\nSender Address: "+1
            System.out.println "Error is being induced at a rate of: "+REmodel.error_rate

            System.out.println "\n_______________________________________________________\n"    

            //Sendable String
            String errored = REmodel.GenerateSendableString()        
            phy << new TxFrameReq(to: 3, type: DATA, data: errored)
  }        
}