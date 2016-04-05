package com.comarch.hackathon.saxparser;

import com.comarch.hackathon.saxparser.model.RdfAttribute;
import com.comarch.hackathon.saxparser.model.RdfElement;
import com.comarch.hackathon.saxparser.model.RdfSubject;
import com.comarch.hackathon.saxparser.util.RdfUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class C3TaxHandler extends DefaultHandler {

    public static final String ROOT = "http://192.168.1.25/em/index.php/Special:URIResolver/C3_Taxonomy";
    public static final String SUB_TAG = "SWIVT:SUBJECT";
    public static final String REF_TO_TAG = "PROPERTY:IS_REFERRING_TO";
    public static final String CHILD_OF_TAG = "PROPERTY:IS_CHILD_OF";
    public static final String REF_RES_TAG = "RDF:RESOURCE";
    public static final List<String> WRONG_INPUT_TAGS = Arrays.asList(
            new String[]{"PROPERTY:HAS_QUERY"});
    
    private RdfSubject currentSubject = null;
    private RdfElement currentElement = null;
    //private final List<RdfSubject> subjects = new ArrayList<>();
    private final Map<String,RdfSubject> subjects = new LinkedHashMap<>();
    
    private boolean filterInput = false;
    private boolean loggingEnabled = false;

    public C3TaxHandler(boolean filterInput) {
        this.filterInput = filterInput;
    }
            
    public Collection<RdfSubject> getSubjects() {
        if (subjects != null) {
            return subjects.values();
        }
        return null;
    }
    
    public RdfSubject getRootElement() {
        //int count = 0;
        RdfSubject result = null;
        if (subjects != null) {
            for (RdfSubject subject : subjects.values()) {
                if (ROOT.equalsIgnoreCase(subject.getId())) {
                    result = subject;
                    break;
                }
                //if (subject.getParent() == null) {
                //    result = subject;
                //    count ++;
                //}
            }
        }
        return result;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase(SUB_TAG)) {
            currentSubject = new RdfSubject();
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
                if (filterInput(qName)) {
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
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase(SUB_TAG)) {
            endSubjectLog();
            currentSubject = null;
        } else {
            endElementLog();
            currentElement = null;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (currentSubject != null) {
            if (currentElement != null) {
                String value = new String(ch, start, length);
                currentElement.setValue(value);
            } else {
                // Nothing to do
            }
        }
    }
    
    private void endSubjectLog() {
        if (loggingEnabled) {
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
    }
    
    private void endElementLog() {
        if (loggingEnabled) {
            if (currentElement != null) {
                // Nothing to do
            }
        }
    }
    
    private List<RdfAttribute> handleAttributes(Attributes attributes) {
        List<RdfAttribute> result = new ArrayList<>();
        if (attributes != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                if (filterInput(attributes.getQName(i))) {
                    RdfAttribute attr = new RdfAttribute();
                    attr.setName(attributes.getQName(i));
                    attr.setValue(attributes.getValue(i));

                    result.add(attr);
                }
            }
        }
        return result;
    }
    
    private boolean filterInput(String attrOrElemName) {
        if (filterInput && attrOrElemName != null) {
            if (WRONG_INPUT_TAGS != null && WRONG_INPUT_TAGS.contains(attrOrElemName.toUpperCase())) {
                return false;
            }
        }
        return true;
    }
    
    public void fillReferences() {
        if (subjects != null) {
            for (RdfSubject subject : subjects.values()) {
                
                RdfElement childOfElement = RdfUtils.getElement(subject.getElements(), CHILD_OF_TAG);
                if (childOfElement != null) {
                    RdfSubject parent = findReference(childOfElement);
                    if (parent != null) {
                        subject.setParent(parent);
                        parent.getChilds().add(subject);
                    }
                }
                
                Collection<RdfElement> refToElements = RdfUtils.getElements(subject.getElements(), REF_TO_TAG);
                if (refToElements != null) {
                    for (RdfElement element : refToElements) {
                        RdfSubject ref = findReference(element);
                        if (ref != null ) {
                            subject.getReferences().add(ref);
                        }
                    }
                }
            }
        }
    }
    
    private RdfSubject findReference(RdfElement refElement) {
        if (subjects != null) {
            String id = RdfUtils.getAttributeValue(refElement.getAttributes(), REF_RES_TAG);
            if (id != null) {
                RdfSubject refSubject = subjects.get(id);
                if (refSubject == null) {
                    //System.out.println("REFERENCE: " + id + " NOT FOUND");
                }
                return refSubject;
            }
        }
        return null;
    }

};

