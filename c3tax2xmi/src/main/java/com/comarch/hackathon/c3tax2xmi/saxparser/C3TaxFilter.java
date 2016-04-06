package com.comarch.hackathon.c3tax2xmi.saxparser;

import com.comarch.hackathon.c3tax2xmi.model.RdfAttribute;
import com.comarch.hackathon.c3tax2xmi.model.RdfElement;
import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author Szlachtap
 */
public class C3TaxFilter {
    
    //private static final Collection<String> DISABLED_INPUT_SUBJECT_TYPES = Arrays.asList(
    //        new String[]{"http://192.168.1.25/em/index.php/Special:URIResolver/Category-3AServices"});
    private static final Collection<String> DISABLED_INPUT_ELEMENTS = Arrays.asList(
            new String[]{"PROPERTY:HAS_QUERY"});
    
    public boolean filterInputSubject(RdfSubject subject) {
        if (subject != null) {
            // Nothing to do for now
            //if (DISABLED_INPUT_SUBJECT_TYPES != null && subject.getTypes() != null) {
            //    for (String type : subject.getTypes()) {
            //        if (containsIgonoreCase(DISABLED_INPUT_SUBJECT_TYPES, type)) {
            //            return false;
            //        }
            //    }
            //}
        }
        return true;
    }
    
    public boolean filterInputAttribute(RdfAttribute attribute) {
        if (attribute != null) {
            // Nothing to do for now
        }
        return true;
    }
    
    public boolean filterInputElement(RdfElement element) {
        if (element != null) {
            if (DISABLED_INPUT_ELEMENTS != null && element.getName() != null && 
                    containsIgonoreCase(DISABLED_INPUT_ELEMENTS, element.getName())) {
                return false;
            }
        }
        return true;
    }
    
    private boolean containsIgonoreCase(Collection<String> collection, String value) {
        if (collection != null) {
            for (String s : collection) {
                if (s.equalsIgnoreCase(value)) {
                    return true;
                }
            }
        }
        return false;
    }
    
}
