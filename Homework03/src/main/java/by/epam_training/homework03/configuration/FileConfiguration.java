package by.epam_training.homework03.configuration;

import by.epam_training.homework03.factory.FileAbstractFactory;
import by.epam_training.homework03.factory.factories.XmlFactory;
import by.epam_training.homework03.parser.FileParser;
import by.epam_training.homework03.reader.FileDataReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileConfiguration {

    private  FileDataReader fileDataReader;
    private  FileParser fileParser;
    private  String filePath;

    public FileConfiguration(FileAbstractFactory factory, String fileName) {
        filePath = getClass().getResource("/" + fileName).getPath();
        fileDataReader = factory.getFileDataReader(filePath);
        fileParser = factory.getFileParser();
    }

    public FileDataReader getFileDataReader() {
        return fileDataReader;
    }

    public void setFileDataReader(FileDataReader fileDataReader) {
        this.fileDataReader = fileDataReader;
    }

    public FileParser getFileParser() {
        return fileParser;
    }

    public void setFileParser(FileParser fileParser) {
        this.fileParser = fileParser;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public static FileConfiguration configurationFile(String fileName){
        String TYPE_OF_FILE = "\\..+";
        FileAbstractFactory factory;
        FileConfiguration fileApplication = null;

        Pattern typeOfFilePattern = Pattern.compile(TYPE_OF_FILE);
        Matcher typeOfFileMatcher = typeOfFilePattern.matcher(fileName);
        if (typeOfFileMatcher.find()){
            if (".xml".equals(typeOfFileMatcher.group())){
                factory = new XmlFactory();
                fileApplication = new FileConfiguration(factory, fileName);
            }
        }
        return fileApplication;
    }
}
