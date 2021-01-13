package by.epam_training.homework03.main;

import by.epam_training.homework03.configuration.FileConfiguration;
import by.epam_training.homework03.entity.Element;
import by.epam_training.homework03.parser.FileParser;
import by.epam_training.homework03.reader.FileDataReader;

import java.util.List;

public class Main {

    public static void main(String[] args){
        FileConfiguration fileConfiguration = FileConfiguration.configurationFile("breakfast-menu.xml");
        FileDataReader fileDataReader = fileConfiguration.getFileDataReader();
        FileParser fileParser = fileConfiguration.getFileParser();

        List<String> file = fileDataReader.read();
        Element rootElement = fileParser.buildFileStructure(file);

        PrintInfoFile.printStructureOfFile(rootElement);

    }
}
