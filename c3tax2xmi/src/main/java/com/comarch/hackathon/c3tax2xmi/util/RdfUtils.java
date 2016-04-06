package com.comarch.hackathon.c3tax2xmi.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.comarch.hackathon.c3tax2xmi.model.RdfAttribute;
import com.comarch.hackathon.c3tax2xmi.model.RdfElement;
import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;

/**
 *
 * @author szlachtaps
 */
public class RdfUtils {
    
    public static String getElementValue(Collection<RdfElement> elements, String elemName) {
        RdfElement element = getFirstElement(elements, elemName);
        if (element != null) {
            return element.getValue();
        }
        return null;
    }
    
    public static RdfElement getFirstElement(Collection<RdfElement> elements, String elemName) {
        Collection<RdfElement> elems = getElements(elements, elemName);
        if (elems != null && !elems.isEmpty()) {
            return elems.iterator().next();
        }
        return null;
    }
    
    public static Collection<RdfElement> getElements(Collection<RdfElement> elements, String elemName) {
        Collection<RdfElement> result = new ArrayList<>();
        if (elements != null && elemName != null) {
            for (RdfElement elem : elements) {
                if (elemName.equalsIgnoreCase(elem.getName())) {
                    result.add(elem);
                }
            }
        }
        return result;
    }
    
    public static Collection<RdfElement> getElementsContainsAttribute(Collection<RdfElement> elements, String attrName) {
        Collection<RdfElement> result = new ArrayList<>();
        if (elements != null && attrName != null) {
            for (RdfElement elem : elements) {
                if (getAttribute(elem.getAttributes(), attrName) != null) {
                    result.add(elem);
                }
            }
        }
        return result;
    }
    
    public static String getAttributeValue(Collection<RdfAttribute> attributes, String attrName) {
        RdfAttribute attribute = getAttribute(attributes, attrName);
        if (attribute != null) {
            return attribute.getValue();
        }
        return null;
    }
    
    public static RdfAttribute getAttribute(Collection<RdfAttribute> attributes, String attrName) {
        if (attributes != null && attrName != null) {
            for (RdfAttribute attr : attributes) {
                if (attrName.equalsIgnoreCase(attr.getName())) {
                    return attr;
                }
            }
        }
        return null;
    }
    
    public static Set<String> findUniqueElementNames(Collection<RdfSubject> subjects) {
    	Set<String> result = new HashSet<String>();
    	
    	for(RdfSubject subject: subjects) {
    		for(RdfElement element: subject.getElements()) {
    			result.add(element.getName());
    		}
    	}
    	
    	return result;
    }
    
    public static Map<String, String> findUniqueElementNamesAndDataTypes(Collection<RdfSubject> subjects) {
    	Map<String, String> result = new HashMap<String, String>();
    	
    	for(RdfSubject subject: subjects) {
    		for(RdfElement element: subject.getElements()) {
    			result.put(element.getName(), element.getDatatype());
    		}
    	}
    	
    	return result;
    }
    
    public static Set<String> findUniqueRdfTypes(Collection<RdfSubject> subjects) {
    	Set<String> result = new HashSet<String>();
    	
    	for(RdfSubject subject: subjects) {
    		for(RdfElement element: subject.getElements("rdf:type")) {
    			result.add(element.getAttribute("rdf:resource").getValue());
    		}
    	}
    	
    	return result;
    }
}
