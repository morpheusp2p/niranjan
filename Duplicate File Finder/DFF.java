import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DFF{

	public static void find(Map<String, List<String>> lists, File dir) {
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                find(lists, f);
                } else {
                    String hash = f.getName() + f.length();
                    List<String> list = lists.get(hash);
                    if (list == null) {
                    	
                    	// Create a linked list and add duplicate entries
                        list = new LinkedList<String>();
                        lists.put(hash, list);
                    }
                    list.add(f.getAbsolutePath());
                }
            }
        }
        
        public static void delete(List<String> list,String filename){
        		for(String file1 : list) {
        			if(file1.equals(filename))
        				continue;
        			else{
        				File file = new File(file1);
        				if(file.delete())
        					System.out.println("File deleted at location " + file1);
        				else
        					System.out.println("File not deleted.");
        			}
        		}
        }
        
	public static void main(String[] args)throws IOException{
		int option;
		String s = null;
		BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the path of the directory");
		String path = buff.readLine();
		
		File file = new File(path);
		Map<String, List<String>> lists = new HashMap<String, List<String>>();
                find(lists, file);	
                
                System.out.println("Looking for Duplicates at location " + path);
                                
                for (List<String> list : lists.values()) {
                   if (list.size() > 1) {
                	   System.out.println("--");
                	   System.out.println("Duplicates Found");
                       for (String file1 : list) {
                           System.out.println(file1);
                       	   s = file1;
                       }
                       System.out.println("Do you want to delete these duplicates.\n1.Yes\n2.No\n");
               	       option = Integer.parseInt(buff.readLine());
               	       if(option == 1)
               	       	   delete(list,s);    
                   }
               }
               
     	}
}
