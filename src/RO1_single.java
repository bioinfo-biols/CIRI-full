import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.zip.GZIPInputStream;

public class RO1_single {
	public String r1;
	public String r2;
	public String out;
	public double id;
	public int ml;
	public RO1_single(String r1a,String r2a,String outa,double id1, int ml1 ) {
		 this.r1=r1a;
		 this.r2=r2a;
		 this.out=outa;
		 this.ml=ml1;
		 this.id=id1;
	 }
		

	public int getro1() throws IOException, InterruptedException {//thread,filein1,filein2,output_prefix
        long t1 = System.currentTimeMillis();
        int ro=0;
        int readlength=0;
    	String[] nametmp=r1.split("\\.");
    	BufferedReader reader=null;
    	BufferedReader reader2=null;
    	if(nametmp[nametmp.length-1].equals("gz")==true){
        	reader= new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(r1)), "GBK"));
    		reader2 = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(r2)), "GBK"));
    		}
    	else{
    		reader=new BufferedReader(new FileReader(r1));
    		reader2=new BufferedReader(new FileReader(r2));
    	}
    	//RO1 part
        FileOutputStream out1=new FileOutputStream(out+"_ro1_align.txt",true);
        PrintStream p1=new PrintStream(out1);
        FileOutputStream out2=new FileOutputStream(out+"_ro1.fq",true);
        PrintStream p2=new PrintStream(out2);
        String line=reader.readLine();
        String line2=reader2.readLine();

        int w1=0;
        int wt=0;
        List<String> dd1=new ArrayList<String>();
        List<String> dd2=new ArrayList<String>();
    	while(line!=null){
    		String n1=line;
    		line=reader.readLine();
    		String seq1=line.toUpperCase();
    		int l1=seq1.length();
    		readlength=l1;
    		line=reader.readLine();
    		line=reader.readLine();
    		String q1=line;
    		line=reader.readLine();
    		line2=reader2.readLine();
    		String seq2=line2.toUpperCase();
    		line2=reader2.readLine();
    		line2=reader2.readLine();
    		String q2=line2;
    		line2=reader2.readLine();
    		reversc rc=new reversc(seq2);
    		seq2=rc.getrc();
    		
    		StringBuffer tq2=new StringBuffer(q2);
    		tq2.reverse();
    		q2=tq2.toString();
	
    		index_head_finder_more k=new index_head_finder_more(seq1,seq2,q1,q2,id,ml);
    		int ifgood=k.getok();
    		if(ifgood==1){
    			String[] result=k.get_merge();
    			int[] b=k.get_pos();
    			double id=k.get_id();
    			dd2.add(n1);
    			dd2.add(result[0]);
    			dd2.add("+");
    			dd2.add(result[1]);	
    			dd1.add(n1+"	"+id+"%	"+b[0]+"	"+b[1]+"	"+b[2]+"	"+b[3]+"	"+l1);
	}		
   // w4[w1]= n1+"	"+"h="+headmatch+" e="+endmatch+" all="+allmatch+" other="+othermatch;    
	w1++;
	wt++;
    if(w1==500000){
    	long tm=System.currentTimeMillis();
    	int a=0;
    	while(a<dd1.size()){
    		p1.println(dd1.get(a));
    		a++;
    		ro++;
    	}
    	a=0;
    	while(a<dd2.size()){
    		p2.println(dd2.get(a));
    		a++;

    	}
    	dd1=new ArrayList<String>();
    	dd2=new ArrayList<String>();
    	w1=0;
    	System.out.println(wt+" pair reads complete, time: " + (tm-t1) /1000+"s,"+ro+" RO reads candidates were found");

    }
    	}
		//System.out.println(dd1.size());
    	int a=0;
    	while(a<dd1.size()){
    		p1.println(dd1.get(a));
    		a++;
    		ro++;
    	}
    	a=0;
    	while(a<dd2.size()){
    		p2.println(dd2.get(a));
    		a++;
    	}
    	dd1=new ArrayList<String>();
    	dd2=new ArrayList<String>();
    	reader.close();
    	reader2.close();
//    writeStreamr3.close();

    long t2 = System.currentTimeMillis();
    System.out.println(wt+" pair reads complete, time: " + (t2-t1) /1000+"s "+ro+" RO reads candidates were found");
    p1.close();
    p2.close();
        long t3=System.currentTimeMillis();
        System.out.println("RO1 end.Take time: "+(t3-t1)/1000+"s"); 
  //      System.out.println("Please use bwa mem with options '-T 19' to align the '"+out+"_ro1_merge.fq' file to reference genome and run RO2 module");
		return readlength;
        }
		
	}
	
	
	
	

