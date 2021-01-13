package by.epam_training.homework03.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Element {

    private String name;
    private List<Attribute> attributes;
    private List<Element> childNodes = new ArrayList<>();
    private String content;


    public Element(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Element> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<Element> childNodes) {
        this.childNodes = childNodes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void add(Element node){
        childNodes.add(node);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element node = (Element) o;
        return Objects.equals(name, node.name) &&
                Objects.equals(attributes, node.attributes) &&
                Objects.equals(childNodes, node.childNodes) &&
                Objects.equals(content, node.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, attributes, childNodes, content);
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", attributes=" + attributes +
                ", \nchildNodes=" + childNodes +
                ", content='" + content + '\'' +
                '}';
    }
}
