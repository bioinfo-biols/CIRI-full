
import java.util.regex.Pattern;


public class sam5 {
    public String word;
    public sam5(String wordi){
 	   this.word=wordi;
 	   }
    public static boolean isNumeric1(String str){
    	  Pattern pattern = Pattern.compile("[0-9]*");
    	  return pattern.matcher(str).matches();
    	 }
    public int[] getpos(){
    	int[] pos=new int[4];
    	word=word.replace("S", "H");
    	String[] tmp=word.split("");
    	int n=0;
    	int s=0;
    	StringBuffer num=new StringBuffer();
        while(n<tmp.length){
        	
        	if(isNumeric1(tmp[n])==true){
        		num.append(tmp[n]);	
        //		System.out.println(tmp[n]);
        	}
        	else{
        		if(tmp[n].equals("H")==true){
        			String num1=num.toString();
        			if(s==0)
        			  pos[0]=Integer.parseInt(num1);
        			else
        			  pos[2]=Integer.parseInt(num1);
        			s++;
        			num=new StringBuffer();        			
        		}
        		else if(tmp[n].equals("M")==true){
        			s++;
        			String num1=num.toString();
        			pos[1]=pos[1]+Integer.parseInt(num1);
        			num=new StringBuffer();
        		}
        		else if(tmp[n].equals("I")==true){
        			s++;
        			String num1=num.toString();
        			pos[1]=pos[1]+Integer.parseInt(num1);
        			pos[3]=pos[3]-Integer.parseInt(num1);
        			num=new StringBuffer();
        		}
        		else{
        			s++;
        			String num1=num.toString();
        			pos[3]=pos[3]+Integer.parseInt(num1);
        			num=new StringBuffer();
        		}
        	}
        		
        	n++;
        }
    	
    	return pos;
    }

}
