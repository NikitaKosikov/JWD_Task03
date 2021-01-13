package by.epam_training.homework03.reader.impl;


import by.epam_training.homework03.reader.FileDataReader;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class XmlReader implements FileDataReader {

    public static final char SLASH_IN_NAME_TAG;
    public static final char CLOSING_TAG;
    public static final char OPENING_TAG;
    public static final String EXIST_LESS_THAN_WITH_SLASH_IN_STRING;

    private String filePath;

    static {
        SLASH_IN_NAME_TAG = '/';
        CLOSING_TAG = '>';
        OPENING_TAG = '<';
        EXIST_LESS_THAN_WITH_SLASH_IN_STRING = "</";
    }

    public XmlReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<String> read(){

        List<String> listStringOfXml = new ArrayList<>();
        StringBuilder stringOfXml = new StringBuilder();
        InputStream fileInputStream = null;
        StringBuilder content = new StringBuilder();
        try {
            fileInputStream = new BufferedInputStream(
                    new FileInputStream(filePath));
            int charXml;

            /* This algorithm for this example
            <name>
            content
            <name>
            */
            while ((charXml = fileInputStream.read())!=-1){
                if ((char) charXml == '\r') {
                    if (stringOfXml.toString().contains(EXIST_LESS_THAN_WITH_SLASH_IN_STRING)) {
                        listStringOfXml.add(stringOfXml.toString().trim());
                        stringOfXml = new StringBuilder();
                        continue;
                    }
                    while ((charXml = fileInputStream.read()) != -1) {
                        if ((char) charXml == OPENING_TAG) {
                            stringOfXml.append(content.toString().trim());
                            content = new StringBuilder();
                            if ((charXml = fileInputStream.read()) != -1) {
                                if ((char) charXml == SLASH_IN_NAME_TAG) {
                                    stringOfXml.append(OPENING_TAG);
                                    stringOfXml.append((char) charXml);
                                    while ((charXml = fileInputStream.read()) != -1) {
                                        if ((char) charXml == '\r'){
                                            break;
                                        }else if ((char) charXml == CLOSING_TAG) {
                                            stringOfXml.append((char) charXml);
                                            listStringOfXml.add(stringOfXml.toString().trim());
                                            stringOfXml = new StringBuilder();
                                        }else {
                                            stringOfXml.append((char) charXml);
                                        }
                                    }
                                } else {
                                    listStringOfXml.add(stringOfXml.toString().trim());
                                    stringOfXml = new StringBuilder();
                                    stringOfXml.append(OPENING_TAG);
                                    stringOfXml.append((char) charXml);
                                }
                            }
                            break;
                        } else {
                            if ((char) charXml != '\n' && (char) charXml!='\r') {
                                content.append((char) charXml);
                            }
                        }
                    }
                }
                else {
                    if ((char) charXml != '\n'&&(char) charXml!='\r') {
                        stringOfXml.append((char) charXml);
                    }
                }

            }
        }catch (IOException exception) {
            exception.printStackTrace();
        }finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return listStringOfXml;
    }
}
