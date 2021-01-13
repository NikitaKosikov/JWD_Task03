package by.epam_training.homework03.parser.impl;

import by.epam_training.homework03.parser.FileParser;
import by.epam_training.homework03.entity.Attribute;
import by.epam_training.homework03.entity.Element;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser implements FileParser {

    public static final String NAME_TAG_PATTERN;
    public static final String ATTRIBUTE_PATTERN;
    public static final String CONTENT_PATTERN;
    public static final String CLOSING_TAG_PATTERN;
    public static final String SPACE_SEPARATION_BETWEEN_ATTRIBUTE_NAME_AND_VALUE;
    public static final String EXIST_SPACE_IN_STRING;
    public static final String ADDING_SLASH_TO_NAME_TAG;
    public static final String EXIST_LESS_THAN_WITH_QUESTION_IN_DECLARATION;


    static {
        NAME_TAG_PATTERN = "<\\w+\\/*>|<[^/].+?>";
        ATTRIBUTE_PATTERN = "\\w+=\".+\"\\s*";
        CONTENT_PATTERN = ">\\s*\\w.*?<|>\\W*\\d.*?\\s*<";
        CLOSING_TAG_PATTERN = "</.+?>|<\\w+/>";
        EXIST_SPACE_IN_STRING = " ";
        SPACE_SEPARATION_BETWEEN_ATTRIBUTE_NAME_AND_VALUE = "=";
        ADDING_SLASH_TO_NAME_TAG = "/";
        EXIST_LESS_THAN_WITH_QUESTION_IN_DECLARATION = "<?";
    }

    @Override
    public Element buildFileStructure(List<String> stringsOfXmlFile) {

        List<String> stringsOfFilteredXmlFile = parseData(stringsOfXmlFile);
        List<Element> stackOfNodes = new LinkedList<>();
        String nameTag;
        String nameClosingTag;
        String content;
        List<Attribute> attributes;
        Element node;
        for (String stringXmlFile : stringsOfFilteredXmlFile){
            node = new Element();
            nameTag = searchNameTag(stringXmlFile);
            attributes = searchAttributes(stringXmlFile);
            content = searchContent(stringXmlFile);
            nameClosingTag = searchClosingTag(stringXmlFile);

            node.setName(nameTag);
            node.setAttributes(attributes);

            if (!content.isEmpty()){
                stackOfNodes.get(stackOfNodes.size()-1).setContent(content);
            }else if (nameClosingTag.isEmpty()){
                stackOfNodes.add(node);
            }

            String nameLastNode = stackOfNodes.get(stackOfNodes.size()-1).getName();
            if (!nameClosingTag.isEmpty()){
                if ((nameLastNode.charAt(0) + ADDING_SLASH_TO_NAME_TAG + nameLastNode.substring(1)).equals(nameClosingTag)
                        || nameLastNode.equals(nameClosingTag)){
                    if (stackOfNodes.size() == 1){
                        return stackOfNodes.remove(0);
                    }else{
                        node = stackOfNodes.remove(stackOfNodes.size()-1);
                        stackOfNodes.get(stackOfNodes.size()-1).add(node);
                    }
                } else {
                    stackOfNodes.add(node);
                }
            }
        }
        return stackOfNodes.remove(0);
    }

    @Override
    public List<String> parseData(List<String> stringsXml){

        Pattern patternName = Pattern.compile(NAME_TAG_PATTERN);
        Pattern patternContent = Pattern.compile(CONTENT_PATTERN);
        Pattern patternClosingTag = Pattern.compile(CLOSING_TAG_PATTERN);

        List<String> strings = new ArrayList<>();
        for (String string : stringsXml){
            Matcher matcherName = patternName.matcher(string);
            Matcher matcherContent = patternContent.matcher(string);
            Matcher matcherClosingTag = patternClosingTag.matcher(string);
            if (string.contains(EXIST_LESS_THAN_WITH_QUESTION_IN_DECLARATION)){
                continue;
            }
            while (matcherName.find()) {
                strings.add(matcherName.group());
            }
            while(matcherContent.find()){
                strings.add(matcherContent.group());
            }
            while (matcherClosingTag.find()){
                strings.add(matcherClosingTag.group());
            }
        }
        return strings;
    }

    public static String searchNameTag(String stringXmlFile){
        Pattern patternName = Pattern.compile(NAME_TAG_PATTERN);
        Matcher matcherName = patternName.matcher(stringXmlFile);
        String name = "";
        if (matcherName.find()){
            int indexWithSpace;
            if ((indexWithSpace = matcherName.group().indexOf(EXIST_SPACE_IN_STRING))!=-1){
                name = matcherName.group().substring(0, indexWithSpace);
            }else{
                name = matcherName.group().substring(0, matcherName.group().length()-1);
            }
                name+='>';
            }
            return name;
        }

    public static List<Attribute> searchAttributes(String stringXmlFile){

        Pattern patternAttributes = Pattern.compile(ATTRIBUTE_PATTERN);
        Matcher matcherAttributes = patternAttributes.matcher(stringXmlFile);

        List<Attribute> attributes = new ArrayList<>();
        Map<String, String> attribute = new HashMap<>();
        String nameAttribute;
        String valueAttribute;
        String[] filteredAttribute;

        while (matcherAttributes.find()){

            filteredAttribute = matcherAttributes.group().split(SPACE_SEPARATION_BETWEEN_ATTRIBUTE_NAME_AND_VALUE);
            nameAttribute = filteredAttribute[0];
            valueAttribute = filteredAttribute[1];
            attribute.put(nameAttribute, valueAttribute);
            attributes.add(new Attribute(attribute));
        }
        return attributes;
    }

    public static String searchContent(String stringXmlFile){
        Pattern patternContent = Pattern.compile(CONTENT_PATTERN);
        Matcher matcherContent = patternContent.matcher(stringXmlFile);

        String content;

        if (matcherContent.find()){
            content = matcherContent.group();
            content = content.substring(1,content.length()-1);
            return content;
        }
        return "";
    }

    public static String searchClosingTag(String stringXmlFile){
        Pattern patternEndTag = Pattern.compile(CLOSING_TAG_PATTERN);
        Matcher matcherEndTag = patternEndTag.matcher(stringXmlFile);
        return matcherEndTag.find() ? matcherEndTag.group() : "";
    }
}
