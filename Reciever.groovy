
class Reciever {

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
                string = string+"^"
            else 
                string = string+" "
        }
        return string
    }

}
