import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CIRI_Full2 {
	public static void main(String[] args) throws InterruptedException, IOException {
		
		long tstart = System.currentTimeMillis();
		String v="Version: 2.1.2";
		String dir=System.getProperty("user.dir");
		int a=args.length;
		int help=0;
		List<String> z=new ArrayList<String>();
		int n=0;
		while(n<a){
			z.add(args[n]);
			n++;
		}
		//String jarFilePath = CIRI_Full2.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		//jarFilePath = java.net.URLDecoder.decode(jarFilePath, "UTF-8");  
		String jarFilePath = System.getProperty("java.class.path");
		int cdirn=jarFilePath.lastIndexOf("/");
		String cdir=jarFilePath.substring(0,cdirn+1)+"/";
		System.out.println("classpath = " + cdir );

		if(z.size()!=0){
		if(!z.get(0).equals("-h")){
			if(z.get(0).equals("Pipeline")){
			//get jar path
			if(z.indexOf("-h")==-1){
			String ciri="bin/CIRI_v2.0.6/CIRI2.pl";
			String as="bin/CIRI_AS_v1.2/CIRI_AS_v1.2.pl";
			String read1="";
			String read2="";
			String workdir=dir+"/CIRI-full_output";
			String ref="";
			String anno="";
			String prefix="out";
			int t=1;
			int breakflag=0;
			if(z.indexOf("-1")!=-1){//inputing read1
				read1=z.get(z.indexOf("-1")+1);
				if(!read1.substring(0,1).equals("/")){
					read1=dir+"/"+read1;
				}
				File test1=new File(read1);
				if(!test1.exists()){
					System.out.println("read_1.fq file not found. Exit");
					breakflag=1;
				}
			}
			else{
				System.out.println("'-1' option not found. read_1.fq file not set. Exit");
				breakflag=1;
			}
			
			if(z.indexOf("-2")!=-1){//inputing read2
				read2=z.get(z.indexOf("-2")+1);
				if(!read2.substring(0,1).equals("/")){
					read2=dir+"/"+read2;
				}
				File test1=new File(read2);
				if(!test1.exists()){
					System.out.println("read_2.fq file not found. Exit");
					breakflag=1;
				}
			}
			else{
				System.out.println("'-2' option not found. read_2.fq file not set. Exit");
				breakflag=1;
			}
			
			if(z.indexOf("-r")!=-1){//inputing refernce.fa
				ref=z.get(z.indexOf("-r")+1);
				if(!ref.substring(0,1).equals("/")){
					ref=dir+"/"+ref;
				}
				File test1=new File(ref);
				if(!test1.exists()){
					System.out.println("reference.fa file not found. Exit");
					breakflag=1;
				}
			}
			else{
				System.out.println("'-r' option not found. reference.fa file not set. Exit");
				breakflag=1;
			}
			
			if(z.indexOf("-a")!=-1){//inputing annoation gtf
				anno=z.get(z.indexOf("-a")+1);
				if(!anno.substring(0,1).equals("/")){
					anno=dir+"/"+anno;
				}
				File test1=new File(anno);
				if(!test1.exists()){
					System.out.println("GTF file not found. no annotation file set");
				}
			}
			else{
				System.out.println("'-a' option not found. no annotation file set");
			}
			if(z.indexOf("-t")!=-1){// inputing number_thread
				t=Math.max(1, Integer.parseInt(z.get(z.indexOf("-t")+1)));
			}
			
			if(z.indexOf("-d")!=-1){//inputing workdir
				workdir=z.get(z.indexOf("-d")+1);
				if(!workdir.substring(0,1).equals("/")){
					workdir=dir+"/"+workdir;
				}
				File test1=new File(workdir);
				if(!test1.exists()&&!test1 .isDirectory()){
					System.out.println("working dir is set to "+workdir);
				}
				else{
					System.out.println("dir was already existed. please check. Exit");
					breakflag=1;
			}
			}
			else{
				System.out.println("'-d' option not found. please set working dir");
				breakflag=1;
			}
			
			if(z.indexOf("-o")!=-1){// inputing number_thread
				prefix=z.get(z.indexOf("-o")+1);
				System.out.println("output prefix: "+prefix);
			}
			else{
				System.out.println("'-o' option not set, output prefix: "+prefix);
			}
			if(breakflag==0){
				File test1=new File(workdir);
				test1.mkdir();
				File test2=new File(workdir+"/CIRI_output");
				File test3=new File(workdir+"/CIRI-AS_output");
				File test4=new File(workdir+"/CIRI-full_output");
				File test5=new File(workdir+"/sam");
				test2.mkdir();
				test3.mkdir();
				test4.mkdir();
				test5.mkdir();
				//building sh;
		        BufferedWriter writeStream1=new BufferedWriter(
			    		new FileWriter(workdir+"/tmp1.sh"));
		        BufferedWriter writeStream2=new BufferedWriter(
			    		new FileWriter(workdir+"/tmp2.sh")); 
		        writeStream1.write("#!/bin/sh");
		        writeStream1.newLine();
		        	writeStream1.write("bwa mem -T 19 -t "+t+" "+ref+" "+read1+" "+read2+" > "+workdir+"/sam/"+prefix+"_ciri.sam");
		        	writeStream1.newLine();
		        
                if (!anno.equals("")) {
                    if(z.indexOf("-0")!=-1){
			            writeStream1.write("perl "+cdir+ciri+" -I "+workdir+"/sam/"+prefix+"_ciri.sam -O "+workdir+"/CIRI_output/"+prefix+".ciri -F "+ref+" -A "+anno+" -T "+t+" -0");
    		        }
		            else {
    			        writeStream1.write("perl "+cdir+ciri+" -I "+workdir+"/sam/"+prefix+"_ciri.sam -O "+workdir+"/CIRI_output/"+prefix+".ciri -F "+ref+" -A "+anno+" -T "+t);
       		        }
                } else {
                    if(z.indexOf("-0")!=-1){
                        writeStream1.write("perl "+cdir+ciri+" -I "+workdir+"/sam/"+prefix+"_ciri.sam -O "+workdir+"/CIRI_output/"+prefix+".ciri -F "+ref+" -T "+t+" -0");
                    }
                    else {
                        writeStream1.write("perl "+cdir+ciri+" -I "+workdir+"/sam/"+prefix+"_ciri.sam -O "+workdir+"/CIRI_output/"+prefix+".ciri -F "+ref+" -T "+t);
                    }
                }

		        writeStream1.newLine();
                if (!anno.equals("")) {
		            writeStream1.write("perl "+cdir+as+" -S "+workdir+"/sam/"+prefix+"_ciri.sam -C "+workdir+"/CIRI_output/"+prefix+".ciri -F "+ref+" -A "+anno+" -O "+workdir+"/CIRI-AS_output/"+prefix+" -D yes");
                } else {
                    writeStream1.write("perl "+cdir+as+" -S "+workdir+"/sam/"+prefix+"_ciri.sam -C "+workdir+"/CIRI_output/"+prefix+".ciri -F "+ref+" -O "+workdir+"/CIRI-AS_output/"+prefix+" -D yes");
                }
		        writeStream1.close();
		        String[] ciri_c={"sh",workdir+"/tmp1.sh"};
		        Process cirip= Runtime.getRuntime().exec(ciri_c);
		        final String dirz=workdir;
			    final InputStream is1 = cirip.getInputStream();
		         new Thread(new Runnable() {
		             public void run() {
		                 BufferedReader br = new BufferedReader(new InputStreamReader(is1));
		                 File log1=new File(dirz+"/ciri_pipe_1.log");
		     	         FileOutputStream out1 = null;
						try {
							out1 = new FileOutputStream(log1,true);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    	         PrintStream p1=new PrintStream(out1);
		                 String linez = null;
		                 try {
							while((linez = br.readLine()) != null) p1.println(linez);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                 p1.close();
		                 }
		             
		         }).start();
				    final InputStream is2 = cirip.getErrorStream();
			         new Thread(new Runnable() {
			             public void run() {
			                 BufferedReader br = new BufferedReader(new InputStreamReader(is2));
			                 File log2=new File(dirz+"/ciri_pipe_2.log");
			     	         FileOutputStream out1 = null;
							try {
								out1 = new FileOutputStream(log2,true);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			    	         PrintStream p1=new PrintStream(out1);
			                 String linez = null;
			                 try {
								while((linez = br.readLine()) != null) p1.println(linez);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			                 p1.close();
			                 }
			             
			         }).start();
				RO1_single ro1=new RO1_single(read1,read2,workdir+"/CIRI-full_output/"+prefix,95,13);
				int k=ro1.getro1();
				writeStream2.write("#!/bin/sh");
		        writeStream2.newLine();
				writeStream2.write("bwa mem -T 19 "+ref+" "+workdir+"/CIRI-full_output/"+prefix+"_ro1.fq > "+workdir+"/sam/"+prefix+"_ro.sam");
				writeStream2.close();
				String[] bwa_ro={"sh",workdir+"/tmp2.sh"};
				Process bwa= Runtime.getRuntime().exec(bwa_ro);
				System.out.println("Start running bwa mem for RO candidate reads");
			    final InputStream is3 = bwa.getInputStream();
		         new Thread(new Runnable() {
		             public void run() {
		                 BufferedReader br = new BufferedReader(new InputStreamReader(is3));
		                 File log1=new File(dirz+"/bwa_ro_1.log");
		     	         FileOutputStream out1 = null;
						try {
							out1 = new FileOutputStream(log1,true);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    	         PrintStream p1=new PrintStream(out1);
		                 String linez = null;
		                 try {
							while((linez = br.readLine()) != null) p1.println(linez);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                 p1.close();
		                 }
		             
		         }).start();
				    final InputStream is4 = bwa.getErrorStream();
			         new Thread(new Runnable() {
			             public void run() {
			                 BufferedReader br = new BufferedReader(new InputStreamReader(is4));
			                 File log2=new File(dirz+"/bwa_ro_2.log");
			     	         FileOutputStream out1 = null;
							try {
								out1 = new FileOutputStream(log2,true);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			    	         PrintStream p1=new PrintStream(out1);
			                 String linez = null;
			                 try {
								while((linez = br.readLine()) != null) p1.println(linez);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			                 p1.close();
			                 }
			             
			         }).start();
			        bwa.waitFor();
					RO2 ro2=new RO2(ref,workdir+"/sam/"+prefix+"_ro.sam",k,100000,workdir+"/CIRI-full_output/"+prefix);
					int k2=ro2.getro2();
					long t2  = System.currentTimeMillis();
					System.out.println("RO2 completed. Time used: "+ (t2-tstart)/1000+ "s. Waiting for the result of CIRI-AS");
			    
				cirip.waitFor();
				long t3  = System.currentTimeMillis();
				System.out.println("CIRI-AS completed. Time used: "+ (t3-tstart)/1000+ "s. Start Merge step");
				Merge3 merge=new Merge3(workdir+"/CIRI_output/"+prefix+".ciri",workdir+"/CIRI-AS_output/"+prefix+"_jav.list",workdir+"/CIRI-full_output/"+prefix+"_ro2_info.list",anno,workdir+"/CIRI-full_output/"+prefix);
				int k3=merge.getmerge();
				long t4  = System.currentTimeMillis();
				System.out.println("CIRI-full pipeline completed. Time used: "+ (t4-tstart)/1000+ "s. Output file is under dir: "+workdir);
			}
			else{
				System.out.println("option not enough or illegal, please type '-h' for detail usage");
			    }
			
			}
			else{
				System.out.println();
				System.out.println("Pipeline module is a pipeline for detecting and reconstructing circRNA.");
				System.out.println();
				System.out.println("Usage");
				System.out.println();
				System.out.println("java -jar CIRI-full.jar Pipeline [options]");
				System.out.println();
				System.out.println("Options:");
				System.out.println();
				System.out.println("-1		reads1 of paired-end reads (required, equal length, fastq or fastq.gz format)");
				System.out.println("-2		reads2 of paired-end reads (required, equal length, fastq or fastq.gz format)");
				System.out.println("-r		reference genome in fasta format, the same file used in preparation step when building bwa index (required). ");
				System.out.println("-a		annotation file of reference genome in GTF format (optional).");
				System.out.println("-o		prefix of output files (optional,default: out)");
				System.out.println("-d		dir of output files (require, please make sure that there is not same folder existed)");
				System.out.println("-t		number of thread use in CIRI and bwa mem (optional,default 1)");
				System.out.println("-0		output all circRNAs regardless junction read counts or PCC signals (option for CIRI)");
				System.out.println();
				System.out.println("Note: before running CIRI-full-pipe, please add bwa to $PATH and build bwa index. make sure that reference genome is under same dir with bwa index");
				System.out.println();

			}
		}
			else if(z.get(0).equals("RO1")){// RO1 mode selected
				if(z.indexOf("-h")==-1){ // -h
				String read1="";
				String read2="";
				String output=dir+"/out";
				int ml=13;
				double per=95;
				int breakflag=0;
				
				if(z.indexOf("-1")!=-1){//inputing read1
					read1=z.get(z.indexOf("-1")+1);
					if(!read1.substring(0,1).equals("/")){
						read1=dir+"/"+read1;
					}
				}
				else{
					System.out.println("read_1.fq file is not set. Exit");
					breakflag=1;
				}
				if(z.indexOf("-2")!=-1){//inputing read2
					read2=z.get(z.indexOf("-2")+1);
					if(!read2.substring(0,1).equals("/")){
						read2=dir+"/"+read2;
					}
				}
				else{
					System.out.println("read_2.fq file is not set. Exit");
					breakflag=1;
				}
				
				if(z.indexOf("-minM")!=-1){ //inputing minmatch
					ml=Integer.parseInt(z.get(z.indexOf("-minM")+1));
				}
				if(z.indexOf("-minI")!=-1){// inputing minindentity
					per=Double.parseDouble(z.get(z.indexOf("-minI")+1));
				}
				if(z.indexOf("-o")!=-1){
					output=z.get(z.indexOf("-o")+1);
					if(!output.substring(0,1).equals("/")){
						output=dir+"/"+output;
					}
				}
				System.out.println("Output prefix = "+output);

				
				if(breakflag==1){
					System.out.println();
					System.out.println("Options for RO1 are not enough or illegal, please check. Type '-h' for more detail.");
					System.out.println();
					System.out.println("Usage of RO1:");
					System.out.println();
					System.out.println("java -jar CIRI-Full.jar RO1 -1 read_1.fq -2 read_2.fq -o prefix -t number_thread");
					System.out.println();
				}
				else{
					RO1_single ro1=new RO1_single(read1,read2,output,per,ml);
					int k=ro1.getro1();
				}
				}
				else{// -h 
					System.out.println("RO1 is designed to identify 5’-RO feature on paired-end reads from RNA-seq data set and then, merge those RO containing paired-end reads into long single-end reads.");
					System.out.println();
					System.out.println("Usage:");
					System.out.println();
					System.out.println("java –jar CIRI-full.jar RO1 [options]");
					System.out.println();
					System.out.println("Options:");
					System.out.println();
					System.out.println("-1	reads1 of paired-end reads (required, equal length,)");
					System.out.println("-2	reads2 of paired-end reads (required, equal length)");
					System.out.println("-o	prefix of output files (optional,default: user_dir/out)");
					System.out.println("-minM	sets the number of minimum 5’-RO length (optional, integer, default 13)");
					System.out.println("-minI	sets the minimum identity percentage of 5’-RO alignment (optional, default 95)");
					System.out.println();

				}
			}
			else if(z.get(0).equals("RO2")){//RO2 mode selected
				if(z.indexOf("-h")==-1){
				String ref="";
				String sam="";
				int readlength = 0;
				int breakflag=0;
				int range=200000;
				String out=dir+"/out";
				if(z.indexOf("-r")!=-1){
					ref=z.get(z.indexOf("-r")+1);
					if(!ref.substring(0,1).equals("/")){
						ref=dir+"/"+ref;
					}
				}
				else{
					System.out.println("Reference fasta file not set. Exit");
					breakflag=1;
				}
				if(z.indexOf("-range")!=-1){
					range=Integer.parseInt(z.get(z.indexOf("-range")+1));
					if(range<1000){
						System.out.println("Maximum range of circRNA should be >1000bp. Using parameter: -range 1000 ");
						range=1000;
					}
				}
				if(z.indexOf("-s")!=-1){
					sam=z.get(z.indexOf("-s")+1);
					if(!sam.substring(0,1).equals("/")){
						sam=dir+"/"+sam;
					}
				}
				else{
					System.out.println("Sam file not set. Exit");
					breakflag=1;
				}
				
				if(z.indexOf("-o")!=-1){
					out=z.get(z.indexOf("-o")+1);
					if(!out.substring(0,1).equals("/")){
						out=dir+"/"+out;
					}
				}
				System.out.println("Output prefix = "+out);

				if(z.indexOf("-l")!=-1){
					readlength=Integer.parseInt(z.get(z.indexOf("-l")+1));
					if(readlength<50&&readlength!=0){
						breakflag=1;
						System.out.println("Read length should be >=50");
					}
				}
				else{
					System.out.println("Read length is not set, please check.");
					breakflag=1;
				}
				if(breakflag==0){
					if(readlength==0)
						System.out.println("Long reads input");
					RO2 ro2=new RO2(ref,sam,readlength,range,out);
					int k=ro2.getro2();
				}
				else{
					System.out.println("Options for RO2 not enough or illegal, please check. Type '-h' for more detail");
					System.out.println();
					System.out.println("Usage:");
					System.out.println();
					System.out.println("java -jar CIRI_Full.jar RO2 -r reference.fa -s prefix_ro.sam -l readlength");
					System.out.println();

				}
			}
				else{////-h output:
					System.out.println("RO2 module verify RO merged-reads from candidates by processing SAM file generated by bwa-mem");
					System.out.println();
					System.out.println("Usage:");
					System.out.println();
					System.out.println("java –jar CIRI_full.jar RO2 [options]");
					System.out.println();
					System.out.println("Options:");
					System.out.println();
					System.out.println("-r		reference genome in fasta format, the same file used in preparation step when building bwa index (required). ");
					System.out.println("-s		sam file of prefix_ro1.fq generate by bwa-mem (required).");
					System.out.println("-l		the read length of given RNA-seq paired end data (required).");
					System.out.println("-o		prefix of output files in the RO2 module (optional,default: user_dir/out))");
					System.out.println("-range	the maximum range of circRNA components on reference position (optional, integer, default 100000).");
					System.out.println();

				}
			}
			else if(z.get(0).equals("Merge")){ // Merge mode
				if(z.indexOf("-h")==-1){
				String gtf="";
				String ciri="";
				String ciri_as="";
				String ciri_ro="";
				String out="";
				String ref="";
				int breakflag=0;
				if(z.indexOf("-a")!=-1){
					gtf=z.get(z.indexOf("-a")+1);
					if(!gtf.substring(0,1).equals("/")){
						gtf=dir+"/"+gtf;
					}
				}
				else{
					System.out.println("Annoation file not set");
				}
								
				if(z.indexOf("-o")!=-1){
					out=z.get(z.indexOf("-o")+1);
					if(!out.substring(0,1).equals("/")){
						out=dir+"/"+out;
					}
					System.out.println("Output prefix = "+out);
				}
				else{
					System.out.println("Output prefix not set, exit");
					breakflag=1;
				}
				
				if(z.indexOf("-r")!=-1){
					ref=z.get(z.indexOf("-r")+1);
					if(!ref.substring(0,1).equals("/")){
						ref=dir+"/"+ref;
					}
				}
				else{
					System.out.println("reference fasta file not set, exit");
					breakflag=1;
				}
				
				if(z.indexOf("-c")!=-1){
					ciri=z.get(z.indexOf("-c")+1);
					if(!ciri.substring(0,1).equals("/")){
						ciri=dir+"/"+ciri;
					}
				}
				else{
					System.out.println("CIRI output file not set");
				}
				
				if(z.indexOf("-as")!=-1){
					ciri_as=z.get(z.indexOf("-as")+1);
					if(!ciri_as.substring(0,1).equals("/")){
						ciri_as=dir+"/"+ciri_as;
					}
				}
				else{
					System.out.println("ciri-as output file (prefix_jav.list) not set ,Use RO result only");
				}
				if(z.indexOf("-ro")!=-1){
					ciri_ro=z.get(z.indexOf("-ro")+1);
					if(!ciri_ro.substring(0,1).equals("/")){
						ciri_ro=dir+"/"+ciri_ro;
					}
				}
				else{
					System.out.println("RO file not set. Use ciri-as result only");
				}
				if(ciri_as.equals("")&&ciri_ro.equals("")){
					breakflag=1;
				}
				
				if(breakflag==0){
					Merge3 merge=new Merge3(ciri,ciri_as,ciri_ro,gtf,out);
					int k=merge.getmerge();
				}
				else{
					System.out.println("Options for Merge not enough or illegal, please check. Type '-h' for more detail");					
					System.out.println();
					System.out.println("Usage:");
					System.out.println();
					System.out.println("java -jar CIRI_Full.jar Merge -a annotation.gtf -c out.ciri -ro out_ro2_info.list.readinfo -as out_jav.list -r reference.fa -o prefix");
					System.out.println();

				}
				}
				else{//-h
					System.out.println("The Merge module combines the result of RO2 and CIRI-AS to reconstruct full-length circRNAs.");
					System.out.println();
					System.out.println("Usage:");
					System.out.println();
					System.out.println("java –jar CIRI_full.jar Merge [options]");
					System.out.println();
					System.out.println("Options:");
					System.out.println();
					System.out.println("-c		output file of CIRI (required)");
					System.out.println("-as		all processing info of CIRI-AS output file (using -d yes) (required)");
					System.out.println("-ro		RO read information file generated by RO2 module (required)");
					System.out.println("-o 		output prefix of Merge module (required)");
					System.out.println("-r		reference genome in fasta format (required)");
					System.out.println("-a		annotation file of reference genome in GTF format (optional).");
					System.out.println();

				}
			}
			else{
				System.out.println("Please check if CIRI_Full mode was set correctly.");
			}
		}
		else{
			help=1;
		}
		}
		else{//-h
			help=1;
		}
		if(help==1){
			System.out.println();
			System.out.println("Program: CIRI-full");
			System.out.println(v);
			System.out.println();
			System.out.println("Usage");
			System.out.println();
			System.out.println("java -jar CIRI_Full.jar <mode types> [options]");
			System.out.println();
			System.out.println("Mode types:");
			System.out.println("");
			System.out.println("Pipeline:	The CIRI-full Pipeline module is an automatic pipeline for detecting and reconstructing circRNAs.");
			System.out.println("RO1:		Detecing 5'RO and merge read pairs into candidate RO merged-reads. Please use bwa mem to map the fastq output to the reference genome with parameter '-T 19'");
			System.out.println("RO2:		Analysis mapping information of candidate RO merged-reads generated by RO1 and detect RO merged-read which can cover whole sequence of circRNA .");
			System.out.println("Merge:		Merge reads information detected by RO and CIRI-AS and reconstruct full length circRNA. '-D yes' parameter should be used when running CIRI-AS.");
			System.out.println();	
			System.out.println("Example:");
			System.out.println();
			System.out.println("java -jar CIRI_Full.jar Pipeline -1 read_1.fq -2 read_2.fq -d outdir -o prefix -r reference.fa -a annotation.gtf -t number_thread");
			System.out.println("java -jar CIRI_Full.jar RO1 -1 read_1.fq -2 read_2.fq -o prefix -t number_thread");
			System.out.println("java -jar CIRI_Full.jar RO2 -r reference.fa -s prefix_ro1.sam -l readlength -o prefix");
			System.out.println("java -jar CIRI_Full.jar Merge -a annotation.gtf -c prefix.ciri -ro prefix_ro2_info.list -as prefix_jav.list -r reference.fa -o out");
			System.out.println("");			
			System.out.println("For detail usage, please type '-h' in each module");
			System.out.println();


		}
	
		// TODO Auto-generated method stub

	}
}

	
   


  



