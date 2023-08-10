import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

    private String rootPath;
    private String regex;
    private String outFile;
    private Charset charset = Charset.forName("UTF-8"); 

    @Override
    public void process() throws IOException {
        List<File> files = listFiles(new File(rootPath));
        List<String> matchedLines = new ArrayList<>();

        for (File file : files) {
            List<String> lines = readLines(file);
            for (String line : lines) {
                if (containsPattern(line)) {
                    matchedLines.add(line);
                }
            }
        }

        writeToFile(matchedLines);
    }

    @Override
    public List<File> listFiles(File rootDir) throws IllegalArgumentException {
        List<File> files = new ArrayList<>();
        if (!rootDir.isDirectory()) {
            throw new IllegalArgumentException("Input directory does not exist");
        }
        File[] fileList = rootDir.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    files.addAll(listFiles(file));
                }
            }
        }
        return files;
    }

    @Override
    public List<String> readLines(File inputFile) throws IllegalArgumentException, IOException {
        List<String> lines = new ArrayList<>();
        if (!inputFile.isFile()) {
            throw new IllegalArgumentException("Input file does not exist");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile, charset))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    @Override
    public boolean containsPattern(String line) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile, charset))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    @Override
    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
