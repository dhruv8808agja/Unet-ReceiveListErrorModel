
class Sender {

    RecieveListErrorModel send(String message) {

    
        RecieveListErrorModel REmodel = new RecieveListErrorModel()

        ArrayList<String> list = new ArrayList<>()
        def i =1
        message.length().times {

            char ch = message.charAt(it)
            int val = ch;
            String bin = Integer.toBinaryString(val)
            //print(bin.length())
            if(bin.length()<8)
            {
                (8-bin.length()).times {
                    bin = '0'+bin
                }
            }
            i=8
            i.times{
                int k,j
                j = (int)bin.charAt(it) - (int)'0'
                k = (int)bin.charAt((it+1)%8) - (int)'0'
                bin = bin+ (j^k)
            }
            list.add(bin)

        }
        //print("The list delivered is  - ")
        //println(list)
       // REmodel.enable()
        REmodel.SetList(list)
        return REmodel

    }

}
