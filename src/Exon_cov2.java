import java.util.*;

public class Exon_cov2 {
	public String cov;
	public Exon_cov2(String cov1){
		this.cov=cov1;
	}
	public List<int[]> get_exon(){
		List<int[]> a=new ArrayList<int[]>();
		int step=5;
		int zone=15;
		String[] tmp=cov.split(",");
		int[] tmpn=new int[tmp.length];
		int[] tmpc=new int[((tmp.length-zone)/step)+1];
		int n=0;
		int high_cov=0;
		int length=tmp.length;
		//System.out.println(length);
		while(n<tmpn.length){
			tmpn[n]=Integer.parseInt(tmp[n]);
			n++;
		}
		n=0;
		while((n*step)+zone<length){
			int i=0;
			while(i<zone){
				tmpc[n]=tmpc[n]+Integer.parseInt(tmp[n*step+i]);
				i++;
			}
			tmpc[n]=tmpc[n]/zone;			
			high_cov=Math.max(tmpc[n],high_cov);
			n++;
		}
		//System.out.println(high_cov+" "+n);              
        int bound=0;
		int[] dis=new int[high_cov+1];
		n=0;
		while(n<tmpc.length){
			dis[tmpc[n]]++;
			n++;
		}
		int[] pre =new int[high_cov];
		n=0;
		while(n<high_cov){
			pre[n]=dis[n+1]-dis[n];
	//		System.out.print(pre[n]+",");
			n++;
		}
	//	System.out.println("");
		n=0;
	//	while(n<high_cov+1){
	//		System.out.print(dis[n]+",");
	//		n++;
	//	}
	//	System.out.println("");
		//for bound;
		int drop=0;
		n=0;
		while(n<high_cov-4){
			int dao=pre[n]+pre[n+1]+pre[n+2]+pre[n+3];
			if(dao<0)
				drop=1;
			if(drop==1&&dao>2&&n>=5){
				bound=n;
				break;
			}
			n++;
		}
		bound=bound+3;
		
	//	System.out.println(bound);
		if(dis.length>3){
		if((dis[0]+dis[1]+dis[2])*100<tmpc.length*10){
			//System.out.println((dis[0]+dis[1]+dis[2]+dis[3]+dis[4])+" "+tmpc.length);
			int[] exon={0,length};
			a.add(exon);
		//	System.out.println(exon[0]+"-"+exon[1]);
		}
		else{

        //List<int[]> result=new ArrayList<int[]>();
        int o=0;
    	int tmpstart=0;
    	int tmpend=0;
    	int e=0;
    	int l=0;
        while(o<tmpc.length){
    		//System.out.print(tmpc[o]+",");

        		if(o==0){
        			tmpstart=0;
        			e=1;
        			l++;
        		}
        		else if(tmpc[o]>=bound&&e==0){
        			int[] wave=new int[zone-1+10];
        			int k=-5;
        			int max=0;
        			int maxc=0;
        			while(k+5<zone-1+10&&o*step+k+1<length){
        				wave[k+5]=tmpn[o*step+k+1]-tmpn[o*step+k];
        				max=Math.max(max, wave[k+5]);
        				maxc=Math.max(maxc,tmpn[o*step+k+1]);
        				k++;
        			}
        			if(max>5&&max*100>maxc*25){
        				int k1=-5;
        				while(k1+5<zone-1+10){
        					if(max==wave[k1+5]){
        						tmpstart=o*step+k1;
        						break;
        					}
        					k1++;
        				}
        				e=1;	
        				l++;
        			}
        		
        	}
        	else if(tmpc[o]>=bound&&e==1){
        		l++;
        	}
        	else if(tmpc[o]<bound&&e==1){
    			int[] wave=new int[zone-1+10];
    			int k=-5;
    			int max=0;
    			int maxc=0;
    			while(k+5<zone-1+10&&o*step+k+1<length){
    				wave[k+5]=tmpn[o*step+k+1]-tmpn[o*step+k];
    				max=Math.min(max, wave[k+5]);
    				maxc=Math.max(maxc,tmpn[o*step+k+1]);
    				k++;
    			}
    			//System.out.println(o*step+" "+max+" "+maxc);

    			if(max<-5&&max*100<-maxc*25&&l>4){
    				int k1=-5;
    				while(k1+5<zone-1+10){
    					if(max==wave[k1+5])
    						tmpend=o*step+k1;
    					k1++;
    				}
    				int[] exon={tmpstart,tmpend};
    				a.add(exon);
    	//			System.out.println((tmpstart)+"-"+(tmpend));
    			}	
    				e=0;
    				l=0;       		
        	}
        	o++;
        }
        int[] exon={tmpstart,length};
        if(length-tmpstart<500){
        	a.add(exon);
 //       	System.out.println((tmpstart)+"-"+(length));
        }
		}
		}
		return a;
	}
	

}
