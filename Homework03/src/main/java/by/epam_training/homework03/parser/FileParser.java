package by.epam_training.homework03.parser;

import by.epam_training.homework03.entity.Element;

import java.util.List;

public interface FileParser {

    List<String> parseData(List<String> stringsXml);
    Element buildFileStructure(List<String> stringsOfFilteredXmlFile);
}
