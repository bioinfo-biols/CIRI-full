

public class reversc {

	 public String line;
	 public reversc(String linei){
		 this.line=linei;
		
	 }
	 public String getrc(){
		 String linea,lineb,linec,lined,linee;
		 linea=line.replace("A", "t");
		 lineb=linea.replace("T", "a");
		 linec=lineb.replace("G", "c");
		 lined=linec.replace("C", "g");
		 linee=lined.toUpperCase();
		 StringBuffer B=new StringBuffer(linee);
		 B=B.reverse();
		 linee=B.toString();
		 return linee;
	 }
}
