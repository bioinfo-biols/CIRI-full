import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RO2 {
	
	public String ref;
	public String sam;
	public int readlength;
	public int range;
	public String out;
	public RO2(String refa,String sama,int ta,int range1, String out1) {
		 this.ref=refa;
		 this.sam=sama;
		 this.readlength=ta;
		 this.range=range1;
		 this.out=out1;
	 }
	
	// if readl=0 long reads.
		
	public int getro2() throws IOException{
		System.out.println("Loading sam file");
        File f=new File(System.getProperty("user.dir"));
		FileReader in =new FileReader(sam);
		LineNumberReader reader=new LineNumberReader(in);
		String line=reader.readLine();
        Map<String,File> map = new HashMap<String,File>();
        long tstart = System.currentTimeMillis();
        int readl=readlength;
        List<String> outputlist=new ArrayList<String>();
        List<String> outputlist2=new ArrayList<String>();


	//	String R="/hiseqPublic2/zhao/dataset/riboMinus/hela/reads/hg19.fa1";
	//	String R="/Users/zhengyi/Documents/data5/chromFa/hg19.fa";
		int total2=0;
		int num_r1=0;
		int num_r2=0;
		int num1=0;
		int num2=0;
		int num4=0;
		int num5=0;
		int num6=0;
		int num7=0;
		int num8=0;
		int num9=0;
		int fullnum=0;
		int partnum=0;
    	String[][] G02=new String[10000][];
		int yes=0;
        List<String> name=new ArrayList<String>();
		while(line.substring(0,1).equals("@")==true){
			String[] tmphead=line.split("	");
			if(tmphead[0].equals("@SQ")){
				String[] tmphead1=tmphead[1].split("\\:");
				String[] tmphead2=tmphead[2].split("\\:");
			//	System.out.println(tmphead1[1]);
				if(Integer.parseInt(tmphead2[1])>500000)
					name.add(tmphead1[1]);
			}
			line=reader.readLine();
			///part the file with chr;
		}
		File[] tmpfile=new File[name.size()];
		int mat=0;
		while(mat<name.size()){
			tmpfile[mat]=File.createTempFile("tmp"+name.get(mat)+"_", ".txt", f);
			tmpfile[mat].deleteOnExit();
		//	System.out.println(tmpfile[mat]);
			map.put(name.get(mat),tmpfile[mat]);
			mat++;
		}
		int onepart=200000;
		int linenum=0;

		ArrayList<String> text = new ArrayList<String>();
		while(line!=null){
			String[] tmpt=line.split("	");
			if(tmpt[2].equals("*")!=true){
				text.add(line);
				linenum++;
			}
			else
				num5++;
			if(linenum>onepart){
				String A=line;
				line=reader.readLine();
				String[] tmpt2=line.split("	");
				while(tmpt[0].equals(tmpt2[0])){
					text.add(line);
					A=line;
					line=reader.readLine();
					tmpt=A.split("	");
					tmpt2=line.split("	");
				}
				String[] G1=new String[10000];
				String[] tmp1;
				mat=0;
				while(mat<name.size()){
			        FileOutputStream out=new FileOutputStream(tmpfile[mat],true);
			        PrintStream p1=new PrintStream(out);

				int k=1;
				int n=0;
		        while(k-1<text.size()){
		        	int p;
		        	tmp1=text.get(k-1).split("	");
		        	if(k>=text.size())
		        		p=1;
		        	else{
		        		String[] tmp2=text.get(k).split("	");
		        		if(tmp1[0].equals(tmp2[0])==true)
		        			p=0;
		        		else
		        			p=1;
		        	}
		        	if(p==0){
						G1[n]=text.get(k-1);
					    n++;
		        	}
		        	else{
		        		G1[n]=text.get(k-1);
		        		String[] tmp=G1[0].split("	");
		        	//	System.out.println(map.get(tmpfile[mat]));
		        		if(tmp[2].equals(name.get(mat))){
		        			int nn=0;
		        			while(nn<=n){
		        				p1.println(G1[nn]);
		        				nn++;
		        			}
		        		}
		        	    n=0;
		        	}
		        	k++;
		        }
		        p1.close();
		        mat++;
				}
				text = new ArrayList<String>();
				linenum=0;
				System.out.print(".");
			}
			else{
				line=reader.readLine();
			}
		}
		if(linenum>0){		
		String[] G1=new String[10000];
		String[] tmp1;
		mat=0;
		while(mat<name.size()){
	        FileOutputStream out=new FileOutputStream(tmpfile[mat],true);
	        PrintStream p1=new PrintStream(out);

		int k=1;
		int n=0;
        while(k-1<text.size()){
        	int p;
        	tmp1=text.get(k-1).split("	");
        	if(k>=text.size())
        		p=1;
        	else{
        		String[] tmp2=text.get(k).split("	");
        		if(tmp1[0].equals(tmp2[0])==true)
        			p=0;
        		else
        			p=1;
        	}
        	if(p==0){
				G1[n]=text.get(k-1);
			    n++;
        	}
        	else{
        		G1[n]=text.get(k-1);
        		String[] tmp=G1[0].split("	");
        	//	System.out.println(map.get(tmpfile[mat]));
        		if(tmp[2].equals(name.get(mat))){
        			int nn=0;
        			while(nn<=n){
        				p1.println(G1[nn]);
        				nn++;
        			}
        		}
        	    n=0;
        	}
        	k++;
        }
        p1.close();
        mat++;
		}
		text = new ArrayList<String>();
		linenum=0;
		System.out.print(".");
}
		
		reader.close();

		System.out.println("Apart completed");
		text=new ArrayList<String>();

		BufferedWriter writeStream=new BufferedWriter(
	    		new FileWriter(out+"_ro2.sam"));//circle
	//	BufferedWriter writeStream1=new BufferedWriter(
	//    		new FileWriter(f+"2_1.txt"));//not fully map
	//	BufferedWriter writeStream2=new BufferedWriter(
	//    		new FileWriter(f+"2_q.txt"));//q
	//	BufferedWriter writeStream4=new BufferedWriter(
	//    		new FileWriter(f+"2_2.txt"));//forward
	//	BufferedWriter writeStream5=new BufferedWriter(
	//    		new FileWriter(f+"2_3.txt"));//unmap
	//	BufferedWriter writeStream6=new BufferedWriter(
	//    		new FileWriter(f+"2_4.txt"));//gap reads;
		BufferedWriter writeStreama3=new BufferedWriter(
	    		new FileWriter(out+"_ro2_info.list"));
		writeStreama3.write("Read_ID	Chr	BSJ_position	Strand	Reconstructed_state	Cirexon	Mapping_order	Splice_site_state+	Splice_site_state-");
		writeStreama3.newLine();
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
			String rseq="";
			while(line_r.substring(0,1).equals(">")!=true){
				tmpseq.append(line_r);
				//System.out.println(rseq.length());
				line_r=reader_r.readLine();
				if(line_r==null)
					break;
			}
			rseq=tmpseq.toString();
			long tend=System.currentTimeMillis();
			System.out.println(seqn+" Loaded "+ ((tend-tstart)/1000)+"s");
			int rseql=rseq.length();
			if(map.containsKey(seqn)){
		FileReader in_p =new FileReader(map.get(seqn));
		LineNumberReader reader_p=new LineNumberReader(in_p);
		line=reader_p.readLine();
		String line1 ="";
		String A=line;
		line=reader_p.readLine();
		int n=0;
		String[] G1=new String[10000];
		String[] tmp1=null;
        while(A!=null){
        	int p;
        	tmp1=A.split("	");
        	if(line==null)
        		p=1;
        	else{
        		String[] tmp2=line.split("	");
        		if(tmp1[0].equals(tmp2[0])==true&&tmp1[5].equals("*")!=true)
        			p=0;
        		else
        			p=1;
        	}
        	if(p==0){
				G1[n]=A;
			    n++;
        	}
        	else{
        		G1[n]=A;
        		int[][] pos1=new int [10000][3];
        		int pos[] =new int[10000];
        		int leftbound=0;
        		int rightbound=0;
        		String[] tmpx=A.split("	");
        		String[] tmpy=G1[0].split("	");
        		String seq="";
        		total2++;
        		if(tmpx[5].equals("*")!=true&&tmpy[2].equals("chrM")!=true){
        			yes=0;
        			String[] G=new String[10000];
                    int t=0;
                    int r=0;
                    String chr1="";
                    int t1=0;
                    char s1=0;
                    int qbest=0;
        			while(t<=n){
                        String[] tmp=G1[t].split("	");
                        int flag=Integer.parseInt(tmp[1]);
                        String flag1=Integer.toBinaryString(flag);
                        flag1="00000"+flag1;
                        char strand=flag1.charAt(flag1.length()-5);
                        if(t==0){
                        	sam5 thesam=new sam5(tmp[5]);
                            pos1[0]=thesam.getpos();
                            pos[0]=Integer.parseInt(tmp[3]);
                            r=r+pos1[0][1];
                            line1=G1[0];
                            seq=tmp[9];
                            chr1=tmp[2];
                            s1=strand;
                            G[0]=G1[0];
                            qbest=Integer.parseInt(tmp[4]);
                            leftbound=pos[0];
                            rightbound=pos[0]+pos1[0][1];
                            t1++;
                    }
                    else{
                         	if(chr1.equals(tmp[2])==true&&s1==strand&&Math.abs(pos[0]-Integer.parseInt(tmp[3]))<range/2&&Integer.parseInt(tmp[4])>5){
                         		sam5 thesam=new sam5(tmp[5]);
                                pos1[t1]=thesam.getpos();
                                pos[t1]=Integer.parseInt(tmp[3]);
                                r=r+pos1[t1][1];
                                G[t1]=G1[t];
                                leftbound=Math.min(leftbound,pos[t1]);
                                rightbound=Math.max(rightbound,pos[t1]+pos1[t1][1]);
                                t1++;
                         	}
                    }
        				t++;
        			}
        			int total=pos1[0][0]+pos1[0][1]+pos1[0][2];
            		int[] temp1;
            		String tmpg;
            		int tmpp;
            		t=t1;
            		
            	    for(int i=0;i<t;i++){            	      
            	    	for(int j=0;j<t-i-1;j++){
            	        if(pos1[j][0]>pos1[j+1][0]){
            	          temp1=pos1[j];
            	          tmpp=pos[j];
            	          tmpg=G[j];
           	              pos1[j]=pos1[j+1];
           	              pos[j]=pos[j+1];
           	              G[j]=G[j+1];
            	          pos1[j+1]=temp1;
            	          G[j+1]=tmpg;
            	          pos[j+1]=tmpp;
            	        }
            	      }
            	    }
            	    double per=(double)r/(double)total;
            	    int warns=0;
            	    int q=0;
            	    int warn2=0;
            	    if(per<0.5){
                    	int i=0;
                    	while(i<=n){       //not fully map
                    		//writeStream1.write(G1[i]);
                    		//writeStream1.newLine();
                    		i++;
                    	}
                    	num1++;
            	    }
            	    else if(qbest<15){
                    	int i=0;
                    	while(i<=n){       //not fully map
                    		//writeStream2.write(G1[i]);
                    		//writeStream2.newLine();
                    		i++;
                    	}
                    	num2++;
            	    }
            	    else{
                    while(q<t-1){
                    	if((Math.abs(pos1[q+1][0]-pos1[q][0]-pos1[q][1]))>8)
                    		//find gap with sw
                    		warns=1;
                    	if(pos[q+1]>pos[q]+3&&pos[q+1]<pos[q]+pos1[q][1])
                    		warns=1;
                 	   if(pos[q+1]+pos1[q+1][1]>pos[0]+4&&pos[q+1]<pos[0]+pos1[0][1]-4){
                 		  warn2=1;
                 		  }
                 	   q++;
                 	   }
                    if(warn2==0){
                    	String startline="";
                    	String endline="";
                    	if(pos1[0][0]>=10){
                    	//	System.out.println("1"+"	"+G[0]);
                    		int pro1=0;
                    		String seqsub1=seq.substring(0,pos1[0][0]);
                    		String ref=rseq.substring(Math.max(0,pos[0]-20000),Math.min(rseql,rightbound+20000)).toUpperCase();
                    		int alimit=(Math.max(0,pos[0]-20000));
                    		index_head_finder2 start1=new index_head_finder2(seqsub1,ref);
                    		pro1=start1.getok();
                    		if(pro1==1){
                    			int a[]=start1.get_pos();
                    			String[] tmp=line1.split("	");
                    			String sam5=pos1[0][0]+"M"+(total-pos1[0][0])+"S";
                    			startline=tmp[0]+"	0	"+tmp[2]+"	"+(alimit+a[0])+"	0	"+sam5+"	"+seqsub1+"	"+ref.substring(a[0]-1,a[1])+"	Index_find_1";
                        		int tmpd=t;
                        		t=t+1;
                        		while(tmpd>0){
                        			G[tmpd]=G[tmpd-1];
                          		  	pos1[tmpd]=pos1[tmpd-1];
                          		  	pos[tmpd]=pos[tmpd-1];
                        			tmpd--;
                        		}
                        		G[0]=startline;
                        		pos1[0][1]=pos1[0][0];
                        		pos1[0][2]=(total-pos1[0][0]);
                        		pos1[0][0]=0;
                        		pos[0]=(alimit+a[0]);
                                leftbound=Math.min(leftbound,pos[0]);
                                rightbound=Math.max(rightbound,pos[0]+pos1[0][1]);

                    		}
                    	}
                    		
                    	if(pos1[t-1][2]>=10){
                    	//	System.out.println("2"+"	"+G[0]);
                    		int pro2=0;
                    		String seqsub2=seq.substring(seq.length()-pos1[t-1][2],seq.length());
                    		String ref=rseq.substring(Math.max(0,leftbound-20000),Math.min(rseql,rightbound+20000)).toUpperCase();
                    		int alimit=(Math.max(0,leftbound-20000));
                    		index_head_finder2 start2=new index_head_finder2(seqsub2,ref);
                    		pro2=start2.getok();
                    		if(pro2==1){
                    			int a[]=start2.get_pos();
                    			String[] tmp=line1.split("	");
                    			String sam5=(total-pos1[t-1][2])+"S"+pos1[t-1][2]+"M";
                    			endline=tmp[0]+"	0	"+tmp[2]+"	"+(alimit+a[0])+"	0	"+sam5+"	"+seqsub2+"	"+ref.substring(a[0]-1,a[1])+"	Index_find_2";
                        		G[t]=endline;
                        		pos1[t][0]=total-pos1[t-1][2];
                        		pos1[t][1]=pos1[t-1][2];
                        		pos1[t][2]=0;
                        		pos[t]=(alimit+a[0]);
                    			t=t+1;
                    		}
                    	}

                    }
                    q=0;
                    while(q<t-1){
                       //System.out.println(pos[0]+"-"+(pos[0]+pos1[0][1])+" "+pos[q+1]+"-"+(pos[q+1]+pos1[q+1][1]));
                  	   if(pos[q+1]+pos1[q+1][1]>pos[0]+4&&pos[q+1]<pos[0]+pos1[0][1]-4)
                  		  warn2=1;
                  		  q++;
                    }
            	    int warnl=0;
            	    int cut=total-readl;
            	    
            	    if(cut<=5)
            	    	warnl=1;
            	    if(cut<=pos1[0][0]+5)
            	    	warnl=1;
            	    if(readl>=total-pos1[t-1][2]-5)
            	    	warnl=1;
            	    if(readl==0)
            	    	warnl=0;
            	    
            	     if(warns!=0){
                    	int i=0;
                    	while(i<t){       //gap map
                    		//writeStream6.write(G[i]);
                    		//writeStream6.newLine();
                    		i++;
                    	}
                    	num6++;
            	    }
            	    else{
                     if(t==1){
                      	 int i=0;	
                    	while(i<t){//forward
                    	//	writeStream4.write(G[i]);
                    	//	writeStream4.newLine();
                    		i++;
                    	}
                       num4++;
                   	}
                    else if(warn2==1){
                    	int i=0;
                    	while(i<t){
                    		G02[i]=G[i].split("	");
                    		outputlist2.add(G[i]);                  //  		System.out.println(G[i]);
                    		i++;
                    	}
                    	num_r1++;
                		yes=1;
                    }
                    else if(warn2==0&&warnl==0){
                    	int i=0;
                    	while(i<t){
                    		G02[i]=G[i].split("	");
                    		outputlist2.add(G[i]);
                  //  		System.out.println(G[i]);
                    		i++;
                    	}
                    	num_r2++;
                		yes=2;
                    }
                    else {
                    	int i=0;
                    	while(i<t){//forward
                    	//	writeStream4.write(G[i]);
                    	//	writeStream4.newLine();
                    		i++;
                    }
                    	num4++;
                    }
            	    if(yes!=0){
            	    	int[][] pos02=new int[t-1+1][];
                		sam5 a=new sam5(G02[0][5]);
                		pos02[0]=a.getpos();
                		int[] exonstart=new int[20];
                		int[] exonend=new int[20];
                		int[][] splice1group=new int[2][t-1];//0 +   ,     1 -
                		int[][] splice2group=new int[2][t-1];
                		int[] tmp_p1=new int[t-1+1];//left bound+
                		int[] tmp_p2=new int[t-1+1];//right bound+
                		int[] tmp_n1=new int[t-1+1];//left bound-
                		int[] tmp_n2=new int[t-1+1];//right bound-
                		int tn=0;
                		int t2=0;
                		int p0=0;
                		int p1=0;
                		String bound1="readposBound+:";
                		String bound2="readposBound-:";
                		int point0=0;
                		int point1=0;
                		int good=0;
                		//if strain +;
                		while(tn<t-1){
                			int ifgood=0;
                    		int pp0=-1;
                    		int pp1=-1;
                			sam5 f1=new sam5(G02[tn][5]);
                			sam5 f2=new sam5(G02[tn+1][5]);
                			int start1=Integer.parseInt(G02[tn][3]);
                			int start2=Integer.parseInt(G02[tn+1][3]);
                			//System.out.println(start1+" "+start2);
                			int[] fp1=f1.getpos();
                			int[] fp2=f2.getpos();
                			int readcut;
                			pos02[tn+1]=fp2;
                			int gap=fp2[0]-fp1[1]-fp1[0];
                			int splicep1,splicep2;
                			if(gap>=0){
                				splicep1=start1+fp1[1]-1+fp1[3];
                				splicep2=start2-3-gap;
                				readcut=fp1[0]+fp1[1];
                			}
                			else{
                				splicep1=start1+fp1[1]-1+gap+fp1[3];
                				splicep2=start2-3;
                				readcut=fp2[0];
                			}
                			String splice1="";//=rseq.substring(start1+fp1[1]-1,start1+fp1[1]+1);
                			String splice2="";//=rseq.substring(start2-3,start2-1);
                			String refsplice1="";
                			String refsplice2="";

                			gap=Math.abs(gap);
                			String site="";
                			String refsite="";
                			String readsite="";

                			int mv=5;//forward search
                			while(mv>0){
                				if(splicep1-mv>=0&&splicep2-mv>=0&&readcut-mv-5>=0&&readcut-mv+5<=seq.length()){
                				splice1=rseq.substring(splicep1-mv,splicep1+2-mv).toUpperCase();
                				splice2=rseq.substring(splicep2-mv,splicep2+2-mv).toUpperCase();
                				site=splice1+splice2;
                				refsplice1=rseq.substring(splicep1-5-mv,splicep1-mv).toUpperCase();
                				refsplice2=rseq.substring(splicep2-mv+2,splicep2-mv+2+5).toUpperCase();
                				refsite=refsplice1+refsplice2;
                				readsite=seq.substring(readcut-mv-5,readcut-mv+5);

                		//		System.out.println(site+" "+(splicep1-mv)+" "+(splicep2+2-mv));
                				if(site.equals("GTAG")){
                					if(pp0==-1){
                						pp0=mv;
                						splice1group[0][tn]=splicep1-mv;
                						splice2group[0][tn]=splicep2+2-mv;
                						int d=0,b=0;
                						char chref[]=refsite.toCharArray();
                						char chread[]=readsite.toCharArray();
                						while(d<chref.length){
                							if(chref[d]==chread[d])
                								b++;
                							d++;
                						}
                						if(splicep1<splicep2){
                							if(b>=9)
                								ifgood=1;
                							else
                								ifgood=0;
                						}
                						else{
                    						if(b>=10)
                    							ifgood=1;
                    						else
                    							ifgood=0;
                    						}
                						
                					}
                					else{
                						if(pp0>mv){
                    						pp0=mv;
                    						splice1group[0][tn]=splicep1-mv;
                    						splice2group[0][tn]=splicep2+2-mv;
                    						int d=0,b=0;
                    						char chref[]=refsite.toCharArray();
                    						char chread[]=readsite.toCharArray();
                    						while(d<chref.length){
                    							if(chref[d]==chread[d])
                    								b++;
                    							d++;
                    						}
                    						if(splicep1<splicep2){
                    							if(b>=9)
                    								ifgood=1;
                    							else
                    								ifgood=0;
                    						}
                    						else{
                        						if(b>=10)
                        							ifgood=1;
                        						else
                        							ifgood=0;
                        						}
                						}
                					}
                					//System.out.println("ref ="+refsite);
                					//System.out.println("read="+readsite);
                				}
                				if(site.equals("CTAC")){
                					if(pp1==-1){
                						pp1=mv;
                						splice1group[1][tn]=splicep1-mv;
                						splice2group[1][tn]=splicep2+2-mv;
                						int d=0,b=0;
                						char chref[]=refsite.toCharArray();
                						char chread[]=readsite.toCharArray();
                						while(d<chref.length){
                							if(chref[d]==chread[d])
                								b++;
                							d++;
                						}
                						if(splicep1<splicep2){
                							if(b>=9)
                								ifgood=1;
                							else
                								ifgood=0;
                						}
                						else{
                    						if(b>=10)
                    							ifgood=1;
                    						else
                    							ifgood=0;
                    						}

                					}
                					else{
                						if(pp1>mv){
                    						pp1=mv;
                    						splice1group[1][tn]=splicep1-mv;
                    						splice2group[1][tn]=splicep2+2-mv;
                    						int d=0,b=0;
                    						char chref[]=refsite.toCharArray();
                    						char chread[]=readsite.toCharArray();
                    						while(d<chref.length){
                    							if(chref[d]==chread[d])
                    								b++;
                    							d++;
                    						}
                    						if(splicep1<splicep2){
                    							if(b>=9)
                    								ifgood=1;
                    							else
                    								ifgood=0;
                    						}
                    						else{
                        						if(b>=10)
                        							ifgood=1;
                        						else
                        							ifgood=0;
                        						}
                						}
                					}
                				//	System.out.println("ref ="+refsite);
                				//	System.out.println("read="+readsite);

                				}
                				}
                				mv--;
                			}
                			mv=0;
                			while(mv<=gap+5&&splicep1+2+mv<=rseq.length()&&splicep2+2+mv<=rseq.length()&&readcut+mv+5<=seq.length()){
                				splice1=rseq.substring(splicep1+mv,splicep1+2+mv).toUpperCase();
                				splice2=rseq.substring(splicep2+mv,splicep2+2+mv).toUpperCase();
                				site=splice1+splice2;
                				refsplice1=rseq.substring(splicep1-5+mv,splicep1+mv).toUpperCase();
                				refsplice2=rseq.substring(splicep2+mv+2,splicep2+mv+2+5).toUpperCase();
                				refsite=refsplice1+refsplice2;
                				readsite=seq.substring(readcut+mv-5,readcut+mv+5);

                			//	System.out.println(site+" "+(splicep1+mv)+" "+(splicep2+2+mv));
                				if(site.equals("GTAG")){
                					if(pp0==-1){
                						pp0=Math.max(0,mv-gap);
                						splice1group[0][tn]=splicep1+mv;
                						splice2group[0][tn]=splicep2+2+mv;
                						int d=0,b=0;
                						char chref[]=refsite.toCharArray();
                						char chread[]=readsite.toCharArray();
                						while(d<chref.length){
                							if(chref[d]==chread[d])
                								b++;
                							d++;
                						}
                						if(splicep1<splicep2){
                							if(b>=9)
                								ifgood=1;
                							else
                								ifgood=0;
                						}
                						else{
                    						if(b>=10)
                    							ifgood=1;
                    						else
                    							ifgood=0;
                    						}
                					}
                					else{
                						
                						if(pp0>Math.max(0,mv-gap)){
                    						pp0=Math.max(0,mv-gap);
                    						splice1group[0][tn]=splicep1+mv;
                    						splice2group[0][tn]=splicep2+2+mv;
                    						int d=0,b=0;
                    						char chref[]=refsite.toCharArray();
                    						char chread[]=readsite.toCharArray();
                    						while(d<chref.length){
                    							if(chref[d]==chread[d])
                    								b++;
                    							d++;
                    						}
                    						if(splicep1<splicep2){
                    							if(b>=9)
                    								ifgood=1;
                    							else
                    								ifgood=0;
                    						}
                    						else{
                        						if(b>=10)
                        							ifgood=1;
                        						else
                        							ifgood=0;
                        						}
                						}
                					}
                				//	System.out.println("ref ="+refsite);
                				//	System.out.println("read="+readsite);

                				}
                				if(site.equals("CTAC")){
                					if(pp1==-1){
                						pp1=Math.max(0,mv-gap);
                						splice1group[1][tn]=splicep1+mv;
                						splice2group[1][tn]=splicep2+2+mv;
                						int d=0,b=0;
                						char chref[]=refsite.toCharArray();
                						char chread[]=readsite.toCharArray();
                						while(d<chref.length){
                							if(chref[d]==chread[d])
                								b++;
                							d++;
                						}
                						if(splicep1<splicep2){
                							if(b>=9)
                								ifgood=1;
                							else
                								ifgood=0;
                						}
                						else{
                    						if(b>=10)
                    							ifgood=1;
                    						else
                    							ifgood=0;
                    						}
                					}
                					else{
                						if(pp1>Math.max(0,mv-gap)){
                    						pp1=Math.max(0,mv-gap);
                    						splice1group[1][tn]=splicep1+mv;
                    						splice2group[1][tn]=splicep2+2+mv;
                    						int d=0,b=0;
                    						char chref[]=refsite.toCharArray();
                    						char chread[]=readsite.toCharArray();
                    						while(d<chref.length){
                    							if(chref[d]==chread[d])
                    								b++;
                    							d++;
                    						}
                    						if(splicep1<splicep2){
                    							if(b>=9)
                    								ifgood=1;
                    							else
                    								ifgood=0;
                    						}
                    						else{
                        						if(b>=10)
                        							ifgood=1;
                        						else
                        							ifgood=0;
                        						}
                						}
                					}
                				//	System.out.println("ref ="+refsite);
                				//	System.out.println("read="+readsite);

                				}
                				
                				mv++;
                			}
                			if(pp0!=-1){
                				point0=point0+pp0;
                				p0++;
                			}
            				bound1=bound1+pp0+",";

                			if(pp1!=-1){
                				point1=point1+pp1;
                				p1++;
                			}
            				bound2=bound2+pp1+",";
            				good=good+ifgood;
            			//	System.out.println("**");
                			tn++;
                		}
                	//	System.out.println(p0+" "+p1);
                		tn=0;
                		t2=0;
                        String end="";
                        int junction=0;
                        if(p0<t-1&&p1<t-1){ 
                        	end="0";
                        	num8++;
}
                        else if(p0>p1||(p0==p1&&point0<=point1)){
                        	end="+";
                        	while(tn<=t-1){
                        		if(tn==0){
                        			//System.out.println(tmp_p2.length+" "+tn+" "+G02[tn][0]);
                        			tmp_p1[tn]=Integer.parseInt(G02[tn][3])+5;
                        			tmp_p2[tn]=splice1group[0][tn];
                        		}
                        		else if(tn==t-1){
                        			tmp_p1[tn]=splice2group[0][tn-1];
                        			sam5 f1=new sam5(G02[tn][5]);
                        			int[] f1p=f1.getpos();
                        			tmp_p2[tn]=Integer.parseInt(G02[tn][3])+f1p[1]-5;
                        		}
                        		else{
                        			tmp_p1[tn]=splice2group[0][tn-1];
                        			tmp_p2[tn]=splice1group[0][tn];
                        		}
                        		tn++;
                        	}
                        	tn=0;
                   			while(tn<=t-1){
                   			    int t11=0;
                   			    int pp=0;
                   			    if(tmp_p1[0]>tmp_p1[tn])
                   			    	junction=1;
                       			if(tn==0){
                       				exonstart[t2]=tmp_p1[tn];
                       				exonend[t2]=tmp_p2[tn];
                       				t2++;
                   			}
                       			else{

                       				while(t11<t2){
                       					if(exonstart[t11]<=tmp_p2[tn]&&exonend[t11]>=tmp_p1[tn]){
                       				//	if(exonstart[t11]<=Integer.parseInt(G02[tn][3])+8&&exonstart[t11]>=Integer.parseInt(G02[tn][3])-8&&exonend[t11]>=Integer.parseInt(G02[tn][3])+pos02[tn][1]-8&&exonend[t11]<=Integer.parseInt(G02[tn][3])+pos02[tn][1]+8){
                       						if(t11==0){
                       						exonstart[t11]=tmp_p1[tn];
                       						exonend[t11]=Math.max(exonend[t11],tmp_p2[tn]);
                       						if(tmp_p1[0]<exonstart[0])
                       							tmp_p1[0]=exonstart[0];
                       						}
                       						if(tn==t-1){
                       							exonstart[t11]=Math.min(exonstart[t11],tmp_p1[tn]);
                       							if(tmp_p2[tn]>exonend[t11])
                       								tmp_p2[tn]=exonend[t11];
                       						}
                       						else{
                        						exonstart[t11]=Math.min(exonstart[t11],tmp_p1[tn]);
                        						exonend[t11]=Math.max(exonend[t11],tmp_p2[tn]);

                       						}
                       					
                       						pp=1;
                       					}
                       					t11++;
                       				}
                       				if(pp==0){
                           				exonstart[t2]=tmp_p1[tn];
                           				exonend[t2]=tmp_p2[tn];
                           				t2++;
                   					}
                       			}
                       			tn++;
                   		}
                        }
                   
                		else{
                		    end="-";
                        	while(tn<=t-1){
                        		if(tn==0){
                        			tmp_n1[tn]=Integer.parseInt(G02[tn][3])+5;
                        			tmp_n2[tn]=splice1group[1][tn];
                        		}
                        		else if(tn==t-1){
                        			tmp_n1[tn]=splice2group[1][tn-1];
                        			sam5 f2=new sam5(G02[tn][5]);
                        			int[] f2p=f2.getpos();
                        			tmp_n2[tn]=Integer.parseInt(G02[tn][3])+f2p[1]-5;
                        		}
                        		else{
                        			tmp_n1[tn]=splice2group[1][tn-1];
                        			tmp_n2[tn]=splice1group[1][tn];
                        		}
                        		tn++;
                        	}
                        	tn=0;
                        	

                			while(tn<=t-1){
                			    int t11=0;
                			    int pp=0;
                   			    if(tmp_n1[0]>tmp_n1[tn])
                   			    	junction=1;
                    			if(tn==0){
                    				exonstart[t2]=tmp_n1[tn];
                    				exonend[t2]=tmp_n2[tn];
                    				t2++;
                			}
                    			else{

                    				while(t11<t2){
                    					if(exonstart[t11]<=tmp_n2[tn]&&exonend[t11]>=tmp_n1[tn]){
                                   				//	if(exonstart[t11]<=Integer.parseInt(G02[tn][3])+8&&exonstart[t11]>=Integer.parseInt(G02[tn][3])-8&&exonend[t11]>=Integer.parseInt(G02[tn][3])+pos02[tn][1]-8&&exonend[t11]<=Integer.parseInt(G02[tn][3])+pos02[tn][1]+8){
                                   						if(t11==0){
                                   						exonstart[t11]=tmp_n1[tn];
                                   						exonend[t11]=Math.max(exonend[t11],tmp_n2[tn]);
                                   						if(tmp_n1[0]<exonstart[0])
                                   							tmp_n1[0]=exonstart[0];
                                   						}
                                   						else if(tn==t-1){
                                   							exonstart[t11]=Math.min(exonstart[t11],tmp_n1[tn]);
                                   							if(tmp_n2[tn]>exonend[t11])
                                   								tmp_n2[tn]=exonend[t11];
                                   						}
                                   						else{
                                    						exonstart[t11]=Math.min(exonstart[t11],tmp_n1[tn]);
                                    						exonend[t11]=Math.max(exonend[t11],tmp_n2[tn]);

                                   						}
                       						pp=1;
                    					}
                    					t11++;
                    				}
                    				if(pp==0){
                        				exonstart[t2]=tmp_n1[tn];
                        				exonend[t2]=tmp_n2[tn];
                        				t2++;
                					}
                    			}
                    			tn++;
                		}
                		}
                		int temp11,temp2;
                		
                	    for(int i1=0;i1<t2;i1++){//
                	      for(int j=0;j<t2-i1-1;j++){//
                	        if(exonstart[j]>exonstart[j+1]){
                	          temp11=exonstart[j];
                	          temp2=exonend[j];
                	          exonstart[j]=exonstart[j+1];
                	          exonend[j]=exonend[j+1];
                	          exonstart[j+1]=temp11;
                	          exonend[j+1]=temp2;
                	        }
                	      }
                	    }
                	    int[] exs={};
                	    int[] exe={};
                        int over=0;
                	    if(end.equals("0")!=true){
                           if(end.equals("+")==true){
                        	   exs=tmp_p1;
                        	   exe=tmp_p2;
                           }
                           else {
                        	   exs=tmp_n1;
                        	   exe=tmp_n2;
                           }
                           
                           tn=0;
                           int kk=0;
                           while(tn<t2){
                        	   if(exs[0]>=exonstart[tn]&&exe[0]<=exonend[tn]==true){
                        		   kk=tn;
                        		   break;
                        	   }
                        	   tn++;
                           }
                           tn=0;
                           while(tn<=t-1){
                        	   if(exs[tn]<exonstart[kk]||exe[tn]>exonend[kk])
                        		   over=1;
                        	   tn++;
                        	   kk++;
                        	   if(kk==t2)
                        		   kk=0;
                           }
                           tn=0;
                           while(tn<=t-2){
                               int tnn=tn+1;
                        	   while(tnn<=t-1){
                                  if(exs[tn]<exe[tnn]&&exe[tn]>exs[tnn]&&(exs[tn]<exs[tnn]-3||exe[tn]<exe[tnn]-3))
                                     over=1;
                                 tnn++;
                        	   }
                        	   tn++;
                           }
                           if(good<t-1)
                        	   over=1;
                           if(over==1)
                        	   num9++;
                          //find gtag
                	    }
                           tn=0;
                       if(end.equals("0")!=true&&over==0){
                    	   String id;
                    	   if(junction==0&&yes==2)
                    		   id="No BSJ";
                    	   else
                    		   id=G02[tn][2]+":"+(exonstart[0]+1)+"|"+exonend[t2-1];
                    	   String state;
                    	   if(yes==1){
                    		   state="Full";
                    		   fullnum++;
                    		   }
                    	   else{
                    		   state="Part";
                    	   		partnum++;}
                       String output="";
                       output=(G02[tn][0]+"	"+G02[tn][2]+"	"+id+"	"+end+"	"+state+"	");
                       while(tn<t2){
                    	   output=output+((exonstart[tn]+1)+"-"+exonend[tn]+",");
                    	   tn++;
                       }
                       output=output+("	");
                       tn=0;
                       while(tn<=t-1){
                    	   output=output+((exs[tn]+1)+"-"+exe[tn]+",");
                    	   tn++;
                       }
                       output=output+("	"+bound1+"	"+bound2);
                       outputlist.add(output);
                       num7++;
                       }
                       else{
                      //     writeStream1.write(G02[tn][0]+"	"+G02[tn][2]+"	"+G02[tn][2]+":"+(exonstart[0]+1)+"-"+exonend[t2-1]+"	"+end+"	");
                           while(tn<t2){
                     //   	   writeStream1.write((exonstart[tn]+1)+"-"+exonend[tn]+",");
                        	   tn++;
                           }
                           tn=0;
                           while(tn<=t-1){
                        	   int oo=0;
                        	   while(oo<8){
                       // 		   writeStream2.write(G02[tn][oo]+"	");
                        		   oo++;
                        	   }
                        	   tn++;
                       // 	   writeStream2.newLine();
                           }
                     //      writeStream1.write("	"+bound1+"	"+bound2);
                     //      writeStream1.newLine();
                       }
            	    }
            	    }
            	    }
        		}
        		n=0;
        	}
            A=line;
            line=reader_p.readLine();
        }
		long tend2=System.currentTimeMillis();
		System.out.println(seqn+" completed "+ ((tend2-tstart)/1000)+"s");
        reader_p.close();

		}
	}
		reader_r.close();
		int q=0;
		while(q<outputlist.size()){
			writeStreama3.write(outputlist.get(q));
			writeStreama3.newLine();
			q++;
		}
		q=0;
		while(q<outputlist2.size()){
			writeStream.write(outputlist2.get(q));
			writeStream.newLine();
			q++;
		}
		
        writeStream.close();
        writeStreama3.close();
     //   writeStream1.close();
    //    writeStream4.close();
     //   writeStream5.close();
    //    writeStream6.close();
       System.out.println("sam file = "+sam);
       System.out.println("candidate RO read number= "+total2);
       System.out.println();
   //    System.out.println("partly map=			"+num1);
   //    System.out.println("low mapping quality =	"+num2);
   //    System.out.println("forward=					"+num4);
   //    System.out.println("not map=					"+num5);
   //    System.out.println("map with gaps=			"+num6);
       
   //    System.out.println("num_r1="+num_r1+" num_r2="+num_r2);

   //    System.out.println("gtag result");
   //    System.out.println("ok read=   "+num7);
   //    System.out.println("No gtag=   "+num8);
   //    System.out.println("wrong  =   "+num9);
       System.out.println("Full_length merged RO reads number= "+fullnum);
       System.out.println("Only contain 5'RO merged RO reads number= "+partnum);
       
       System.out.println("see detail in file: "+out+"_ro2_info.list");

       return 0;
	

	}
	

}
