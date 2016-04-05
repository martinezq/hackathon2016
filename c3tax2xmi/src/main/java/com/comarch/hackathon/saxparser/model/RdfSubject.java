package com.comarch.hackathon.saxparser.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dziubek
 */
public class RdfSubject extends RdfBaseObject {
    
    private List<RdfAttribute> attributes = new ArrayList<>();
    private List<RdfElement> elements = new ArrayList<>();

    public List<RdfAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<RdfAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<RdfElement> getElements() {
        return elements;
    }

    public void setElements(List<RdfElement> elements) {
        this.elements = elements;
    }

}
