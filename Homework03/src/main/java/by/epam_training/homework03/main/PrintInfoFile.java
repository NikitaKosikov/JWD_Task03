package by.epam_training.homework03.main;

import by.epam_training.homework03.entity.Element;

public class PrintInfoFile {

    public static void printStructureOfFile(Element element) {
        for (Element node : element.getChildNodes()) {
            if (!node.getChildNodes().isEmpty()){
                printStructureOfFile(node);
            }else{
                System.out.print(node.getContent() + '-');
            }
        }
        System.out.println();
    }
}
