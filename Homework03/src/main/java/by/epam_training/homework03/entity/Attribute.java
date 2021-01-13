package by.epam_training.homework03.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Attribute {

    private Map<String, String> attribute = new HashMap<>();

    public Attribute(){}

    public Attribute(Map<String, String> attribute) {
        this.attribute = attribute;
    }

    public Map<String, String> getAttribute() {
        return attribute;
    }

    public void setAttribute(Map<String, String> attribute) {
        this.attribute = attribute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute1 = (Attribute) o;
        return Objects.equals(attribute, attribute1.attribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute);
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "attribute=" + attribute +
                '}';
    }
}
