


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

//Node structure for Decoder Tree
class DNode{
        Integer value;
        DNode left;
        DNode right;
        
        DNode(Integer value){
            this.value = value;
            left = null;
            right = null;
        }
        
        @Override
        public String toString(){
            return value.toString();
        }
    }

public class decoder {
    
    
    static DNode root= new DNode(-1);
    
    //The following function builds the Decoder tree
    static void buildTree(Integer n, String code){
        
        int i=0;
        DNode temp = root;
        while(i<code.length()){
            
            if(code.charAt(i)=='0'){
                if(temp.left == null)
                    temp.left = new DNode(-1);
                temp = temp.left;
            }
            if(code.charAt(i) == '1'){
                if(temp.right == null)
                    temp.right = new DNode(-1);
                temp = temp.right;
            }
            i++;
        }  
        temp.value= n;   
    }

    public static void main(String args[]){
        String encodedpath = args[0];
        String codes = args[1];
        String destination ="decoded.txt";
        FileReader fr = null;
        BufferedReader br = null;
        String line = null;
        String s[];
        InputStream fis = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
       
        try {
                //Stepp 1: Read code table to regenerate huffman tree  
                fr = new FileReader(codes);             
                br = new BufferedReader(fr);
                while((line = br.readLine()) != null) {
                    s= line.split(" ");
                    buildTree(Integer.valueOf(s[0]),s[1]);
                }
   
            } 
            catch(FileNotFoundException ex){   
            } 
            catch(IOException ex){  
            }
            finally{
                
                    try {
                        if( br != null)
                        br.close();
                    } catch (IOException ex) {
                    }
            }
                 
            
            //Step 2: Read the encoded.bin file to decode and use the tree to decode
        try 
            {
            
            fis = new FileInputStream(encodedpath);
            fw = new FileWriter(destination);
            bw = new BufferedWriter(fw);
            int data;

            
            byte temp[] = new byte[4096];
            
            DNode t = root;
            while((data =fis.read(temp)) != -1) {
               for(int i= 0; i< data; i++){
                    String str= String.format("%8s", Integer.toBinaryString(temp[i] & 0xFF)).replace(' ', '0');
                    int j =0;
                    while(j<str.length()){
                        
                        while(!(t.left == null && t.right == null) && j<str.length()){
                            if(str.charAt(j) == '0')
                               t = t.left;
                            else
                               t = t.right;
                            j++;
                        }
                        if(j != str.length()){
                            bw.write(String.valueOf(t.value) + "\n");
                            t = root;
                        }
                    }   
          
                }       
            }
            bw.write(String.valueOf(t.value));
            bw.newLine();
                       
        }
        catch (IOException e) { }    
        finally 
        {
            try {
                
                if (fis != null)
                   fis.close();
                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();
                
                
            } catch (IOException ex) {}
        }      
    }
    
    
}
