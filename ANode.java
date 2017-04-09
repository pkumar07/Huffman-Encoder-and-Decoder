
public class ANode {
    Integer value;
    Integer freq;
    ANode left;
    ANode right;
  
    public ANode(Integer val,Integer frequency){
        value = val;
        freq = frequency;
        left = null;
        right = null;
    }
    
    @Override
    public String toString(){
       return "<" + value + "," + freq + ">"; 
    }
}
