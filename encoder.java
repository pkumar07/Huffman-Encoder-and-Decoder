


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


//Encoder using 4-way cache optimized heap    

public class encoder {
    
    static LinkedHashMap<Integer, String> codes = new LinkedHashMap<>();
    
    //The following method writes the HashMap into the code_table file
    void writeToFile(){
        String filename= "code_table.txt";
        FileWriter fw = null;
        BufferedWriter bw = null;
       
        Iterator<Map.Entry<Integer,String>> it = codes.entrySet().iterator();
        try {
            fw = new FileWriter(filename);
            bw = new BufferedWriter(fw);
            while(it.hasNext()){
                Map.Entry<Integer,String> set = it.next();
                bw.write(set.getKey() + " " + set.getValue());
                bw.newLine();
            } 
        }
        catch (IOException ex) {}
        finally{
            try{
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex){ }		
        }
    }
    
    /*void printCodesForPairing(PNode root, String str){
        if(root == null)
            return;
 
        if(root.data.value != -1)
            codes.put(root.data.value, str);

        printCodesMain(root.data.left, str + "0");
        printCodesMain(root.data.right, str + "1");
    }*/
    
    //The following method helps in the generation of the Huffman codes and puts them in the HashMap
    void printCodesMain(ANode root, String str){
        if(root == null)
         return;
 
        if(root.value != -1)          
           codes.put(root.value, str);
  
        printCodesMain(root.left, str + "0");
        printCodesMain(root.right, str + "1");
    }
    
    //The following function builds the Huffman tree using 4 way cache optimized heap
    void build_tree_using_4way_heap(Vector<ANode> arr){
        DaryHeap h = new DaryHeap();
         for(ANode n: arr)
             h.insert(n);
        
        ANode hleft,hright,middle;
        
        while(h.heap.size() != 1){
            hleft = h.deleteMin();
            hright = h.deleteMin();
            middle = new ANode(-1,hleft.freq + hright.freq );
            middle.left = hleft;
            middle.right = hright;
            h.insert(middle);    
        }
        printCodesMain(h.getMin(), "");   	//comment this for time
        writeToFile();						//comment this for time
    }

    /*
    
    //The following function builds the Huffman tree using binary heap
    void build_tree_using_bin_heap(Vector<ANode> arr){
        BinaryHeap h = new BinaryHeap();
        for(ANode n: arr)
            h.insert(n);
        ANode hleft,hright,middle;
        while(h.heap.size() != 1){
            hleft = h.deleteMin();
            hright = h.deleteMin();
            middle = new ANode(-1,hleft.freq + hright.freq );
            middle.left = hleft;
            middle.right = hright;
            h.insert(middle);      
        }
       // printCodesMain(h.getMin(), "");
        //writeToFile();     
    }

    //The following function builds the Huffman tree using pairing heap
    void build_tree_using_pair_heap(Vector<ANode> arr){
        PairingHeap h = new PairingHeap();
        for(ANode n: arr)
            h.insert(new PNode(n));
        PNode hleft,hright,middle;
        while(h.size != 1){
            hleft = h.deleteMin();
            hright = h.deleteMin();
            middle = new PNode(new ANode(-1,hleft.data.freq + hright.data.freq));
            middle.data.left = hleft.data;
            middle.data.right = hright.data;
            h.insert(middle);
        }            
       // printCodesForPairing(h.getMin(), "");
        //writeToFile();    
    }
    */
 
    public static void main(String[] args){
        
        LinkedHashMap<Integer,Integer> map = new LinkedHashMap<>();
    
        String fileName = args[0];
     
        String line = null;
        OutputStream os = null;
        FileReader fr = null; 
        BufferedReader br = null;
        FileOutputStream out = null;
        BufferedWriter bw = null;
        //Creation of frequency table
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            int no =-1;
            while((line = br.readLine()) != null) {
                if(!line.equals("")){
                    no = Integer.valueOf(line.trim());
                    if(!map.containsKey(no))
                        map.put(no,1);
                    else{
                        int value = map.get(no);
                        map.put(no,++value);
                    }
                }
            }         
        }
        catch(FileNotFoundException ex) {}
        catch(IOException ex) {}
        finally{
            try { 
                br.close();
            } catch (IOException ex) {     
            }
        }
       /*---------------------Map created------------------------------------------*/
        Vector<ANode> arr = new Vector<>();
        Iterator<Map.Entry<Integer,Integer>> it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Integer,Integer> set = it.next();
            arr.add(new ANode(set.getKey(),set.getValue()));
        }
       
        //Vector has No,Frequency val which will be used to construct the heap
        
        encoder ads1 = new encoder();
        //Calculation of time in millisecond
        
        /*
        long start_time = System.currentTimeMillis();
        System.out.println("Start time: " + start_time);
        for(int i = 0; i < 10; i++){ //run 10 times on given data set
            ads1.build_tree_using_4way_heap(arr);
           
        }
         System.out.println("end time: " + System.currentTimeMillis());
        System.out.println("Time using binary heap (millisecond): " + (System.currentTimeMillis()- start_time)/10.0);
        
         System.out.println("--------------------------------");
        
        long start_time1 = System.currentTimeMillis();
        System.out.println("Start time: " +start_time1);
        for(int i = 0; i < 10; i++){ //run 10 times on given data set
             ads1.build_tree_using_bin_heap(arr);  
        }
         System.out.println("end time: " + System.currentTimeMillis());
        System.out.println("Time using 4-way cache optimized heap (millisecond): " + (System.currentTimeMillis()- start_time1)/10.0);
        
        System.out.println("--------------------------------");
        long start_time2 = System.currentTimeMillis();
        System.out.println("Start time: " +start_time2);
        for(int i = 0; i < 10; i++){ //run 10 times on given data set
             ads1.build_tree_using_pair_heap(arr);  
        }
        System.out.println("end time: " + System.currentTimeMillis());
        System.out.println("Time using Pairing heap (millisecond): " + (System.currentTimeMillis()- start_time2)/10.0);
        
        */
       
       
        ads1.build_tree_using_4way_heap(arr);  //comment this for time
       
       //comment the entire encode data for calculating time
        
        /*Encoding starts here */
        //Encode data
        
        String encodedpath = "encoded.bin";
        
        try{ 
            os = new FileOutputStream(encodedpath);
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);    
            String s = new String();
           
            while((line = br.readLine()) != null) {
                if(!line.equals("")){
                    s = s + codes.get(Integer.valueOf(line));
                    if((s.length()%8) == 0 ){
                        for (int i = 0; i < s.length(); i += 8) {
                            String byteString = s.substring(i, i + 8); 
                            int parsedByte = 0xFF & Integer.parseInt(byteString, 2);
                            os.write(parsedByte); 
                        }
                        s = new String();
                    }       
                }          
            } 
              
            while (s.length() % 8 != 0){
                s += "0";
            }
            
            for (int i = 0; i < s.length(); i += 8) {
                String byteString = s.substring(i, i + 8); 
                int parsedByte = 0xFF & Integer.parseInt(byteString, 2);
                os.write(parsedByte);
                }    
        }
        catch (IOException | NumberFormatException ex) { }
        finally{
            try {
                if (bw != null)
                    bw.close();
                if(os != null)
                    os.close();
            } catch (IOException ex) {  }
            
        }
            
      
        
        
            
            
            
            
        
        
        
      
        
        
        
        

    }
    
}
