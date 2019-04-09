import org.arl.fjage.Message
import org.arl.unet.*
import org.arl.unet.phy.*
import org.arl.fjage.*
import static org.arl.unet.Services.*
import static org.arl.unet.phy.Physical.*
import org.arl.unet.sim.*


class PingDaemon extends UnetAgent {
  private AgentID node
  private AgentID phy
  public int addr
  public static String errored
  void startup() {
    phy = agentForService Services.PHYSICAL
    subscribe topic(phy)
    node = agentForService Services.NODE_INFO
    addr = node.address    
  }

  

  

  void processMessage(Message msg) {   
    if(addr == 2 && msg instanceof RxFrameNtf)
    {
            System.out.println "\n_______________________________________________________\n"    

            String rec = ""
            for(int ss: msg.data)
            rec = rec + (char)ss

            String errored = rec

            if(RecieveListErrorModel.m_enable)
            {
              Sender sender = new Sender()
            RecieveListErrorModel REmodel = sender.send(rec)
            // REmodel.enable()
            REmodel.DoCorrupt()


            System.out.println "This is node2: \nDetails of Message received are as follows:\nData Received: "+msg.data+"\nThe recieved message is - " + rec+"\nSender Address: "+1
            System.out.println "Error is being induced at a rate of: "+REmodel.error_rate

            // System.out.println "\n_______________________________________________________\n"    

            //Sendable String
            errored = REmodel.GenerateSendableString()      
            }
              
            phy << new TxFrameReq(to: 3, type: DATA, data: errored)

            System.out.println "\n_______________________________________________________\n"    

    }

    if(addr == 3 && msg instanceof RxFrameNtf)
    {
                  System.out.println "\n_______________________________________________________\n"    

          System.out.println "This is node3:\n"
          //System.out.println "Recieved String from node2: ${msg.toString()}"

          String receivedString =""
            String rec = ""
            for(int ss: msg.data)
            {
             receivedString = receivedString + (char)ss
            }

            int errorCount = 0         
            
            Reciever MyReciever = new Reciever()
            //Using Reciever to check and verify the recieved String from node 2 according to the used pattern
            rec = MyReciever.DoCheck(receivedString)
            errorCount = MyReciever.Errors                                                        //error counter
            

            int ind = rec.indexOf('\n')
            String message = rec.substring(0,ind)

            System.out.println "Details of Message received are as follows: \nData Received: "+msg.data+"\nMessage Received: "+message+"  \nSender Address: "+2
            System.out.println "\nErrors:\n"+rec+"\nNumber of errors: "+errorCount +"\n_______________________________________________________\n\n"
        
        
      
    }
  }
}
