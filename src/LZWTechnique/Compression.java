package LZWTechnique;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
public class Compression {
	String data;
    String temporaryData="";
    ArrayList<String> Dictionary=new ArrayList<String>();
    File WriteToFile;//file of compression
    PrintWriter Output;//to write data to the compressed file
    File ReadFromFile;//file of the original data 
    Scanner Input;//read from the original data file
    File Decompress;
    Scanner ReadFromCompressed;
    PrintWriter WriteToDecompressed;
    public void fillDictionary(ArrayList<String> obj) {
    	for(int i=0;i<128;i++)
        	obj.add(String.valueOf((char)i));
    }
   public void Compress() {
    	try 
    	{
    		//For Read From the file 
            ReadFromFile=new File("originalData.txt");
            Input =new Scanner(ReadFromFile);
    		//For write to the file 
    		WriteToFile=new File("compressedData.txt");
            Output =new PrintWriter(WriteToFile);
    	} 
    	catch(FileNotFoundException e) {
    		System.out.println("Error to open files or something went wrong");
    	}
    	fillDictionary(Dictionary);
    	int indexOnDictionary=0;
    	while(Input.hasNextLine()){
    		data=Input.nextLine();
			for(int i=0;i<data.length();i++) {
				temporaryData+=data.charAt(i);
				if(Dictionary.contains(temporaryData))
					indexOnDictionary=Dictionary.indexOf(temporaryData);
				else {
					Dictionary.add(temporaryData);
					Output.println(indexOnDictionary);
					temporaryData="";
					i--;
				}
			}
    	}
    	Output.println(indexOnDictionary);
    	Input.close();
    	Output.close();
    	Dictionary.clear();
    }
    public void Decompress() {
    	fillDictionary(Dictionary);
    	try 
    	{
    		//For reading from the compressed file
    		WriteToFile=new File("compressedData.txt");
    		ReadFromCompressed=new Scanner(WriteToFile);
            //for writing to the decompressed file
            Decompress=new File("decompressedData.txt");
            WriteToDecompressed=new PrintWriter(Decompress);
    	} 
    	catch(FileNotFoundException e) {
    		System.out.println("Error to open files or something went wrong");
    	}
    	String originalData="",last="",code="";
    	while(ReadFromCompressed.hasNextLine()) {
    		code=ReadFromCompressed.nextLine();
    		if(last.equals("")) {
    			last=Dictionary.get(Integer.parseInt(code));
    			originalData+=last;
    		}
    		
    		else {
    			if(Integer.parseInt(code)>=Dictionary.size()) {
    				
    				Dictionary.add(last+last.charAt(0));
    				originalData+=last+last.charAt(0);
    				last=last+last.charAt(0);
    				System.out.println("last is : " + last);
    			}
    			else
    			{
    				if(!Dictionary.contains(last+Dictionary.get(Integer.parseInt(code)))) {
        				Dictionary.add(last+Dictionary.get(Integer.parseInt(code)).charAt(0));
        				originalData+=Dictionary.get(Integer.parseInt(code));//hint
        				last=Dictionary.get(Integer.parseInt(code));
        			}
        			else {
        				last=last+Dictionary.get(Integer.parseInt(code));
        			}
    			}
    			
    		}
    	}
    	WriteToDecompressed.println(originalData);
    	ReadFromCompressed.close();
    	WriteToDecompressed.close();
    }
}
