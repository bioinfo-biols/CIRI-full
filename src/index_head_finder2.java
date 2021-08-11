

public class index_head_finder2 {
    public String seq1;//short
    public String seq2;//long
    public double per;
    public int startp;
    public int endp;

    public int ifgood;
   
    public index_head_finder2(String A,String B){
    	this.seq1=A;
    	this.seq2=B;
    	int sp=(seq1.length()-8)/2;
		String index=seq1.substring(sp,sp+8);
		int startpoint;
			startpoint=sp;
			char t1[]= index.toCharArray();
			while(startpoint+seq1.length()-sp<=seq2.length()){
				char t2[]=seq2.substring(startpoint,startpoint+8).toCharArray();
				int right1=0;
				int i=0;
				double idmax=0;
				while(i<8){
					if(t1[i]==t2[i]&&t1[i]!='N'&&t2[i]!='N')
						right1++;
					i++;
				}
				
				if(right1>=7){//find index right>=5
					String tmps1=seq1;
					//System.out.println(seq1.length()+" "+seq2.length()+" "+(startpoint-sp)+" "+(startpoint-sp+seq1.length()));
					String tmps2=seq2.substring(startpoint-sp,startpoint-sp+seq1.length());
					char subseq1[]=tmps1.toCharArray();
					char subseq2[]=tmps2.toCharArray();
					int right=0;
					i=0;
					
					
					while(i<subseq1.length){
						if(subseq1[i]==subseq2[i]&&subseq1[i]!='N'&&subseq2[i]!='N')
							right++;
						i++;
					}
					double id=right*100/i;
					if(id>=95&&id>=idmax){
						ifgood=1;
						per=id;
						startp=startpoint-sp+1;
						endp=startpoint-sp+seq1.length();
						idmax=id;
					}
				}
			startpoint++;
		}
    }
	public int getok(){
		return ifgood;
	}
	public int[] get_pos(){
		
		int[] a={startp,endp};
		return a; 
	}
	public double get_id(){
		return per;
	}

    
}