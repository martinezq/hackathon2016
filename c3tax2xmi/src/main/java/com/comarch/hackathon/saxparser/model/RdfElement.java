package com.comarch.hackathon.saxparser.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author szlachtap
 */
public class RdfElement extends RdfBaseObject {
    List<RdfAttribute> attributes = new ArrayList<>();

    public List<RdfAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<RdfAttribute> attributes) {
        this.attributes = attributes;
    }
    
    
}
