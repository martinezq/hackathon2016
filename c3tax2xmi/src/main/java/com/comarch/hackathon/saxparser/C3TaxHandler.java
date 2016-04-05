package com.comarch.hackathon.saxparser;

import com.comarch.hackathon.saxparser.model.RdfAttribute;
import com.comarch.hackathon.saxparser.model.RdfElement;
import com.comarch.hackathon.saxparser.model.RdfSubject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class C3TaxHandler extends DefaultHandler {

    public static final String ID_TAG = "RDF:ABOUT";
    public static final String SUB_TAG = "SWIVT:SUBJECT";
    public static final String REF_TO_TAG = "PROPERTY:IS_REFERRING_TO";
    public static final String CHILD_OF_TAG = "PROPERTY:IS_CHILD_OF";
    public static final String REF_RES_TAG = "RDF:RESOURCE";
    
    private RdfSubject currentSubject = null;
    private RdfElement currentElement = null;
    //private final List<RdfSubject> subjects = new ArrayList<>();
    private final Map<String,RdfSubject> subjects = new LinkedHashMap<>();

    public Collection<RdfSubject> getSubjects() {
        return subjects.values();
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase(SUB_TAG)) {
            currentSubject = new RdfSubject();
            currentSubject.setId(handleId(attributes));
            currentSubject.setName(qName);
            currentSubject.setValue(null);
            
            List<RdfAttribute> attrs = handleAttributes(attributes);
            if (attrs != null) {
                currentSubject.getAttributes().addAll(attrs);
            }
            
            if (currentSubject.getId() != null) {
                subjects.put(currentSubject.getId(), currentSubject);
            }
        } else {
            // Add elements to subject
            if (currentSubject != null) {
                currentElement = new RdfElement();
                currentElement.setName(qName);
                currentElement.setValue(null);
                
                List<RdfAttribute> attrs = handleAttributes(attributes);
                if (attrs != null) {
                    currentElement.getAttributes().addAll(attrs);
                }
                
                currentSubject.getElements().add(currentElement);
            }
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase(SUB_TAG)) {
            endSubjectLog();
            currentSubject = null;
        } else {
            endElementLog();
            currentElement = null;
        }
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        if (currentElement != null) {
            String value = new String(ch, start, length);
            currentElement.setValue(value);
        }
    }
    
    private String handleId(Attributes attributes) {
        if (attributes != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                String name = attributes.getQName(i);
                if (ID_TAG.equalsIgnoreCase(name)) {
                    return attributes.getValue(i);
                }
            }
        }
        return null;
    }
    
    private List<RdfAttribute> handleAttributes(Attributes attributes) {
        List<RdfAttribute> result = new ArrayList<>();
        if (attributes != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                RdfAttribute attr = new RdfAttribute();
                attr.setName(attributes.getQName(i));
                attr.setValue(attributes.getValue(i));

                result.add(attr);
            }
        }
        return result;
    }
    
    private void endSubjectLog() {
        if (currentSubject != null) {
            boolean log = false;
            if (subjects.size() % 100 == 0) {
                log = true;
            }
            if (log) {
                System.out.println("SUBJECT NR: " + subjects.size());
                System.out.println("SUBJECT ID: " + currentSubject.getId());
            }
        }
    }
    
    private void endElementLog() {
        if (currentElement != null) {
            // Nothing to do
        }
    }
    
    public void fillReferences() {
        if (subjects != null) {
            for (RdfSubject subject : subjects.values()) {
                List<RdfElement> elements = subject.getElements();
                if (elements != null) {
                    for (RdfElement element : elements) {
                        if (CHILD_OF_TAG.equalsIgnoreCase(element.getName())) {
                            RdfSubject parent = findReference(element);
                            subject.setParent(parent);
                        } else if (REF_TO_TAG.equalsIgnoreCase(element.getName())) {
                            RdfSubject ref = findReference(element);
                            subject.getReferences().add(ref);
                        }
                    }
                }
            }
        }
    }
    
    private RdfSubject findReference(RdfElement refElement) {
        if (subjects != null) {
            String id = null;
            if (refElement.getAttributes() != null) {
                for (RdfAttribute attr : refElement.getAttributes()) {
                    if (REF_RES_TAG.equalsIgnoreCase(attr.getName())) {
                        id = attr.getValue();
                        break;
                    }
                }
            }
            
            if (id != null) {
                return subjects.get(id);
            }
        }
        return null;
    }

};

