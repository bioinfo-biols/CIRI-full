import java.util.List;

public class Exon_length {
	public List<int[]> group;
	public int startbound;
	public int endbound;
	
	public Exon_length(List<int[]> group1,int start1,int end1){
		this.group=group1;
		this.startbound=start1;
		this.endbound=end1;
		int i=0;
		
		while(i<group.size()){
			int[] exon=group.get(i);
			if(exon[0]<endbound&&exon[1]>startbound){
				exon[0]=Math.max(startbound,exon[0]);
				exon[1]=Math.min(endbound, exon[1]);
				group.set(i,exon);
			}
			else{
				group.remove(i);
				i--;
			}
			int q=0;
			while(q<i){
				int[] exonq=group.get(q);
				if(exonq[0]<exon[1]&&exonq[1]>exon[0]){
					int qstart=Math.min(exon[0], exonq[0]);
					int qend=Math.max(exon[1], exonq[1]);
					int[] d={qstart,qend};
					group.set(i, d);
					group.remove(q);
					q--;
					i--;
				}
				q++;
			}
			i++;
		}
		i=0;
	}
	 
	public int get_length(){
		int i=0;
		int length=0;
		while(i<group.size()){
			int[] exon=group.get(i);
			length=length+exon[1]-exon[0];
			i++;
		}
		return length;
	}

}
