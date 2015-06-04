/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newsvc;
import java.io.*;

/**
 *
 * @author mohak
 */
public class svc_new {

    /**
     * @param args the command line arguments
     */
    public static int check_line(String line){
        int count = 1,i;
        for(i=0;i<=line.length()-1;i++)
        {
            if (line.charAt(i) == ' ' && line.charAt(i+1)!=' ')
            {
                count++;
            }
        }
        if(count<=10)
            return 1;
        else 
            return 0;
    }
    
    public static void main(String[] args) throws IOException {
        String file="new",filename = null,filename1 = null,line = null,option = null,inp = null,command = null,substr=null;
        int opt,version = 0,del=0,len,counter=0,verno=0,check=0;
        BufferedReader buffread = new BufferedReader(new InputStreamReader(System.in));//Reader to take input
        do{
            System.out.println("Enter option -\n 1.Add to file \n 2.Delete from file\n3.Display the file\n4.Exit");
            opt = Integer.parseInt(buffread.readLine());//stores the option from user
            
            switch(opt){
                
                case 1://case 1 for adding strings to file
                
                    System.out.println("Enter the string you want to add to file");
                    inp = buffread.readLine();//input string
                    check = check_line(inp);
                    if(check == 0){
                        System.out.println("Invalid String entered");
                        break;
                    }
                    System.out.println("Enter command");
                    option = buffread.readLine();            
                    command = option.substring(4);//later part of the command
                    if(command.endsWith("txt")){
                        if(version == 0){//for first file
                            try{
                                filename = "C:\\Users\\Admin\\Documents\\NetBeansProjects\\newsvc\\filefolder\\"+file+version+".txt";
                                File file1 = new File(filename);
                                if(!file1.exists()) {//checking if file exists or not. if doesn't then making one
                                    try {
                                        file1.createNewFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                PrintStream buffwr = new PrintStream(file1);
                                buffwr.println(inp);//writing in file
                                buffwr.close();
                            }
                            catch(FileNotFoundException ex){
                                System.out.println("File not found!");
                            }   
                        }
                        else{//for other cases i.e. not first file
                            try{
                                filename = "C:\\Users\\Admin\\Documents\\NetBeansProjects\\newsvc\\filefolder\\"+file+(version-1)+".txt";
                                filename1 = "C:\\Users\\Admin\\Documents\\NetBeansProjects\\newsvc\\filefolder\\"+file+version+".txt";
                                File file1 = new File(filename);
                                File file2 = new File(filename1);
                                if(!file1.exists()) {
                                    try {
                                        file1.createNewFile();
                                    }catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if(!file2.exists()) {
                                    try {
                                        file2.createNewFile();
                                    }catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                BufferedReader buffrd = new BufferedReader(new FileReader(file1));
                                PrintStream buffwr = new PrintStream(file2);
                                while((line = buffrd.readLine()) != null){//copying each line from first file to new version of file
                                    buffwr.println(line);                          
                                }
                                buffwr.println(inp);
                                buffwr.close();//closing streams
                                buffrd.close();
                            }
                            catch(FileNotFoundException ex){
                                System.out.println("File not found!");
                            }   
                        }
                    }
                    else
                        System.out.println("Wrong command entered");
                    version++;//incrementing version of files
                    break;
                
                case 2://deleting a line from the file
                
                    System.out.println("Enter the line you want to delete from file");
                    del = Integer.parseInt(buffread.readLine());
                    try{
                        counter = 1;
                        filename = "C:\\Users\\Admin\\Documents\\NetBeansProjects\\newsvc\\filefolder\\"+file+(version-1)+".txt";
                        filename1 = "C:\\Users\\Admin\\Documents\\NetBeansProjects\\newsvc\\filefolder\\"+file+version+".txt";
                        File file1 = new File(filename);
                        File file2 = new File(filename1);
                        if(!file1.exists()) {
                            try {
                                file1.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (!file2.exists()) {
                            try {
                                file2.createNewFile();
                            }catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        BufferedReader buffrd = new BufferedReader(new FileReader(file1));
                        PrintStream buffwr = new PrintStream(file2);
                        while((line = buffrd.readLine()) != null){//copying old file until encountered deletion line
                            if(counter == del)
                                break;
                            buffwr.println(line);
                            counter++;                            
                        }
                        while((line = buffrd.readLine()) != null)//copying after deleted line
                            buffwr.println(line);
                        buffwr.close();
                        buffrd.close();
                    }
                    catch(FileNotFoundException ex){
                        System.out.println("File not found!");
                    }
                    version++;
                    break;
                    
                case 3:
                    
                    System.out.println("Enter command");//input command for verion number
                    option = buffread.readLine();            
                    command = option.substring(4);
                    verno = Integer.parseInt(command);
                    if(verno>version || verno<0){//check for invalid version number
                        System.out.println("Version number doesn't exist");
                        break;
                    }
                    filename = "C:\\Users\\Admin\\Documents\\NetBeansProjects\\newsvc\\filefolder\\"+file+verno+".txt";
                    BufferedReader buffrd = new BufferedReader(new FileReader(filename));
                    while((line = buffrd.readLine()) != null){
                        System.out.println(line);
                    }
                    buffrd.close();
                    break;
                    
                case 4:
                    return;
            }
        }while(true);
    }
}