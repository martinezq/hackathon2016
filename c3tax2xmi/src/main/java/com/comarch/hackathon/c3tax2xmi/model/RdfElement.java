package com.comarch.hackathon.c3tax2xmi.model;

import java.util.ArrayList;
import java.util.List;

import com.comarch.hackathon.c3tax2xmi.util.RdfUtils;

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

    public String getDatatype() {
    	for(RdfAttribute attr: attributes) {
    		if(attr.getName().equalsIgnoreCase("rdf:datatype")) {
    			return attr.getValue();
    		}
    	}
    	
    	return null;
    }
    
}
