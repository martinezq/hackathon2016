package com.comarch.hackathon.saxparser.model;

import java.util.ArrayList;
import java.util.List;

import com.comarch.hackathon.saxparser.util.RdfUtils;

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
    
    public RdfAttribute getAttribute(String attrName) {
    	return RdfUtils.getAttribute(attributes, attrName);
    }
    
    
}
