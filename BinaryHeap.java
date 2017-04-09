
import java.util.NoSuchElementException;
import java.util.Vector;

public class BinaryHeap {
    Vector<ANode> heap;
    final int arity = 2;
    
    public BinaryHeap(){
        heap = new Vector<>();
    }

    public int getParent(int child){
        return (child-1)/arity;
    }
    
    public int kthChild(int i, int k){
        return (arity * i) + k;
    }
    
    public void heapifyUp(int child){
        ANode temp = heap.get(child);
        while(heap.get(child).freq < heap.get(getParent(child)).freq){
            ANode t = heap.get(child);
            heap.set(child, heap.get(getParent(child)));
            heap.set(getParent(child), t);   
            child = getParent(child);
        }
    }
    
    public void insert(ANode n){
        heap.add(n);
        heapifyUp(heap.size()-1);
    }
    
    public int minChild(int i){
        int child = kthChild(i,1);
        int k=2;
        int pos = kthChild(i,k);
        while((k<=arity) && (pos < heap.size())){
            if(heap.get(pos).freq < heap.get(child).freq)
                child = pos;
            pos = kthChild(i,k++);
        }
        return child;
    }
    
    public void heapifyDown(int i){
       if(heap.isEmpty()) return;
       int child;
       ANode temp = heap.get(i);
       while(kthChild(i, 1) < heap.size()){
            child = minChild(i);
           
            if(heap.get(child).freq < temp.freq)
                heap.set(i,heap.get(child));
               
            else
                break;
            i = child;
        }
       heap.set(i,temp);
        
    }
    
    public ANode delete(int i){
        if(heap.isEmpty())
            throw new NoSuchElementException("No elements in heap");
        ANode temp = heap.get(i);
        heap.set(i, heap.get(heap.size()-1));
        heap.remove(heap.size()-1);
        heapifyDown(i);
        return temp;     
    }   
    
    public ANode deleteMin(){
        ANode min = heap.get(0);
        delete(0);
        return min;    
    }
    
    public ANode getMin(){
        ANode min = heap.get(0);
        return min;    
    }
    
}
