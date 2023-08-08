import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public interface JavaGrep {

  
    void process() throws IOException;

   
    List<File> listFiles(File rootDir) throws IllegalArgumentException;

   
    List<String> readLines(File inputFile) throws IllegalArgumentException, IOException;

  
    boolean containsPattern(String line);

 
    String getRootPath();

  
    void setRootPath(String rootPath);


    String getRegex();

 
    void setRegex(String regex);

    String getOutFile();

    
    void setOutFile(String outFile);

    
    Charset getCharset();

   
    void setCharset(Charset charset);

   
    void writeToFile(List<String> lines) throws IOException;

}
