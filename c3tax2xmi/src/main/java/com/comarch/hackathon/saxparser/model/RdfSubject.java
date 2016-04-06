package com.comarch.hackathon.saxparser.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comarch.hackathon.saxparser.util.RdfUtils;

/**
 *
 * @author szlachtap
 */
public class RdfSubject extends RdfBaseObject {
    
    public static final String ATTR_ABOUT = "RDF:ABOUT";
    public static final String ATTR_RESOURCE = "rdf:resource";
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
    
    private Map<String, List<RdfSubject>> referecesByName = new HashMap<String, List<RdfSubject>>();
            
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
        RdfElement element = RdfUtils.getElement(elements, ELEM_TYPE);
        if (element != null) {
        	return RdfUtils.getAttributeValue(element.getAttributes(), ATTR_RESOURCE);
        }
        return null;
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
    
    public String buildName() {
    	String name = getTitle();
    	if (name == null) {
    		name = getLabel();
    	}
    	if (name == null) {
    		name = getName();
    	}
    	return name;
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
    
    public Collection<RdfElement> getElements(String elemName) {
        return RdfUtils.getElements(elements, elemName);
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

    public List<RdfSubject> getChildren() {
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
    
    public List<RdfSubject> getReferecesByName(String name) {
		return referecesByName.get(name);
	}

}
