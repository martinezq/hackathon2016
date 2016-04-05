package com.comarch.hackathon.saxparser;

import com.comarch.hackathon.saxparser.model.RdfAttribute;
import com.comarch.hackathon.saxparser.model.RdfElement;
import com.comarch.hackathon.saxparser.model.RdfSubject;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class C3TaxHandler extends DefaultHandler {

    private RdfSubject currentSubject = null;
    private RdfElement currentElement = null;
    private List<RdfSubject> subjects;
    
    public C3TaxHandler(List<RdfSubject> subjects) {
        this.subjects = subjects;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("SWIVT:SUBJECT")) {
            currentSubject = new RdfSubject();
            currentSubject.setName(qName);
            currentSubject.setValue(null);
            
            List<RdfAttribute> attrs = handleAttributes(attributes);
            if (attrs != null) {
                currentSubject.getAttributes().addAll(attrs);
            }
            
            subjects.add(currentSubject);
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
                
                //currentSubject.getElements().add(currentElement);
            }
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("SWIVT:SUBJECT")) {
            currentSubject = null;
            //logSubjects();
        } else {
            currentElement = null;
        }
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        if (currentElement != null) {
            String value = new String(ch, start, length);
            currentElement.setValue(value);
        }
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
    
    private void logSubjects() {
        if (subjects.size() % 100 == 0) {
            System.out.println("SUBJECT NR: " + subjects.size());
            RdfSubject sub = subjects.get(subjects.size() - 1);
            if (sub.getAttributes() != null && !sub.getAttributes().isEmpty()) {
                System.out.println("SUBJECT : "
                        + sub.getAttributes().get(sub.getAttributes().size() - 1).getValue());
            } else {
                System.out.println("SUBJECT : NULL ATTRS");
            }
        }
    }

};

