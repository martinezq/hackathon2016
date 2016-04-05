package com.comarch.hackathon.saxparser.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dziubek
 */
public class RdfSubject extends RdfBaseObject {
    
    private String id = null;
    private List<RdfAttribute> attributes = new ArrayList<>();
    private List<RdfElement> elements = new ArrayList<>();
    
    private RdfSubject parent = null;
    private List<RdfSubject> references = new ArrayList<>();
            
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public RdfSubject getParent() {
        return parent;
    }

    public void setParent(RdfSubject parent) {
        this.parent = parent;
    }

    public List<RdfSubject> getReferences() {
        return references;
    }

    public void setReferences(List<RdfSubject> references) {
        this.references = references;
    }

}
