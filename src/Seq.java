import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Seq {
	public String newlist;
	public String ref;
	public Seq(String newlist1, String ref1) {
		 this.newlist=newlist1;
		 this.ref=ref1;
	 }
	public int getseq() throws IOException{

		String label=newlist.split("\\/")[newlist.split("\\/").length-1].split("\\.")[0];
        BufferedWriter writeStream=new BufferedWriter(
	    		new FileWriter(newlist+"_circle.fa"));
        FileReader in =new FileReader(newlist);
		LineNumberReader reader=new LineNumberReader(in);
		String line=reader.readLine();
        Map<String,List<String>> mapchr=new HashMap<String,List<String>>();
		while(line!=null){
			String[] tmp=line.split("	");
			if(mapchr.containsKey(tmp[2])){
				mapchr.get(tmp[2]).add(line);
			}
			else{
				List<String> a=new ArrayList<String>();
				a.add(line);
				mapchr.put(tmp[2], a);
			}
			line=reader.readLine();
		}
		reader.close();
			
        FileReader in_r =new FileReader(ref);
		LineNumberReader reader_r=new LineNumberReader(in_r);
		String line_r=reader_r.readLine();
		
		while(line_r!=null){
			String seqn=null;
			if(line_r.indexOf(" ")!=-1)
			  seqn=line_r.substring(1,line_r.indexOf(" "));
			else
		      seqn=line_r.substring(1,line_r.length());
			line_r=reader_r.readLine();
			StringBuffer tmpseq=new StringBuffer();
			while(line_r.substring(0,1).equals(">")!=true){
				tmpseq.append(line_r);
				//System.out.println(rseq.length());
				line_r=reader_r.readLine();
				if(line_r==null)
					break;
			}
			
			String rseq=tmpseq.toString();
			System.out.println(seqn+" read "+rseq.length());
			if(mapchr.containsKey(seqn)){
			List<String> b=mapchr.get(seqn);
			int nS=0;
			while(nS<b.size()){
				String[] tmp=b.get(nS).split("	");
        		if(tmp[9].equals("Full")==true){

        			tmp[8]=tmp[8].replace("(ICF)", "");
        			tmp[8]=tmp[8].replace("(intron_retention)", "");
        			//System.out.println(tmp[8]);
        			String[] cirexon=tmp[8].split(",");
        			int s=0;
        			int[] startS=new int[cirexon.length];
        			int[] endS=new int[cirexon.length];
        			while(s<cirexon.length){
        				String[] tmp2=cirexon[s].split(":");
        				startS[s]=Integer.parseInt(tmp2[0]);
        				endS[s]=Integer.parseInt(tmp2[1]);
        				s++;
        			}
        	         for(int a1=0;a1<s-1;a1++){
        	           	 for(int a2=0;a2<s-1-a1;a2++){
        	           		 if(startS[a2]>startS[a2+1]){
        	           			 int temp1=startS[a2];
        	            	     int temp2=endS[a2];
        	           			 startS[a2]=startS[a2+1];
        	           			 endS[a2]=endS[a2+1];
        	           			 startS[a2+1]=temp1;
        	            		 endS[a2+1]=temp2;
        	           			 }
        	            	 }
        	            }
        	         int iS=0;
        	         int[] truestart=new int[s];
        	         int[] trueend=new int[s];
        	         int totolS=0;
        	         while(iS<s){
        	        	 int iiS=0;
        	        	 int p=0;
        	        	 while(iiS<totolS){
        	        		 if(startS[iS]<=trueend[iiS]&&endS[iS]>=truestart[iiS]){
        	        			 p=1;
        	        			 truestart[iiS]=Math.min(startS[iS], truestart[iiS]);
        	        			 trueend[iiS]=Math.max(endS[iS], trueend[iiS]);
        	        			 break;
        	        		 }
        	        		 iiS++;
        	        	 }
        	        	 if(p==0){
        	        		  truestart[totolS]=startS[iS];
        	        		  trueend[totolS]=endS[iS];
        	        		  totolS++;
        	        	 }
        	        	 iS++;
        	         }
        	         String name=">"+label+"#"+tmp[1];
        	         String seq="";
        	         iS=0;
        	         while(iS<totolS){
        	        	 seq=seq+rseq.substring(truestart[iS]-1,trueend[iS]).toUpperCase();
        	        	 iS++;
        	         }
        	         System.out.println(name);
        	         writeStream.write(name);
        	         writeStream.newLine();
        	         writeStream.write(seq);
        	         writeStream.newLine();
        		}
				nS++;
			}
			}
	    System.out.println(seqn+" Completed");

	}
		reader_r.close();
		writeStream.close();
	
		return 0;
	}
	
	



}
