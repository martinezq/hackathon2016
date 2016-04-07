package com.comarch.hackathon.c3tax2xmi.model;

/**
 *
 * @author szlachtap
 */
public abstract class RdfBaseObject {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append('"').append(name).append("\" = \"").append(value).append('"');
    	return sb.toString();
    }
    
}
