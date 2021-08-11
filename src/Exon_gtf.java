import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Exon_gtf {
	public int zonestart;
	public int zoneend;
	public List<int[]> gtflist;
	public Exon_gtf(int zonestart1,int zoneend1,List<int[]> gtflist1){
		this.zonestart=zonestart1;
		this.zoneend=zoneend1;
		this.gtflist=gtflist1;
	}
	public List<int[]> get_exon(){
		 	List<int[]> exonlist=new ArrayList<int[]>();
		 	Iterator<int[]> iter = gtflist.iterator();
		 	while(iter.hasNext()){
		 		int[] exon=iter.next();
		 		if(exon[0]>=zonestart&&exon[1]<=zoneend){
		 			//Iterator<int[]> collect=exonlist.iterator();
		 			//int pp=0;
		 			//while(collect.hasNext()){
		 			//	int[] exon_o=collect.next();
		 			//	if(Math.abs(exon_o[0]-exon[0])<=5&&Math.abs(exon_o[1]-exon[1])<=5){
		 			//		pp=1;
		 					//int index=exonlist.indexOf(exon_o);
		 					//exon_o[0]=Math.min(exon_o[0], exon[0]);
		 					//exon_o[1]=Math.max(exon_o[1], exon[1]);
		 					//exonlist.set(index, exon_o);
		 			//		break;
		 			//	}
		 			//}
		 			//if(pp==0){
		 				//System.out.println(exon[0]+" "+exon[1]);
		 			exonlist.add(exon);
		 			//}
		 		}
		 }
		 	return exonlist;
	}
	
}
