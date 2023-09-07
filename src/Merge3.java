import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Merge3 {
	public String ciri;
	public String ciri_as;
	public String ciri_ro;
	public String gtf;
	public String out;
	

	public Merge3(String ciri1,String ciri_as1,String ciri_ro1, String gtf1,String out1) {
		 this.ciri=ciri1;
		 this.ciri_as=ciri_as1;
		 this.ciri_ro=ciri_ro1;
		 this.gtf=gtf1;
		 this.out=out1;

	 }
	public int getmerge() throws IOException{
				System.out.println();
				System.out.println("Merge module start");
				System.out.println();
				System.out.println("Loading Annotation...");
				
		        long t1 = System.currentTimeMillis();
		        String line;
		        Map<String,List<String>> mapchr=new HashMap<String,List<String>>();
		        Map<String,List<int[]>> mapchr2=new HashMap<String,List<int[]>>();
		        ArrayList<String> q1= new ArrayList<String>();
		        int ifgtf=1;
		        if(!gtf.equals("")){
		        	FileReader in=new FileReader(gtf);
		        	LineNumberReader reader=new LineNumberReader(in);
		        	line=reader.readLine();
		        	while(line.split("	").length<5){
		        		line=reader.readLine();
		        	}

				List<String> chrlist=new ArrayList<String>();
				List<int[]> chrlist2=new ArrayList<int[]>();

		        while(line!=null){
		        	String[] tmp=line.split("	");
		        	if(mapchr.containsKey(tmp[0])){
		        			if(tmp[2].equals("exon")){
		            		String[] tz=(tmp[8]+" ").split("; ");
		            		int x=0;
		            		String value=null;
		            		while(x<tz.length){
		            			String[] ttz=tz[x].split(" ");
		            			if(ttz[0].equals("gene_id")){
		            				value=ttz[1].substring(1, ttz[1].length()-1);
		            				break;
		            			}
		            			x++;
		            		}
		            		int[] exonse={Integer.parseInt(tmp[3]),Integer.parseInt(tmp[4])};

		    			String exon=tmp[0]+"	"+tmp[3]+"	"+tmp[4]+"	"+value;
		    			mapchr.get(tmp[0]).add(exon);
		    			mapchr2.get(tmp[0]).add(exonse);

		        		}
		        	}
		        	else{
		    			chrlist=new ArrayList<String>();
		    			chrlist2=new ArrayList<int[]>();
		        		if(tmp[2].equals("exon")){
		            		String[] tz=(tmp[8]+" ").split("; ");
		            		int x=0;
		            		String value=null;
		            		while(x<tz.length){
		            			String[] ttz=tz[x].split(" ");
		            			if(ttz[0].equals("gene_id")){
		            				value=ttz[1].substring(1, ttz[1].length()-1);
		            				break;
		            			}
		            			x++;
		            		}
		            		int[] exonse={Integer.parseInt(tmp[3]),Integer.parseInt(tmp[4])};


		    			String exon=tmp[0]+"	"+tmp[3]+"	"+tmp[4]+"	"+value;
						mapchr.put(tmp[0], chrlist);
						mapchr.get(tmp[0]).add(exon);
						mapchr2.put(tmp[0], chrlist2);
						mapchr2.get(tmp[0]).add(exonse);
		        		}
		        	}
		        	line=reader.readLine();
		        }
		        reader.close();
		        
		        System.out.println("Annotation Loaded");
		        }
		        else{
		        	ifgtf=0;
			        System.out.println("Annotation file not set");
		        }
		        //Load_CIRI_AS
		        System.out.println("Loading CIRI_AS output...");
		        int asnum=0;
		        int ronum=0;
		        Map<String,List<String>> as_chr=new HashMap<String,List<String>>();
		        if(!ciri_as.equals("")){
		        FileReader in_as=new FileReader(ciri_as);
		        LineNumberReader reader_as=new LineNumberReader(in_as);
		        line=reader_as.readLine();
		        line=reader_as.readLine();
				List<String> chr_as=new ArrayList<String>();
		        while(line!=null){
		        	String[] tmp=line.split("	");
		        	if(as_chr.containsKey(tmp[1])){
		    			String tmpline=tmp[0]+"	"+tmp[1]+"	"+tmp[2]+"	"+tmp[3]+"	"+tmp[4]+"	"+tmp[5];
		    			as_chr.get(tmp[1]).add(tmpline);
		        	}
		        	else{
		        		chr_as=new ArrayList<String>();
		    			String tmpline=tmp[0]+"	"+tmp[1]+"	"+tmp[2]+"	"+tmp[3]+"	"+tmp[4]+"	"+tmp[5];
		    			as_chr.put(tmp[1],chr_as);
		    			as_chr.get(tmp[1]).add(tmpline);
		        	}
		        	asnum++;
		        	line=reader_as.readLine();
		        }
		        reader_as.close();
		        System.out.println("CIRI_AS output Loaded");
		        }
		        else{
			        System.out.println("CIRI-AS output file not set");
		        }
		        //Load CIRI_exp
		        System.out.println("Loading CIRI output...");
		        FileReader in_ciri=new FileReader(ciri);
		        LineNumberReader reader_ciri=new LineNumberReader(in_ciri);
		        line=reader_ciri.readLine();
		        line=reader_ciri.readLine();
		        Map<String, Integer> ciri_exp = new HashMap<String, Integer>();
		        Map<String, String> ciri_strain = new HashMap<String, String>();
		        int maxexp=0;

		        while(line!=null){
		        	String[] tmpc=line.split("	");
		        	int exp=Integer.parseInt(tmpc[4]);
		        	maxexp=Math.max(exp, maxexp);
		        	ciri_exp.put(tmpc[0],exp);
		        	ciri_strain.put(tmpc[0],tmpc[10]);
		        	line=reader_ciri.readLine();
		        }
		        reader_ciri.close();
		        System.out.println("CIRI output Loaded");
		        //Load_RO_reads
		        System.out.println("Loading CIRI_RO output...");
		        List<String> ro=new ArrayList<String>();
		        if(!ciri_ro.equals("")){

		        FileReader in_ro=new FileReader(ciri_ro);
		        LineNumberReader reader_ro=new LineNumberReader(in_ro);
		        line=reader_ro.readLine();
		        line=reader_ro.readLine();
		        while(line!=null){
		        	ro.add(line);
		        	String[] tmp=line.split("	");
		        	if(!tmp[2].equals("No BSJ")){
		        		if(!ciri_strain.containsKey(tmp[2])){
				        	ciri_strain.put(tmp[2],tmp[3]);
		        		}
		        	}
		        	line=reader_ro.readLine();
		        	ronum++;
		        }
		        reader_ro.close();
		        }
		        System.out.println("CIRI_RO output Loaded");
		        //bsj-->ciri_RO
		        System.out.println("Combine AS and RO output");
		        int roreadnum=0;
		        Map<String, String> as_ro_result=new HashMap<String, String>();
		        Iterator<String> ro_iter = ro.iterator();
		        while(ro_iter.hasNext()){
		        String ro_line=ro_iter.next();
		    	String[] tmp=ro_line.split("	");
		    	String[] tmp2=tmp[6].split(",");
		    	String chrnon=tmp[1];
		    	int n=0;
		    	int[] rostart=new int[tmp2.length];
		    	int[] roend=new int[tmp2.length];
		    	while(n<tmp2.length){
		    		String[] tmp3=tmp2[n].split("-");
		    		rostart[n]=Integer.parseInt(tmp3[0]);
		    		roend[n]=Integer.parseInt(tmp3[1]);
		    		n++;
		    	}
		    	n=0;
		    	int bestcirexon=0;
		    	int bestexon=0;
		    	int bestexp=0;
		    	String bsj=null;
		    	if(as_chr.containsKey(chrnon)){
		    	Iterator<String> as_iter = as_chr.get(chrnon).iterator();
		    	while(as_iter.hasNext()){
		    		String as_line=as_iter.next();
		    		int in_c=0;
		    		int exon_c=0;
		    		int cirexon_c=0;
		    		String[] tmpa1=as_line.split("	");
		    			int c_start=Integer.parseInt(tmpa1[2]);
		    			int c_end=Integer.parseInt(tmpa1[3]);
		    			int i=0;
		    			if(tmp[2].equals("No BSJ")!=true){
		    				String[] tmp1=tmp[2].split("\\:");
		    				String[] tmp11=tmp1[1].split("\\|");
		    				int start=Integer.parseInt(tmp11[0]);
		    				int end=Integer.parseInt(tmp11[1]);
		    				if(Math.abs(start-c_start)<=3&&Math.abs(end-c_end)<=3){
		    					bsj=tmpa1[0];
		        				break;
		    				}
		    			}
		    			else{
		    			while(i<tmp2.length){
		        		//	System.out.println(rostart[i]+" "+roend[i]);
		    				if(rostart[i]>=c_start&&roend[i]<=c_end){
		    					in_c++;
		    				}
		    				i++;
		    			}
		    			if(in_c==i){
						int n1=0;
		    			while(n1<tmp2.length){
		            		if(tmpa1[4].equals("")!=true && tmpa1[4].equals("n/a")!=true){
		            			String[] exon=tmpa1[4].split(",");
		            			i=0;
		    					while(i<exon.length){
		    						String[] s_e=exon[i].split(":");
		    						int e_start=Integer.parseInt(s_e[0]);
		    						int e_end=Integer.parseInt(s_e[1]);
		    						if(e_start<roend[n1]&&e_end>rostart[n1]){
		    							exon_c++;
		    							break;
		    						}
		    						i++;
		    						}
		            			}
		            		i=0;
		        			if(tmpa1[5].equals("n/a")!=true){
		        				tmpa1[5]=tmpa1[5].replace("(ICF)", "");
		        				tmpa1[5]=tmpa1[5].replace("(intron_retention)", "");

		        				String[] cirexon=tmpa1[5].split(",");
		        				while(i<cirexon.length){
		    						String[] s_e=cirexon[i].split(":");
		    						int e_start=Integer.parseInt(s_e[0]);
		    						int e_end=Integer.parseInt(s_e[1]);
		    						if(e_start<roend[n1]&&e_end>rostart[n1]){
		    							cirexon_c++;
		    							break;
		    						}
		        					i++;
		        				}
		    				}
							n1++;
		    			}
		    			int ex=ciri_exp.get(tmpa1[0]);
		    		//	System.out.println(tmp[0]+"	"+tmpa1[0]+"	"+in_c+" "+exon_c+" "+cirexon_c+" "+ex);
		    			if(in_c==0){
		    				bestexon=exon_c;
		    				bestcirexon=cirexon_c;
		    				bestexp=ex;
		    				bsj=tmpa1[0];
		    			}
		    			else if(bestcirexon<cirexon_c){
		    				bestexon=exon_c;
		    				bestcirexon=cirexon_c;
		    				bestexp=ex;
		    				bsj=tmpa1[0];
		    			}
		    			else if(bestcirexon==cirexon_c){
		    				if(bestexp<ex){
		        				bestexon=exon_c;
		        				bestcirexon=cirexon_c;
		        				bestexp=ex;
		        				bsj=tmpa1[0];
		    				}
		    				else if(bestexp==ex){
		    					if(bestexon<exon_c){
		            				bestexon=exon_c;
		            				bestcirexon=cirexon_c;
		            				bestexp=ex;
		            				bsj=tmpa1[0];
		    					}
		    					}
		    				}
		    			}
		    			}
		    		
		    	}
		    	
		        }
				//System.out.println("* "+tmp[0]+"	"+bsj+"	"+bestin+" "+bestexon+" "+bestcirexon+" "+bestexp);
				if(bsj!=null){
				if(as_ro_result.containsKey(bsj)){
					String tmpr=as_ro_result.get(bsj);
					tmpr=tmpr+tmp[0]+"##"+tmp[6]+"&&";
					as_ro_result.put(bsj,tmpr);
				}
				else{
					as_ro_result.put(bsj,tmp[0]+"##"+tmp[6]+"&&");
				}
				roreadnum++;
				ro_iter.remove();
				//System.out.println(tmp[0]+"##"+tmp[6]+"&&");
				}
		        }
				System.out.println("Combine completed, "+roreadnum+" reads are used");

		      //as-bsj result 
		  //      BufferedWriter writeStream=new BufferedWriter(
		 //	    		new FileWriter(ciri_as+".MIX.txt"));
		 //       writeStream.write("BSJ	Chr	Start	End	GTF-annotated_exon	Cirexon	Coveage	BSJ_reads_information RO_reads_information Original_gene");
		 //       writeStream.newLine();
		        FileReader in_as2=new FileReader(ciri_as);
		        LineNumberReader reader_as2=new LineNumberReader(in_as2);
		        line=reader_as2.readLine();
		        line=reader_as2.readLine();
		        while(line!=null){
		        	String[] tmpf=line.split("	");
		        	//junction to cirexon
		        	String[] cirexon=new String[100];
		        	String outline;
		        	if(as_ro_result.containsKey(tmpf[0])){
		        		int cn=0;
		        		if(tmpf[5].equals("n/a")!=true){
		        		String ex1=tmpf[5].replace("(ICF)", "");
		        		ex1=ex1.replace("(intron_retention)", "");
		        		String[] tmpc=ex1.split(",");
		        		while(cn<tmpc.length){
		        			cirexon[cn]=tmpc[cn];
		        			cn++;
		        		}
		        		}
		        		String[] tmpstring=tmpf[7].split("<");
		        		int o=0;
		        		int[] jstart=new int[500];
		        		int[] jend=new int[500];
		        		

		        		while(o<tmpstring.length-2){	
		        			String[] tmpstring1=tmpstring[o+1].split("::");
		        			String[] tmpstring2=tmpstring1[0].split(":");
		        			jstart[o]=Integer.parseInt(tmpstring2[0]);
		        			jend[o]=Integer.parseInt(tmpstring2[1]);
		        			o++;
		        		}
		        		String tmpn=as_ro_result.get(tmpf[0]);
		        		String[] tmplong=tmpn.split("&&");
		        		int ol=0;
		        		while(ol<tmplong.length){
		        			String[] tmplong1=tmplong[ol].split("##");
		        			String[] tmplong2=tmplong1[1].split(",");
		        			int o1=0;
		        			while(o1+1<tmplong2.length){
		        				String[] tmpj1=tmplong2[o1].split("-");
		        				String[] tmpj2=tmplong2[o1+1].split("-");
		        				int tmpjs=Integer.parseInt(tmpj1[1]);
		        				int tmpje=Integer.parseInt(tmpj2[0]);
		        				int q=0;
		        				int ov=0;
		        				while(q<o){
		        					if(tmpjs==jstart[q]&&tmpje==jend[q]){
		        						ov=1;
		        						break;
		        					}
		        					q++;
		        				}
		        				if(ov==0){
		        					jstart[o]=tmpjs;
		        					jend[o]=tmpje;
		        					o++;
		        				}
		        				o1++;
		        			}
		        		ol++;
		        		}
		    	        int start=Integer.parseInt(tmpf[2]);
		    	        int end=Integer.parseInt(tmpf[3]);
		        		jstart[o]=end;
		        		jend[o]=start;
		        		o++;
		        		int cni=0;
		        		while(cni<cn){
		        			String[] tmp=cirexon[cni].split(":");
		        			jstart[o]=Integer.parseInt(tmp[1]);
		        			jend[o]=Integer.parseInt(tmp[0]);
		        			cni++;
		        			o++;
		        		}
		        		
		    	        for(int a1=0;a1<o-1;a1++){
		   	         	 for(int a2=0;a2<o-1-a1;a2++){
		   	         		 if(jstart[a2]<jstart[a2+1]){
		   	         			 int temp1=jstart[a2];
		   	         			 jstart[a2]=jstart[a2+1];
		   	         			 jstart[a2+1]=temp1;
		   	         			 }
		   	          		}
		   	          	 }
		    	        for(int a1=0;a1<o-1;a1++){
		      	         	 for(int a2=0;a2<o-1-a1;a2++){
		      	         		 if(jend[a2]<jend[a2+1]){
		      	         			 int temp2=jend[a2];
		      	         			 jend[a2]=jend[a2+1];
		      	         		   	 jend[a2+1]=temp2;
		      	         			 }
		      	          		}
		      	          	 }

		    	        int n=0;
		    	        while(n<o){    	        					
		    	        	String tmpcir=null;
		    	        		int i1=0;
		    	        		int minlength=400;
		    	        		while(i1<o){

		    	        			if(jstart[i1]-jend[n]<=400&&jstart[i1]-jend[n]>0){
		    	        				minlength=Math.min(minlength, jstart[i1]-jend[n]);
		    	        				if(minlength==jstart[i1]-jend[n]){
		    	        					tmpcir=jend[n]+":"+jstart[i1];
		    	        				}
		    	        			}
		    	        			i1++;
		    	        		}
		    	        		    	        	
		    	        	if(tmpcir!=null){
		    	        		int i=0;
		    	        		int have=0;
		    	        		while(i<cn){
		    	        			if(cirexon[i].equals(tmpcir)==true){
		    	        				have=1;
		    	        				break;
		    	        			}
		    	        			i++;
		    	        		}
		    	        		if(have==0){
		    	        			cirexon[cn]=tmpcir;
		    	        			cn++;
		    	        		//	System.out.println(tmpcir);
		    	        		}
		    	        	}
		    	        	n++;
		    	        }
		    	        int i=0;
		    	        String allcirexon="";
		    	        while(i<cn){
		    	        	allcirexon=allcirexon+cirexon[i]+",";
		    	        	i++;
		    	        }
		    	        if(cn==0)
		    	        	allcirexon="n/a";
		    	        if(tmpf[4].equals(""))
		    	        	tmpf[4]="n/a";
		    	       // System.out.println(allcirexon);
		    	        outline=tmpf[0]+"	"+tmpf[1]+"	"+tmpf[2]+"	"+tmpf[3]+"	"+tmpf[4]+"	"+allcirexon+"	"+tmpf[6]+"	"+tmpf[7]+"	"+tmpn;
		        	}
		        	else{
		    	        if(tmpf[4].equals(""))
		    	        	tmpf[4]="n/a";
		    	        outline=tmpf[0]+"	"+tmpf[1]+"	"+tmpf[2]+"	"+tmpf[3]+"	"+tmpf[4]+"	"+tmpf[5]+"	"+tmpf[6]+"	"+tmpf[7]+"	n/a";
		        	}
		        	String genid="n/a";
		        	if(mapchr.containsKey(tmpf[1])){
			        Iterator<String> gtfid=mapchr.get(tmpf[1]).iterator();
			        while(gtfid.hasNext()){
			        	String tq=gtfid.next();
			        	String[] tm=tq.split("	");
			        	int st=Integer.parseInt(tm[1]);
			        	int en=Integer.parseInt(tm[2]);
			        	if(st<Integer.parseInt(tmpf[3])&&en>Integer.parseInt(tmpf[2])){
			        		genid=tm[3];
			        		break;
			        	}
			        }
		        	}
		        	String strain;
		        	if(ciri_strain.containsKey(tmpf[0])){
		        		strain = ciri_strain.get(tmpf[0]);
		        	}
		        	else{
		        		strain = "none";
		        	}
		        	outline=outline+"	"+genid+"	"+strain;
			        q1.add(outline);

		        	line=reader_as2.readLine();
		        }
		        reader_as2.close();
		        ////ro_full_line_cluster
		        Iterator<String> ro_iter2=ro.iterator();
		        int totalnum=asnum+ronum;
		        
				int[][] exonstart=new int[totalnum][100];
			    int[][] exonend=new int[totalnum][100];//[junction number][exonnumber]
			    int[][] exonstate=new int[totalnum][100];//0,1=left ,2=right
			    int[] readsnum=new int[totalnum];//reads
			    int[] readsnum2=new int[totalnum];
			    String[] chr=new String[totalnum];
			    int[] start =new int[totalnum];
			    int[] end= new int[totalnum];
			    String[][] readname=new String[totalnum][500];
			    int total=0;
			    int[] exonnum=new int[80000];
			    
			    while(ro_iter2.hasNext()){
			    String tmpline=ro_iter2.next();
		    	String[] tmp=tmpline.split("	");
		    	if(tmp[4].equals("Part")!=true){
		    	String[] tmp2=tmp[5].split(",");
		    	int i=0;
		    	int[] tmpstart=new int[20];
		    	int[] tmpend=new int[20];
		    	String chr1=tmp[1];
		    	while(i<tmp2.length){
		    		String[] tmp3=tmp2[i].split("-");
		    		tmpstart[i]=Integer.parseInt(tmp3[0]);
		    		tmpend[i]=Integer.parseInt(tmp3[1]);
		    		i++;
		    	}
		    	if(tmp[3].equals("0")!=true){
		    	int n=0,pp=0;
		    	while(n<total){
		  //  		System.out.println(tmpstart[0]+"	"+start[n]+"	"+tmpend[i-1]+"	"+end[n]+"	"+chr1+"	"+chr[n]);
		  //  			System.out.println("yes");
		    		if(Math.abs(tmpstart[0]-start[n])<4&&Math.abs(tmpend[i-1]-end[n])<4&&chr[n].equals(chr1)==true){//
		    	//		   System.out.print("yes!");	
		    		   readsnum[n]++;
		    		   readname[n][readsnum[n]-1]=tmp[0]+"##"+tmp[6]+"&&";
		    		   int nn=0;
		    		   while(nn<tmp2.length){
		    			   int nnn=0;
		    			   int pp2=0;
		    			   while(nnn<exonnum[n]){
		    				   if(tmpstart[nn]<exonstart[n][nnn]+4&&tmpstart[nn]>exonstart[n][nnn]-4&&tmpend[nn]<exonend[n][nnn]+4&&tmpend[nn]>exonend[n][nnn]-4){
		    				   //if(tmpstart[nn]<=exonend[n][nnn]&&tmpend[nn]>=exonstart[n][nnn]){
		    					   exonstart[n][nnn]=Math.min(exonstart[n][nnn], tmpstart[nn]);
		    					   exonend[n][nnn]=Math.max(exonend[n][nnn], tmpend[nn]);
		    					   pp2=1;
		    					   break;
		    				   }
		    				   nnn++;
		    			   }
		    			   if(pp2==0){
		    				   exonstart[n][exonnum[n]]=tmpstart[nn];
		    				   exonend[n][exonnum[n]]=tmpend[nn];
		    				   exonstate[n][exonnum[n]]=0;
		    				   exonnum[n]++;
		    			   }
		    			   nn++;
		    		   }
		    		   pp=1;
		    	       break;
		    		}
		    		n++;
		    	}
		    	if(pp==0){
		    		start[total]=tmpstart[0];
		    		end[total]=tmpend[i-1];
		    		chr[total]=chr1;
		    		readsnum[total]=1;
		    		readname[total][0]=tmp[0]+"##"+tmp[6]+"&&";
		    		i=0;
		    		while(i<tmp2.length){
		    			exonstart[total][i]=tmpstart[i];
		    			exonend[total][i]=tmpend[i];
		    			exonstate[total][i]=0;
		    			exonnum[total]++;
		    			i++;
		    		}
		    		total++;
		    	}
		    	}
		    	ro_iter2.remove();
		    	}
			}
			    //ro_part_line_cluster
		        Iterator<String> ro_iter3=ro.iterator();
		        while(ro_iter3.hasNext()){
		        	String tmpline=ro_iter3.next();
		        	String[] tmp=tmpline.split("	");
		        	if(tmp[2].equals("No BSJ")!=true){
		        	String[] tmp2=tmp[6].split(",");
		        	String[] tt=tmp[2].split("\\:");
		        	String[] ttt=tt[1].split("\\|");
		        	int ts=Integer.parseInt(ttt[0]);
		        	int te=Integer.parseInt(ttt[1]);

		        	int i=0;
		        	int[] tmpstart=new int[20];
		        	int[] tmpend=new int[20];
		        	String chr1=tmp[1];
		        	while(i<tmp2.length){
		        		String[] tmp3=tmp2[i].split("-");
		        		tmpstart[i]=Integer.parseInt(tmp3[0]);
		        		tmpend[i]=Integer.parseInt(tmp3[1]);
		        		i++;
		        	}
		        	if(tmp[3].equals("0")!=true){
		        	int n=0,pp=0;
		        	while(n<total){
		      //  		System.out.println(tmpstart[0]+"	"+start[n]+"	"+tmpend[i-1]+"	"+end[n]+"	"+chr1+"	"+chr[n]);
		      //  			System.out.println("yes");
		        		if(Math.abs(ts-start[n])<4&&Math.abs(te-end[n])<4&&chr[n].equals(chr1)==true){//
		        	//		   System.out.print("yes!");	
		        		   readsnum2[n]++;
		        		   readsnum[n]++;
		        		   readname[n][readsnum[n]-1]=tmp[0]+"##"+tmp[6]+"&&";
		        		   int nn=0;
		        		   while(nn<tmp2.length){
		        			   int nnn=0;
		        			   int pp2=0;
		        			   while(nnn<exonnum[n]){
		        				   if(exonstate[n][nnn]==0){
		        					   if(nn==0){
		        					   if(exonstart[n][nnn]<=tmpstart[nn]&&Math.abs(tmpend[nn]-exonend[n][nnn])<4){
		        						   //if(tmpstart[nn]<=exonend[n][nnn]&&tmpend[nn]>=exonstart[n][nnn]){
		        						   exonstart[n][nnn]=Math.min(exonstart[n][nnn], tmpstart[nn]);
		        						   exonend[n][nnn]=Math.max(exonend[n][nnn], tmpend[nn]);
		        						   pp2=1;
		        						   break;
		        					   	}
		        					   }
		        					   else if(nn==tmp2.length-1){
		            					   if(exonend[n][nnn]>=tmpend[nn]&&Math.abs(tmpstart[nn]-exonstart[n][nnn])<4){
		            						   //if(tmpstart[nn]<=exonend[n][nnn]&&tmpend[nn]>=exonstart[n][nnn]){
		            						   exonstart[n][nnn]=Math.min(exonstart[n][nnn], tmpstart[nn]);
		            						   exonend[n][nnn]=Math.max(exonend[n][nnn], tmpend[nn]);
		            						   pp2=1;
		            						   break;
		            					   }
		        					   }
		        					   else{
		            					   if(Math.abs(exonstart[n][nnn]-tmpstart[nn])<4&&Math.abs(tmpend[nn]-exonend[n][nnn])<4){
		            						   //if(tmpstart[nn]<=exonend[n][nnn]&&tmpend[nn]>=exonstart[n][nnn]){
		            						   exonstart[n][nnn]=Math.min(exonstart[n][nnn], tmpstart[nn]);
		            						   exonend[n][nnn]=Math.max(exonend[n][nnn], tmpend[nn]);
		            						   pp2=1;
		            						   break;
		            					   }
		        					   }
		        				   }
		        					   else if(exonstate[n][nnn]==1){
		        						   if(nn==0){
		        							   if(Math.abs(tmpend[nn]-exonend[n][nnn])<4){
		        								   exonstart[n][nnn]=Math.min(exonstart[n][nnn], tmpstart[nn]);
		                						   exonend[n][nnn]=Math.max(exonend[n][nnn], tmpend[nn]);
		        								   exonstate[n][nnn]=1;
		        								   pp2=1;
		        								   break;
		        							   }
		        						   }
		        						   else if(nn==tmp2.length-1){
		        							   if(exonstart[n][nnn]>=tmpstart[nn]&&exonend[n][nnn]>=tmpend[nn]&&exonend[n][nnn]-tmpstart[nn]<=300){
		            						   exonstart[n][nnn]=Math.min(exonstart[n][nnn], tmpstart[nn]);
		            						   exonend[n][nnn]=Math.max(exonend[n][nnn], tmpend[nn]);
		        							   exonstate[n][nnn]=0;
		    								   pp2=1;
		    								   break;
		        							   }
		        						   }
		        						   else{
		        							   if(exonstart[n][nnn]>=tmpstart[nn]&&Math.abs(tmpend[nn]-exonend[n][nnn])<4){
		                						   exonstart[n][nnn]=Math.min(exonstart[n][nnn], tmpstart[nn]);
		                						   exonend[n][nnn]=Math.max(exonend[n][nnn], tmpend[nn]);
		            							   exonstate[n][nnn]=0;
		        								   pp2=1;
		        								   break;
		        							   }
		        						   }
		        					   }
		        					   else{
		        						   if(nn==0){
		        							   if(exonend[n][nnn]<=tmpend[nn]&&exonstart[n][nnn]<=tmpstart[nn]&&tmpend[nn]-exonstart[n][nnn]<300){
		                						   exonstart[n][nnn]=Math.min(exonstart[n][nnn], tmpstart[nn]);
		                						   exonend[n][nnn]=Math.max(exonend[n][nnn], tmpend[nn]);
		            							   exonstate[n][nnn]=0;
		        								   pp2=1;
		        								   break;
		        						   }
		        						   }
		        						   else if(nn==tmp2.length-1){
		        							   if(Math.abs(tmpstart[nn]-exonstart[n][nnn])<4){
		                						   exonend[n][nnn]=Math.max(exonend[n][nnn], tmpend[nn]);
		            							   exonstate[n][nnn]=2;
		            							   pp2=1;
		            							   break;
		        							   }
		        						   }
		        						   else{
		        							   if(exonend[n][nnn]<=tmpend[nn]&&Math.abs(tmpstart[nn]-exonstart[n][nnn])<4){
		                						   exonstart[n][nnn]=Math.min(exonstart[n][nnn], tmpstart[nn]);
		                						   exonend[n][nnn]=Math.max(exonend[n][nnn], tmpend[nn]);
		            							   exonstate[n][nnn]=0;
		        								   pp2=1;
		        								   break;
		        							   }
		        						   }
		        					   }
		        				   nnn++;
		        				   }
		        			   
		        			   if(pp2==0){
		        				   exonstart[n][exonnum[n]]=tmpstart[nn];
		        				   exonend[n][exonnum[n]]=tmpend[nn];
		        				   if(nn==0){
		        					   exonstate[n][exonnum[n]]=1;
		        				   }
		        				   else if(nn==tmp2.length-1){
		        					   exonstate[n][exonnum[n]]=2;
		        				   }
		        				   else{
		        					   exonstate[n][exonnum[n]]=0;
		        				   }

		        				   exonnum[n]++;
		        				   
		        			   }
		        			   nn++;
		        		   }
		        		   pp=1;
		        	       break;
		        		}
		        		n++;
		        	}
		        	if(pp==0){
		        		start[total]=ts;
		        		end[total]=te;
		        		chr[total]=chr1;
		        		readsnum[total]=1;
		        		readsnum2[total]=1;
		        		readname[total][0]=tmp[0]+"##"+tmp[6]+"&&";
		        		i=0;
		        		if(tmpend[0]-tmpstart[tmp2.length-1]<400||tmpstart[0]-tmpend[tmp2.length-1]>0&&tmpstart[0]-tmpend[tmp2.length-1]<200){
		        			while(i<tmp2.length-1){
		            			if(i==0){
		            				exonstart[total][i]=tmpstart[tmp2.length-1];
		            				exonend[total][i]=tmpend[i];
		            			}
		            			else{
		                			exonstart[total][i]=tmpstart[i];
		                			exonend[total][i]=tmpend[i];
		            			}
		            			exonstate[total][i]=0;
		            			exonnum[total]++;
		            			i++;
		            		}
		        		}
		        		else{
		        		while(i<tmp2.length){
		        			exonstart[total][i]=tmpstart[i];
		        			exonend[total][i]=tmpend[i];
		        			if(i==0)
		        				exonstate[total][i]=1;
		        			else if(i==tmp2.length-1)
		        				exonstate[total][i]=2;
		        			else
		        				exonstate[total][i]=0;
		        			exonnum[total]++;
		        			i++;
		        		}
		        		}

		        		total++;
		        	}
		        	}
		        	}
		        }
		        int i=0;
		        String outline;
		        while(i<total){
		        	outline=chr[i]+":"+start[i]+"|"+end[i]+"	"+chr[i]+"	"+start[i]+"	"+end[i]+"	";
		        	List<int[]> gtf_exon = new ArrayList<int[]>();
		        	if(ifgtf==1) {
		        		Exon_gtf test=new Exon_gtf(start[i],end[i],mapchr2.get(chr[i]));
		        		gtf_exon=test.get_exon();
		        	}
		            Iterator<int[]> gtfexon=gtf_exon.iterator();
		            int dd=0;
		            
		            while(gtfexon.hasNext()){
		            	int[] t=gtfexon.next();
		            	outline=outline+t[0]+":"+t[1]+",";
		            	dd++;
		            }
		            if(dd==0)
		            	outline=outline+"n/a";
		            outline=outline+"	";
		            int q=0;
		            int qq=0;
		            while(q<exonnum[i]){
		            	if(exonstate[i][q]==0){
		            		outline=outline+exonstart[i][q]+":"+exonend[i][q]+",";
		            		qq++;
		            	}
		            	q++;
		            }
		            if(qq==0){
		            	outline=outline+"n/a";
		            }
		            outline=outline+"	n/a	n/a	";
		            q=0;
		            while(q<readsnum[i]){
		            	 outline=outline+readname[i][q];
		            	q++;
		            }
		            
		        	String genid="n/a";
		        	if(mapchr.containsKey(chr[i])){
			        Iterator<String> gtfid=mapchr.get(chr[i]).iterator();
			        while(gtfid.hasNext()){
			        	String tq=gtfid.next();
			        	String[] tm=tq.split("	");
			        	int st=Integer.parseInt(tm[1]);
			        	int en=Integer.parseInt(tm[2]);
			        	if(st<end[i]&&en>start[i]){
			        		genid=tm[3];
			        		break;
			        	}
			        }
		        	}
		        	
		        	String strain;
		        	String outbsj=chr[i]+":"+start[i]+"|"+end[i];
		        	if(ciri_strain.containsKey(outbsj)){
		        		strain = ciri_strain.get(outbsj);
		        	}
		        	else{
		        		strain = "none";
		        	}
		        	outline=outline+"	"+genid+"	"+strain;
		        	q1.add(outline);
		        	i++;
		        }
		        long t2 = System.currentTimeMillis();
		        
		      // output detail for figuring.
			        BufferedWriter writeStream=new BufferedWriter(
				    		new FileWriter(out+"_merge_circRNA_detail.anno"));
			        writeStream.write("BSJ	Chr	Start	End	GTF-annotated_exon	Cirexon	Coveage	BSJ_reads_information RO_reads_information Original_gene	strain");
			        writeStream.newLine();
		        	System.out.println("Outputing detail annotation file : "+out+"_merge_circRNA_detail.anno");
			        int u=0;
			        while(u<q1.size()){
			        	writeStream.write(q1.get(u));
			        	writeStream.newLine();
			        	u++;
			        }
			        writeStream.close();
		      
		        
		        System.out.println("Merge complete. Start reconstruction.");
		        System.out.println("Time: " + (t2-t1) /1000+"s");
		        
		        return 0;
	}
	

}
