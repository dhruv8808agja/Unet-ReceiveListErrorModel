
class RecieveListErrorModel {

    List<String> Rec_List_Packets = new ArrayList<>()
    List<String> Sent_List_Packets = new ArrayList<>()
    List<Integer> m_packetList = new ArrayList<>()
    double  error_rate = 0.3
    int size
    def m_times_invoked

    public static def m_enable = true


    //Check if error model is enabled or not
    def IsEnabled()
    {
        m_enable
    }


    //Enable the error model
    void enable()
    {
        m_enable = true
    }


    //Disable the error model
    void disable()
    {
        m_enable = false
    }


    //Check if the packet is corrupt or not
    def IsCorrupt(packet)
    {
        //To be done
        int a0,a1,a2,a7
        char ch = packet.charAt(0)
        a0 = (int)ch- (int)'0'
        ch = packet.charAt(1)
        a1 = (int)ch -  (int)'0'
        ch = packet.charAt(2)
        a2 = (int)ch -  (int)'0'
        ch = packet.charAt(7)
        a7 = (int)ch - (int)'0'

        int a12 = a1^a2, a01 = a0^a1, a07 = a0^a7
        a12+=(int)'0'
        a01+=(int)'0'
        a07+=(int)'0'
        if(  (char)a07 != packet.charAt(15))
            return 0
        if((char)a01!= packet.charAt(8) || (char)a12 != packet.charAt(9))
            return 1
        return -1
    }

    //Resetting complete packet to original value
    void Reset()
    {
       Sent_List_Packets = Rec_List_Packets.clone()
    }


    //Getting the list to be sent
    def GetList()
    {
        Sent_List_Packets
    }

    void SetList(ArrayList<String> toBeErrored)
    {
        Rec_List_Packets = toBeErrored
        Sent_List_Packets.clear()
        size = Rec_List_Packets.size()
        Sent_List_Packets = Rec_List_Packets.clone()
    }

    void DoCorrupt()
    {

        if(!m_enable)
        {
            println("Error model is disabled")
            return
        }
        int n
        double ans = (double)size*error_rate
        n =(int)ans
      //  println(n+" "+size)
        if(n==0)
            return
        int diff = size/n
        int next = 0
        int iter=0
        while(iter < n)
        {
            int add =(int)(Math.random()*diff)
         //   println(add)
            int i = (int)(Math.random()*2)
            add+= next
            int itr =i
            StringBuffer bin = new StringBuffer(Sent_List_Packets.get(add))
            while(itr<16)
            {
                if(bin.charAt(itr)=='0')
                    bin.replace(itr,itr+1,"1")
                else
                    bin.replace(itr,itr+1,"0")
                itr+=2
            }
            Sent_List_Packets.remove(add)
            Sent_List_Packets.add(add,bin)
            next+= diff
            iter++
        }
    }

    String GenerateSendableString()
    {
        String sendable=""
        for(String iter: Sent_List_Packets)
        {
            int a1,a2,a3
            a1 = Integer.parseInt(iter.substring(0,6),2)
            a2 = Integer.parseInt(iter.substring(6,11),2)
            a3 = Integer.parseInt(iter.substring(11,16),2)
            sendable = sendable+ (char)a1+(char)a2+(char)a3
        }
        return sendable
    }

}
