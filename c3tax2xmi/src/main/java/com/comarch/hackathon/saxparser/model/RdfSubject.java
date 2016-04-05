package com.comarch.hackathon.saxparser.model;

import com.comarch.hackathon.saxparser.util.RdfUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author szlachtap
 */
public class RdfSubject extends RdfBaseObject {
    
    public static final String ATTR_ABOUT = "RDF:ABOUT";
    public static final String ELEM_UUID = "PROPERTY:UUID";
    public static final String ELEM_TYPE = "RDF:TYPE";
    public static final String ELEM_LABEL = "RDFS:LABEL";
    public static final String ELEM_DESCRIPTION = "PROPERTY:DESCRIPTION";
    public static final String ELEM_TITLE = "PROPERTY:TITLE";
    
    private List<RdfAttribute> attributes = new ArrayList<>();
    private List<RdfElement> elements = new ArrayList<>();
    
    private RdfSubject parent = null;
    private List<RdfSubject> childs = new ArrayList<>();
    private List<RdfSubject> references = new ArrayList<>();
            
    public String getId() {
        return getAttributeValue(ATTR_ABOUT);
    }
    
    public String getAbout() {
        return getAttributeValue(ATTR_ABOUT);
    }
    
    public String getUUID() {
        return getElementValue(ELEM_UUID);
    }
    
    public String getType() {
        return getElementValue(ELEM_LABEL);
    }
    
    public String getLabel() {
        return getElementValue(ELEM_LABEL);
    }
    
    public String getDescription() {
        return getElementValue(ELEM_DESCRIPTION);
    }
    
    public String getTitle() {
        return getElementValue(ELEM_TITLE);
    }
    
    public String getAttributeValue(String attrName) {
        return RdfUtils.getAttributeValue(attributes, attrName);
    }

    public List<RdfAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<RdfAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getElementValue(String elemName) {
        return RdfUtils.getElementValue(elements, elemName);
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

    public List<RdfSubject> getChilds() {
        return childs;
    }

    public void setChilds(List<RdfSubject> childs) {
        this.childs = childs;
    }

    public List<RdfSubject> getReferences() {
        return references;
    }

    public void setReferences(List<RdfSubject> references) {
        this.references = references;
    }

}
