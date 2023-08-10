import org.apache.log4j.BasicConfigurator;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class JavaGrepLambdaImp extends JavaGrepImp {

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

       
        BasicConfigurator.configure();

        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try {
            javaGrepLambdaImp.process();
        } catch (Exception e) {
            javaGrepLambdaImp.logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void process() throws IOException {
        List<File> files = listFiles(this.rootPath);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.outFile))) {
            files.stream()
                .map(this::readLines)
                .flatMap(Collection::stream)
                .filter(this::containsPattern)
                .forEach(line -> {
                    try {
                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    } catch (IOException e) {
                        logger.error("Error writing line to file", e);
                    }
                });
        }
    }

    @Override
    public List<File> listFiles(String rootDir) {
        File currentDir = new File(rootDir);
        File[] directoryFiles = currentDir.listFiles();

        if (directoryFiles == null) {
            return Collections.singletonList(currentDir);
        }

        return Arrays.stream(directoryFiles)
            .map(file -> listFiles(file.getAbsolutePath()))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    @Override
    public List<String> readLines(File inputFile) throws IllegalArgumentException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            return reader.lines().collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Given file does not exist");
        } catch (IOException e) {
            throw new IllegalArgumentException("Given file cannot be read");
        }
    }
}
