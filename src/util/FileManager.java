package util;
import java.io.*;

public class FileManager {
    
    //Read File
    public static String readFile(File file) throws IOException{
        StringBuilder content=new StringBuilder();
        BufferedReader reader=new BufferedReader(new FileReader(file));
        String line;
        while((line=reader.readLine())!=null)
        {
            content.append(line).append("\n");
        }
        reader.close();
        return content.toString();
    }

    //Write File
    public static void writeFile(File file,String content) throws IOException{
        BufferedWriter writer=new BufferedWriter(new FileWriter(file));
        writer.write(content);
        writer.close();
    }

    
}
