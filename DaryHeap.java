
import java.util.Vector;

public class DaryHeap {
    Vector<ANode> heap;
    final int arity =4;
   
    public DaryHeap(){
       heap = new Vector<>(); 
    }
   
    public boolean isEmpty(){
       return heap.isEmpty();
    }
    
    //Get parent of index n
    public int getParent(int n){
       return (n-1)/arity;
    }
    
    public int kChild(int i, int k){
       return (arity * i) + k;
    }
    
   
    public ANode deleteMin() {
        if (heap.isEmpty()) 
            System.out.println("No elements in heap"); 
        ANode temp = heap.get(0);
        ANode last = heap.get(heap.size()-1);
        int mchild,i;
        for(i=0; kChild(i,1) < heap.size(); i=mchild) {
            mchild = kChild(i,1);
            if (mchild > heap.size()){
                break;
            }

            int j, schild = mchild;
            for(j=1; j<arity; j++) {
                    if (mchild+j == heap.size()) break;
                    if(heap.get(schild).freq > heap.get(mchild+j).freq)
                            schild = mchild+j;
            }

            mchild = schild;

            if (last.freq > heap.get(mchild).freq) 
                heap.set(i,heap.get(mchild));
            else 
                break;
            
        }

        heap.set(i,last);
        heap.remove(heap.size()-1);
        return temp;
   }

   //child stores index of the element that has to be heapified
    public void heapifyUp(int child){
        ANode temp = heap.get(child);
        while(child > 0 && temp.freq < heap.get(getParent(child)).freq){
            heap.set(child, heap.get(getParent(child)));
            child = getParent(child);
        }
        heap.set(child,temp);  
   }
   
   
    public ANode getMin(){
       if(isEmpty())
           throw new IllegalStateException("No elements in heap");
       return heap.get(0); 
    }

   
    public void insert(ANode n){
       heap.add(n);
       heapifyUp(heap.size()-1);
    }
 
}
