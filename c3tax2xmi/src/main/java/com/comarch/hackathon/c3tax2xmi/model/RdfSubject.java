package com.comarch.hackathon.c3tax2xmi.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.comarch.hackathon.c3tax2xmi.util.RdfUtils;

/**
 *
 * @author szlachtap
 */
public class RdfSubject extends RdfBaseObject {
    
    public static final String ATTR_ABOUT = "RDF:ABOUT";
    public static final String ATTR_RESOURCE = "RDF:RESOURCE";
    public static final String ELEM_UUID = "PROPERTY:UUID";
    public static final String ELEM_TYPE = "RDF:TYPE";
    public static final String ELEM_LABEL = "RDFS:LABEL";
    public static final String ELEM_DESCRIPTION = "PROPERTY:DESCRIPTION";
    public static final String ELEM_TITLE = "PROPERTY:TITLE";
    public static final String ELEM_CHILD_OF = "PROPERTY:IS_CHILD_OF";
    
    private List<RdfAttribute> attributes = new ArrayList<>();
    private List<RdfElement> elements = new ArrayList<>();
    
    private final List<RdfSubject> childs = new ArrayList<>();
    private final Map<String, List<RdfSubject>> referencesByName = new HashMap<>();
            
    public String getId() {
        return getAttributeValue(ATTR_ABOUT);
    }
    
    public String getAbout() {
        return getAttributeValue(ATTR_ABOUT);
    }
    
    public String getUUID() {
        return getElementValue(ELEM_UUID);
    }
    
    public String getFirstType() {
        RdfElement element = getFirstElement(ELEM_TYPE);
        if (element != null) {
        	return RdfUtils.getAttributeValue(element.getAttributes(), ATTR_RESOURCE);
        }
        return null;
    }
    
    public Collection<String> getTypes() {
        Collection<RdfElement> collection = RdfUtils.getElements(elements, ELEM_TYPE);
        Collection<String> types = new ArrayList<>();
        if (collection != null) {
        	for (RdfElement element : collection) {
        		String resource = RdfUtils.getAttributeValue(element.getAttributes(), ATTR_RESOURCE);
        		if (resource != null) {
        			types.add(resource);
        		}
        	}
        }
        return types;
    }
    
    public boolean hasType(String type) {
    	Collection<String> types = getTypes();
    	for (String check : types) {
    		if (check.endsWith(type)) {
    			return true;
    		}
    	}
    	return false;
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
    
    public String getExportName() {
    	String name = getTitle();
    	if (name == null) {
    		name = getLabel();
    	}
    	if (name == null) {
    		name = getName();
    	}
    	return name;
    }
    
    public RdfAttribute getAttribute(String attrName) {
        return RdfUtils.getAttribute(attributes, attrName);
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

    public RdfElement getFirstElement(String elemName) {
        return RdfUtils.getFirstElement(elements, elemName);
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

    public List<RdfSubject> getChildren() {
        return childs;
    }

    public void addChildren(RdfSubject child) {
        if (child != null) {
            childs.add(child);
        }
    }
    
    public RdfSubject getParent() {
        List<RdfSubject> childOfRefs = getReferecesByName(ELEM_CHILD_OF);
        if (childOfRefs != null && childOfRefs.size() == 1) {
            return childOfRefs.iterator().next();
        }
        return null;
    }
    
    public List<RdfSubject> getReferecesByName(String name) {
        if (name != null) {
            String refName = name.toUpperCase();
            return referencesByName.get(refName);
        }
        return null;
    }
    
    public void addRefereceByName(String name, RdfSubject reference) {
        if (name != null && reference != null) {
            String refName = name.toUpperCase();
            if (referencesByName.get(refName) == null) {
                List<RdfSubject> refs = new ArrayList<>();
                refs.add(reference);
                referencesByName.put(refName, refs);
            } else {
                referencesByName.get(refName).add(reference);
            }
        }
    }
    
    public String getExportId() {
    	return getAbout();
    }

	public Set<String> getAllReferencesNames() {
		return referencesByName.keySet();
	}

	public boolean hasAnyType(Collection<String> legalTypes) {
		for (String legalType: legalTypes) {
			if (hasType(legalType)) {
				return true;
			}
		}
		return false;
	}
}
