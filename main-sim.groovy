//! Simulation: ReceiveListErrorModel simulation

import org.arl.unet.*
import org.arl.unet.phy.*
import org.arl.fjage.*
import static org.arl.unet.Services.*
import static org.arl.unet.phy.Physical.*
import org.arl.unet.sim.*
import java.util.*





simulate  {
  def n = []
  n << node('1', address: 1, location: [0,0,0])                        //initializing three nodes 1..3 with location same for no propogation delay
  n << node('2', address: 2, location: [0,0,0])
  n << node('3', address: 3, location: [0,0,0])
 
  

  n.eachWithIndex { n2, i ->
    n2.startup = {
      def phy = agentForService PHYSICAL
      def node = agentForService NODE_INFO
      subscribe phy

      if(node.address == 1)
      {
        //NODE 1
        //Input String to be sent
        Scan scan = new Scan()
        String sendString = scan.scanit()

        //Sending the read String
        add new OneShotBehavior({
          phy << new TxFrameReq(to: 2, type: DATA, data: sendString)
        })

      }

      if(node.address == 3)
      {   

            add new OneShotBehavior({                                                             //use of OneShotBehaviour for one time sending and receiving message
            // wait for 3 sec to receive
            //this condition will always be executed after the NODE 2 condition statements   
            def final_rxNtf = receive({ it instanceof RxFrameNtf },3000)
            
            if(final_rxNtf)                                                                       //if recieved notification is valid(i.e., contains some data)
            { 

            int errorCount = 0                                                                    
            String rec = ""
            for(int ss: final_rxNtf.data)
            {
             rec = rec + (char)ss
             if((char)ss == '^')
             errorCount++
            }
            int ind = rec.indexOf('\n')
            String message = rec.substring(0,ind)
            Thread.sleep(3000) 

            println "This is node3: \nDetails of Message received are as follows: \nData Received: "+final_rxNtf.data+"\nMessage Received: "+message+"  \nSender Address: "+2
               println "\nErrors:\n"+rec+"\nNumber of errors: "+errorCount +"\n_______________________________________________________\n\n"
            }
          })
      }

      if(node.address == 2)
      {
       
        add new OneShotBehavior({
           //as 1000 < 3000, this if condition with node.address == 2 will execute before the if condition node.address == 3
          def rxNtf = receive({ it instanceof RxFrameNtf }, 1000)
          if(rxNtf){
            String rec = ""
            for(int ss: rxNtf.data)
            rec = rec + (char)ss

            //Now inducing Error with use of RecieveListErrorModel.groovy
            Sender sender = new Sender()
            RecieveListErrorModel REmodel = sender.send(rec)
            REmodel.enable()
            REmodel.DoCorrupt()

            //checking at node3
            String errored = new Reciever().check(REmodel.Sent_List_Packets)

            Thread.sleep(3000)
            println "This is node2: \nDetails of Message received are as follows:\nData Received: "+rxNtf.data+"\nThe recieved message is - " + rec+"\nSender Address: "+1

            println "Error is being induced at a rate of: "+REmodel.error_rate

            println "\n_______________________________________________________\n\n"
             
            //sending errored string to node3 
            phy << new TxFrameReq(to: 3, type: DATA, data: errored)
            } 
        })
      }      
    }
  }
}
