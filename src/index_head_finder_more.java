

public class index_head_finder_more {
    public String seq1;
    public String seq2;
    public String q1;
    public String q2;
    public double per;
    public int overlength;
    public int indexp;
    public String merge_read;
    public String merge_q;
    public int startp;
    
    public int testlength;
    public double testper;
    

    public int ifgood;
   
    public index_head_finder_more(String A,String B,String C,String D, double testper1,int testlength1){
    	this.seq1=A;
    	this.seq2=B;
    	this.q1=C;
    	this.q2=D;
    	this.testlength=testlength1;
    	this.testper=testper1;
    	
		String[] index={seq1.substring(0,8),seq1.substring(1,9),seq1.substring(2,10)};
		int startpoint;
		int indexn=0;
		while(indexn<index.length){
			startpoint=0;
			while(startpoint+8<=seq2.length()){
				char seed1[]=index[indexn].toCharArray();
				char seed2[]=seq2.substring(startpoint,startpoint+8).toCharArray();
				int seedright=0;
				int i=0;
				while(i<8){
					if(seed1[i]==seed2[i]&&seed1[i]!='N'&&seed2[i]!='N')
						seedright++;
					i++;
				}
				if(seedright>=7){//find index
					String tmps1=seq1.substring(indexn,seq2.length()-startpoint);
					String tmps2=seq2.substring(startpoint,seq2.length());
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
					if(id>=testper&&i>=testlength){
						ifgood=1;
						per=id;
						indexp=indexn;
						startp=startpoint;
						overlength=tmps1.length();
						merge_read=seq2.substring(0,startpoint)+seq1.substring(indexn,seq1.length());
						merge_q=q2.substring(0,startpoint)+q1.substring(indexn,q1.length());
						break;
					}
				}
				startpoint++;
			}
			if(ifgood==1)
				break;
			indexn++;
		}
    }
	public int getok(){
		return ifgood;
	}
	public String[] get_merge(){
		String[] tmp={merge_read,merge_q};
		return tmp;
	}
	public int[] get_pos(){
		
		int[] a={(indexp+1),(indexp+overlength),(startp+1),(startp+overlength)};
		return a; 
	}
	public double get_id(){
		return per;
	}

    
}