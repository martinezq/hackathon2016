package com.comarch.hackathon.saxparser;

import com.comarch.hackathon.saxparser.model.RdfSubject;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class C3TaxParser {
    
    private C3TaxHandler handler;
    private boolean filterInput = true;
    private boolean fillReferences = true;

    public C3TaxParser() {
        this(true, true);
    }
     
    public C3TaxParser(boolean filterInput, boolean fillReferences) {
        this.filterInput = filterInput;
        this.fillReferences = fillReferences;
    }
    
    public void parse(File file) throws SAXException, IOException, ParserConfigurationException {
        System.setProperty("jdk.xml.entityExpansionLimit", "0");

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        //saxParser.setProperty("jdk.xml.entityExpansionLimit", "0");

        long t = System.currentTimeMillis();
        System.out.println("START PARSING, FILE: " + file.getAbsolutePath());
        
        handler = new C3TaxHandler(filterInput);
        saxParser.parse(file, handler);
        
        System.out.println("END PARSING, TIME: " + (System.currentTimeMillis() - t) + "[ms]");
        
        if (fillReferences) {
            t = System.currentTimeMillis();
            System.out.println("START FILLING REFERENCES");
            handler.fillReferences();
            System.out.println("END FILLING REFERENCES, TIME: " + (System.currentTimeMillis() - t) + "[ms]");
            System.out.println();
        }
        
        System.out.println("TOTAL SUBJECTS: " + handler.getSubjects().size());
    }
    
    public Collection<RdfSubject> getParsedElements() {
        return handler.getSubjects();
    }
    
    public RdfSubject getRootElement() {
        return handler.getRootElement();
    }

}
