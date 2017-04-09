
class PNode{
    PNode child;
    PNode rightsib;
    PNode leftsib;
    ANode data;
    
    public PNode(ANode a){
        child = null;
        rightsib = null;
        leftsib = null;
        data = a;
    }   
}

public class PairingHeap { 
    PNode trees[] = new PNode[5];
    PNode root; 
    int size;
    
    PairingHeap( ){
        root = null;
        size = 0;
    }
    
    public PNode[] doubleArr(PNode arr[], int i){
        if (i == arr.length){
            PNode oldArray[] = arr;
            arr = new PNode[i * 2];
            System.arraycopy(oldArray, 0, arr, 0, i);
        }
        return arr;
    }
 
    public PNode insert(PNode node){
        size++;
        if (root == null)
            root = node;
        else
            root = meld(root, node);
        return node;
    }
    
    public PNode meld(PNode tree1, PNode tree2){
        if (tree2 == null)
            return tree1;
 
        if (tree2.data.freq >= tree1.data.freq){
            tree2.leftsib = tree1;
            tree1.rightsib = tree2.rightsib;
            if (tree1.rightsib != null)
                tree1.rightsib.leftsib = tree1;
            tree2.rightsib = tree1.child;
            if (tree2.rightsib != null)
                tree2.rightsib.leftsib = tree2;
            tree1.child = tree2;
            return tree1;     
        }
        else{
            tree2.leftsib = tree1.leftsib;
            tree1.leftsib = tree2;
            tree1.rightsib = tree2.child;
            if (tree1.rightsib != null)
                tree1.rightsib.leftsib = tree1;
            tree2.child = tree1;
            return tree2;  
            
        }
    }
     
     
    public PNode addSibling(PNode sibling1){
        if(sibling1.rightsib == null )
            return sibling1;
      
        int siblingCount;
        for (siblingCount= 0; sibling1 != null; siblingCount++){
            trees = doubleArr(trees, siblingCount);
            trees[siblingCount] = sibling1;
            sibling1.leftsib.rightsib = null;  
            sibling1 = sibling1.rightsib;
        }
        trees = doubleArr(trees, siblingCount);
        trees[siblingCount] = null;
      
        int i;
        for (i=0; (i + 1) < siblingCount; i = i+ 2)
            trees[i] = meld(trees[i], trees[i + 1]);
       
        int j = i - 2;
        if (j == siblingCount - 3)
            trees[j] = meld(trees[j], trees[j + 2] );
       
        for ( ; j >= 2; j = j-2)
            trees[j - 2] = meld(trees[j-2], trees[j]);
        return trees[0];
    }
    
 
     
    public PNode deleteMin( ){
        if (root == null )
            return null;
        PNode temp = root;
        if (root.child == null)
            root = null;
        else
            root = addSibling(root.child);
        size--;
        return temp;
    }
    
     public PNode getMin( ){
        if (root == null)
            return null; 
        return root;
    }  
      
    public void inorder(){
        inorder(root);
    }
    public void inorder(PNode node){
        if (node != null){
            inorder(node.child);
            System.out.print(node.data.freq +" ");
            inorder(node.rightsib);
        }
    }
    
}
