
class Reciever {

    int Errors =0

    String DoCheck(String string)
    {
        int i=0
        ArrayList<String> list= new ArrayList<>();
        for(i=0;i<string.length();i=i+3)
        {
            String sst =""
            int bin = (int)string.charAt(i)
            String str = Integer.toBinaryString(bin)
                    while(str.length()<6)
                    str = '0'+str
            sst =sst+str

            bin = (int)string.charAt(i+1)
            str = Integer.toBinaryString(bin)
                     while(str.length()<5)
                    str = '0'+str
            sst =sst+str

            bin = (int)string.charAt(i+2)
            str =Integer.toBinaryString(bin)
                     while(str.length()<5)
                    str = '0'+str
            sst =sst+str

            list.add(sst)
        }
        
        return check(list)
    }


    String check(ArrayList<String> list)
    {
        RecieveListErrorModel re = new RecieveListErrorModel();
        boolean[] check = new boolean[list.size()]
        int i=0
        int errors =0
        for(String str : list)
        {
            if(re.IsCorrupt(str)!= -1)
            {check[i] = true
                errors++}
            i++
        }
        String string = ""
        for(String st : list)
        {
            int val = Integer.parseInt(st.substring(1,8),2)
            string = string+((char)val)
        }
      //  println(string)
      string =string+'\n'
        for(boolean bool: check)
        {
            if(bool)
            {
                string = string+"^"
                Errors++
            }
            else 
                string = string+" "
        }
        return string
    }

}
