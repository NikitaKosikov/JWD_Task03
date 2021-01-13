package by.epam_training.homework03.factory;

import by.epam_training.homework03.parser.FileParser;
import by.epam_training.homework03.reader.FileDataReader;

public interface FileAbstractFactory {
    FileParser getFileParser();
    FileDataReader getFileDataReader(String filePath);
}
