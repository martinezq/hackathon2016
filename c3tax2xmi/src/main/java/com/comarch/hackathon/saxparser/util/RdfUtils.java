package com.comarch.hackathon.saxparser.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.comarch.hackathon.saxparser.model.RdfAttribute;
import com.comarch.hackathon.saxparser.model.RdfElement;
import com.comarch.hackathon.saxparser.model.RdfSubject;

/**
 *
 * @author szlachtaps
 */
public class RdfUtils {
    
    public static String getElementValue(Collection<RdfElement> elements, String elemName) {
        RdfElement element = getElement(elements, elemName);
        if (element != null) {
            return element.getValue();
        }
        return null;
    }
    
    public static RdfElement getElement(Collection<RdfElement> elements, String elemName) {
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
}
